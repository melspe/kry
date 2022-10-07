import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.security.*;

public class Password {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Please your password here: ");
        Scanner sc = new Scanner(System.in);
        String password = sc.nextLine();
        System.out.println("MD5: "+ md5(password)+ "\nSHA256: " + toHexString(sha256(password))+ "\nBycrpt: "+bcrypt(password));

    }

    public static String md5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    public static byte[] sha256 (String password) throws NoSuchAlgorithmException {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        }

        public static String toHexString(byte[] hash)
        {
            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hash);

            // Convert message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 64)
            {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        }

        public static String bcrypt (String password) throws NoSuchAlgorithmException {
            String result = "";
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            result = toHexString(hashedPassword);
            return result;
        };

    /*
    *bcrypt ist eine Hashfunktion, die für das Hashen und Speichern von Passwörtern erfunden wurde.
    Die Funktionsweise ähnelt der Blockverschlüsselung Blowfish. Im Gegensatz zu einfachen und damit auch schnelleren Hash-Methoden wie MD5
    * und SHA-X erzeugt bcrypt mit jedem Vorgang einen anderen Hash-Code und verlangsamt so den Vorgang.
    bcrypt funktioniert in 2 Phasen:
    1. Es wird ein Schlüsselplan ausgeführt, der eine Mischung aus Unterschlüsseln und Primärschlüsseln besteht. Das Passwort
    * bildet hier den Primärschlüssel ab. Hierbei wird die Schlüsselverstärkung gefördert, um die Berechnungen zu entschleunigen.

    2.Dieser Wert wird 64-mal mit eksblowfish im ECB-Modus>) mit dem Status der vorherigen Phase verschlüsselt.
    * Das Ergebnis dieser Phase sind die Kosten und der 128-Bit-Salzwert, die mit dem Ergebnis der Verschlüsselungsschleife verkettet werden.
    *
    *
    *
    * */
}