package br.com.dantas.utils;

import static br.com.dantas.utils.NumberConverter.convertToDouble;

public class SimpleMath {

    public Double sum(String numberOne, String numberTwo) {
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    public Double subtraction(String numberOne, String numberTwo) {
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    public Double multiplication(String numberOne, String numberTwo) {
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    public Double division(String numberOne, String numberTwo) {
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    public Double mean(String numberOne, String numberTwo) {
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    public Double squareRoot(String number) {
        return Math.sqrt(convertToDouble(number));
    }
}
