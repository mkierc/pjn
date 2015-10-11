package lab6;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Ranking {
    public static void main(String[] args) {
        TreeMap<String, String> dictMap = generateDictionaryFromFile();
        TreeMap<String, Integer> rankMap = generateRankingFromFile(dictMap);

        int hapaxCount = 0;
        int allCount = 0;
        int fiftyPercent = 0;
        int fiftyPercentValue = 0;
        for (Map.Entry<String, Integer> entry : rankMap.entrySet()) {
            if (entry.getValue().equals(1)) {
                hapaxCount++;
            }
            allCount += entry.getValue();
        }

        SortedSet<Map.Entry<String, Integer>> set = entriesSortedByValues(rankMap);
        Iterator<Map.Entry<String, Integer>> iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            System.out.printf("%25s\t\t%16d\n", entry.getKey(), entry.getValue());
            if (fiftyPercentValue < allCount / 2) {
                fiftyPercentValue += entry.getValue();
                fiftyPercent++;
            }
        }

        System.out.println("Ilość hapax legomena: " + hapaxCount);
        System.out.println("Ilość stanowiących 50%: " + fiftyPercent);
    }

    public static TreeMap<String, Integer> generateRankingFromFile(TreeMap<String, String> dictMap) {
        Scanner inScanner;
        TreeMap<String, Integer> rankMap = new TreeMap<>();
        try {
            inScanner = new Scanner(new FileReader("resources//lab6//potop.txt"));
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine().toLowerCase();
                line = line.replace(".", "");
                line = line.replace(",", "");
                line = line.replace("-", "");
                line = line.replace("!", "");
                line = line.replace("?", "");
                line = line.replace("(", "");
                line = line.replace(")", "");
                line = line.replace("<", "");
                line = line.replace(">", "");
                line = line.replace(";", "");
                line = line.replace("\"", "");
                String[] words = line.split(" ");
                int i = 0;
                while (i < words.length) {
                    //System.out.println(words[i]);
                    String baseWord;
                    if (dictMap.containsKey(words[i])) {
                        baseWord = dictMap.get(words[i]);
                        if (rankMap.containsKey(baseWord)) {
                            int temp = rankMap.get(baseWord);
                            temp++;
                            rankMap.put(baseWord, temp);
                        } else {
                            rankMap.put(baseWord, 1);
                        }
                    }
                    i++;
                }

            }
            return rankMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TreeMap<String, String> generateDictionaryFromFile() {
        Scanner inScanner;
        TreeMap<String, String> dictMap = new TreeMap<>();

        try {
            inScanner = new Scanner(new FileReader("resources//lab6//odm.txt"));
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine().toLowerCase();
                String[] words = line.split(", ");
                int i = 0;
                while (i < words.length) {
                    dictMap.put(words[i], words[0]);
                    //System.out.println(words[i] + " " + words[0]);
                    i++;
                }

            }
            return dictMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e2, Map.Entry<K, V> e1) {
                int res = e1.getValue().compareTo(e2.getValue());
                return res != 0 ? res : 1; // equal values
            }
        });
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
