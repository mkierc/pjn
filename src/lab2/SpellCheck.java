package lab2;

import lab1.GuessTheLanguage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellCheck {
    public static void main(String[] args) {
        List<String> dictionary = new ArrayList<>();

        Scanner inScanner;
        try {
            inScanner = new Scanner(new FileReader("resources//lab2//formy.txt"));
            while (inScanner.hasNextLine()) {
                dictionary.add(inScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Podaj słowo: ");
        String wordToTest = GuessTheLanguage.getSampleFromInput();


        float minimalDistance = Float.MAX_VALUE;
        String bestGuess = null;
        for(String dictWord : dictionary) {
            float temp = Levenshtein.countLevenshteinDistance(wordToTest, dictWord, false);
            if (temp < minimalDistance) {
                minimalDistance = temp;
                bestGuess = dictWord;
            }
            if (temp == 0.0) {
                bestGuess = dictWord;
                break;
            }
        }

        if (wordToTest.equals(bestGuess))
            System.out.println(wordToTest + " jest poprawne.");
        else
            System.out.println("Czy chodziło ci o " + bestGuess + "?");

    }
}
