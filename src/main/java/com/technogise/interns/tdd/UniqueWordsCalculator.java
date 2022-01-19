package com.technogise.interns.tdd;

import java.util.Collections;
import java.util.HashSet;

public class UniqueWordsCalculator {

    private final static int ZERO_UNIQUE_WORDS = 0;
    private final static String SPACE_DELIMITER = " ";
    private final static String PUNCTUATIONS = "\\p{Punct}";
    private final static String EMPTY_STRING = "";

    public int getNumberOfUniqueWords(String inputString){

        if (inputString.isEmpty()) {
            return ZERO_UNIQUE_WORDS;
        }else{
            String inputStringInLowerCase = inputString.toLowerCase();
            String inputStringInLowerCaseWithotPunctuations = inputStringInLowerCase.replaceAll(PUNCTUATIONS,EMPTY_STRING);
            String[] arrayOfWords = inputStringInLowerCaseWithotPunctuations.split(SPACE_DELIMITER);
            HashSet<String> setOfWords = new HashSet<>();
            Collections.addAll(setOfWords,arrayOfWords);
            int numberOfUniqueWords = setOfWords.size();
            return numberOfUniqueWords;
        }
    }
}




