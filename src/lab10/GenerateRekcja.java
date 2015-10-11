package lab10;

import java.io.*;
import java.util.*;

/**
 * Po zbudowaniu i uruchomieniu GenerateRekcja.main() należy odpalić z konsoli skrypt morfo-generate.bat
 */
public class GenerateRekcja {
    public static void main(String[] args) {
        Set<String> przyimkiSet = new TreeSet<>();

        przyimkiSet.add("od");
        przyimkiSet.add("do");
        przyimkiSet.add("na");
        przyimkiSet.add("pod");
        przyimkiSet.add("bez");

        TreeMap<String, Set<String>> przyimkiMap = findPrzyimki(przyimkiSet);

        //System.out.println(przyimkiMap.get("od"));

        for (String s : przyimkiSet) {
            writeToFile(przyimkiMap.get(s), s);
        }
        generateMorfoScript(przyimkiSet);
    }

    public static TreeMap<String, Set<String>> findPrzyimki(Set<String> przyimkiSet) {
        Scanner inScanner;
        TreeMap<String, Set<String>> przyimkiMap = new TreeMap<>();

        for (String s : przyimkiSet) {
            przyimkiMap.put(s, new TreeSet<>());
        }

        try {
            inScanner = new Scanner(new FileReader("resources//lab7//pap-probka.txt"));
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine().toLowerCase();
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
                line = line.replace(":", "");
                line = line.replace("\"","");
                line = line.replace("/","");
                line = line.replace("+", "");
                line = line.replaceAll("[0-9]*", "");
                String[] words = line.split(" ");

                int i = 0;
                while (i < words.length - 1) {
                    if (przyimkiSet.contains(words[i])) {
                        Set<String> rekcje = przyimkiMap.get(words[i]);
                        rekcje.add(words[i+1]);
                        przyimkiMap.put(words[i], rekcje);
                    }
                    i++;
                }
            }
            return przyimkiMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToFile(Set<String> lista, String fileName) {
        try {
            File outputFile = new File("resources//lab10//rekcja-" + fileName + ".txt");
            File parentDirectory = outputFile.getParentFile();

            if (null != parentDirectory)
                parentDirectory.mkdirs();

            FileWriter fw = new FileWriter(outputFile);

            for (String s : lista) {
                fw.write(s + "\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void generateMorfoScript(Set<String> przyimki) {
        try {
            File outputFile = new File("resources//lab10//morfo-generate.bat");
            File parentDirectory = outputFile.getParentFile();

            if (null != parentDirectory)
                parentDirectory.mkdirs();

            FileWriter fw = new FileWriter(outputFile);

            fw.write("cd PJN/lab10\n");
            fw.write("echo %cd%\n");
            for (String s : przyimki) {
                fw.write("java -jar morfologik-tools-1.9.0-standalone.jar plstem -oe utf-8 -i rekcja-" + s + ".txt -o output-" + s + ".txt\n");
            }
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}


