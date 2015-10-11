package lab7;

import lab1.GuessTheLanguage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class KeyWords {
    public static void main(String[] args) {
        TreeMap<Integer, Set<String>> keyWordsMap = readKeyWordsFromFile();
        System.out.println("Podaj słowo kluczowe: ");
        String keyWord = GuessTheLanguage.getSampleFromInput();

        Set<Integer> znalezioneNotatki = new TreeSet<>();
        for (Map.Entry<Integer, Set<String>> entryKeyWord : keyWordsMap.entrySet()) {
            Set<String> notatkaKeyWords = entryKeyWord.getValue();
            if (notatkaKeyWords.contains(keyWord)) {
                znalezioneNotatki.add(entryKeyWord.getKey());
            }
        }
        if (znalezioneNotatki.isEmpty()) {
            System.out.println("Nie znaleziono notatek o słowie kluczowym '" + keyWord + "'");
        } else {
            System.out.println("Znaleziono notatek [" + znalezioneNotatki.size() + "] o następujących słowach kluczowych: ");
            for (Integer i : znalezioneNotatki) {
                System.out.println(i + " : " + keyWordsMap.get(i));
            }
        }

        /*
        // Wyświetlenie słów kluczowych dla kilku notatek
        System.out.println("\nSłowa kluczowe notatki nr 1 : \n" + keyWordsMap.get(1));
        System.out.println("Słowa kluczowe notatki nr 2 : \n" + keyWordsMap.get(2));
        System.out.println("Słowa kluczowe notatki nr 5 : \n" + keyWordsMap.get(5));
        */
    }

    public static TreeMap<Integer, Set<String>> readKeyWordsFromFile() {
        Scanner inScanner;
        TreeMap<Integer, Set<String>> keyWordsMap = new TreeMap<>();

        try {
            inScanner = new Scanner(new FileReader("resources//lab7//slowaKluczowe.txt"));
            while (inScanner.hasNextLine()) {
                String line = inScanner.nextLine().toLowerCase();
                String[] words = line.split(" ");
                Integer number = Integer.parseInt(words[0]);
                Set<String> lista = new TreeSet<>();
                int i = 1;
                while (i < words.length) {
                    lista.add(words[i]);
                    i++;
                }
                keyWordsMap.put(number, lista);
            }
            return keyWordsMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
