import java.io.File;
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
        FileResource fr = new FileResource();
        String text = fr.asString();
        HashMap<String, HashSet<String>> languages = new HashMap<>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr2 = new FileResource(f);
            System.out.println("Reading "+f.getName()+" dictionary...");
            HashSet<String> dictionary = readDictionary(fr2);
            System.out.println("Completed "+f.getName()+" dictionary.");
            languages.put(f.getName(), dictionary);
        }
        breakForAllLangs(text, languages);
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
        char mostCommon = mostCommonCharIn(dictionary);
        for (int i = 1; i < 100; i++) {
            int[] key = tryKeyLength(encrypted, i, mostCommon);
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

    private int getMaxIndex(int[] chars) {
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] > max) {
                max = chars[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        int[] chars = new int[26];
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                if (alpha.indexOf(word.charAt(i)) != -1) {
                    int index = alpha.indexOf(word.charAt(i));
                    chars[index]++;
                }
            }
        }
        return alpha.charAt(getMaxIndex(chars));
    }

    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        int mostMatches = 0;
        String lang = "";
        String answer = "";
        for (String language : languages.keySet()) {
            HashSet<String> dictionary = languages.get(language);
            String decryption = breakForLanguage(encrypted, dictionary);
            int count = countWords(decryption, dictionary);
            if (count > mostMatches) {
                mostMatches = count;
                lang = language;
                answer = decryption;
            }
        }
        System.out.println(lang+" had "+mostMatches+" matches and resulted in:\n"+answer);
    }
}
