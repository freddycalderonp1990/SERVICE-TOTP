package ec.gob.policia.service.algorithmtotp.utils;

import lombok.Data;

@Data
public class ResponseGenerico {
    private Object data;
    private String mensaje;
    private int codigo;
}
