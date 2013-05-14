package com.playtech.cm.selenium.campaigns;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DBData;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.details.ActivityDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.dataProviders.CampaignData;

import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 9/25/12 * Time: 2:25 PM
 */

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ActivityDetailsTest extends BaseTest {
    CampaignData data;
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    ActivityTabPage activityTabPage;
    ActivityDetailsPage activityDetailsPage;
    CMDashboardPage cmDashboardPage;
    String errorName = "Activity Name should not be empty.";

    @BeforeClass
    public void tearUp() {
        DbUnitUtil.clean("demodb-jdbc.properties");
        data = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(data);
        activityTabPage = campaignDetailsPage.openActivityTab();
        activityDetailsPage = activityTabPage.getOpenedDetails();
    }

    @BeforeMethod
    public void openActivityDetailsPage() {
        CampaignTabs campaignTabs = StalePageFactory.initElements(driver.getDriver(), CampaignTabs.class);
        activityTabPage = campaignTabs.openActivityTab();
        activityDetailsPage = activityTabPage.getOpenedDetails();
    }

    @Test
    public void leaveNameEmpty() {
        //given
        activityDetailsPage.typeName("");
        //when
        campaignDetailsPage = activityDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getErrorsCount(), 1);
        assertTrue(campaignDetailsPage.isErrorPresent(errorName));
        activityTabPage = campaignDetailsPage.openActivityTab();
        activityDetailsPage = activityTabPage.getOpenedDetails();
        activityDetailsPage.openActivityTab();
        activityDetailsPage.typeName("Activity 1");
        activityDetailsPage.clickSave();
    }

    @Test(dataProvider = "validateFieldName", dataProviderClass = CampaignData.class)
    public void validateNameField(String campaignName) {
        //given
        data.setName(campaignName);
        //when
        activityDetailsPage.typeName(campaignName);
        activityDetailsPage.clickSave();
        //then
        activityTabPage = campaignDetailsPage.openActivityTab();
        activityDetailsPage = activityTabPage.getOpenedDetails();
        assertEquals(activityDetailsPage.getActivityName(), campaignName);
    }

    @Test(dataProvider = "validateFieldDescription", dataProviderClass = CampaignData.class)
    public void validateDescriptionField(String desc) {
        //given
        data.setDescription(desc);
        //when
        activityDetailsPage.typeDescription(desc);
        activityDetailsPage.clickSave();
        //then
        activityTabPage = campaignDetailsPage.openActivityTab();
        activityDetailsPage = activityTabPage.getOpenedDetails();
        assertEquals(activityDetailsPage.getActivityDescription(), desc);
    }

    @Test
    public void validateStartDate() {
        //given
        String activityID = activityDetailsPage.getActivityID();
        campaignDetailsPage = activityDetailsPage.openCampaignDetailsTab();
        campaignDetailsPage.fillForm(data);
        //when
        campaignDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getStartDate(), hibernateUtils.getActivityStartDate(activityID));
    }

    @Test
    public void validateEndDate() {
        //given
        String activityID = activityDetailsPage.getActivityID();
        campaignDetailsPage = activityDetailsPage.openCampaignDetailsTab();
        campaignDetailsPage.fillForm(data);
        //when
        campaignDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getEndDate(), hibernateUtils.getActivityEndDate(activityID));
    }

}
