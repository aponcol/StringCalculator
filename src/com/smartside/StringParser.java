package com.smartside;

import java.util.ArrayList;

public final class StringParser {

    private StringParser() {}

    private final static String defaultDelimiters = ",|\n";

    private final static String customDelimitersPrefix = "//";

    private final static String simpleDefaultDelimiter = ",";
    private final static int simpleDelimiterIndex = 2;

    private final static String complexDelimiterPrefix = "[";
    private final static String complexDelimiterSuffix = "]";

    private final static String startOfNumbersPrefix = "\n";

    public static int[] getNumbers(String input) {

        String[] unparsedNumbers = getUnparsedNumbers(input);

        return getParsedNumbers(unparsedNumbers);

    }

    private static String[] getUnparsedNumbers(String input) {

        if (input.contains(customDelimitersPrefix)) {
            input = getCleanInputWithSimpleDefaultDelimiter(input);
        }

        return input.split(defaultDelimiters);

    }

    private static String getCleanInputWithSimpleDefaultDelimiter(String input) {

        ArrayList<String> delimiters = getDelimiters(input);

        for (String delimiter : delimiters) {
            input = input.replace(delimiter, simpleDefaultDelimiter);
        }

        return removeCustomDelimiters(input);
    }

    private static ArrayList<String> getDelimiters(String input) {

        if (input.contains(complexDelimiterPrefix)) {

            return getComplexDelimiters(input);

        } else {

            return getSimpleDelimiter(input);

        }
    }

    private static ArrayList<String> getComplexDelimiters(String input) {

        ArrayList<String> delimiters = new ArrayList<>();

        do {

            delimiters.add(

                input.substring(
                    input.indexOf(complexDelimiterPrefix) + 1,
                    input.indexOf(complexDelimiterSuffix)
                )

            );

            input = input.substring(
                input.indexOf(complexDelimiterSuffix) + 1
            );

        } while (input.contains(complexDelimiterPrefix));

        return delimiters;
    }

    private static ArrayList<String> getSimpleDelimiter(String input) {

        ArrayList<String> delimiters = new ArrayList<>();

        delimiters.add(
            Character.toString(
                input.charAt(simpleDelimiterIndex)
            )
        );

        return delimiters;
    }

    private static String removeCustomDelimiters(String input) {

        return input.substring(
            input.indexOf(startOfNumbersPrefix) + 1
        );

    }

    private static int[] getParsedNumbers(String[] unparsedNumbers) {

        int[] parsedNumbers = new int[unparsedNumbers.length];

        for (int i = 0; i < parsedNumbers.length; i++) {
            parsedNumbers[i] = Integer.parseInt(unparsedNumbers[i]);
        }

        return parsedNumbers;
    }

}
