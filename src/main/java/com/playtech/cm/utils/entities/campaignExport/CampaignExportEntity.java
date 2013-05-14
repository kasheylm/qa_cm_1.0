package com.playtech.cm.utils.entities.campaignExport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Denis Veselovskiy
 * Date: 10/22/12, Time: 6:21 PM
 */
public class CampaignExportEntity {
    private String exportType;
    private String exportActivities;
    private String actionedFrom;
    private String actionedTo;
    private List<String> availableFields;
    private List<String> selectedFields;
    private String exportFileName;
    private String exportFileFormat;
    private List<String> testGroups;

    public static final List<String> allTestActivityFieldsPlayerHistory = Arrays.asList("Username",
            "Activity",
            "Test Group",
            "Action",
            "Action Date",
            "Title",
            "First Name",
            "Last Name",
            "Email",
            "Phone",
            "Mobile",
            "External ID",
            "Address",
            "City",
            "Country",
            "ZIP",
            "Language",
            "Currency",
            "Wants Promo Email",
            "Wants Promo SMS",
            "Wants Promo Mail",
            "Wants Promo Phone",
            "Email Invalid",
            "SMS Invalid",
            "Direct Mail Invalid",
            "Phone Invalid",
            "Do Not Send Email",
            "Do Not Send SMS",
            "Do Not Send Direct Mail",
            "Do Not Send Call",
            "Account Frozen",
            "Account Suspended");
    public static final  List<String> allNonTestActivityFieldsPlayerHistory = Arrays.asList("Username",
            "Activity",
            "Action",
            "Action Date",
            "Title",
            "First Name",
            "Last Name",
            "Email",
            "Phone",
            "Mobile",
            "External ID",
            "Address",
            "City",
            "Country",
            "ZIP",
            "Language",
            "Currency",
            "Wants Promo Email",
            "Wants Promo SMS",
            "Wants Promo Mail",
            "Wants Promo Phone",
            "Email Invalid",
            "SMS Invalid",
            "Direct Mail Invalid",
            "Phone Invalid",
            "Do Not Send Email",
            "Do Not Send SMS",
            "Do Not Send Direct Mail",
            "Do Not Send Call",
            "Account Frozen",
            "Account Suspended");

    public static final List<String> allTestActivityFieldsUniquePlayers = Arrays.asList("Username",
            "Activity",
            "Test Group",
            "Action Count",
            "Title",
            "First Name",
            "Last Name",
            "Email",
            "Phone",
            "Mobile",
            "External ID",
            "Address",
            "City",
            "Country",
            "ZIP",
            "Language",
            "Currency",
            "Wants Promo Email",
            "Wants Promo SMS",
            "Wants Promo Mail",
            "Wants Promo Phone",
            "Email Invalid",
            "SMS Invalid",
            "Direct Mail Invalid",
            "Phone Invalid",
            "Do Not Send Email",
            "Do Not Send SMS",
            "Do Not Send Direct Mail",
            "Do Not Send Call",
            "Account Frozen",
            "Account Suspended");
    public static final  List<String> allNonTestActivityFieldsUniquePlayers = Arrays.asList("Username",
            "Activity",
            "Action Count",
            "Title",
            "First Name",
            "Last Name",
            "Email",
            "Phone",
            "Mobile",
            "External ID",
            "Address",
            "City",
            "Country",
            "ZIP",
            "Language",
            "Currency",
            "Wants Promo Email",
            "Wants Promo SMS",
            "Wants Promo Mail",
            "Wants Promo Phone",
            "Email Invalid",
            "SMS Invalid",
            "Direct Mail Invalid",
            "Phone Invalid",
            "Do Not Send Email",
            "Do Not Send SMS",
            "Do Not Send Direct Mail",
            "Do Not Send Call",
            "Account Frozen",
            "Account Suspended");


    public CampaignExportEntity (Boolean isForTestActivity){
        this.exportType = "Actioned Players History";
        this.exportActivities = "All Campaign Activities";
        this.testGroups = new ArrayList<String>();
        this.actionedFrom = "";
        this.actionedTo = "";
        if(isForTestActivity){
            this.availableFields = allTestActivityFieldsPlayerHistory;
        } else {
            this.availableFields = allNonTestActivityFieldsPlayerHistory;
        }
        this.selectedFields = Arrays.asList();
        this.exportFileFormat = "CSV";
        this.exportFileName = "";
        }

    public void fillDefaultUniqueExportEntity(){
        this.exportType = "Unique Actioned Players";
        this.exportActivities = "All Campaign Activities";
        this.testGroups = new ArrayList<String>();
        this.actionedFrom = "";
        this.actionedTo = "";
        this.availableFields = allNonTestActivityFieldsUniquePlayers;
        this.selectedFields = Arrays.asList();
        this.exportFileName = "";
        this.exportFileFormat = "CSV";
    }

    public void fillDefaultHistoryExportEntity(){
        this.exportType = "Actioned Players History";
        this.exportActivities = "All Campaign Activities";
        this.testGroups = new ArrayList<String>();
        this.actionedFrom = "";
        this.actionedTo = "";
        this.availableFields = allNonTestActivityFieldsPlayerHistory;
        this.selectedFields = Arrays.asList();
        this.exportFileName = "";
        this.exportFileFormat = "CSV";
    }

    public CampaignExportEntity copyEntity(){
        CampaignExportEntity copy = new CampaignExportEntity(false);
        copy.setExportType(this.getExportType());
        copy.setExportActivities(this.getExportActivities());
        copy.setTestGroups(this.getTestGroups());
        copy.setActionedFrom(this.getActionedFrom());
        copy.setActionedTo(this.getActionedTo());
        copy.setAvailableFields(this.getAvailableFields());
        copy.setSelectedFields(this.getSelectedFields());
        copy.setExportFileFormat(this.getExportFileFormat());
        copy.setExportFileName(this.getExportFileName());
        return copy;
    }

    public void removeAvailableFields(List<String> listToRemove){
        ArrayList<String> temp = new ArrayList<String>(this.availableFields);
        temp.removeAll(listToRemove);
        setAvailableFields(temp);
    }

//    Auto generated getters/setters

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getExportActivities() {
        return exportActivities;
    }

    public void setExportActivities(String exportActivities) {
        this.exportActivities = exportActivities;
    }

    public String getActionedFrom() {
        return actionedFrom;
    }

    public void setActionedFrom(String actionedFrom) {
        this.actionedFrom = actionedFrom;
    }

    public String getActionedTo() {
        return actionedTo;
    }

    public void setActionedTo(String actionedTo) {
        this.actionedTo = actionedTo;
    }

    public List<String> getAvailableFields() {
        return availableFields;
    }

    public void setAvailableFields(List<String> availableFields) {
        this.availableFields = availableFields;
    }

    public List<String> getSelectedFields() {
        return selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String getExportFileFormat() {
        return exportFileFormat;
    }

    public void setExportFileFormat(String exportFileFormat) {
        this.exportFileFormat = exportFileFormat;
    }

    public void setTestGroups(List<String> groups) {
        testGroups = groups;
    }

    public List<String> getTestGroups() {
        return testGroups;
    }
}
