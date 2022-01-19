package com.technogise.interns.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueWordsTest {
    final private UniqueWordsCalculator uniqueWordsCalculator = new UniqueWordsCalculator();

    @Test
    public void testEmptyStringReturnsZeroUniqueWords(){
        String inputString = "";
        int EXPECTED_NUMBER_OF_UNIQUE_WORDS = 0;

        int actualNumberOfUniqueWords = uniqueWordsCalculator.getNumberOfUniqueWords(inputString);

        assertEquals(EXPECTED_NUMBER_OF_UNIQUE_WORDS,actualNumberOfUniqueWords);

    }
    @Test
    public void testOneWordStringReturnsOneUniqueWord(){
        String inputString = "Pranay";
        int EXPECTED_NUMBER_OF_UNIQUE_WORDS = 1;

        int actualNumberOfUniqueWords = uniqueWordsCalculator.getNumberOfUniqueWords(inputString);

        assertEquals(EXPECTED_NUMBER_OF_UNIQUE_WORDS,actualNumberOfUniqueWords);
    }

    @Test
    public void testMultiWordUniqueStringReturnsNumberOfWordsInString(){
        String inputString = "My name is Pranay Jain";
        int EXPECTED_NUMBER_OF_UNIQUE_WORDS = 5;

        int actualNumberOfUniqueWords = uniqueWordsCalculator.getNumberOfUniqueWords(inputString);

        assertEquals(EXPECTED_NUMBER_OF_UNIQUE_WORDS,actualNumberOfUniqueWords);
    }
    @Test
    public void testMultiWordWithDuplicatesReturnsNumberOfUniqueWordsInString(){
        String inputString = "Pranay Pranay Jain";
        int EXPECTED_NUMBER_OF_UNIQUE_WORDS = 2;

        int actualNumberOfUniqueWords = uniqueWordsCalculator.getNumberOfUniqueWords(inputString);

        assertEquals(EXPECTED_NUMBER_OF_UNIQUE_WORDS,actualNumberOfUniqueWords);
    }
    @Test
    public void testMultiWordWithDuplicatesInMixedCaseReturnsNumberOfUniqueWordsInString(){
        String inputString = "Pranay pranay Jain";
        int EXPECTED_NUMBER_OF_UNIQUE_WORDS = 2;

        int actualNumberOfUniqueWords = uniqueWordsCalculator.getNumberOfUniqueWords(inputString);

        assertEquals(EXPECTED_NUMBER_OF_UNIQUE_WORDS,actualNumberOfUniqueWords);

    }
    @Test
    public void testMultiWordWithDuplicatesAndPunctuationsInMixedCasesReturnsNumberOfUniqueWordsInString(){
        String inputString = "Eggs, eggs in the egg's.";
        int EXPECTED_NUMBER_OF_UNIQUE_WORDS = 3;

        int actualNumberOfUniqueWords = uniqueWordsCalculator.getNumberOfUniqueWords(inputString);

        assertEquals(EXPECTED_NUMBER_OF_UNIQUE_WORDS,actualNumberOfUniqueWords);

    }

}
