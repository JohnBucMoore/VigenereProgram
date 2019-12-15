import edu.duke.FileResource;

import java.util.HashSet;

public class Tester {

    public String testCaesarCipher() {
        CaesarCipher cc = new CaesarCipher(1);
        FileResource fr = new FileResource("VigenereTestData/oslusiadas_key17.txt");
        String text = fr.asString();
        String encrypted = cc.encrypt(text);
        return encrypted;
        /*
        String decrypted = cc.decrypt(encrypted);
        System.out.println(encrypted);
        System.out.println(decrypted);
         */
    }

    public void testCaesarCracker() {
        CaesarCracker cc = new CaesarCracker('a');
        System.out.println(cc.getKey(testCaesarCipher()));
        System.out.println(cc.decrypt(testCaesarCipher()));
    }

    public void testVigenereCipher() {
        FileResource fr = new FileResource("VigenereTestData/titus-small.txt");
        String text = fr.asString();
        int[] rome = {17, 14, 12, 4};
        VigenereCipher vc = new VigenereCipher(rome);
        System.out.println(vc.encrypt(text));
        int endLine = vc.encrypt(text).indexOf('\n');
        String encryption = vc.encrypt(text).substring(0, endLine);
        String firstLine = "Tcmp-pxety mj nikhqv htee mrfhtii tyv,";
        if (firstLine.equals(encryption)) {
            System.out.println("First line is a match");
        }
    }

    public void testVigenereBreaker() {
        VigenereBreaker vb = new VigenereBreaker();
        System.out.println(vb.sliceString("abcdefghijklm", 2, 5));
        VigenereBreaker vb2 = new VigenereBreaker();
        vb2.breakVigenere();
    }

    public void testReadDictionary() {
        FileResource fr = new FileResource("dictionaries/English");
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> dictionary = vb.readDictionary(fr);
        /*
        for (String word : dictionary) {
            System.out.println(word);
        }
         */
    }

    public void testBreakForLanguage() {
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }

    public static void main(String[] args) {
        Tester t = new Tester();
        t.testCaesarCracker();
        t.testVigenereCipher();
        t.testVigenereBreaker();
        //t.testReadDictionary();
        t.testBreakForLanguage();
    }
}
