package com.technogise.interns.srp;

public class TextManipulator {
private String manipulatedText;
      public TextManipulator(String text){
          manipulatedText=text;
      }
        public void appendText(String newText) {

            manipulatedText=manipulatedText.concat(newText);
        }

        public String findWordAndReplace(String word, String replacementWord) {
            if (manipulatedText.contains(word)) {
                manipulatedText=manipulatedText.replace(word, replacementWord);
            }
            return manipulatedText;
        }

        public String findWordAndDelete(String word) {
            if (manipulatedText.contains(word)) {
                manipulatedText= manipulatedText.replace(word, "");
            }
            return manipulatedText;
        }

    }

