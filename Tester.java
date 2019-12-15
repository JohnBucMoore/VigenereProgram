import edu.duke.FileResource;

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

    public static void main(String[] args) {
        Tester t = new Tester();
        t.testCaesarCracker();
        t.testVigenereCipher();
        t.testVigenereBreaker();
    }
}
