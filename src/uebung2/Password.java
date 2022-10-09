import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.security.*;

public class Password {
    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {
        /*System.out.println("Please your password here: ");
        Scanner sc = new Scanner(System.in);
        String password = sc.nextLine();
        System.out.println("MD5: "+ md5(password)+ "\nSHA256: " + toHexString(sha256(password))+ "\nBycrpt: "+bcrypt(password));*/

        System.out.println("------------------------------------------------------------------------------------------------------------------");

        System.out.println("Plaintext: "+plaintext());

        List<String> plaintexts = new ArrayList<String>();

        System.out.println("MD5: "+Tabmd5(plaintexts));
        System.out.println("SHA-257: "+Tabsha257(plaintexts));
        System.out.println("Bycrpt: "+Tabbcrypt(plaintexts));






    }


    public static String md5(String input)
    {
        try {

            // MD5 per getInstance aufrufen
            MessageDigest md = MessageDigest.getInstance("MD5");

            // mit digest() die Message digest kalkulieren
            // Retourgabe eines Arrays von Bytes
            byte[] messageDigest = md.digest(input.getBytes());

            // das byte array umwandeln
            BigInteger no = new BigInteger(1, messageDigest);

            // in einen Hexwert umwandeln
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // Error Message
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    public static byte[] sha256 (String password) throws NoSuchAlgorithmException {
            // SHA-256 per getInstance aufrufen
            MessageDigest md = MessageDigest.getInstance("SHA-256");

        // mit digest() die Message digest kalkulieren
        // Retourgabe eines Arrays von Bytes
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        }

        public static String toHexString(byte[] hash)
        {
            // das byte array umwandeln
            BigInteger number = new BigInteger(1, hash);

            // in einen Hexwert umwandeln
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
            // byte array für salt generieren
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            // SHA-256 per getInstance aufrufen
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

    //---------------------------------------------------------------------------------------------------------------------------------------------------

    public static String plaintext() throws FileNotFoundException {
        List<String> input = new ArrayList<String>();
        Scanner s = new Scanner(new File("src/uebung2/ListPw"));

        while (s.hasNext()){
            input.add(s.next());
        };

        return input.toString();

    };

    public static String Tabmd5(List<String> plaintext) throws FileNotFoundException {
        List<String> input = new ArrayList<String>();
        Scanner s = new Scanner(new File("src/uebung2/ListPw"));
        while(s.hasNext()){
            input.add(md5(s.next()));
        }
        return input.toString();

    };


    public static String Tabsha257(List<String> plaintext) throws FileNotFoundException {
        List<String> input = new ArrayList<String>();
        Scanner s = new Scanner(new File("src/uebung2/ListPw"));
        while(s.hasNext()){
            input.add(md5(s.next()));
        }
        return input.toString();

    };


    public static String Tabbcrypt(List<String> plaintext) throws FileNotFoundException {
        List<String> input = new ArrayList<String>();
        Scanner s = new Scanner(new File("src/uebung2/ListPw"));
        while(s.hasNext()){
            input.add(md5(s.next()));
        }
        return input.toString();

    };

}