package Translation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Translation2 {
	public static void main(String[] args) {
        Map<String, String> dictionary = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\exeter perimedia\\TranslateWords Challenge\\french_dictionary.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                dictionary.put(words[0], words[1]);
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
            return;
        }

        
        String[] findWordsList;
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\exeter perimedia\\TranslateWords Challenge\\find_words.txt"))) {
            findWordsList = br.lines().toArray(String[]::new);
        } catch (IOException e) {
            System.err.println("Error reading find words list: " + e.getMessage());
            return;
        }

       
        Map<String, Integer> frequencyMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("E:\\exeter perimedia\\TranslateWords Challenge\\t8.shakespeare.txt"));
             FileWriter fw = new FileWriter("output.csv")) {
            fw.write("English word,French word,Frequency\n");

            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String originalWord = words[i];
                    String lowerCaseWord = originalWord.toLowerCase();
                    if (contains(findWordsList, lowerCaseWord) && dictionary.containsKey(lowerCaseWord)) {
                        String translatedWord = dictionary.get(lowerCaseWord);
                        words[i] = originalWord.charAt(0) + translatedWord.substring(1);

                  
                        frequencyMap.put(translatedWord, frequencyMap.getOrDefault(translatedWord, 0) + 1);
                    }
                }
                fw.write(String.join(",", words) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            return;
        }

       
        try (FileWriter fw = new FileWriter("E:\\exeter perimedia\\TranslateWords Challenge\\frequency.csv")) {
            fw.write("French word,Frequency\n");
            for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                fw.write(entry.getKey() + "," + entry.getValue() + System.lineSeparator());
            }
            
        } catch (IOException e) {
            System.err.println("Error writing frequency file: " + e.getMessage());
            return;
        }

        System.out.println("Output saved to output.csv");
        System.out.println("Frequency saved to frequency.csv");
    }

    private static boolean contains(String[] array, String str) {
        for (String s : array) {
            if (s.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
