public class Caesar {
    //Teste Programm mit main Funktion
    public static void main(String[] args) {
        String plaintext = "Guten Tag!";
        int key = 3;

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Klartext: " + plaintext + " ---> " + ciphertext);

        String decoded = decrypt(ciphertext, key);
        System.out.println("Geheimtext: " + ciphertext + " ---> " + decoded);
    }

    /* Klartext mit dem Schluessel verschluesseln */
    public static String encrypt(String klartext, int key){
        String geheimtext = "";
        klartext = klartext.toLowerCase(); //Alles in Kleinbuchstaben umwandeln
        for(int i=0; i<klartext.length(); i++){
            //Buchstaben verschieben und an Geheimtext haengen
            char next = klartext.charAt(i);
            geheimtext= geheimtext + shift(next, key);
        }
        return geheimtext;
    }

    /* Verschiebt den Buchstaben um die angegebene Verschiebung (key)*/
    public static char shift(char letter, int shift){
        //Buchstaben als Zahl behandeln
        if(letter>='a' && letter <='z'){ //Sonderzeichen nicht veraendern
            letter += shift;
            while(letter > 'z'){
                letter -= 26;
            }
        }
        return letter;
    }

    /* Entschluesselt den Geheimtext mit dem angegebenen Schluessel */
    public static String decrypt(String geheimtext, int key){
        String klartext = "";
        geheimtext = geheimtext.toLowerCase(); //Alles in Kleinbuchstaben umwandeln
        for(int i=0; i<geheimtext.length(); i++){
            //Jeden Buchstaben verschieben und zwar um (26-Schluessel(key)) Stellen
            //und an entschluesselten Text anhaengen
            char next = geheimtext.charAt(i);
            klartext = klartext + shift(next, 26-key);
        }
        return klartext;
    }
}