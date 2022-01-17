package com.technogise.interns.srp;

public class TextDefinition {
    private String text;

    public TextDefinition(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public void printText() {
        System.out.println(getText());
    }
}
