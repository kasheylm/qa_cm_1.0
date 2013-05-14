package com.playtech.cm.selenium.campaigns.triggers;

import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.DateTimeTriggerPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.ScheduledTriggerDialog;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.TriggerSectionPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.TriggerData;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 10/8/12
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeTriggerTest extends BaseTrigger{
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    TriggerSectionPage triggerSection;
    ScheduledTriggerDialog scheduledTriggerDialog;
    DateTimeTriggerPage dateTimeTrigger;
    CMDashboardPage cmDashboardPage;
    String campaignName = "TRIGGER TEST";
    String activityName = "Activity 1";


    @BeforeMethod
    public void createNewcampaign() {
//        GIVEN
        DbUnitUtil.clean("demodb-jdbc.properties");
        CampaignData data  = new CampaignData();
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

    @Test(groups = "trigger",dataProvider = "getDateTimeTriggerData", dataProviderClass = TriggerData.class)
         public void createDateTimeTriggerTest(TriggerData data){
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        dateTimeTrigger.fillTriggerData(data);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
//        THEN
        assertTriggerData(data,campaignName,activityName);
    }

    @Test(groups = "trigger",dataProvider = "getEditDateTimeTriggerData", dataProviderClass = TriggerData.class)
    public void editDateTimeTriggerTest(TriggerData beforeEditData, TriggerData afterEditData){
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        dateTimeTrigger.fillTriggerData(beforeEditData);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        assertTriggerData(beforeEditData,campaignName,activityName);
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
        scheduledTriggerDialog = triggerSection.editTrigger(0);
        dateTimeTrigger.fillTriggerData(afterEditData);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
//        THEN
        assertTriggerData(afterEditData,campaignName,activityName);
    }

    @Test(groups = "trigger",dataProvider = "addNewDateTimeTriggerData", dataProviderClass = TriggerData.class)
    public void addNewDateTimeToTriggerTest(TriggerData beforeEditData, TriggerData afterEditData){
//        WHEN
        String[] addDateAndTime = afterEditData.getSpecOnDateDateAndTime().get(0);
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        dateTimeTrigger.fillTriggerData(beforeEditData);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        assertTriggerData(beforeEditData,campaignName,activityName);
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
        scheduledTriggerDialog = triggerSection.editTrigger(0);
        dateTimeTrigger.addDateAndTime(addDateAndTime[0],addDateAndTime[1]);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
//        THEN
        assertTriggerData(afterEditData,campaignName,activityName);
    }

    @Test(groups = "trigger",dataProvider = "getSaveEmptyDateTimeTriggerData", dataProviderClass = TriggerData.class)
    public void saveEmptyDateTimeTriggerTest(TriggerData data, List<String> errorMessages){
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        dateTimeTrigger.deleteAllDatesAndTimes();
        dateTimeTrigger.clickOk();
        dateTimeTrigger.checkThatValidationErrorShown(errorMessages);
        dateTimeTrigger.close();
        campaignDetailsPage.clickSave();
//        THEN
        assertNoTriggerInDB(campaignName,activityName);
    }

    @Test(groups = "trigger",dataProvider = "getValidateDateFieldDateTimeTriggerData", dataProviderClass = TriggerData.class)
    public void validateDateFiledTest(TriggerData data, List<String> errorMessages){
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        dateTimeTrigger.fillTriggerData(data);
        dateTimeTrigger.clickOk();
        dateTimeTrigger.checkThatValidationErrorShown(errorMessages);
        dateTimeTrigger.close();
        campaignDetailsPage.clickSave();
//        THEN
        assertNoTriggerInDB(campaignName,activityName);
    }

    @Test(groups = "trigger",dataProvider = "getRemoveRowDateTimeTriggerData", dataProviderClass = TriggerData.class)
    public void checkRemoveMultiplyDateTimeRowsTest(TriggerData beforeEditData, TriggerData afterEditData, List<String[]> rowsToDelete){
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(beforeEditData.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(beforeEditData.getTriggerOnSpecified());
        dateTimeTrigger.fillTriggerData(beforeEditData);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
        assertTriggerData(beforeEditData,campaignName,activityName);
        campaignTabs.openActivityTab();
        activityTab.clickTriggers();
        scheduledTriggerDialog = triggerSection.editTrigger(0);
        dateTimeTrigger.deleteDateAndTime(rowsToDelete);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();
        campaignDetailsPage.clickSave();
//        THEN
        assertTriggerData(afterEditData,campaignName,activityName);
    }

    @Test(groups = "trigger",dataProvider = "getSessionCleanedDateTimeTriggerData", dataProviderClass = TriggerData.class)
    public void checkDateTimeTriggerSessionCleanedTest(TriggerData data,String incorrectDate, String errorMessage1, String errorMessage2){
//        WHEN
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
        dateTimeTrigger.addDateAndTime(incorrectDate,"0:15");
        dateTimeTrigger.clickOk();
        assertTrue(dateTimeTrigger.getErrorsCount() == 2);
        assertTrue(dateTimeTrigger.isErrorPresent(errorMessage1));
        assertTrue(dateTimeTrigger.isErrorPresent(errorMessage2));
        dateTimeTrigger.clickCancel();
        scheduledTriggerDialog = triggerSection.selectNewTrigger(data.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(data.getTriggerOnSpecified());
//        THEN
        assertTrue(dateTimeTrigger.getErrorsCount() == 0);
        assertNoTriggerInDB(campaignName,activityName);
    }
}
