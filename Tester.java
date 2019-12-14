import edu.duke.FileResource;

public class Tester {

    public void testCaesarCipher() {
        CaesarCipher cc = new CaesarCipher(13);
        FileResource fr = new FileResource("VigenereTestData/titus-small.txt");
        String text = fr.asString();
        String encrypted = cc.encrypt(text);
        String decrypted = cc.decrypt(encrypted);
        System.out.println(encrypted);
        System.out.println(decrypted);
    }

    public static void main(String[] args) {
        Tester t = new Tester();
        t.testCaesarCipher();
    }
}
