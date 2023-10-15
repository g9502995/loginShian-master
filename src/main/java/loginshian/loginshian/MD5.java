package loginshian.loginshian;

import java.security.MessageDigest;

public class MD5 {

    public String encode(String str) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            result = md.digest();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return parseByte2HexStr(result);
    }


    private String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    public static String uncode(String A){
        MD5 md5=new MD5();
       return md5.encode(A);
    }
}
