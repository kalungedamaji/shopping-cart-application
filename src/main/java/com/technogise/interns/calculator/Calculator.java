package com.technogise.interns.calculator;

public class Calculator {

    public int sum(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    public int minus(int a, int b) {
        return a - b;
    }

    public int divide(int a, int b) {
        return a / b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int min(int a, int b) {
        return a<b?a:b;
    }

    public int max(int a, int b) {
        return a>b?a:b;
    }
}
