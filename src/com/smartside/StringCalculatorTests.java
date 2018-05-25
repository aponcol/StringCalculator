package com.smartside;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

public class StringCalculatorTests
{

    @Test
    public void Add_EmptyString_Returns_0()
    {

        Assert.assertEquals(0, StringCalculator.Add(""));

    }

    @Test
    public void Add_0_Returns_0()
    {

        String input = "0";

        Assert.assertEquals(0, StringCalculator.Add(input));

    }

    @Test
    public void Add_Single_Number_Returns_Same_Number()
    {

        String input = "1";

        Assert.assertEquals(1, StringCalculator.Add(input));

    }

    @Test
    public void Add_Different_Single_Number_Returns_Same_Number()
    {

        String input = "4";

        Assert.assertEquals(4, StringCalculator.Add(input));

    }

    @Test
    public void Add_Two_Numbers_Returns_Sum()
    {

        String input = "1,2";

        Assert.assertEquals(3, StringCalculator.Add(input));

    }

    @Test
    public void Add_Multiple_Numbers_Returns_Sum()
    {

        String input = "5,5,6";

        Assert.assertEquals(16, StringCalculator.Add(input));

    }

    @Test
    public void New_Line_Can_Be_Used_As_Delimiter_For_Input()
    {

        String input = "1\n2,3";

        Assert.assertEquals(6, StringCalculator.Add(input));

    }

    @Test
    public void Custom_Delimiter_Is_Allowed()
    {

        String input = "//;\n1;2";

        Assert.assertEquals(3, StringCalculator.Add(input));

    }

    @Test(expected = InvalidParameterException.class)
    public void Negative_Number_Throw_Invalid_Input_Exception()
    {

        String input = "-1";

        StringCalculator.Add(input);
    }

    @Test(expected = InvalidParameterException.class)
    public void Another_Negative_Number_Throw_Invalid_Input_Exception()
    {

        String input = "-3";

        StringCalculator.Add(input);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void Negative_Number_Throw_Invalid_Input_With_Message() throws InvalidParameterException
    {

        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("negatives not allowed: -5");

        String input = "-5";

        StringCalculator.Add(input);
    }

    @Test
    public void Multiple_Negative_Numbers_Throw_Invalid_Input_With_All_Negative_Numbers_In_Message()
            throws InvalidParameterException
    {

        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("negatives not allowed: -3 -5");

        String input = "-3,-5";

        StringCalculator.Add(input);

    }

    @Test
    public void Numbers_Below_Upper_Limit_Are_Added()
    {

        String input = "1,1000";

        Assert.assertEquals(1001, StringCalculator.Add(input));
    }

    @Test
    public void Numbers_Above_Upper_Limit_Are_Not_Added()
    {

        String input = "2,1001";

        Assert.assertEquals(2, StringCalculator.Add(input));
    }

    @Test
    public void Complex_MultiCharacter_Delimiter_Is_Allowed()
    {

        String input = "//[***]\n1***2***3";

        Assert.assertEquals(6, StringCalculator.Add(input));
    }

    @Test
    public void Another_Complex_MultiCharacter_Delimiter_Is_Allowed()
    {

        String input = "//[*d*]\n2*d*2*d*3";

        Assert.assertEquals(7, StringCalculator.Add(input));
    }

    @Test
    public void Multiple_Complex_SingleCharacter_Delimiter_Is_Allowed()
    {

        String input = "//[*][&]\n1*2&3";

        Assert.assertEquals(6, StringCalculator.Add(input));
    }

    @Test
    public void Multiple_Complex_MultiCharacter_Delimiter_Is_Allowed()
    {

        String input = "//[***][&&&]\n1***2&&&3";

        Assert.assertEquals(6, StringCalculator.Add(input));
    }

    @Test
    public void Another_Multiple_Complex_MultiCharacter_Delimiter_Is_Allowed()
    {

        String input = "//[*&*][&*&]\n3*&*2&*&3";

        Assert.assertEquals(8, StringCalculator.Add(input));
    }

    @Test
    public void closingBracket()
    {

        String input = "//[]]\n3]2]3";

        Assert.assertEquals(8, StringCalculator.Add(input));
    }

    @Test
    public void negativeNumberWithDashSeparator() throws InvalidParameterException
    {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("negatives not allowed: -2");

        String input = "//[-]\n3--2-3";

        StringCalculator.Add(input);
    }
}
