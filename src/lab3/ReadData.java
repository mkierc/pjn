package lab3;

import lab2.Levenshtein;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadData {
    public static void main(String[] args) {
        TreeMap<Float, Double> errorStatisticMap = countErrorStatistic();
        TreeMap<String, Double> formsStatisticMap = countFormsStatistic("wp");

//        for (Map.Entry<Float, Double> entry : errorStatisticMap.entrySet())
//            System.out.println(entry.getKey() + " " + entry.getValue());

        for (Map.Entry<String, Double> entry : formsStatisticMap.entrySet())
            System.out.printf("%25s\t\t%.16f\n", entry.getKey(), entry.getValue());

    }

    public static TreeMap<Float, Double> countErrorStatistic() {
        Scanner inScanner;
        TreeMap<Float, Double> errorStatisticMap = new TreeMap<>();
        try {
            inScanner = new Scanner(new FileReader("resources//lab3//bledy.txt"));

            int count = 0;
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine();
                String[] errors = line.split(";");
                float distance = Levenshtein.countLevenshteinDistance(errors[0], errors[1], false);
//                System.out.println(errors[0] + " " + errors[1] + " " + distance);

                if (errorStatisticMap.containsKey(distance)){
                    double value = errorStatisticMap.get(distance);
                    value++;
                    errorStatisticMap.put(distance, value);
                } else {
                    errorStatisticMap.put(distance, 1.0);
                }
                count++;
            }
            for (Map.Entry<Float, Double> entry : errorStatisticMap.entrySet()) {
                double value = entry.getValue();
                value /= count;
                errorStatisticMap.put(entry.getKey(), value);
            }
            return errorStatisticMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Nie wczytano statystyk.");
        }
        return null;
    }

    public static TreeMap<String, Double> countFormsStatistic(String fileName) {
        final int ILOSC_WSZYSTKICH_FORM = 1374255;
        Scanner inScanner;
        TreeMap<String, Double> formsStatisticMap = new TreeMap<>();
        try {
            inScanner = new Scanner(new FileReader("resources//lab3//" + fileName + ".iso.utf8"));

            Pattern pattern = Pattern.compile("[a-zA-ZąćęłńóśżźĄĆĘŁŃÓŚŻŹ]+");
            Matcher matcher;

            int count = 0;

            while (inScanner.hasNextLine()) {
                matcher = pattern.matcher(inScanner.nextLine().toLowerCase());
                while (matcher.find()) {
                    if (formsStatisticMap.containsKey(matcher.group(0))){
                        double value = formsStatisticMap.get(matcher.group(0));
                        value++;
                        formsStatisticMap.put(matcher.group(0), value);
                    } else {
                        formsStatisticMap.put(matcher.group(0), 1.0);
                    }
                    count++;
                }
            }
            for (Map.Entry<String, Double> entry : formsStatisticMap.entrySet()) {
                double value = entry.getValue();
                value++;
                value /= (count + ILOSC_WSZYSTKICH_FORM);
                formsStatisticMap.put(entry.getKey(), value);
            }
            return formsStatisticMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Nie wczytano statystyk.");
        }
        return null;
    }
}
