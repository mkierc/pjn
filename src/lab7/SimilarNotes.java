package lab7;

import lab1.GuessTheLanguage;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SimilarNotes {
    public static void main(String[] args) {
        TreeMap<Integer, Set<String>> keyWordsMap = KeyWords.readKeyWordsFromFile();
        System.out.println("Podaj numer notatki: ");
        Integer noteId = Integer.parseInt(GuessTheLanguage.getSampleFromInput());

        Set<String> searchedKeyWords = keyWordsMap.get(noteId);

        Set<Integer> znalezioneNotatki = new TreeSet<>();
        for(Map.Entry<Integer, Set<String>> entryKeyWord : keyWordsMap.entrySet()) {
            Set<String> notatkaKeyWords = entryKeyWord.getValue();
            for (String s : searchedKeyWords) {
                if (notatkaKeyWords.contains(s)) {
                    znalezioneNotatki.add(entryKeyWord.getKey());
                }
            }
        }

        if (znalezioneNotatki.isEmpty()) {
            System.out.print("Nie znaleziono podobnych notatek ze słowami kluczowymi '");
            for (String s : searchedKeyWords) {
                System.out.print(s + "', '");
            }
            System.out.println("'\n");
        } else {
            System.out.print("Słowa kluczowe dla wybranej notatki to: ");
            for (String s : searchedKeyWords) {
                System.out.print("'" + s + "' ");
            }
            System.out.println("\n");

            System.out.println("Znaleziono podobnych notatek [" + znalezioneNotatki.size() + "] o następujących słowach kluczowych: ");
            for (Integer i : znalezioneNotatki) {
                System.out.println(i + " : " + keyWordsMap.get(i));
            }
        }

    }
}
