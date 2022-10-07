import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.security.*;

public class Password {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Please your password here: ");
        Scanner sc = new Scanner(System.in);
        String password = sc.nextLine();
        System.out.println("MD5: "+ md5(password)+ "\nSHA256: " + sha256(password));

    }

    public static String md5(String password) throws NoSuchAlgorithmException {
        String hashwert = "";
        byte[] bytesOfMessage = new byte[0];
        try {
            bytesOfMessage = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theMD5digest = md.digest(bytesOfMessage);
        hashwert = theMD5digest.toString();
        return hashwert;
    }

    public static String sha256 (String password) throws NoSuchAlgorithmException {
        String hashwert = "";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        hashwert = hash.toString();
        return hashwert;
    }
}