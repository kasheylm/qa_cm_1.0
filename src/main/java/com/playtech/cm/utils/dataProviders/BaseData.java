package com.playtech.cm.utils.dataProviders;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.DataProvider;

/**
 * User: Denis Veselovskiy
 * Date: 10/9/12 * Time: 5:25 PM
 */
public class BaseData {

    public static Object generateText(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static Object generateDigits(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    @DataProvider
    public static Object[][] validateFieldBase() {
        Object[][] text = {
                {"<script language='JavaScript'> alert('Добрый день') </script>"},
                {"ASDGFFLKLKLKlddvddsghdsh"},
                {":!@#$%^&*()_."},
                {"ASDASDasdas-123123211212&^%&^%"},
        };
        return(text);
    }

    @DataProvider
    public static Object[][] validateFieldWithDigits() {
        return ArrayUtils.add(validateFieldBase(), new Object[]{generateDigits(16)});
    }


    @DataProvider
    public static Object[][] validateNumericField() {
        Object[][] text = {
                {"12345678901234567890.12"},
        };
        return(text);
    }

}
