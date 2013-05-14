package com.playtech.cm.utils.dataProviders;

import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.entities.campaignExport.CampaignExportEntity;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Denis Veselovskiy
 * Date: 10/22/12, Time: 6:22 PM
 */
public class CampaignExportData {
//A C T I O N E D   P L A Y E R   H I S T O R Y   D A T A .
@DataProvider(name = "historyCheckUIData")
public static Object[][] historyCheckUIData() {
    CampaignExportEntity dataNonTest = new CampaignExportEntity(false);
    CampaignExportEntity dataTest = new CampaignExportEntity(true);
    dataTest.setTestGroups(Arrays.asList("All Test Groups"));

    return new Object[][]{
            {dataNonTest, false},
            {dataTest, true}
    };
}

    @DataProvider(name = "historyExportTestGroupsData")
    public static Object[][] historyExportTestGroupsData() {
        String campaignName = "Export_test_campaign";
        List<String> expectedAvailableFields = new ArrayList<String>();
        CampaignExportEntity data = new CampaignExportEntity(true);
        data.setAvailableFields(expectedAvailableFields);
        data.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory);
        data.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        data.setExportFileName("H_exportAllTestGroups");

        return new Object[][]{
                {campaignName,"XML", data},
                {campaignName,"CSV", data}
        };
    }

    @DataProvider(name = "historyExportOneTestGroupData")
    public static Object[][] historyExportOneTestGroupData() {
        String campaignName = "Export_test_campaign";
        List<String> expectedAvailableFields = new ArrayList<String>();
        CampaignExportEntity data1 = new CampaignExportEntity(true);
        data1.setAvailableFields(expectedAvailableFields);
        data1.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory);
        data1.setTestGroups(Arrays.asList("Control Group"));
        data1.setExportFileName("H_exportOneTestGroup_ControlGroup");
        data1.setActionedFrom("2012-10-01");

        CampaignExportEntity data2 = data1.copyEntity();
        data2.setTestGroups(Arrays.asList("Test Group 1"));
        data2.setExportFileName("H_exportOneTestGroup_TestGroup1");

        CampaignExportEntity data3 = data1.copyEntity();
        data3.setTestGroups(Arrays.asList("Test Group 2"));
        data3.setExportFileName("H_exportOneTestGroup_TestGroup2");
        return new Object[][]{
                {campaignName, "CSV", data1},
                {campaignName, "XML", data1},
                {campaignName, "CSV", data2},
                {campaignName, "XML", data2},
                {campaignName, "CSV", data3},
                {campaignName, "XML", data3}
        };
    }

    @DataProvider(name = "historyExportDoNotSelectTestGroups")
    public static Object[][] historyExportDoNotSelectTestGroups() {
        String campaignName = "Export_test_campaign";
        List<String> expectedAvailableFields = new ArrayList<String>();
        CampaignExportEntity data = new CampaignExportEntity(true);
        data.setAvailableFields(expectedAvailableFields);
        data.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory);
        data.setTestGroups(Arrays.asList("All Test Groups"));
        data.setExportFileName("H_ExportDoNotSelectTestGroups");
        data.setActionedFrom("2012-10-01");

        return new Object[][]{
                {campaignName,"XML", data},
                {campaignName,"CSV", data}
        };
    }

    @DataProvider(name = "historyExportDatesDoesNotMetActionedDate")
    public static Object[][] historyExportDatesDoesNotMetActionedDate() {
        String[][] startAndEndDates = new String[][]{
                {"2012-01-01","2012-02-01"},
                {"2012-10-30","2012-11-02"},
                {"2012-11-02","2012-10-30"},
                {"2012-02-01","2012-01-01"}
        };
        String fileNameTestSuffix = "";
        String[] fileFormats = new String[]{"CSV","XML"};
        String[] campaignNames = new String[]{"Export_test_campaign","Campaign_to_export"};

        CampaignExportEntity dataToMultiply = new CampaignExportEntity(false);
        dataToMultiply.setAvailableFields(new ArrayList<String>());

        Object data [][] = new Object[startAndEndDates.length*fileFormats.length*campaignNames.length][1];
        int z = 0;
        for(int i = 0; i < startAndEndDates.length; i++){
            for(int j = 0; j < fileFormats.length; j++){
                for(int k = 0; k < campaignNames.length; k++){
                    if(campaignNames[k].equals("Export_test_campaign")){
                        dataToMultiply.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
                        dataToMultiply.setAvailableFields(new ArrayList<String>());
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory);
                        fileNameTestSuffix = "TEST";
                    } else {
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory);
                        dataToMultiply.setTestGroups(new ArrayList<String>());
                        fileNameTestSuffix = "NON_TEST";
                    }
                    dataToMultiply.setActionedFrom(startAndEndDates[i%4][0]);
                    dataToMultiply.setActionedTo(startAndEndDates[i%4][1]);
                    dataToMultiply.setExportFileFormat(fileFormats[j]);
                    dataToMultiply.setExportFileName("H_notMetActionedDateTest"+fileNameTestSuffix+"#"+z);
                    data[z] = new Object[]{campaignNames[k],dataToMultiply.copyEntity()};
                    z+=1;
                }
            }
        }
        return data;
    }

    @DataProvider(name = "historyExportDatesPartlyMetActionedDateTest")
    public static Object[][] historyExportDatesPartlyMetActionedDateTest() {
        String[][] startAndEndDates = new String[][]{
                {"2012-10-31","2012-10-31"},
                {"2012-11-01","2012-11-02"},
                {"2012-11-01",""},
                {"","2012-10-31"}
        };
        String fileNameTestSuffix = "";
        String[] fileFormats = new String[]{"CSV","XML"};
        String[] campaignNames = new String[]{"Export_test_campaign","Campaign_to_export"};

        CampaignExportEntity dataToMultiply = new CampaignExportEntity(false);
        dataToMultiply.setAvailableFields(new ArrayList<String>());

        Object data [][] = new Object[startAndEndDates.length*fileFormats.length*campaignNames.length][1];
        int z = 0;
        for(int i = 0; i < startAndEndDates.length; i++){
            for(int j = 0; j < fileFormats.length; j++){
                for(int k = 0; k < campaignNames.length; k++){
                    if(campaignNames[k].equals("Export_test_campaign")){
                        dataToMultiply.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
                        dataToMultiply.setAvailableFields(new ArrayList<String>());
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory);
                        fileNameTestSuffix = "TEST";
                    } else {
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory);
                        dataToMultiply.setTestGroups(new ArrayList<String>());
                        fileNameTestSuffix = "NON_TEST";
                    }
                    dataToMultiply.setActionedFrom(startAndEndDates[i%4][0]);
                    dataToMultiply.setActionedTo(startAndEndDates[i%4][1]);
                    dataToMultiply.setExportFileFormat(fileFormats[j]);
                    dataToMultiply.setExportFileName("H_MetActionedDateTest"+fileNameTestSuffix+"#"+z);
                    data[z] = new Object[]{campaignNames[k],dataToMultiply.copyEntity()};
                    z+=1;
                }
            }
        }
        return data;
    }

    @DataProvider(name = "validateFileNameField")
    public static Object[][] validateFileNameField() {
        String campaignName = "Campaign_to_export";
//        Triplets: test value/expected saved file name/expected name on form after save
        String[] testFileName1 = {"1wrong_file_name\\","1wrong_file_name","1wrong_file_name\\"};
        String[] testFileName2 = {"2wrong_file_name/","2wrong_file_name_","2wrong_file_name/"};
        String[] testFileName3 = {"3wrong_file_name:","3wrong_file_name_","3wrong_file_name:"};
        String[] testFileName4 = {"4wrong_file_name*","4wrong_file_name_","4wrong_file_name*"};
        String[] testFileName5 = {"5wrong_file_name?","5wrong_file_name_","5wrong_file_name?"};
        String[] testFileName6 = {"6wrong_file_name\"","6wrong_file_name","6wrong_file_name\""};
        String[] testFileName7 = {"7wrong_file_name>","7wrong_file_name_","7wrong_file_name>"};
        String[] testFileName8 = {"8wrong_file_name<","8wrong_file_name_","8wrong_file_name<"};
        String[] testFileName9 = {"9wrong_file_name|","9wrong_file_name_","9wrong_file_name|"};
        String[] testFileName10 = {"1234567890_1234567890_1234567890_1234567890_1234567890_1234567890_1234567890_1234567890_1234567890_1234567890", "1234567890_1234567890_1234567890_1234567890_123456","1234567890_1234567890_1234567890_1234567890_123456"};
        String[] testFileName11 = {"<script language='JavaScript'> alert('Добрый день') </script>","_script language='JavaScript'_ alert('Добрый день'","<script language='JavaScript'> alert('Добрый день'"};
        String[] testFileName12 = {"ASDGFFLKLKLKlddvddsghdsh", "ASDGFFLKLKLKlddvddsghdsh","ASDGFFLKLKLKlddvddsghdsh"};
        String[] testFileName13 = {":!@#$%^&*()_.", "_!@#$%^&_()_.",":!@#$%^&*()_."};
        String[] testFileName14 = {"ASDASDasdas-123123211212&^%&^%", "ASDASDasdas-123123211212&^%&^%","ASDASDasdas-123123211212&^%&^%"};

        CampaignExportEntity dataNonTest = new CampaignExportEntity(false);
             dataNonTest.setActionedFrom(Config.getFloatingFutureDate(1, "yyyy-MM-dd"));

        return new Object[][]{
                {campaignName, dataNonTest,testFileName1},
                {campaignName, dataNonTest,testFileName2},
                {campaignName, dataNonTest,testFileName3},
                {campaignName, dataNonTest,testFileName4},
                {campaignName, dataNonTest,testFileName5},
//TODO    clarify whether are these a bugs
//                {campaignName, dataNonTest,testFileName6},
                {campaignName, dataNonTest,testFileName7},
                {campaignName, dataNonTest,testFileName8},
                {campaignName, dataNonTest,testFileName9},
                {campaignName, dataNonTest,testFileName10},
//                {campaignName, dataNonTest,testFileName11},
                {campaignName, dataNonTest,testFileName12},
                {campaignName, dataNonTest,testFileName13},
                {campaignName, dataNonTest,testFileName14}
        };
    }

    @DataProvider(name = "historyDifferentFieldSelection")
    public static Object[][] historyDifferentFieldSelection() {
        CampaignExportEntity dataNonTest = new CampaignExportEntity(false);
        dataNonTest.setExportFileName("H_noFields_nonTest");
        dataNonTest.setActionedFrom("2011-01-01");
        dataNonTest.setActionedTo("2013-01-01");

        CampaignExportEntity dataTest = new CampaignExportEntity(true);
        dataTest.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        dataTest.setExportFileName("H_noFields_Test");
        dataTest.setActionedFrom("2011-01-01");
        dataTest.setActionedTo("2013-01-01");

        CampaignExportEntity dataNonTest1 = dataNonTest.copyEntity();
        dataNonTest1.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.subList(1, CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.size()));
        dataNonTest1.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.subList(0,1));
        dataNonTest1.setExportFileName("H_oneField_nonTest");

        CampaignExportEntity dataNonTest2 = dataNonTest.copyEntity();
        dataNonTest2.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.subList(5, CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.size()));
        dataNonTest2.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.subList(0,5));
        dataNonTest2.setExportFileName("H_fiveFields_nonTest");

        CampaignExportEntity dataNonTest3 = dataNonTest.copyEntity();
        dataNonTest3.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.subList(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.size()-1, CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.size()));
        dataNonTest3.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.subList(0, CampaignExportEntity.allNonTestActivityFieldsPlayerHistory.size()-1));
        dataNonTest3.setExportFileName("H_exceptOneField_nonTest");

        CampaignExportEntity dataTest1 = dataTest.copyEntity();
        dataTest1.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory.subList(1, CampaignExportEntity.allTestActivityFieldsPlayerHistory.size()));
        dataTest1.setAvailableFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory.subList(0,1));
        dataTest1.setExportFileName("H_oneField_Test");

        CampaignExportEntity dataTest2 = dataTest.copyEntity();
        dataTest2.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory.subList(5, CampaignExportEntity.allTestActivityFieldsPlayerHistory.size()));
        dataTest2.setAvailableFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory.subList(0,5));
        dataTest2.setExportFileName("H_fiveFields_Test");

        CampaignExportEntity dataTest3 = dataTest.copyEntity();
        dataTest3.setSelectedFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory.subList(CampaignExportEntity.allTestActivityFieldsPlayerHistory.size()-1, CampaignExportEntity.allTestActivityFieldsPlayerHistory.size()));
        dataTest3.setAvailableFields(CampaignExportEntity.allTestActivityFieldsPlayerHistory.subList(0, CampaignExportEntity.allTestActivityFieldsPlayerHistory.size()-1));
        dataTest3.setExportFileName("H_exceptOneField_Test");

        return new Object[][]{
                {"Export_test_campaign", dataTest, "CSV"},
                {"Export_test_campaign", dataTest, "XML"},
                {"Campaign_to_export", dataNonTest,"CSV"},
                {"Campaign_to_export", dataNonTest,"XML"},

                {"Export_test_campaign", dataTest1, "CSV"},
                {"Export_test_campaign", dataTest1, "XML"},
                {"Campaign_to_export", dataNonTest1,"CSV"},
                {"Campaign_to_export", dataNonTest1,"XML"},

                {"Export_test_campaign", dataTest2, "CSV"},
                {"Export_test_campaign", dataTest2, "XML"},
                {"Campaign_to_export", dataNonTest2,"CSV"},
                {"Campaign_to_export", dataNonTest2,"XML"},

                {"Export_test_campaign", dataTest3, "CSV"},
                {"Export_test_campaign", dataTest3, "XML"},
                {"Campaign_to_export", dataNonTest3,"CSV"},
                {"Campaign_to_export", dataNonTest3,"XML"}
        };
    }

//U N I Q U E   A C T I O N E D   P L A Y E R S   D A T A
@DataProvider(name = "uniqueCheckUIData")
public static Object[][] uniqueCheckUIData() {
    CampaignExportEntity dataNonTest = new CampaignExportEntity(false);
    dataNonTest.setExportType("Unique Actioned Players");
    dataNonTest.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers);
    CampaignExportEntity dataTest = new CampaignExportEntity(true);
    dataTest.setExportType("Unique Actioned Players");
    dataTest.setAvailableFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
    dataTest.setTestGroups(Arrays.asList("All Test Groups"));

    return new Object[][]{
            {dataNonTest, false},
            {dataTest, true}
    };
}

    @DataProvider(name = "uniqueExportTestGroupsData")
    public static Object[][] uniqueExportTestGroupsData() {
        String campaignName = "Export_test_campaign";
        List<String> expectedAvailableFields = new ArrayList<String>();
        CampaignExportEntity data = new CampaignExportEntity(true);
        data.setExportType("Unique Actioned Players");
        data.setAvailableFields(expectedAvailableFields);
        data.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
        data.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        data.setExportFileName("U_exportAllTestGroups");

        return new Object[][]{
                {campaignName,"XML", data},
                {campaignName,"CSV", data}
        };
    }

    @DataProvider(name = "uniqueExportOneTestGroupData")
    public static Object[][] uniqueExportOneTestGroupData() {
        String campaignName = "Export_test_campaign";
        List<String> expectedAvailableFields = new ArrayList<String>();
        CampaignExportEntity data1 = new CampaignExportEntity(true);
        data1.setExportType("Unique Actioned Players");
        data1.setAvailableFields(expectedAvailableFields);
        data1.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
        data1.setTestGroups(Arrays.asList("Control Group"));
        data1.setExportFileName("U_exportOneTestGroup_ControlGroup");
        data1.setActionedFrom("2012-10-01");

        CampaignExportEntity data2 = data1.copyEntity();
        data2.setTestGroups(Arrays.asList("Test Group 1"));
        data2.setExportFileName("U_exportOneTestGroup_TestGroup1");

        CampaignExportEntity data3 = data1.copyEntity();
        data3.setTestGroups(Arrays.asList("Test Group 2"));
        data3.setExportFileName("U_exportOneTestGroup_TestGroup2");
        return new Object[][]{
                {campaignName, "CSV", data1},
                {campaignName, "XML", data1},
                {campaignName, "CSV", data2},
                {campaignName, "XML", data2},
                {campaignName, "CSV", data3},
                {campaignName, "XML", data3}
        };
    }

    @DataProvider(name = "uniqueExportDoNotSelectTestGroups")
    public static Object[][] uniqueExportDoNotSelectTestGroups() {
        String campaignName = "Export_test_campaign";
        List<String> expectedAvailableFields = new ArrayList<String>();
        CampaignExportEntity data = new CampaignExportEntity(true);
        data.setExportType("Unique Actioned Players");
        data.setAvailableFields(expectedAvailableFields);
        data.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
        data.setTestGroups(Arrays.asList("All Test Groups"));
        data.setExportFileName("U_ExportDoNotSelectTestGroups");
        data.setActionedFrom("2012-10-01");

        return new Object[][]{
                {campaignName,"XML", data},
                {campaignName,"CSV", data}
        };
    }

    @DataProvider(name = "uniqueExportDatesDoesNotMetActionedDate")
    public static Object[][] uniqueExportDatesDoesNotMetActionedDate() {
        String[][] startAndEndDates = new String[][]{
                {"2012-01-01","2012-02-01"},
                {"2012-10-30","2012-11-02"},
                {"2012-11-02","2012-10-30"},
                {"2012-02-01","2012-01-01"}
        };
        String fileNameTestSuffix = "";
        String[] fileFormats = new String[]{"CSV","XML"};
        String[] campaignNames = new String[]{"Export_test_campaign","Campaign_to_export"};

        CampaignExportEntity dataToMultiply = new CampaignExportEntity(false);
        dataToMultiply.setExportType("Unique Actioned Players");
        dataToMultiply.setAvailableFields(new ArrayList<String>());

        Object data [][] = new Object[startAndEndDates.length*fileFormats.length*campaignNames.length][1];
        int z = 0;
        for(int i = 0; i < startAndEndDates.length; i++){
            for(int j = 0; j < fileFormats.length; j++){
                for(int k = 0; k < campaignNames.length; k++){
                    if(campaignNames[k].equals("Export_test_campaign")){
                        dataToMultiply.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
                        dataToMultiply.setAvailableFields(new ArrayList<String>());
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
                        fileNameTestSuffix = "TEST";
                    } else {
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers);
                        dataToMultiply.setTestGroups(new ArrayList<String>());
                        fileNameTestSuffix = "NON_TEST";
                    }
                    dataToMultiply.setActionedFrom(startAndEndDates[i%4][0]);
                    dataToMultiply.setActionedTo(startAndEndDates[i%4][1]);
                    dataToMultiply.setExportFileFormat(fileFormats[j]);
                    dataToMultiply.setExportFileName("U_notMetActionedDateTest"+fileNameTestSuffix+"#"+z);
                    data[z] = new Object[]{campaignNames[k],dataToMultiply.copyEntity()};
                    z+=1;
                }
            }
        }
        return data;
    }

    @DataProvider(name = "uniqueExportDatesPartlyMetActionedDateTest")
    public static Object[][] uniqueExportDatesPartlyMetActionedDateTest() {
        String[][] startAndEndDates = new String[][]{
                {"2012-10-31","2012-10-31"},
                {"2012-11-01","2012-11-02"},
                {"2012-11-01",""},
                {"","2012-10-31"}
        };
        String fileNameTestSuffix = "";
        String[] fileFormats = new String[]{"CSV","XML"};
        String[] campaignNames = new String[]{"Export_test_campaign","Campaign_to_export"};

        CampaignExportEntity dataToMultiply = new CampaignExportEntity(false);
        dataToMultiply.setExportType("Unique Actioned Players");
        dataToMultiply.setAvailableFields(new ArrayList<String>());

        Object data [][] = new Object[startAndEndDates.length*fileFormats.length*campaignNames.length][1];
        int z = 0;
        for(int i = 0; i < startAndEndDates.length; i++){
            for(int j = 0; j < fileFormats.length; j++){
                for(int k = 0; k < campaignNames.length; k++){
                    if(campaignNames[k].equals("Export_test_campaign")){
                        dataToMultiply.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
                        dataToMultiply.setAvailableFields(new ArrayList<String>());
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
                        fileNameTestSuffix = "TEST";
                    } else {
                        dataToMultiply.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers);
                        dataToMultiply.setTestGroups(new ArrayList<String>());
                        fileNameTestSuffix = "NON_TEST";
                    }
                    dataToMultiply.setActionedFrom(startAndEndDates[i%4][0]);
                    dataToMultiply.setActionedTo(startAndEndDates[i%4][1]);
                    dataToMultiply.setExportFileFormat(fileFormats[j]);
                    dataToMultiply.setExportFileName("U_MetActionedDateTest"+fileNameTestSuffix+"#"+z);
                    data[z] = new Object[]{campaignNames[k],dataToMultiply.copyEntity()};
                    z+=1;
                }
            }
        }
        return data;
    }

    @DataProvider(name = "uniqueDifferentFieldSelection")
    public static Object[][] uniqueDifferentFieldSelection() {
        CampaignExportEntity dataNonTest = new CampaignExportEntity(false);
        dataNonTest.setExportType("Unique Actioned Players");
        dataNonTest.setExportFileName("U_noFields_nonTest");
        dataNonTest.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers);
        dataNonTest.setActionedFrom("2011-01-01");
        dataNonTest.setActionedTo("2013-01-01");

        CampaignExportEntity dataTest = new CampaignExportEntity(true);
        dataTest.setExportType("Unique Actioned Players");
        dataTest.setExportFileName("U_noFields_Test");
        dataTest.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        dataTest.setAvailableFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers);
        dataTest.setActionedFrom("2011-01-01");
        dataTest.setActionedTo("2013-01-01");

        CampaignExportEntity dataNonTest1 = dataNonTest.copyEntity();
        dataNonTest1.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.subList(1,CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.size()));
        dataNonTest1.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.subList(0,1));
        dataNonTest1.setExportFileName("U_oneField_nonTest");

        CampaignExportEntity dataNonTest2 = dataNonTest.copyEntity();
        dataNonTest2.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.subList(5,CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.size()));
        dataNonTest2.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.subList(0,5));
        dataNonTest2.setExportFileName("U_fiveFields_nonTest");

        CampaignExportEntity dataNonTest3 = dataNonTest.copyEntity();
        dataNonTest3.setSelectedFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.subList(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.size()-1,CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.size()));
        dataNonTest3.setAvailableFields(CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.subList(0,CampaignExportEntity.allNonTestActivityFieldsUniquePlayers.size()-1));
        dataNonTest3.setExportFileName("U_exceptOneField_nonTest");

        CampaignExportEntity dataTest1 = dataTest.copyEntity();
        dataTest1.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers.subList(1,CampaignExportEntity.allTestActivityFieldsUniquePlayers.size()));
        dataTest1.setAvailableFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers.subList(0,1));
        dataTest1.setExportFileName("U_oneField_Test");

        CampaignExportEntity dataTest2 = dataTest.copyEntity();
        dataTest2.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers.subList(5,CampaignExportEntity.allTestActivityFieldsUniquePlayers.size()));
        dataTest2.setAvailableFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers.subList(0,5));
        dataTest2.setExportFileName("U_fiveFields_Test");

        CampaignExportEntity dataTest3 = dataTest.copyEntity();
        dataTest3.setSelectedFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers.subList(CampaignExportEntity.allTestActivityFieldsUniquePlayers.size()-1,CampaignExportEntity.allTestActivityFieldsUniquePlayers.size()));
        dataTest3.setAvailableFields(CampaignExportEntity.allTestActivityFieldsUniquePlayers.subList(0,CampaignExportEntity.allTestActivityFieldsUniquePlayers.size()-1));
        dataTest3.setExportFileName("U_exceptOneField_Test");

        return new Object[][]{
                {"Export_test_campaign", dataTest, "CSV"},
                {"Export_test_campaign", dataTest, "XML"},
                {"Campaign_to_export", dataNonTest,"CSV"},
                {"Campaign_to_export", dataNonTest,"XML"},

                {"Export_test_campaign", dataTest1, "CSV"},
                {"Export_test_campaign", dataTest1, "XML"},
                {"Campaign_to_export", dataNonTest1,"CSV"},
                {"Campaign_to_export", dataNonTest1,"XML"},

                {"Export_test_campaign", dataTest2, "CSV"},
                {"Export_test_campaign", dataTest2, "XML"},
                {"Campaign_to_export", dataNonTest2,"CSV"},
                {"Campaign_to_export", dataNonTest2,"XML"},

                {"Export_test_campaign", dataTest3, "CSV"},
                {"Export_test_campaign", dataTest3, "XML"},
                {"Campaign_to_export", dataNonTest3,"CSV"},
                {"Campaign_to_export", dataNonTest3,"XML"}
        };
    }
}
