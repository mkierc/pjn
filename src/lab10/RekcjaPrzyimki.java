package lab10;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class RekcjaPrzyimki {
    public static void main(String[] args) {
        Set<String> przyimkiSet = new TreeSet<>();

        przyimkiSet.add("od");
        przyimkiSet.add("do");
        przyimkiSet.add("na");
        przyimkiSet.add("pod");
        przyimkiSet.add("bez");

        TreeMap<String, Set<String>> rekcjeMap = readGeneratedRekcje(przyimkiSet);


        for (String przyimek : przyimkiSet) {
            System.out.println("Przyimek '" + przyimek + "', tworzy związek rządu z:");
            for (Map.Entry<String, Set<String>> entry : rekcjeMap.entrySet()) {
                Set<String> rekcjeSet = entry.getValue();
                for (String rekcja : rekcjeSet){
                    System.out.println(rekcja);
                }
            }
            System.out.println("-----------------------------------------------------------------");
        }


    }

    public static TreeMap<String, Set<String>> readGeneratedRekcje(Set<String> przyimkiSet) {
        Scanner inScanner;
        TreeMap<String, Set<String>> rekcjeMap = new TreeMap<>();

        for (String s : przyimkiSet) {
            rekcjeMap.put(s, new TreeSet<>());
        }

        try {
            for (String s : przyimkiSet) {
                inScanner = new Scanner(new FileReader("resources//lab10//output-" + s + ".txt"));
                while (inScanner.hasNextLine()) {
                    String line = inScanner.nextLine().toLowerCase();
                    String[] words = line.split("\t");

                    if (words.length > 2 && !words[2].equals("-")) {
                        String[] odmiany = words[2].split("\\+");
                        int i = 0;
                        while (i < odmiany.length) {
                            Set<String> rekcjeSet = rekcjeMap.get(s);
                            rekcjeSet.add(odmiany[i]);
                            rekcjeMap.put(s, rekcjeSet);
                            i++;
                        }
                    }
                }
            }
            return rekcjeMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
