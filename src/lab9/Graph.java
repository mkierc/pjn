package lab9;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static lab6.Ranking.generateDictionaryFromFile;
import static lab7.GeneratePapMap.readNotatkiPapFromFile;

public class Graph {
    public static void main(String[] args) {
        Set<String> stopLista = readGeneratedStopList();
        TreeMap<String, String> dictMap = generateDictionaryFromFile();
        TreeMap<Integer, List<String>> notatkiPapMap = readNotatkiPapFromFile(dictMap);
        TreeMap<Integer, List<String>> przetrawioneNotatkiPapMap = applyStopList(stopLista, notatkiPapMap);

        int k = 2;
        TreeMap<Integer, String> wierzcholkiMapByInt = generateWierzcholkiByInt(przetrawioneNotatkiPapMap);
        TreeMap<String, Integer> wierzcholkiMapByString = generateWierzcholkiByString(przetrawioneNotatkiPapMap);
        TreeMap<Integer, Set<Map.Entry<Integer, Integer>>> krawedzieMap = generateKrawedzie(przetrawioneNotatkiPapMap, wierzcholkiMapByString, k);

        // Wyświetlenie stop listy
        System.out.println("Stoplista :\n" + stopLista);

        // Porównanie notatki nr. 1 przed i po zaaplikowaniu stoplisty
        System.out.println("\nNotatka 1 przed stoplistą :\n" + notatkiPapMap.get(1));
        System.out.println("Notatka 1 po stopliście :\n" + przetrawioneNotatkiPapMap.get(1));

        // Wyświetlenie grafu dla notatki nr. 1
        System.out.println("\nGraf dla notatki 1 :\n" + krawedzieMap.get(1));
    }

    public static TreeMap<Integer, Set<Map.Entry<Integer,Integer>>> generateKrawedzie(TreeMap<Integer, List<String>> notatkiPapMap, TreeMap<String, Integer> wierzcholkiMap, int k) {
        TreeMap<Integer, Set<Map.Entry<Integer, Integer>>> krawedzieMap = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
            HashSet<Map.Entry<Integer, Integer>> krawedzieSet = new HashSet<>();
            String[] words = entry.getValue().toArray(new String[]{});
            for (int i = 0; i < words.length - k; i++) {
                for (int j = 0; j < k; j++) {
                    if (wierzcholkiMap.get(words[i]) <= wierzcholkiMap.get(words[i+j]))
                        krawedzieSet.add(new AbstractMap.SimpleEntry<>(wierzcholkiMap.get(words[i]), wierzcholkiMap.get(words[i+j])));
                    else
                        krawedzieSet.add(new AbstractMap.SimpleEntry<>(wierzcholkiMap.get(words[i+j]), wierzcholkiMap.get(words[i])));
                }
            }
            krawedzieMap.put(entry.getKey(), krawedzieSet);
        }
        return krawedzieMap;
    }

    public static TreeMap<Integer, String> generateWierzcholkiByInt(TreeMap<Integer, List<String>> notatkiPapMap) {
        TreeSet<String> wierzcholki = new TreeSet<>();
        TreeMap<Integer, String> wierzcholkiMap = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
            List<String> notatka = entry.getValue();
            for (String word : notatka) {
                wierzcholki.add(word);
            }
        }
        int i = 1;
        for (String s : wierzcholki) {
            wierzcholkiMap.put(i, s);
            i++;
        }
        return wierzcholkiMap;
    }

    public static TreeMap<String, Integer> generateWierzcholkiByString(TreeMap<Integer, List<String>> notatkiPapMap) {
        TreeSet<String> wierzcholki = new TreeSet<>();
        TreeMap<String, Integer> wierzcholkiMap = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
            List<String> notatka = entry.getValue();
            for (String word : notatka) {
                wierzcholki.add(word);
            }
        }
        int i = 1;
        for (String s : wierzcholki) {
            wierzcholkiMap.put(s, i);
            i++;
        }
        return wierzcholkiMap;
    }

    public static TreeMap<Integer, List<String>> applyStopList(Set<String> stopLista, TreeMap<Integer, List<String>> notatkiPapMap) {
        TreeMap<Integer, List<String>> przetrawioneNotatkiPapMap = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
            List<String> notatka = entry.getValue();
            List<String> przetrawionaNotatka = new ArrayList<>();

            for (String word : notatka) {
                if (!stopLista.contains(word))
                    przetrawionaNotatka.add(word);
            }
            przetrawioneNotatkiPapMap.put(entry.getKey(), przetrawionaNotatka);
        }
        return przetrawioneNotatkiPapMap;
    }

    public static Set<String> readGeneratedStopList() {
        Scanner inScanner;
        Set<String> stopList = new TreeSet<>();

        try {
            inScanner = new Scanner(new FileReader("resources//lab9//stopLista.txt"));
            while (inScanner.hasNextLine()) {
                String word = inScanner.nextLine().toLowerCase();
                stopList.add(word);
            }
            return stopList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
