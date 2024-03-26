package ec.gob.policia.service.algorithmtotp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

    public static String sha512(String password) {
        MessageDigest sha = null;
        byte[] hash = null;
        try {
            sha = MessageDigest.getInstance("SHA-512");
            hash = sha.digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return convertToHex(hash);
    }


    private static String convertToHex(byte[] raw) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < raw.length; i++) {
            sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
