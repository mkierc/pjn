package lab1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ReadStatistics {
    public static void main(String[] args) {
        TreeMap<String, Integer> nGramMap;
        nGramMap = readStatistics("pol-1", 3);

        // OUTPUT NA EKRAN:
        for (Map.Entry<String, Integer> entry : nGramMap.entrySet())
            System.out.println(entry.getKey() + " : " + entry.getValue());
    }

    /**
     * Odczytuje statystyki z podanego pliku txt w katalogu resources/lab1/statistics
     * w formacie:
     *
     * [ngram1] [ilość_wystąpień]
     * [ngram2] [ilość_wystąpień]
     * ...
     *
     * @param   fileName                    nazwa pliku do odczytania
     * @param   nGramLength                 długość n-gramów dla których wybieramy statystyki
     * @return  TreeMap<String, Integer>    treemapa zawierająca odczytane statystyki n-gramów
     */
    public static TreeMap<String, Integer> readStatistics(String fileName, int nGramLength) {
        Scanner inScanner;
        TreeMap<String, Integer> nGramMap = new TreeMap<>();
        try {
            inScanner = new Scanner(new FileReader("resources//lab1//statistics//" + nGramLength + "//" + fileName + ".txt"));
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                String[] nGram = line.split(" ");
                nGramMap.put(nGram[0], Integer.parseInt(nGram[1]));
            }
            return nGramMap;
        } catch (FileNotFoundException e) {
            System.out.println("Brak statystyk dla podanej nazwy.");
        }
        return null;
    }
}
