package lab1;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuessTheLanguage {
    public static void main(String[] args) {
        int nGramLength = 2;
        TreeMap<String, String> languagesFilenamesMap = getLanguagesFilesMap();
        TreeMap<String, TreeMap<String, Integer>> languagesStatisticsMap = getLanguagesStatisticsMap(languagesFilenamesMap, nGramLength);
        System.out.println("Wprowadź zdanie do rozpoznania: ");
        TreeMap<String, Integer> sampleStatistics = generateStatisticsFromSample(getSampleFromInput(), nGramLength);
        TreeMap<Double, String> resultMap = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<String, TreeMap<String, Integer>> entry : languagesStatisticsMap.entrySet())
            resultMap.put(countDistance(sampleStatistics, languagesStatisticsMap.get(entry.getKey())), entry.getKey());

        double max = 0;
        for (Map.Entry<Double, String> entry : resultMap.entrySet())
            if (entry.getKey() > max)
                max = entry.getKey();

        System.out.println("Rozpoznany język to: " + languagesFilenamesMap.get(resultMap.get(max)));

        // Cała statystyka

        System.out.printf("%15s\t\t%s\n--------------------------------------\n", "Język(plik)", "Odległość");
        for (Map.Entry<Double, String> entry : resultMap.entrySet())
            System.out.printf("%15s\t\t%.16f\n", languagesFilenamesMap.get(entry.getValue()), entry.getKey());
    }

    public static double countDistance(TreeMap<String, Integer> sampleMap1, TreeMap<String, Integer> sampleMap2) {
        // Wyrównanie różnic n-gramów w mapach
        for (Map.Entry<String, Integer> entry : sampleMap1.entrySet())
            if (!sampleMap2.containsKey(entry.getKey()))
                sampleMap2.put(entry.getKey(), 0);
        for (Map.Entry<String, Integer> entry : sampleMap2.entrySet())
            if (!sampleMap1.containsKey(entry.getKey()))
                sampleMap1.put(entry.getKey(), 0);

        // Pobranie ilości wystąpień
        int total1 = sampleMap1.get("-total");
        int total2 = sampleMap2.get("-total");

        // Policzenie odległości
        sampleMap1.remove("-total");
        sampleMap2.remove("-total");
        double distance = 0;
        int count = 0;
        for (Map.Entry<String, Integer> entry : sampleMap1.entrySet()) {
            double value1 = (double)(sampleMap1.get(entry.getKey())) / (double)total1;
            double value2 = (double)(sampleMap2.get(entry.getKey())) / (double)total2;
            distance += value1 * value2;
            count++;
        }
        sampleMap1.put("-total", total1);
        sampleMap2.put("-total", total2);
        return distance / (double)count;
    }

    public static String getSampleFromInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TreeMap<String, Integer> generateStatisticsFromSample(String sample, int nGramLength) {
        TreeMap<String, Integer> nGramMap = new TreeMap<>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(?=(");
        for (int i = 0; i < nGramLength; i++)
            stringBuilder.append("[a-z]");
        stringBuilder.append(")).");

        Pattern pattern = Pattern.compile(stringBuilder.toString());
        Matcher matcher;

        int total = 0;

        matcher = pattern.matcher(sample.toLowerCase());
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
        nGramMap.put("-total", total);
        return nGramMap;
    }

    public static TreeMap<String, String> getLanguagesFilesMap() {
        TreeMap<String, String> languagesFilesMap = new TreeMap<>();
        languagesFilesMap.put("ang-1","Angielski(1)");
        languagesFilesMap.put("ang-2","Angielski(2)");
        languagesFilesMap.put("ang-3","Angielski(3)");
        languagesFilesMap.put("ang-4","Angielski(4)");
        languagesFilesMap.put("fin-1","Fiński(1)");
        languagesFilesMap.put("fin-2","Fiński(2)");
        languagesFilesMap.put("his-1","Hiszpański(1)");
        languagesFilesMap.put("his-2","Hiszpański(2)");
        languagesFilesMap.put("nie-1","Niemiecki(1)");
        languagesFilesMap.put("nie-2","Niemiecki(2)");
        languagesFilesMap.put("nie-3","Niemiecki(3)");
        languagesFilesMap.put("nie-4","Niemiecki(4)");
        languagesFilesMap.put("pol-1","Polski(1)");
        languagesFilesMap.put("pol-2","Polski(2)");
        languagesFilesMap.put("pol-3","Polski(3)");
        languagesFilesMap.put("wlo-1","Włoski(1)");
        languagesFilesMap.put("wlo-2","Włoski(2)");
        return languagesFilesMap;
    }

    public static TreeMap<String, TreeMap<String, Integer>> getLanguagesStatisticsMap(TreeMap<String, String> languagesFilenamesMap, int nGramLength) {
        TreeMap<String, TreeMap<String, Integer>> languagesStatisticsMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : languagesFilenamesMap.entrySet())
            languagesStatisticsMap.put(entry.getKey(), ReadStatistics.readStatistics(entry.getKey(), nGramLength));
        return languagesStatisticsMap;
    }
}
