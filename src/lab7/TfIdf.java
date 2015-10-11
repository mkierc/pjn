package lab7;

import java.io.*;
import java.util.*;

public class TfIdf {
    public static void main(String[] args) {
        TreeMap<Integer, List<String>> notatkiPapMap = readPrzetrawioneNotatki();

        Integer notatkiCount = 0;
        for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
            if (entry.getKey() > notatkiCount) notatkiCount = entry.getKey();
        }

        TreeMap<String, Double> idf = generateIdf(notatkiPapMap, notatkiCount);
        TreeMap<Integer, TreeMap<String, Double>> tfIdf = generateTfIdf(notatkiPapMap, idf);

        TreeMap<Integer, Set<String>> slowaKluczoweMap = generateKeywordMap(tfIdf);
        writeKeywordsToFile(slowaKluczoweMap);

        // Wyświetlenie IDF
        System.out.println("idf 'z' :\t\t\t" + idf.get("z"));
        System.out.println("idf 'andrzej' : \t" + idf.get("andrzej"));

        // Wyświetlenie mapy wartość TF-IDF dla pierwszego tekstu
        System.out.println("\nwartości tf-idf dla 1 notatki : " + (tfIdf.get(1)));

        // Wyświetlenie wartości TF-IDF dla pierwszego tekstu i kilku przykładowych słów
        System.out.println("\nwaga 'z' :\t\t\t" + (tfIdf.get(1)).get("z"));          // 4 wystąpienia
        System.out.println("waga 'rok' :\t\t" + (tfIdf.get(1)).get("rok"));          // 4 wystąpienia
        System.out.println("waga 'historie' :\t" + (tfIdf.get(1)).get("historie"));  // 2 wystąpienia
        System.out.println("waga 'andrzej' :\t" + (tfIdf.get(1)).get("andrzej"));    // 1 wystąpienie

        // Wyświetlenie przykładowej notatki w postaci BAG OF WORDS:
        System.out.println("\n'bag of words' notatki nr 1 :\n" + (notatkiPapMap.get(1)));

        // Wyświetlenie notatkiCount
        //System.out.println(notatkiCount);

        // Wyświetlenie słów kluczowych dla kilku notatek
        System.out.println("\nSłowa kluczowe notatki nr 1 : \n" + slowaKluczoweMap.get(1));
        System.out.println("Słowa kluczowe notatki nr 2 : \n" + slowaKluczoweMap.get(2));
        System.out.println("Słowa kluczowe notatki nr 5 : \n" + slowaKluczoweMap.get(5));
    }


    public static void writeKeywordsToFile(TreeMap<Integer, Set<String>> slowaKluczoweMap) {
        try {
            File outputFile = new File("resources//lab7//slowaKluczowe.txt");
            File parentDirectory = outputFile.getParentFile();

            if (null != parentDirectory) parentDirectory.mkdirs();

            FileWriter fw = new FileWriter(outputFile);

            for (Map.Entry<Integer, Set<String>> entry : slowaKluczoweMap.entrySet()) {
                fw.write(entry.getKey() + " ");
                Set<String> lista = entry.getValue();
                for (String s : lista) {
                    fw.write(s + " ");
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static TreeMap<Integer, Set<String>> generateKeywordMap(TreeMap<Integer, TreeMap<String, Double>> tfIdf) {
        TreeMap<Integer, Set<String>> keyWordMap = new TreeMap<>();

        for (Map.Entry<Integer, TreeMap<String, Double>> entryTfIdf : tfIdf.entrySet()) {
            TreeMap<String, Double> wordCountMap = entryTfIdf.getValue();
            SortedSet<Double> topThree = new TreeSet<>(Comparator.<Double>reverseOrder());
            for (Map.Entry<String, Double> entryWordCount : wordCountMap.entrySet()) {
                topThree.add(entryWordCount.getValue());
            }
            Double[] topThreeArray = topThree.toArray(new Double[topThree.size()]);
            Set<String> keyWordSet = new TreeSet<>();

            for (Map.Entry<String, Double> entryWordCount : wordCountMap.entrySet()) {
                int i = 0;
                while (i < 3 && i < topThreeArray.length) {
                    if (entryWordCount.getValue().equals(topThreeArray[i])) {
                        keyWordSet.add(entryWordCount.getKey());
                    }
                    i++;
                }
            }
            keyWordMap.put(entryTfIdf.getKey(), keyWordSet);
        }

        return keyWordMap;
    }

    public static TreeMap<String, Double> generateIdf(TreeMap<Integer, List<String>> notatkiPapMap, Integer notatkiCount) {
        TreeMap<String, Double> idf = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entryNotatka : notatkiPapMap.entrySet()) {
            List<String> listaSlow = entryNotatka.getValue();
            for (String s : listaSlow) {
                if (idf.containsKey(s)) {
                    Double liczbaWystapien = idf.get(s);
                    if (liczbaWystapien < notatkiCount) liczbaWystapien += 1.0d;
                    idf.put(s, liczbaWystapien);
                } else {
                    idf.put(s, 1d);
                }
            }
        }

        for (Map.Entry<String, Double> entryIdf : idf.entrySet()) {
            Double idfValue = entryIdf.getValue();
            idfValue = notatkiCount / idfValue;
            idfValue = Math.log10(idfValue);
            idf.put(entryIdf.getKey(), idfValue);
        }

        return idf;
    }

    public static TreeMap<Integer, TreeMap<String, Double>> generateTfIdf(TreeMap<Integer, List<String>> notatkiPapMap, TreeMap<String, Double> idf) {
        TreeMap<Integer, TreeMap<String, Double>> tfIdf = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entryNotatka : notatkiPapMap.entrySet()) {

            List<String> listaSlow = entryNotatka.getValue();
            TreeMap<String, Double> mapaWystapienSlow = new TreeMap<>();

            for (String s : listaSlow) {
                if (mapaWystapienSlow.containsKey(s)) {
                    Double liczbaWystapien = mapaWystapienSlow.get(s);
                    liczbaWystapien += 1.0d;
                    mapaWystapienSlow.put(s, liczbaWystapien);
                } else {
                    mapaWystapienSlow.put(s, 1d);
                }
            }

            for (Map.Entry<String, Double> mapaWystapienEntry : mapaWystapienSlow.entrySet()) {
                Double wordValue = mapaWystapienEntry.getValue();
                wordValue = wordValue * idf.get(mapaWystapienEntry.getKey());
                mapaWystapienSlow.put(mapaWystapienEntry.getKey(), wordValue);
            }

            tfIdf.put(entryNotatka.getKey(), mapaWystapienSlow);
        }

        return tfIdf;
    }

    public static TreeMap<Integer, List<String>> readPrzetrawioneNotatki() {
        Scanner inScanner;
        TreeMap<Integer, List<String>> notatkiPapMap = new TreeMap<>();

        try {
            inScanner = new Scanner(new FileReader("resources//lab7//notatkiPapMap.txt"));
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine().toLowerCase();
                String[] words = line.split(" ");
                Integer number = Integer.parseInt(words[0]);
                List<String> lista = new ArrayList<>();
                int i = 1;
                while (i < words.length) {
                    lista.add(words[i]);
                    i++;
                }
                notatkiPapMap.put(number, lista);
            }
            return notatkiPapMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
