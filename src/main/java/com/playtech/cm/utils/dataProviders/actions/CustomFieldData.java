package com.playtech.cm.utils.dataProviders.actions;

import com.playtech.cm.utils.dataProviders.BaseData;
import com.playtech.cm.utils.entities.actions.CustomFieldsEntity;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Denis Veselovskiy
 * Date: 11/15/12 * Time: 6:52 PM
 */
public class CustomFieldData extends BaseData {
    static String emptyFieldWarning = "Do you really want to set empty value?";
    static String errorMessage = "Custom Field should not be empty.";

    @DataProvider(name = "validateEmptyCustomFieldData")
    public static Object[][] validateEmptyCustomFieldData() {
    CustomFieldsEntity testCustomFieldFirst = new CustomFieldsEntity();
        testCustomFieldFirst.setName("Custom01 (TEXT)");
        testCustomFieldFirst.setValue("");

    CustomFieldsEntity testCustomFieldMiddle = new CustomFieldsEntity();
        testCustomFieldMiddle.setName("Custom08 (NUMBER)");
        testCustomFieldMiddle.setValue("");

    CustomFieldsEntity testCustomFieldLast = new CustomFieldsEntity();
        testCustomFieldLast.setName("Custom20 (TEXT)");
        testCustomFieldLast.setValue("");

        return new Object[][]{
                {testCustomFieldFirst, emptyFieldWarning},
                {testCustomFieldMiddle, emptyFieldWarning},
                {testCustomFieldLast, emptyFieldWarning}
        };
    }

    @DataProvider(name = "validateOkPressingData")
    public static Object[][] validateOkPressingData() {
        CustomFieldsEntity testCustomField = new CustomFieldsEntity();
        testCustomField.setName("Custom09 (TEXT)");
        testCustomField.setValue("TEST_VALUE_FOR_CUSTOM_FIELD");

        return new Object[][]{
                {testCustomField}
        };
    }

    @DataProvider(name = "validateCancelPressingData")
    public static Object[][] validateCancelPressingData() {
        CustomFieldsEntity customFieldToType = new CustomFieldsEntity();
        customFieldToType.setName("Custom09 (TEXT)");
        customFieldToType.setValue("TEST_VALUE_FOR_CUSTOM_FIELD");

        CustomFieldsEntity expectedCustomField = new CustomFieldsEntity();
        expectedCustomField.setName("Custom09 (TEXT)");
        expectedCustomField.setValue("");

        return new Object[][]{
                {customFieldToType, expectedCustomField}
        };
    }

    @DataProvider(name = "valueSelectingData")
    public static Object[][] valueSelectingData() {
        List<String> testFieldValues = Arrays.asList("Custom01 (TEXT)",
                "Custom02 (TEXT)",
                "Custom05 (TEXT)",
                "Custom06 (TEXT)",
                "Custom09 (TEXT)",
                "Custom10 (TEXT)",
                "Custom11 (TEXT)",
                "Custom12 (TEXT)",
                "Custom13 (TEXT)",
                "Custom14 (TEXT)",
                "Custom15 (TEXT)",
                "Custom16 (TEXT)",
                "Custom17 (TEXT)",
                "Custom18 (TEXT)",
                "Custom19 (TEXT)",
                "Custom20 (TEXT)");

        CustomFieldsEntity customFieldToType = new CustomFieldsEntity();
        customFieldToType.setName("Custom14 (TEXT)");
        customFieldToType.setValue("TEST_VALUE_FOR_CUSTOM_FIELD");

        return new Object[][]{
                {customFieldToType, testFieldValues}
        };
    }

    @DataProvider(name = "validateDefaultsData")
    public static Object[][] validateDefaultsData() {
        CustomFieldsEntity expectedCustomField = new CustomFieldsEntity();
        expectedCustomField.setName("Select Custom Field");
        expectedCustomField.setValue("");

        return new Object[][]{
                {expectedCustomField}
        };
    }

    @DataProvider(name = "noFieldNameData")
    public static Object[][] noFieldNameData() {
        String fieldValue1 = "";
        String fieldValue2 = "TEST VALUE";
        return new Object[][]{
                {fieldValue1, errorMessage, emptyFieldWarning},
                {fieldValue2, errorMessage, null}
        };
    }

    @DataProvider(name = "noFieldSelectedError")
    public static Object[][] noFieldSelectedError() {
        return new Object[][]{ {errorMessage} };
    }
}
