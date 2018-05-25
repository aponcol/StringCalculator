package com.smartside;

import java.security.InvalidParameterException;

public final class StringCalculator {

    private StringCalculator() {}

    public static final String NEGATIVES_NOT_ALLOWED = "negatives not allowed:";
    public static final int UPPER_LIMIT = 1000;

    public static int Add(String input) {

        if (input.equals("")) return 0;

        int[] numbers = StringParser.getNumbers(input);

        return getSum(numbers);
    }

    private static int getSum(int[] numbers) {

        int sum = 0;
        String negativeNumbers = "";

        for (int number : numbers) {

            if (number < 0) {
                negativeNumbers += " " + number;
                continue;
            }

            if (number > UPPER_LIMIT) {
                continue;
            }

            sum += number;
        }

        if (negativeNumbers != "") throw new InvalidParameterException(NEGATIVES_NOT_ALLOWED + negativeNumbers);

        return sum;
    }



}
