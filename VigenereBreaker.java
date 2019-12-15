import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i+=totalSlices) {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            CaesarCracker cc = new CaesarCracker(mostCommon);
            key[i] = cc.getKey(slice);
        }
        return key;
    }

    public void breakVigenere () {
        FileResource fr = new FileResource("VigenereTestData/athens_keyflute.txt");
        String text = fr.asString();
        FileResource dict = new FileResource("dictionaries/English");
        HashSet<String> dictionary = readDictionary(dict);
        String decryption = breakForLanguage(text, dictionary);
        System.out.println(decryption);
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<>();
        for (String line : fr.lines()) {
            dictionary.add(line.toLowerCase());
        }
        return dictionary;
    }

    public int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        String[] words = message.split("\\W");
        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int max = 0;
        int[] decryptionKey = new int[encrypted.length()];
        int index = 0;
        for (int i = 1; i < 100; i++) {
            int[] key = tryKeyLength(encrypted, i, 'e');
            VigenereCipher vc = new VigenereCipher(key);
            String decryption = vc.decrypt(encrypted);
            int count = countWords(decryption, dictionary);
            if (count > max) {
                max = count;
                decryptionKey = key;
                index = i;
            }
        }
        VigenereCipher vc = new VigenereCipher(decryptionKey);
        return vc.decrypt(encrypted);
    }
}
