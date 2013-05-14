package com.playtech.cm.jms;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ActionsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.UpdateCustomFieldsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionDialogPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionSectionPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.CustomReportDialogPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.GameLossesPlayerReportPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.DateTimeTriggerPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.ScheduledTriggerDialog;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.TriggerSectionPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.TriggerData;
import com.playtech.cm.utils.entities.actions.CustomFieldsEntity;
import com.playtech.cm.utils.entities.conditions.GameLossesPlayerReportEntity;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 * User: Denis Veselovskiy
 * Date: 3/28/13 * Time: 5:00 PM
 */

public class PlayerGameLossesRequestTest extends BaseTest {
    CampaignData campaignData;
    TriggerData triggerData;
    GameLossesPlayerReportEntity conditionData;
    CustomFieldsEntity actionData;
    String campaignID;
    String activityName;
    String activityID;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    TriggerSectionPage triggerSection;
    ScheduledTriggerDialog scheduledTriggerDialog;
    DateTimeTriggerPage dateTimeTrigger;
    CMDashboardPage cmDashboardPage;
    ConditionSectionPage conditionSectionPage;
    ConditionDialogPage conditionDialog;
    CustomReportDialogPage customReportDialog;
    GameLossesPlayerReportPage playerGameLossesPage;
    UpdateCustomFieldsPage updateCustomFieldsPage;
    ActionsPage actionsPage;



    @BeforeClass()
    public void createNewCampaign() {

//      CREATE EMPTY CAMPAIGN
        DbUnitUtil.clean("demodb-jdbc.properties");
        campaignData = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(campaignData);
        campaignDetailsPage.fillForm(campaignData);
        campaignTabs = campaignDetailsPage.clickSave();
        campaignID = campaignTabs.getCampaignID();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        triggerSection = activityTab.clickTriggers();

//      ADD TRIGGER
        triggerData = new TriggerData();
        triggerData.setDateAndTimeTrigger();
        scheduledTriggerDialog = triggerSection.selectNewTrigger(triggerData.getTriggerType());
        dateTimeTrigger = (DateTimeTriggerPage) scheduledTriggerDialog.selectSchedulerType(triggerData.getTriggerOnSpecified());
        dateTimeTrigger.fillTriggerData(triggerData);
        campaignDetailsPage = dateTimeTrigger.saveTrigger();

//      ADD CONDITION
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        conditionSectionPage = activityTab.clickConditions();
        conditionData = new GameLossesPlayerReportEntity();
        conditionDialog = conditionSectionPage.addCondition(conditionData.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(conditionData.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(conditionData.getReportType());
        playerGameLossesPage.fillForm(conditionData);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();

//      ADD ACTION
        campaignTabs = campaignDetailsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        actionsPage = activityTab.clickActions();

        actionData = new CustomFieldsEntity();
        actionData.setDefaultCustomField();
        updateCustomFieldsPage = (UpdateCustomFieldsPage) actionsPage.addAction(actionData.getAction());
        updateCustomFieldsPage.waitForm();
        updateCustomFieldsPage.selectField(actionData.getName());
        updateCustomFieldsPage.typeFieldValue(actionData.getValue());
        actionsPage = updateCustomFieldsPage.clickOK();
        actionsPage.changeStatusTo("Approve");
        campaignTabs = actionsPage.clickSave();
        waitCampaignApproved(campaignID);
    }

    @Test()
    public void firstTest() throws FileNotFoundException {
        String jmsXml = JmsUtil.convertPayloadToXml(JmsUtil.readMessage());
        System.out.println(jmsXml);
        Document doc1 = parse(getXml("1"));
        Document doc2 = parse(new ByteArrayInputStream(jmsXml.getBytes()) );
        compareXML(doc1, doc2);
    }

    private InputStream getXml(String xmlName) {
        return this.getClass().getClassLoader().getResourceAsStream("jms/playerGameLosses/" + xmlName + ".xml");
    }
}
