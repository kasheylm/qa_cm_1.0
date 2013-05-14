package com.playtech.cm.selenium.campaigns.triggers;

import com.playtech.cm.db.DBData;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.FrequencyTriggerPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.ScheduledTriggerDialog;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.TriggerSectionPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.TriggerData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/2/12 * Time: 5:19 PM
 */
public class FrequencyTriggerTest extends BaseTrigger{
    CampaignData campaignData;
    String activityName;
    String activityID;
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    TriggerSectionPage triggerSection;
    ScheduledTriggerDialog scheduledTriggerDialog;
    FrequencyTriggerPage frequencyTriggerPage;
    CMDashboardPage cmDashboardPage;

    String errorRepeatEmpty = "Repeat every should have value between 1 - 23 hours.";
    String errorRepeatInteger = "You must provide an integer value for Every Number.";
    String errorStartDateDefault = "Starting from date should be specified or use default.";
    String errorStartDateNotParsable = "Frequency Start Date. Please use the format yyyy-MM-dd for specifying dates";
    String errorExcludingDateNotParsable = "Datefield. Please use the format yyyy-MM-dd for specifying dates.";

    @BeforeMethod
    public void createNewCampaign() {
        //GIVEN
        DbUnitUtil.clean("demodb-jdbc.properties");
        campaignData = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(campaignData);
        campaignDetailsPage.fillForm(campaignData);
        campaignTabs = campaignDetailsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        triggerSection = activityTab.clickTriggers();
    }

//    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
//    public void createHoursTrigger(TriggerData triggerData){
//        //WHEN
//        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
//        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
//        frequencyTriggerPage.fillFormHours(triggerData);
//        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
//        campaignDetailsPage.clickSave();
//        //THEN
//        assertTriggerData(triggerData, campaignData.getName(), activityName);
//    }

    @Test(groups = "trigger",dataProvider = "getDaysFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void createDaysTrigger(TriggerData triggerData){
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.fillFormDays(triggerData);
        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
        campaignDetailsPage.clickSave();
        //THEN
        assertTriggerData(triggerData, campaignData.getName(), activityName);
    }

//    @Test(groups = "trigger",dataProvider = "getEditFrequencyTriggerData", dataProviderClass = TriggerData.class)
//    public void editHoursTrigger(TriggerData triggerData1, TriggerData triggerData2){
//        //WHEN
//        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData1.getTriggerType());
//        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData1.getTriggerOnSpecified());
//        frequencyTriggerPage.fillFormHours(triggerData1);
//        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
//        campaignDetailsPage.clickSave();
//        //THEN
//        assertTriggerData(triggerData1, campaignData.getName(), activityName);
//        //WHEN
//        activityTab = campaignTabs.openActivityTab();
//        activityName = activityTab.getOpenedDetails().getActivityName();
//        triggerSection = activityTab.clickTriggers();
//        scheduledTriggerDialog = triggerSection.editTrigger(0);
//        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData1.getTriggerOnSpecified());
//        frequencyTriggerPage.fillFormHours(triggerData2);
//        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
//        campaignDetailsPage.clickSave();
//        //THEN
//        assertTriggerData(triggerData2, campaignData.getName(), activityName);
//    }
//
//    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
//    public void validateEmptyRepeatEveryField(TriggerData triggerData) {
//        //WHEN
//        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
//        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
//        frequencyTriggerPage.fillFormHours(triggerData);
//        frequencyTriggerPage.typeRepeatEvery("");
//        frequencyTriggerPage.clickOk();
//        assertTrue(frequencyTriggerPage.getErrorsCount() == 1);
//        assertTrue(frequencyTriggerPage.isErrorPresent(errorRepeatEmpty));
//        frequencyTriggerPage.clickCancel();
//    }
//
//    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerDataWithFieldsData", dataProviderClass = TriggerData.class)
//    public void validateStartingFromField(TriggerData triggerData, String startDate) {
//        //WHEN
//        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
//        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
//        frequencyTriggerPage.fillFormHours(triggerData);
//        frequencyTriggerPage.typeStartDate(startDate);
//        frequencyTriggerPage.clickOk();
//        assertTrue(frequencyTriggerPage.getErrorsCount() == 2);
//        assertTrue(frequencyTriggerPage.isErrorPresent(errorStartDateDefault));
//        assertTrue(frequencyTriggerPage.isErrorPresent(errorStartDateNotParsable));
//        frequencyTriggerPage.clickCancel();
//    }

    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerDataWithFieldsData", dataProviderClass = TriggerData.class)
    public void validateExcludingField(TriggerData triggerData, String excludingDates) {
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.fillFormHours(triggerData);
        frequencyTriggerPage.deleteExcludingDates();
        frequencyTriggerPage.addExcludingDate("");
        frequencyTriggerPage.selectExcludingDates(new ArrayList<String>(Arrays.asList(excludingDates)));
        frequencyTriggerPage.clickOk();
        assertTrue(frequencyTriggerPage.getErrorsCount() == 1);
        assertTrue(frequencyTriggerPage.isErrorPresent(errorExcludingDateNotParsable));
        frequencyTriggerPage.clickCancel();
    }

    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void checkAllWarnings(TriggerData triggerData) {
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.setUseDefaultStartDate(triggerData.getFreqUseActivityStartDateAndTime());
        frequencyTriggerPage.typeStartDate("@#cassaf");
        frequencyTriggerPage.deleteExcludingDates();
        frequencyTriggerPage.addExcludingDate("");
        frequencyTriggerPage.selectExcludingDates(new ArrayList<String>(Arrays.asList("@#cassaf")));
        frequencyTriggerPage.clickOk();
        assertTrue(frequencyTriggerPage.getErrorsCount() == 4);
        assertTrue(frequencyTriggerPage.isErrorPresent(errorRepeatEmpty));
        assertTrue(frequencyTriggerPage.isErrorPresent(errorStartDateDefault));
        assertTrue(frequencyTriggerPage.isErrorPresent(errorStartDateNotParsable));
        assertTrue(frequencyTriggerPage.isErrorPresent(errorExcludingDateNotParsable));
        frequencyTriggerPage.clickCancel();
    }

    @Test(groups = "trigger",dataProvider = "getDaysFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void checkUseDefaultDateDaysTrigger(TriggerData triggerData){
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.fillFormDays(triggerData);
        frequencyTriggerPage.setUseDefaultStartDate(true);
        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
        campaignDetailsPage.clickSave();

        //THEN
        assertEquals(campaignDetailsPage.getStartDate(), hibernateUtils.getActivityStartDate(activityID));
    }

    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void checkUseDefaultDateHoursTrigger(TriggerData triggerData){
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.fillFormHours(triggerData);
        frequencyTriggerPage.setUseDefaultStartDate(true);
        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
        campaignDetailsPage.clickSave();
        //THEN
        assertEquals(campaignDetailsPage.getStartDate(), hibernateUtils.getActivityStartDate(activityID));
    }

    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void checkAddExcludingDates(TriggerData triggerData){
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.fillFormHours(triggerData);
        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
        campaignDetailsPage.clickSave();
        //THEN
        assertExcludingdates(triggerData);
    }

    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void checkRemoveExcludingDates(TriggerData triggerData){
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.fillFormHours(triggerData);
        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
        campaignDetailsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        triggerSection = activityTab.clickTriggers();
        scheduledTriggerDialog = triggerSection.editTrigger(0);
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.deleteExcludingDates();
        triggerData.setExcludingDates(new ArrayList<String>());
        campaignDetailsPage = frequencyTriggerPage.saveTrigger();
        campaignDetailsPage.clickSave();
        //THEN
        assertExcludingdates(triggerData);
    }

    @Test(groups = "trigger",dataProvider = "getHoursFrequencyTriggerData", dataProviderClass = TriggerData.class)
    public void checkThatSessionIsCleaned(TriggerData triggerData) {
        //WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        frequencyTriggerPage.setUseDefaultStartDate(triggerData.getFreqUseActivityStartDateAndTime());
        frequencyTriggerPage.typeStartDate("@#cassaf");
        frequencyTriggerPage.deleteExcludingDates();
        frequencyTriggerPage.addExcludingDate("");
        frequencyTriggerPage.selectExcludingDates(new ArrayList<String>(Arrays.asList("@#cassaf")));
        frequencyTriggerPage.clickOk();
        assertTrue(frequencyTriggerPage.getErrorsCount() == 4);
        assertTrue(frequencyTriggerPage.isErrorPresent(errorRepeatEmpty));
        assertTrue(frequencyTriggerPage.isErrorPresent(errorStartDateDefault));
        assertTrue(frequencyTriggerPage.isErrorPresent(errorStartDateNotParsable));
        triggerSection = frequencyTriggerPage.clickCancel();
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        frequencyTriggerPage = (FrequencyTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        assertTrue(frequencyTriggerPage.getErrorsCount() == 0);
        assertFalse(frequencyTriggerPage.isErrorPresent(errorRepeatEmpty));
        assertFalse(frequencyTriggerPage.isErrorPresent(errorStartDateDefault));
        assertFalse(frequencyTriggerPage.isErrorPresent(errorStartDateNotParsable));
        frequencyTriggerPage.clickCancel();
    }

    private void assertExcludingdates(TriggerData triggerData) {
        List<String> actualExclusionDates = DBData.getExclusionDates(campaignData.getName(), activityName);
        List<String> expectedExcludingDates = triggerData.getExcludingDates();
        assertTwoListEqualsWithoutOrder(expectedExcludingDates, actualExclusionDates);
    }
}
