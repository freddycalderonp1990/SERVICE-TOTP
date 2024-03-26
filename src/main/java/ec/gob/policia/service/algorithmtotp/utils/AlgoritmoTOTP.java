package ec.gob.policia.service.algorithmtotp.utils;

import ec.gob.policia.service.algorithmtotp.dtos.CodeRequestDto;
import ec.gob.policia.service.algorithmtotp.dtos.CodeValidateRequestDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AlgoritmoTOTP {
    // ******************* CONFIGURACIONES *******************************
    private static String _formatTime = "yyyy-MM-dd HH:mm:ss";
    private static int _change_key_every = 30; //seconds cada cuanto cambia la clave
    private int _hash_len = 128; //longitud del hast 128 caracteres
    private static int _pin_len = 6; //longitud del pin
    private static int _default_rounds =
            2; //rondas por defecto, cuantas veces vamos a utilizar el hmac_
    private static String _key_security =
            "09c17b2c82df67cce61baaf10dadbcd8225103893c2497a2e345695b0c1548a781e538b16e4d7f4e1e47ed526e26724e7b4b0161879af03c30df04b3deae8e95"; // Codigo unico que se define para seguridad al generar el codigo
    // ******************* END  CONFIGURACIONES *******************************



   public static  String getCode(CodeRequestDto requestDto){

       String keyGenerateCode=requestDto.getPassword();
       if(requestDto.getPassword()!=null){
           keyGenerateCode=keyGenerateCode+requestDto.getPassword();
       }


        //la clave es el nombre de la app
        String  clave = Encrypt.sha512(keyGenerateCode);

        System.out.print("\nkeyGenerateCode = ");
        System.out.print(keyGenerateCode);
        System.out.print("\nclave = ");
        System.out.print(clave);

        String fecha = Date.getFechaHora(_formatTime);


       // fecha = "2024-02-14 10:07:03";

        System.out.print("\n FECHA ACTUAL = ");
        System.out.print(fecha);


        Long unixtime = Date.getUnixTime(fecha,_formatTime);
        System.out.print("\n FECHA UNIX-TIME = ");
        System.out.print(unixtime);

        int unixtimeFloor = Date.setUnixtimeDuration(unixtime,_change_key_every);
        System.out.print("\n unixtimeFloor = ");
        System.out.print(unixtimeFloor);

        String passwordHash = getHashKey(clave, unixtimeFloor);
        System.out.print("\n passwordHash = ");
        System.out.print(passwordHash);



        String codigo = getCodeKey(passwordHash);

        System.out.print("| Clave= ${clave} | sha512 = ${passwordHash} | code= ${codigo}");

        return codigo;
    }

    //se encarga de crear el hash , a partir del pass - clave , y el tiempo unixtime
    static String getHashKey(String pass, int unixtimeFloor) {
        String password = pass + String.valueOf(unixtimeFloor) + _key_security;

        System.out.print("\ngetHashKey-passwordpassword = ");
        System.out.print(password);

        //Se define cuantas veces se encripta el codigo
        //para darle mayor seguridad

        for (int i = 0; i < _default_rounds; i++) {
            password = Encrypt.sha512(password);
            System.out.print("\n password = ");
            System.out.print(password);
        }

        return password;
    }



    //Convierte en arreglo el hash
    private static List<String> _getArrayHash(String has) {

        List<String> res = new ArrayList<>();
        for (int i = 0; i < has.length(); i++) {
            String s = String.valueOf(has.charAt(i));


            res.add(s);
        }
        return res;
    }

    private static int getCodeAscci(String letra) {
        //Obtenemos el codigo ASCII¿
        byte[] bytes = letra.getBytes(StandardCharsets.US_ASCII);
        int o_b = bytes[0];
        return o_b;
    }



    public static String reducirHash(String acumulator, String letra) {



        int o_b = getCodeAscci(letra);
        //Se captura el año
        //le agregamos variedad al codigo para que no coincidan con el pasar de los años
        int anio = Integer.parseInt(DateTimeFormatter.ofPattern("yyyy")
                .format(LocalDateTime.now()));


        anio = anio + 457;



        double mult_divisor = Double.valueOf(anio) / _pin_len;

        double multiplier = 1 + o_b / mult_divisor;
        double resultado = 0;




        try {
            int valorLetra = Integer.parseInt(letra);
            ;
            if (valorLetra > 1) {
                resultado = valorLetra * multiplier;

                return String.valueOf(resultado);
            }
        } catch (Exception e) {
            //Obtenemos el codigo ASCII

            int o_a = getCodeAscci(letra);

            if (o_a > 10) {
                resultado = o_a * multiplier;

                return String.valueOf(resultado);
            }
        }

        resultado = getCodeAscci(letra) * multiplier;

        return String.valueOf(resultado);
    }





    //Obtiene el codigo
    private static String getCodeKey(String hash) {
        List<String> res = _getArrayHash(hash);


        AtomicReference<String> codeReduce2 = new AtomicReference<>("");
        String codeReduce = "";


        res.stream().reduce((acumulator, letra) -> {
            codeReduce2.set(reducirHash(acumulator, letra));
            return String.valueOf(codeReduce2);
        }).ifPresent(System.out::println);


        codeReduce = String.valueOf(codeReduce2);
        System.out.print("\n codeReduce = "+codeReduce);

        //Limitamos para que que la cadena solo tenga 10 numeros el codigo
        codeReduce = codeReduce.substring(0, 11);
        System.out.print("\n codeReduce = "+codeReduce);

        //Ahora se revierte el codigo

        String reverseCode = _getReverseCode(codeReduce);
        System.out.print("\n reverseCode = "+reverseCode);

        //Le indicamos la longitud de digitos del codigo
        reverseCode = reverseCode.substring(0, _pin_len);

        return reverseCode;



    }


    // Se encarga de revertir el codigo
//Ejemplo 12345  = 54312
//Pora obtener codigos mas variables ya que un valor que se encuantra a la derecha es mas probable que cambie
//obteneiendo de esta manera codigos variables para el usuario
    private static  String _getReverseCode(String code) {


        String  nstr="";
        char ch;
        for (int i=0; i<code.length(); i++)
        {
            ch= code.charAt(i); //extracts each character
            nstr= ch+nstr; //adds each character in front of the existing string
        }

        return nstr;
    }



    private static boolean validacionCode(Long unixtime, String codigo,String clave)
    {
        //se realiza el redondeo del valor y dividiendo la duracion
        int  unixtimeFloor =Date.setUnixtimeDuration(unixtime,_change_key_every);
        // se realiza la encriptacion


        System.out.print("\n unixtimeFloor = ");
        System.out.print(unixtimeFloor);


        System.out.print("\nclave = ");
        System.out.print(clave);

        String passwordHash = getHashKey(clave, unixtimeFloor);

        System.out.print("\n passwordHash = ");
        System.out.print(passwordHash);




        String code = getCodeKey(passwordHash);

        System.out.print("\n code = ");
        System.out.print(code);

        System.out.print("\n codigo = ");
        System.out.print(codigo);
        return code.equals(codigo);
    }


    public static boolean verificarCodigo(CodeValidateRequestDto requestDto)
    {

        String keyGenerateCode=requestDto.getPassword();
        if(requestDto.getPassword()!=null){
            keyGenerateCode=keyGenerateCode+requestDto.getPassword();
        }

        String codigo=requestDto.getCode();


        System.out.print("\nkeyGenerateCode = ");
        System.out.print(keyGenerateCode);

        String  clave = Encrypt.sha512(keyGenerateCode);

        //Obtiene el time unix en segundos
        String fecha =Date.getFechaHora(_formatTime);
        Long unixtime = Date.getUnixTime(fecha,_formatTime);



        System.out.print("\n FECHA ACTUAL = ");
        System.out.print(fecha);


        System.out.print("\n FECHA UNIX-TIME = ");
        System.out.print(unixtime);



        //******** PASO 1 DE VALIDACIÓN CON EL TIEMPO ACTUAL *************
        boolean validacion = validacionCode(unixtime, codigo, clave);


        if (validacion) {
            return validacion;
        }
        //********END  PASO 1 DE VALIDACIÓN CON EL TIEMPO ACTUAL *************

        //******** PASO 2 DE VALIDACIÓN - RESTANDO EL VALOR HACIA ATRAS *************
        // se le resta
        //Ejemplo si el tiempo esta defindo en 30 segundo el valor sera 29
        int timeBack = _change_key_every - 1;
        //Este valor se le resta al tiempo
        unixtime   = unixtime - timeBack;
        validacion = validacionCode(unixtime, codigo, clave);
        if (validacion) {
            return validacion;
        }
        //******** END PASO 2 DE VALIDACIÓN - RESTANDO EL VALOR HACIA ATRAS *************

        //******** PASO 3 DE VALIDACIÓN - RESTANDO EL VALOR HACIA ADELANTE *************
        // se le SUMA
        int timeNext = _change_key_every;
        //Este valor se le resta al tiempo
        unixtime   = unixtime + timeBack;
        validacion = validacionCode(unixtime, codigo, clave);
        if (validacion) {
            return validacion;
        }
        //**
        //******** PASO 3 DE VALIDACIÓN - RESTANDO EL VALOR HACIA ADELANTE *************

        //******** PASO 4 DE VALIDACIÓN - RESTANDO EL VALOR HACIA ATRAS *************
        // se le resta
        //Ejemplo si el tiempo esta defindo en 30 segundo el valor sera 60
        /* $timeBack = $GLOBALS["change_key_every"] * 2;
        //Este valor se le resta al tiempo
        $unixtime = $unixtime - $timeBack;

        $validacion = validacionCode($unixtime, $codigo, $clave);
        if ($validacion) {
        print_r("es igual en la validacion 3");
        return $validacion;
        }*/
        //******** END PASO 4 DE VALIDACIÓN - RESTANDO EL VALOR HACIA ATRAS *************
        return validacion;
    }







}
