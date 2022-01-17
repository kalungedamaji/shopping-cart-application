package com.technogise.interns.srp;

public class TextManipulator {
String ManipulatedText;
TextDefinition object;
        public void appendText(String newText) {
           ManipulatedText = object.getText().concat(newText);
        }

        public String findWordAndReplace(String word, String replacementWord) {
            if (object.getText().contains(word)) {
                ManipulatedText = object.getText().replace(word, replacementWord);
            }
            return ManipulatedText;
        }

        public String findWordAndDelete(String word) {
            if (object.getText().contains(word)) {
                ManipulatedText = object.getText().replace(word, "");
            }
            return ManipulatedText;
        }

    }

