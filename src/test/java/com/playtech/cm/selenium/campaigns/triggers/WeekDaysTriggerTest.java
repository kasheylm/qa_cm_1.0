package com.playtech.cm.selenium.campaigns.triggers;

import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.ScheduledTriggerDialog;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.TriggerSectionPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.WeekDaysTriggerPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.TriggerData;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/24/12
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeekDaysTriggerTest extends BaseTrigger {
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    TriggerSectionPage triggerSection;
    ScheduledTriggerDialog scheduledTriggerDialog;
    WeekDaysTriggerPage weekDayTrigger;
    CMDashboardPage cmDashboardPage;
    String campaignName = "TRIGGER TEST";
    String activityName = "Activity 1";


    @BeforeMethod
    public void createNewcampaign() {
//        GIVEN
        DbUnitUtil.clean("demodb-jdbc.properties");
        CampaignData data = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(data);
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.typeName(campaignName);
        campaignDetailsPage.clickSave();
        campaignTabs = PageFactory.initElements(driver.getDriver(), CampaignTabs.class);
        campaignTabs.openActivityTab();
        activityTab = PageFactory.initElements(driver.getDriver(), ActivityTabPage.class);
        triggerSection = activityTab.clickTriggers();
    }

    @Test(groups = "trigger", dataProvider = "getWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void createWeekDaysTriggerTest(TriggerData data) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(data);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
//        THEN
        assertTriggerData(data, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getEditWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void editWeekDaysTriggerTest(TriggerData beforeEditData, TriggerData afterEditData) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(beforeEditData);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
//        THEN
        assertTriggerData(beforeEditData, campaignName, activityName);
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
        scheduledTriggerDialog = triggerSection.editTrigger(0);
        weekDayTrigger.fillWeekDaysTrigger(afterEditData);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();

        assertTriggerData(afterEditData, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getExlusionDateWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void exclusionDateFieldTest(TriggerData data, List<String> errorMessages) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(data);
        weekDayTrigger.clickOk();

//        THEN
        weekDayTrigger.checkThatValidationErrorShown(errorMessages);
        weekDayTrigger.close();
        campaignDetailsPage.clickSave();
        assertNoTriggerInDB(campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getAtTimeFieldWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void atTimeFieldTest(List<String> timeValues, TriggerData data) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(data);
        for (String value : timeValues) {
            weekDayTrigger.typeTimeValue(value);
//        THEN
            assertEquals(weekDayTrigger.getTimes(), new ArrayList<String>(Arrays.asList("0:00")));
        }

    }

    @Test(groups = "trigger", dataProvider = "getAddExclusionDateWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void addExclusionDatesWeekDaysTriggerTest(TriggerData beforeEditData, TriggerData afterEditData) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(beforeEditData);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
//        THEN
        assertTriggerData(beforeEditData, campaignName, activityName);
        scheduledTriggerDialog = triggerSection.editTrigger(0);

        for (String excludingDate : afterEditData.getExcludingDates()) {
            weekDayTrigger.addExcludingDate(excludingDate);
        }
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();

        assertTriggerData(afterEditData, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getRemoveExclusionDateWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void removeExclusionDatesWeekDaysTriggerTest(TriggerData beforeEditData, TriggerData afterEditData) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(beforeEditData);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
//        THEN
        assertTriggerData(beforeEditData, campaignName, activityName);
        scheduledTriggerDialog = triggerSection.editTrigger(0);
        weekDayTrigger.deleteExcludingDates();
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();

        assertTriggerData(afterEditData, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getAddTimesWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void addTimesToWeekDaysTriggerTest(TriggerData beforeEditData, TriggerData afterEditData) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(beforeEditData);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
//        THEN
        assertTriggerData(beforeEditData, campaignName, activityName);
        scheduledTriggerDialog = triggerSection.editTrigger(0);

        for (String time : afterEditData.getWeekDaysAtTimes()) {
            weekDayTrigger.addTime(time);
        }
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();

        assertTriggerData(afterEditData, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getRemoveTimesWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void removeTimesFromWeekDaysTriggerTest(TriggerData beforeEditData, TriggerData afterEditData) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(beforeEditData);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
//        THEN
        assertTriggerData(beforeEditData, campaignName, activityName);
        scheduledTriggerDialog = triggerSection.editTrigger(0);

        weekDayTrigger.deleteAllTimes();
        weekDayTrigger.addTime(afterEditData.getWeekDaysAtTimes().get(0));
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();

        assertTriggerData(afterEditData, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "singleDayForWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void singleDayWeekDaysTriggerTest(TriggerData data) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        weekDayTrigger.fillWeekDaysTrigger(data);
        campaignDetailsPage = weekDayTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
//        THEN
        assertTriggerData(data, campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getSessionCleanedWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void checkWeekDaysTriggerSessionCleanedTest(TriggerData data, String invalidString, String errorMessage1, String errorMessage2) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        weekDayTrigger.addExcludingDate(invalidString);
        weekDayTrigger.clickOk();
        assertTrue(weekDayTrigger.getErrorsCount() == 2);
        assertTrue(weekDayTrigger.isErrorPresent(errorMessage1));
        assertTrue(weekDayTrigger.isErrorPresent(errorMessage2));
        weekDayTrigger.clickCancel();
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
//        THEN
        assertTrue(weekDayTrigger.getErrorsCount() == 0);
        assertNoTriggerInDB(campaignName, activityName);
    }

    @Test(groups = "trigger", dataProvider = "getEmptyFieldsWeekDaysTriggerData", dataProviderClass = TriggerData.class)
    public void saveWeekDaysTriggerWithEmptyDaysAndNoTimeTest(TriggerData data, List<String> errorMessages) {
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        weekDayTrigger = (WeekDaysTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        weekDayTrigger.deleteAllTimes();
        weekDayTrigger.clickOk();
//        THEN
        weekDayTrigger.checkThatValidationErrorShown(errorMessages);
        weekDayTrigger.close();
        campaignDetailsPage.clickSave();
        assertNoTriggerInDB(campaignName, activityName);
    }
}
