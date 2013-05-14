package com.playtech.cm.selenium.campaigns.actions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ActionsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.UpdateCustomFieldsPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.dataProviders.actions.CustomFieldData;
import com.playtech.cm.utils.entities.actions.CustomFieldsEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 11/15/12 * Time: 5:51 PM
 */
public class UpdateCustomFieldsNonTestActivityTest extends BaseTest{
    CampaignData campaignData;
    String activityName;
    String activityID;
    CustomFieldsEntity expectedCustomFieldsEntity;
    CustomFieldsEntity actualCustomFieldsEntity;
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ActionsPage actionsPage;
    UpdateCustomFieldsPage updateCustomFieldsPage;
    String errorWrongFormat = "Value for field custom08 must be number in format ####################.##";

    @BeforeMethod
    public void createNewCampaign() {
        //GIVEN
        DbUnitUtil.clean("demodb-jdbc.properties");
        campaignData = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(campaignData);
        campaignTabs = campaignDetailsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        actionsPage = (ActionsPage) activityTab.clickActions();
        expectedCustomFieldsEntity = new CustomFieldsEntity();
        actualCustomFieldsEntity = new CustomFieldsEntity();
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = CustomFieldData.class)
    public void validateFirstCustomFieldTest(String value) {
        expectedCustomFieldsEntity.setName("Custom01 (TEXT)");
        expectedCustomFieldsEntity.setValue(value);
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(expectedCustomFieldsEntity.getName());
        updateCustomFieldsPage.typeFieldValue(value);
        actionsPage = updateCustomFieldsPage.clickOK();
        campaignTabs = actionsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        actionsPage = (ActionsPage) activityTab.clickActions();
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.clickEdit(0);
        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        //THEN
        assertObjectsEquals(expectedCustomFieldsEntity, actualCustomFieldsEntity);
    }

    @Test(dataProvider = "validateFieldBase", dataProviderClass = CustomFieldData.class)
    public void validateNumericCustomFieldNegativeTest(String value) {
        expectedCustomFieldsEntity.setName("Custom08 (NUMBER)");
        expectedCustomFieldsEntity.setValue(value);
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(expectedCustomFieldsEntity.getName());
        updateCustomFieldsPage.typeFieldValue(value);
        actionsPage = updateCustomFieldsPage.clickOK();
        assertTrue(updateCustomFieldsPage.isErrorPresent(errorWrongFormat));
    }

    @Test(dataProvider = "validateNumericField", dataProviderClass = CustomFieldData.class)
    public void validateNumericCustomFieldPositiveTest(String value) {
        expectedCustomFieldsEntity.setName("Custom08 (NUMBER)");
        expectedCustomFieldsEntity.setValue(value);
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(expectedCustomFieldsEntity.getName());
        updateCustomFieldsPage.typeFieldValue(value);
        actionsPage = updateCustomFieldsPage.clickOK();
        campaignTabs = actionsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        actionsPage = (ActionsPage) activityTab.clickActions();
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.clickEdit(0);
        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        //THEN
        assertObjectsEquals(expectedCustomFieldsEntity, actualCustomFieldsEntity);
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = CustomFieldData.class)
    public void validateLastCustomFieldTest(String value) {
        expectedCustomFieldsEntity.setName("Custom01 (TEXT)");
        expectedCustomFieldsEntity.setValue(value);
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(expectedCustomFieldsEntity.getName());
        updateCustomFieldsPage.typeFieldValue(expectedCustomFieldsEntity.getValue());
        actionsPage = updateCustomFieldsPage.clickOK();
        campaignTabs = actionsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        actionsPage = (ActionsPage) activityTab.clickActions();
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.clickEdit(0);
        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        //THEN
        assertObjectsEquals(expectedCustomFieldsEntity, actualCustomFieldsEntity);
    }

//    Commented because of unusual confirmation.
//    @Test(dataProvider = "validateEmptyCustomFieldData", dataProviderClass = CustomFieldData.class)
//    public void validateEmptyCustomFieldTest(CustomFieldsEntity expectedCustomFieldsEntity, String expectedAlert) {
//        //WHEN
//        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
//        updateCustomFieldsPage.waitForm();
//        updateCustomFieldsPage.selectField(expectedCustomFieldsEntity.getName());
//        updateCustomFieldsPage.typeFieldValue(expectedCustomFieldsEntity.getValue());
//        //THEN
//        actionsPage = updateCustomFieldsPage.clickOkAndValidateAlert(expectedAlert);
//        campaignTabs = actionsPage.clickSave();
//        activityTab = campaignTabs.openActivityTab();
//        actionsPage = activityTab.clickActions();
//        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.clickEdit(0);
//        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
//        assertObjectsEquals(expectedCustomFieldsEntity, actualCustomFieldsEntity);
//    }

    @Test(dataProvider = "validateOkPressingData", dataProviderClass = CustomFieldData.class)
    public void checkThatValueChangesByClickingOkTest(CustomFieldsEntity expectedCustomFieldsEntity) {
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(expectedCustomFieldsEntity.getName());
        updateCustomFieldsPage.typeFieldValue(expectedCustomFieldsEntity.getValue());
        actionsPage = updateCustomFieldsPage.clickOK();
        campaignTabs = actionsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        actionsPage = (ActionsPage) activityTab.clickActions();
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.clickEdit(0);
        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        //THEN
        assertObjectsEquals(expectedCustomFieldsEntity, actualCustomFieldsEntity);
    }

    @Test(dataProvider = "validateCancelPressingData", dataProviderClass = CustomFieldData.class)
    public void valueNotChangedByClickingCancelTest(CustomFieldsEntity customFieldsEntityToType, CustomFieldsEntity expectedEntity) {
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(customFieldsEntityToType.getName());
        updateCustomFieldsPage.typeFieldValue(customFieldsEntityToType.getValue());
        actionsPage = updateCustomFieldsPage.clickCancel();
        campaignTabs = actionsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        actionsPage = (ActionsPage) activityTab.clickActions();
        //THEN
        assertTrue(actionsPage.isActionsTableEmpty());
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.selectField(expectedEntity.getName());
        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        assertObjectsEquals(expectedEntity, actualCustomFieldsEntity);
    }

    @Test(dataProvider = "valueSelectingData", dataProviderClass = CustomFieldData.class)
    public void checkValueSelectingTest(CustomFieldsEntity customFieldsEntityToType, List<String> testFieldNames) {
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(customFieldsEntityToType.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(customFieldsEntityToType.getName());
        updateCustomFieldsPage.typeFieldValue(customFieldsEntityToType.getValue());
        for(String testFieldName:testFieldNames){
            customFieldsEntityToType.setName(testFieldName);
            updateCustomFieldsPage.selectField(testFieldName);
            actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        //THEN
            assertObjectsEquals(customFieldsEntityToType, actualCustomFieldsEntity);
        }
    }

    @Test(dataProvider = "validateDefaultsData", dataProviderClass = CustomFieldData.class)
    public void checkDefaultsTest(CustomFieldsEntity expectedCustomFieldsEntity) {
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        activityTab = campaignTabs.openActivityTab();
        actionsPage = (ActionsPage) activityTab.clickActions();
        actualCustomFieldsEntity = updateCustomFieldsPage.getForm();
        //THEN
        assertObjectsEquals(expectedCustomFieldsEntity, actualCustomFieldsEntity);
    }

    @Test(dataProvider = "noFieldNameData", dataProviderClass = CustomFieldData.class)
    public void validateSaveFieldNameNotSelectedTest(String testValue, String expectedErrorMsg, String expectedWarningMessage) {
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.typeFieldValue(testValue);
        if(!testValue.equals("")){
            updateCustomFieldsPage.clickOK();
        } else {
            updateCustomFieldsPage.clickOkAndValidateAlert(expectedWarningMessage);
        }
        //THEN

        assertTrue(updateCustomFieldsPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "noFieldSelectedError", dataProviderClass = CustomFieldData.class)
    public void checkSessionIsCleaned( String warning) {
        //WHEN
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.typeFieldValue("no filed selected");
        updateCustomFieldsPage.clickOK();
        //THEN
        assertTrue(updateCustomFieldsPage.isErrorPresent(warning), "No error when trying to add Action - Update Custom Field. Custom Field is not selected.");
        actionsPage = updateCustomFieldsPage.clickCancel();
        actionsPage = actionsPage.clickActions();
        updateCustomFieldsPage = (UpdateCustomFieldsPage)actionsPage.addAction(expectedCustomFieldsEntity.getAction());
        assertTrue(updateCustomFieldsPage.getErrorsCount()==0, "Session is not cleaned when adding Update Custom Field action to test group.");
    }
}
