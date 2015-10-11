package lab2;

import lab1.GuessTheLanguage;

import java.util.HashMap;

public class Levenshtein {
    private static final HashMap<Character, Character> polishDiacriticalsMap = createDiacriticalMap();
    private static final HashMap<Character, Character> polishOrtographicMap = createOrthographicMap();

    public static void main(String[] args) {

        System.out.println("Podaj pierwsze słowo: ");
        String word1 = GuessTheLanguage.getSampleFromInput();
        System.out.println("Podaj drugie słowo: ");
        String word2 = GuessTheLanguage.getSampleFromInput();
        System.out.println("Levenshtein distance between \"" + word1.toUpperCase()
                + "\" and \"" + word2.toUpperCase() + "\" : " + countLevenshteinDistance(word1, word2, true));
    }

    public static float countLevenshteinDistance(String word1, String word2, boolean showDistanceTable) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        float[][] distance = new float[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= word2.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= word1.length(); i++)
            for (int j = 1; j <= word2.length(); j++) {
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + replacingDistance(word1.charAt(i - 1), word2.charAt(j - 1)),
                        distance[i - 1][j - 1] + ortographicDistance(word1.charAt(i - 1), word2.charAt(j - 1)),
                        distance[i - 1][j - 1] + diacriticalDistance(word1.charAt(i - 1), word2.charAt(j - 1)));

                // TODO: Czeskie błędy - obecnie nie działa (wyjeżdza poza zakres tablicy)
                /*
                if (i > 1 && j > 1 && word1.charAt(i - 1) == word2.charAt(j - 2) && word1.charAt(i - 2) == word2.charAt(j - 1))
                    if (! (word1.charAt(i - 1) == word2.charAt(i - 2)))
                        distance[i][j] -= 1.5f;
                */

                // Wypisywanie tablicy
                if (showDistanceTable) {
                    if (j == 1) System.out.println();
                    System.out.print(distance[i][j] + " ");
                }
            }

        return distance[word1.length()][word2.length()];
    }

    private static float replacingDistance(char c1, char c2) {
        if (c1 == c2)
            return 0.0f;
        else
            return 1.0f;
    }

    // A, B  == 1.0
    // A, Ą  == 0.5
    // A, A  == 0.0
    private static float diacriticalDistance(Character c1, Character c2) {
        if (c1.equals(c2))
            return 0.0f;
        else if (replaceDiacrital(c1).equals(replaceDiacrital(c2)))
            return 0.5f;
        else
            return 1.0f;
    }

    // Ó, P  == 1.0
    // Ó, U  == 0.5
    // Ó, Ó  == 0.0
    private static float ortographicDistance(Character c1, Character c2) {
        if (c1.equals(c2))
            return 0.0f;
        else if (replaceOrthographic(c1).equals(c2))
            return 0.5f;
        else
            return 1.0f;
    }

    private static Character replaceDiacrital(Character c) {
        if (polishDiacriticalsMap.containsKey(c))
            return polishDiacriticalsMap.get(c);
        else
            return c;
    }

    private static Character replaceOrthographic(Character c) {
        if (polishOrtographicMap.containsKey(c))
            return polishOrtographicMap.get(c);
        else
            return c;
    }

    private static HashMap<Character, Character> createDiacriticalMap() {
        HashMap<Character, Character> lettersMap = new HashMap<>();
        lettersMap.put('ą','a');
        lettersMap.put('ć','c');
        lettersMap.put('ę','e');
        lettersMap.put('ł','l');
        lettersMap.put('ń','n');
        lettersMap.put('ó','o');
        lettersMap.put('ś','s');
        lettersMap.put('ż','z');
        lettersMap.put('ź','z');
        return lettersMap;
    }

    private static HashMap<Character, Character> createOrthographicMap() {
        HashMap<Character, Character> lettersMap = new HashMap<>();
        lettersMap.put('ó','u');
        lettersMap.put('u','ó');
        lettersMap.put('w','f');
        lettersMap.put('f','w');
        return lettersMap;
    }

    private static float minimum(float a, float b, float c, float d, float e) {
        return Math.min(Math.min(Math.min(Math.min(a, b), c), d), e);
    }
}
