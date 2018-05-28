package com.smartside;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class StringParser
{

    public static final  String defaultNegativeSymbol   = "-";
    public static final  String temporaryNegativeSymbol = "[NEG]";

    private final static String defaultDelimiters       = ",|\n";
    private final static String simpleDefaultDelimiter  = ",";
    private final static int    simpleDelimiterIndex    = 2;

    private final static String customDelimitersPrefix  = "//";
    private final static String complexDelimiterPrefix  = "[";
    // Ex. [;][***] In the pattern below each delimiter will match separately
    private final static String complexDelimiterPattern = "\\[((?!\\]\\[).)*\\]";

    private final static String startOfNumbersPrefix    = "\n";

    private StringParser() {}

    public static int[] getNumbers( String input )
    {

        String[] unparsedNumbers = getUnparsedNumbers(input);

        return getParsedNumbers(unparsedNumbers);

    }

    private static String[] getUnparsedNumbers( String input )
    {

        if (input.contains(customDelimitersPrefix))
        {
            input = getCleanInputWithSimpleDefaultDelimiter(input);
        }

        return input.split(defaultDelimiters);

    }

    private static String getCleanInputWithSimpleDefaultDelimiter( String input )
    {

        ArrayList<String> delimiters = getDelimiters(input);

        input = removeCustomDelimiters(input);

        for (String delimiter : delimiters)
        {

            if (delimiterEndsWithNegativeSymbol(delimiter))
            {
                input = replaceNegativeEndingDelimiterWithSimpleDelimiter(input, delimiter);
            }
            else
            {
                input = input.replace(delimiter, simpleDefaultDelimiter);
            }
        }

        return removeCustomDelimiters(input);
    }

    private static String replaceNegativeEndingDelimiterWithSimpleDelimiter( String input,
                                                                             String delimiter )
    {

        input = input.replace(delimiter + defaultNegativeSymbol, delimiter + temporaryNegativeSymbol);

        input = input.replace(delimiter, simpleDefaultDelimiter);

        return input.replace(temporaryNegativeSymbol, defaultNegativeSymbol);

    }

    private static boolean delimiterEndsWithNegativeSymbol( String delimiter )
    {
        return delimiter.charAt(delimiter.length() - 1) == '-';
    }

    private static ArrayList<String> getDelimiters( String input )
    {

        if (input.contains(complexDelimiterPrefix))
        {

            return getComplexDelimiters(input);

        }
        else
        {

            return getSimpleDelimiter(input);

        }
    }

    private static ArrayList<String> getComplexDelimiters( String input )
    {

        ArrayList<String> delimiters = new ArrayList<>();

        Pattern pattern = Pattern.compile(complexDelimiterPattern);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find())
        {

            delimiters.add(input.substring(matcher.start() + 1, matcher.end() - 1));

        }

        return delimiters;
    }

    private static ArrayList<String> getSimpleDelimiter( String input )
    {

        ArrayList<String> delimiters = new ArrayList<>();

        delimiters.add(Character.toString(input.charAt(simpleDelimiterIndex)));

        return delimiters;
    }

    private static String removeCustomDelimiters( String input )
    {

        return input.substring(input.indexOf(startOfNumbersPrefix) + 1);

    }

    private static int[] getParsedNumbers( String[] unparsedNumbers )
    {

        int[] parsedNumbers = new int[unparsedNumbers.length];

        for (int i = 0; i < parsedNumbers.length; i++)
        {
            parsedNumbers[i] = Integer.parseInt(unparsedNumbers[i]);
        }

        return parsedNumbers;
    }

}
