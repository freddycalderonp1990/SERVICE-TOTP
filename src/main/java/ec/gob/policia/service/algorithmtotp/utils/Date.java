package ec.gob.policia.service.algorithmtotp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.time.ZonedDateTime;
import java.time.ZoneId;



public class Date {




    public static String getFechaHora(String formatTime ) {
        //obtenemos la fecha del servidor



        // Obtener la fecha y hora actual en la zona horaria de Guayaquil
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Guayaquil"));

        // Formatear la fecha y hora según tus preferencias
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatTime);
        String fechaServer = zonedDateTime.format(formatter);

       //fechaServer= "2024-02-14 10:07:03";

        return fechaServer;
    }


    //el formato de la fecha debe ser Y-m-d H:i:s
    //
    public static Long getUnixTime(String fecha,String formatTime ) {
        //como el valor nos da en milisegundos los transformamos a segundos
        // Long unixTime = System.currentTimeMillis() / 1000L;

        Long milisegs = 0L;

        SimpleDateFormat sd = new SimpleDateFormat(formatTime);
        java.util.Date date = new java.util.Date();

        try {
            date = sd.parse(fecha);
            milisegs = date.getTime() / 1000;
            return milisegs;

        } catch (ParseException e) {

            System.out.print("Error fecha no valida");
            return milisegs;
        }


    }

    static int setUnixtimeDuration(Long unixtime, int change_key_every ) {
        double unixtimeDuration = unixtime / change_key_every;

        //se redondea el valor
        int floor = (int) Math.floor(unixtimeDuration);

        return floor;
    }

}
