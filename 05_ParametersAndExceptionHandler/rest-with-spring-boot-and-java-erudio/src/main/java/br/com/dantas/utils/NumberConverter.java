package br.com.dantas.utils;

import br.com.dantas.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public static Double convertToDouble(String strNumber) {
        return Double.parseDouble(convertNumeric(strNumber));
    }

    private static String convertNumeric(String strNumber) {
        if (!isNumeric(strNumber)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return strNumber.replace(",", ".");
    }

    private static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
