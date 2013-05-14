package com.playtech.cm.selenium.campaigns;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.elements.CampaignTable;
import com.playtech.cm.pages.dashboard.TemplateToolsPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.ExtendedWebDriver.ExtendedWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/17/12, Time: 4:20 PM
 */
public class TestCMDashboardOpeningTest extends BaseTest{
    TemplateToolsPage templateToolsPage;
    CMDashboardPage cmDashboardPage;
    CampaignTable campaignTable;
    @BeforeClass
    public void tearUp() {
        DbUnitUtil.clean("demodb-jdbc.properties");
        relogin();
    }

    @Test
    public void openCampaignPage() {
        //WHEN
        templateToolsPage = homePage.clickLinkTemplateToolsPage();
        cmDashboardPage = templateToolsPage.clickLinkCMDashboard();
//        THEN
        campaignTable = cmDashboardPage.getCampaignTable();
        assertTrue(cmDashboardPage.isLinkNewCampaignEnabled());
        assertEquals(cmDashboardPage.getErrorsCount(), 0);
        assertEquals(campaignTable.getCampaignRowList().size(), 0);
    }
}
