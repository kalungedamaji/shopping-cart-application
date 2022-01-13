package com.technogise.interns.calculator;

public class Calculator {

    public int sum(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }
    public int minus(int firstNumber, int secondNumber) {
        return firstNumber - secondNumber;
    }
    public int divide(int firstNumber, int secondNumber) {
        return firstNumber / secondNumber;
    }
    public int multiply(int firstNumber, int secondNumber) {
        return firstNumber * secondNumber;
    }
    public int min(int firstNumber, int secondNumber) {

        return firstNumber < secondNumber ? firstNumber : secondNumber;

    }
    public int max(int firstNumber, int secondNumber) {
        return firstNumber >= secondNumber ? firstNumber : secondNumber;
    }
}
