package edu.hw3;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("MagicNumber")
public class Task4 {
    private static final Map<Integer, String> ROMAN_NUM_MAP;

    static {
        ROMAN_NUM_MAP = new LinkedHashMap<>();
        ROMAN_NUM_MAP.put(1000, "M");
        ROMAN_NUM_MAP.put(900, "CM");
        ROMAN_NUM_MAP.put(500, "D");
        ROMAN_NUM_MAP.put(400, "CD");
        ROMAN_NUM_MAP.put(100, "C");
        ROMAN_NUM_MAP.put(90, "XC");
        ROMAN_NUM_MAP.put(50, "L");
        ROMAN_NUM_MAP.put(40, "XL");
        ROMAN_NUM_MAP.put(10, "X");
        ROMAN_NUM_MAP.put(9, "IX");
        ROMAN_NUM_MAP.put(5, "V");
        ROMAN_NUM_MAP.put(4, "IV");
        ROMAN_NUM_MAP.put(1, "I");
    }

    private Task4() {

    }

    public static String convertRoman(int inputNum) {
        if (inputNum < 1 || inputNum > 3999) {
            throw new IllegalArgumentException();
        }

        StringBuilder result = new StringBuilder();
        int convertingNum = inputNum;
        for (var romanNum : ROMAN_NUM_MAP.entrySet()) {
            while (romanNum.getKey() <= convertingNum) {
                convertingNum -= romanNum.getKey();
                result.append(romanNum.getValue());
            }
        }
        return result.toString();
    }
}
