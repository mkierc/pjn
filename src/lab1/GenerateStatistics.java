package lab1;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateStatistics {
    public static void main(String[] args) {
        int nGramLength = 5;
        TreeMap<String, Integer> nGramMap;
        TreeMap<String, String> languagesFilesMap = GuessTheLanguage.getLanguagesFilesMap();

        for (Map.Entry<String, String> entry : languagesFilesMap.entrySet()) {
            nGramMap = generateStatisticsFromFile(entry.getKey(), nGramLength);
            writeStatistics(entry.getKey(), nGramLength, nGramMap);
        }
    }

    /**
     * Generuje statystyki n-gramów z podanego pliku txt w katalogu resources/lab1/samples
     *
     * @param   fileName                    nazwa pliku do odczytania
     * @param   nGramLength                 długość n-gramów dla których wybieramy statystyki
     * @return  TreeMap<String, Integer>    treemapa zawierająca odczytane statystyki n-gramów
     */
    public static TreeMap<String, Integer> generateStatisticsFromFile(String fileName, int nGramLength) {
        Scanner inScanner;
        TreeMap<String, Integer> nGramMap = new TreeMap<>();

        try {
            inScanner = new Scanner(new FileReader("resources//lab1//samples//" + fileName + ".txt"));
            /**
             * Pattern pattern = Pattern.compile("(?=([a-z][a-z])).");
             *                                          ^
             *                                         /|\
             *                                          |
             *                            [a-z] powtarzamy n razy dla n-gramu, stąd StringBuilder
             */
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(?=(");
            for (int i = 0; i < nGramLength; i++)
                stringBuilder.append("[a-z]");
            stringBuilder.append(")).");

            Pattern pattern = Pattern.compile(stringBuilder.toString());
            Matcher matcher;

            int total = 0;

            while (inScanner.hasNextLine()) {
                matcher = pattern.matcher(inScanner.nextLine().toLowerCase());
                matcher.useAnchoringBounds(true);
                while (matcher.find()) {
                    if (nGramMap.containsKey(matcher.group(1))){
                        Integer value = nGramMap.get(matcher.group(1));
                        value++;
                        nGramMap.put(matcher.group(1), value);
                    } else {
                        nGramMap.put(matcher.group(1), 1);
                    }
                    total++;
                }
            }
            nGramMap.put("-total", total);
            return nGramMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Zapisuje statystyki n-gramów do podanego pliku txt w katalogu resources/lab1/statistics
     * w formacie:
     *
     * [ngram1] [ilość_wystąpień]
     * [ngram2] [ilość_wystąpień]
     * ...
     *
     * @param   fileName                    nazwa pliku do zapisania
     * @param   nGramLength                 długość n-gramów dla których wybieramy statystyki
     * @param   nGramMap                    treemapa zawierająca odczytane statystyki n-gramów
     */
    public static void writeStatistics(String fileName, int nGramLength, TreeMap<String, Integer> nGramMap) {
        try {
            File outputFile = new File("resources//lab1//statistics//" + nGramLength + "//" + fileName + ".txt");
            File parentDirectory = outputFile.getParentFile();

            if (null != parentDirectory)
                parentDirectory.mkdirs();

            FileWriter fw = new FileWriter(outputFile);

            for (Map.Entry<String, Integer> entry : nGramMap.entrySet())
                fw.write(entry.getKey() + " " + entry.getValue() + "\n");
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
