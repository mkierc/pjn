package lab7;

import java.io.*;
import java.util.*;

import static lab6.Ranking.generateDictionaryFromFile;

public class GeneratePapMap {
    public static void main(String[] args) {
        TreeMap<String, String> dictMap = generateDictionaryFromFile();
        TreeMap<Integer, List<String>> notatkiPapMap = readNotatkiPapFromFile(dictMap);

        Integer notatkiCount = 0;
        for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
            if (entry.getKey() > notatkiCount)
                notatkiCount = entry.getKey();
        }
        writeNotatkiPapMapToFile(notatkiPapMap);

        // Wyświetlenie przykładowej notatki w postaci BAG OF WORDS:
        System.out.println((notatkiPapMap.get(1)));
        // Wyświetlenie notatkiCount
        System.out.println(notatkiCount);
    }


    public static TreeMap<Integer, List<String>> readNotatkiPapFromFile(TreeMap<String, String> dictMap) {
        Scanner inScanner;
        TreeMap<Integer, List<String>> notatkiPapMap = new TreeMap<>();
        final CharSequence HASH = "#";
        Integer number = 0;

        try {
            inScanner = new Scanner(new FileReader("resources//lab7//pap-probka.txt"));

            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine().toLowerCase();
                if (line.contains(HASH)) {
                    String[] splittedLine = line.split("#");
                    number = Integer.parseInt(splittedLine[1]);
                    notatkiPapMap.put(number, new ArrayList<>());
                } else {
                    line = line.replace(".","");
                    line = line.replace(",","");
                    line = line.replace("-","");
                    line = line.replace("!","");
                    line = line.replace("?","");
                    line = line.replace("(","");
                    line = line.replace(")","");
                    line = line.replace("<","");
                    line = line.replace(">","");
                    line = line.replace(";", "");
                    line = line.replace("\"","");
                    String[] words = line.split(" ");

                    List<String> lista = notatkiPapMap.get(number);

                    int i = 0;
                    while (i < words.length) {
                        if (dictMap.containsKey(words[i])) {
                            lista.add(dictMap.get(words[i]));
                        }
                        i++;
                    }
                    notatkiPapMap.put(number, lista);
                }
            }
            return notatkiPapMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeNotatkiPapMapToFile(TreeMap<Integer, List<String>> notatkiPapMap) {
        try {
            File outputFile = new File("resources//lab7//notatkiPapMap.txt");
            File parentDirectory = outputFile.getParentFile();

            if (null != parentDirectory)
                parentDirectory.mkdirs();

            FileWriter fw = new FileWriter(outputFile);

            for (Map.Entry<Integer, List<String>> entry : notatkiPapMap.entrySet()) {
                fw.write(entry.getKey() + " ");
                List<String> lista = entry.getValue();
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

}
