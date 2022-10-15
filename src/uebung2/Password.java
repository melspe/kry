import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.security.*;

public class Password {

    private static Boolean continueWriting = false;
    private static Boolean match = false;
    private static String matchAlgo = "";
    private static String matchPW = "";

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {

        System.out.println("Unterpunkt 1");
        System.out.println("Please enter your password here: ");
        Scanner sc = new Scanner(System.in);
        String password = sc.nextLine();
        System.out.println("MD5: "+ md5(password)+ "\nSHA256: " + toHexString(sha256(password))+ "\nBycrpt: "+bcrypt(password));

        System.out.println("------------------------------------------------------------------------------------------------------------------");

        System.out.println("Unterpunkt 2");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your password here:");
        String hashPW = "";
        try {
            hashPW = br.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (hashPW.length() < 6) {
            System.out.println("PW too short!");
            return;
        }
        // hier definiert man die Algorithmen, auf die abgeprüft werden soll
        final String hashTypes[] = { "MD5", "SHA-224", "SHA-256", "SHA-512" };

        // alle definierten Hash-Algorithmen durchprobieren
        for (String hashType : hashTypes) {
            resultPW(hashPW, hashType);
        }

        // Wenn gefunden - Ausgabe der Infos über den Algorithmus sowie das Passwort im
        // Plaintext
        if (match) {
            System.out.println("Match! Algorithm: " + matchAlgo);
            System.out.println("Plain PW: " + matchPW);
        } else
            System.out.println("No matching PW found");
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

    public static void resultPW(String hashPW, String hashType){
        MessageDigest md;
        try {
            //Passwort einlesen
            BufferedReader br = new BufferedReader(new FileReader("src/uebung2/ListPw"));
            FileWriter fw = new FileWriter(new File("src/uebung2/rainbowTables"+hashType+".txt"));
            String line ="";

            while ((line = br.readLine()) != null) {
                md = MessageDigest.getInstance(hashType);
                md.reset();
                md.update(line.getBytes(StandardCharsets.UTF_8));
                byte[] result = md.digest();

                StringBuilder sb = new StringBuilder();
                for (byte b : result) {
                    sb.append(String.format("%02x", b));
                }

                String resultString = sb.toString();
                //in File speichern
                fw.write(resultString);
                fw.write(System.lineSeparator());
                fw.flush();

                //Check, ob bereits Hash vorhanden ist --> Plaintext finden
                if (resultString.equals(hashPW)) {
                    match = true;
                    matchPW = line;
                    matchAlgo = hashType;
                    if(!continueWriting){
                        br.close();
                        fw.close();
                        return;
                }
                continue;
            }
        }
        br.close();
        fw.close();


    } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return;
    }























    /* public static String plaintext() throws FileNotFoundException {
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

    };*/

}