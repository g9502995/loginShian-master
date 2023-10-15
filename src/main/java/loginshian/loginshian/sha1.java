package loginshian.loginshian;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha1 {
    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成雜湊計算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }



    public static String password(String password) throws NoSuchAlgorithmException {

        sha1 sha = new sha1();
        byte[] resultBytes = sha.eccrypt(password);

        return new String(resultBytes);
    }
}
