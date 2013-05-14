package com.playtech.cm.utils.entities;

import com.playtech.cm.utils.Utils;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 03/05/12
 * Time: 18:21
 */
public class SearchData {
    private String name;
    private String instance;
    private String category;
    private String startDate;
    private String endDate;
    private Boolean ongoing;
    private String publicationDate;
    private String createdUpdatedApprovedBy;
    private String user;
    private String betweenDateFirst;
    private String betweenDateLast;
    private Boolean includeArchived;
    private String status;
    private String id;
    private String tags;
    private Boolean testActivityNo;
    private Boolean testActivityYes;
    private Boolean testActivityBoth;
    private String description;


    public SearchData(){
        this.name = "DEFAULT TEST CAMPAIGN NAME";
        this.instance = "playtech81004";
        this.category = "Acquisition";
        this.startDate = "2010-01-01 11:06";
        this.endDate = "2020-01-01 00:00";
        this.ongoing = false;
        this.publicationDate = "";
        this.createdUpdatedApprovedBy = "";
        this.user = "protrack";
        this.betweenDateFirst = "";
        this.betweenDateLast = "";
        this.includeArchived = false;
        this.status = "In Design";
        this.id = "";
        this.tags = "";
        this.testActivityNo = false;
        this.testActivityYes = false;
        this.testActivityBoth = true;
    }

    @DataProvider(name = "getIdValidateData")
    public static Object[][] getIdValidateData() {
        String searchString1 = "10001";
        String searchString2 = "123456789012345678";
        String searchString3 = "1000";
        String searchString4 = "123456!@#$%^&(*()";
        String searchString5 = "abcD";
        String searchString6 = "<script language=\"JavaScript\"> alert('Добрый день') </script>";
        String searchString7 = "987654321";
        String searchString8 = " ";
        String errMsg1 = "ID. Please enter a non-decimal numeric value.";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setId(searchString1);
        searchDataResult1.setName("Test id existing");
        searchDataResult1.setInstance("playtech81004");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult1.setCategory("Acquisition");
        searchDataResult1.setStatus("In Design");



        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setId(searchString2);
        searchDataResult2.setName("Test id existing 19 digit length");
        searchDataResult2.setInstance("playtech81004");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult2.setCategory("Acquisition");
        searchDataResult2.setStatus("In Design");

        return new Object[][]{
                {searchString1, Arrays.asList(searchDataResult1), 1, ""},
                {searchString2, Arrays.asList(searchDataResult2), 1, ""},
                {searchString3, new ArrayList(), 0, ""},
                {searchString4, Arrays.asList(searchDataResult1, searchDataResult2), 2, errMsg1},
                {searchString5, Arrays.asList(searchDataResult1, searchDataResult2), 2, errMsg1},
                {searchString6, Arrays.asList(searchDataResult1, searchDataResult2), 2, errMsg1},
                {searchString7, new ArrayList(), 0, ""},
                {searchString8, Arrays.asList(searchDataResult1, searchDataResult2), 2, ""},
        };

    }

    @DataProvider(name = "getInstanceValidateData")
    public static Object[][] getInstanceValidateData() {
        String searchString1 = "playtech81004";
        String searchString2 = "playtech81008";
        String searchString3 = "All";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setInstance(searchString1);
        searchDataResult1.setName("Instance test1");
        searchDataResult1.setId("10001");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult1.setCategory("Acquisition");
        searchDataResult1.setStatus("In Design");

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setInstance(searchString1);
        searchDataResult2.setName("Instance test2");
        searchDataResult2.setId("10002");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult2.setCategory("Acquisition");
        searchDataResult2.setStatus("In Design");

        return new Object[][]{
                {searchString1, Arrays.asList(searchDataResult1, searchDataResult2), 2},
                {searchString2, new ArrayList(), 0},
        };
    }

    @DataProvider(name = "getNameValidateData")
    public static Object[][] getNameValidateData() {
        String searchString1 = "SimpleName";
        String searchString2 = "1234567890";
        String searchString3 = "!@#$%^*()_+=-?.>/,`~";
        String searchString4 = "<script language='JavaScript'> alert('Добрый день') </script>";
        String searchString5 = "asdddddddd!@#dqweasdadaasddddddddddddqweasdadaasddddddddddddqweasdadaasddddddddddddqweasdadaasdddddddddddd1234adadaasddddddddddddqweasdadaasddddddddddddqweasdadaasddddddddddddqweasdadaasddddddddddddqw";
        String searchString6 = "NOT EXISTED";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName(searchString1);
        searchDataResult1.setId("10001");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName(searchString2);
        searchDataResult2.setId("10002");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName(searchString3);
        searchDataResult3.setId("10003");
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName(searchString4);
        searchDataResult4.setId("10004");
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult5 = new SearchData();
        searchDataResult5.setName(searchString5);
        searchDataResult5.setId("10005");
        searchDataResult5.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult5.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        return new Object[][]{
                {searchString1, Arrays.asList(searchDataResult1), 1},
                {searchString2, Arrays.asList(searchDataResult2), 1},
                {searchString3, Arrays.asList(searchDataResult3), 1},
                {searchString4, Arrays.asList(searchDataResult4), 1},
                {searchString5, Arrays.asList(searchDataResult5), 1},
                {searchString6, new ArrayList(), 0},
        };
    }

    @DataProvider(name = "getTagValidateData")
    public static Object[][] getTagValidateData() {
        String searchString1 = "TagValue";
        String searchString2 = "1234567890";
        String searchString3 = "!@#$%^*()_+=-?.>/";
        String searchString4 = "Search name123456!@#$%^(*()";
        String searchString5 = "<script language='JavaScript'> alert('Добрый день') </script>";
        String searchString6 = "NOT EXISTED";
        String searchString7 = "asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasda";


        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setTags(searchString1);
        searchDataResult1.setName("name1");
        searchDataResult1.setId("10001");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName("name2");
        searchDataResult2.setId("10002");
        searchDataResult2.setTags(searchString2);
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName("name3");
        searchDataResult3.setId("10003");
        searchDataResult3.setTags(searchString3);
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName("name4");
        searchDataResult4.setId("10004");
        searchDataResult4.setTags(searchString4);
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult5 = new SearchData();
        searchDataResult5.setName("name5");
        searchDataResult5.setId("10005");
        searchDataResult5.setTags(searchString5);
        searchDataResult5.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult5.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        return new Object[][]{
                {searchString1, Arrays.asList(searchDataResult1), 1},
                {searchString2, Arrays.asList(searchDataResult2), 1},
                {searchString3, Arrays.asList(searchDataResult3), 1},
                {searchString4, Arrays.asList(searchDataResult4), 1},
                {searchString5, Arrays.asList(searchDataResult5), 1},
                {searchString6, new ArrayList(), 0},
                {searchString7, new ArrayList(), 0},
        };
    }

    @DataProvider(name = "getStatusData")
    public static Object[][] getStatusData(){
      String status1 = "In Design";
      String status2 = "Approved";
      String status3 = "Active";
      String status4 = "Completed";
      String status5 = "Disabled";
      String status6 = "Paused";
      String status7 = "Pending Approval";
      String status8 = "All";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName(status1);
        searchDataResult1.setId("10001");
        searchDataResult1.setStatus(status1);
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName(status2);
        searchDataResult2.setId("10002");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2019-02-04 01:00"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));
        searchDataResult2.setStatus(status2);

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName(status3);
        searchDataResult3.setId("10003");
        searchDataResult3.setStatus(status3);
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName(status4);
        searchDataResult4.setId("10004");
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2019-02-04 01:00"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));
        searchDataResult4.setStatus(status4);

        SearchData searchDataResult5 = new SearchData();
        searchDataResult5.setName(status5);
        searchDataResult5.setId("10005");
        searchDataResult5.setStatus(status5);
        searchDataResult5.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult5.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult6 = new SearchData();
        searchDataResult6.setName(status6);
        searchDataResult6.setId("10006");
        searchDataResult6.setStatus(status6);
        searchDataResult6.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult6.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult7 = new SearchData();
        searchDataResult7.setName(status7);
        searchDataResult7.setId("10007");
        searchDataResult7.setStatus(status7);
        searchDataResult7.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06:00"));
        searchDataResult7.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));


        return new Object[][]{
                {status1, Arrays.asList(searchDataResult1), 1},
                {status2, Arrays.asList(searchDataResult2), 1},
                {status3, Arrays.asList(searchDataResult3), 1},
                {status4, Arrays.asList(searchDataResult4), 1},
                {status5, Arrays.asList(searchDataResult5), 1},
                {status6, Arrays.asList(searchDataResult6), 1},
                {status7, Arrays.asList(searchDataResult7), 1},
                {status8, Arrays.asList(searchDataResult1, searchDataResult2, searchDataResult3, searchDataResult4,
                        searchDataResult5, searchDataResult6, searchDataResult7), 7},
        };
    }

    @DataProvider(name = "getCategoryData")
    public static Object[][] getCategoryData(){
        String category1 = "Acquisition";
        String category2 = "Retention";
        String category3 = "Conversion";
        String category4 = "Cross Marketing";
        String category5 = "Reactivation";
        String category6 = "All";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName("In Design");
        searchDataResult1.setStatus("In Design");
        searchDataResult1.setCategory(category1);
        searchDataResult1.setInstance("playtech81004");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult1.setId("10001");

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName("Approved");
        searchDataResult2.setStatus("Approved");
        searchDataResult2.setCategory(category2);
        searchDataResult2.setInstance("playtech81004");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2015-01-01 11:06"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 11:06"));
        searchDataResult2.setId("10002");

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName("Active");
        searchDataResult3.setStatus("Active");
        searchDataResult3.setCategory(category3);
        searchDataResult3.setInstance("playtech81004");
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 11:06"));
        searchDataResult3.setId("10003");

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName("Completed");
        searchDataResult4.setStatus("Completed");
        searchDataResult4.setCategory(category4);
        searchDataResult4.setInstance("playtech81004");
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2009-01-01 11:06"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult4.setId("10004");

        SearchData searchDataResult5 = new SearchData();
        searchDataResult5.setName("Disabled");
        searchDataResult5.setStatus("Disabled");
        searchDataResult5.setCategory(category5);
        searchDataResult5.setInstance("playtech81004");
        searchDataResult5.setStartDate(Utils.getConvertedDateToCommonFormat("2015-01-01 11:06"));
        searchDataResult5.setEndDate(Utils.getConvertedDateToCommonFormat("2018-01-01 11:06"));
        searchDataResult5.setId("10005");

        return new Object[][]{
                {category1, Arrays.asList(searchDataResult1), 1},
                {category2, Arrays.asList(searchDataResult2), 1},
                {category3, Arrays.asList(searchDataResult3), 1},
                {category4, Arrays.asList(searchDataResult4), 1},
                {category5, Arrays.asList(searchDataResult5), 1},
                {category6, Arrays.asList(searchDataResult1, searchDataResult2, searchDataResult3, searchDataResult4,
                        searchDataResult5), 5},
        };
    }

    @DataProvider(name = "getTestValidateData")
    public static Object[][] getTestValidateData() {
        Boolean searchCriteria1 = true;
        Boolean searchCriteria2 = false;

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName("TEST CMP");
        searchDataResult1.setId("10001");
        searchDataResult1.setTestActivityYes(searchCriteria1);
        searchDataResult1.setTestActivityNo(searchCriteria2);
        searchDataResult1.setTestActivityBoth(searchCriteria2);
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName("SECOND TEST CMP");
        searchDataResult2.setId("10002");
        searchDataResult2.setTestActivityYes(searchCriteria1);
        searchDataResult2.setTestActivityNo(searchCriteria2);
        searchDataResult2.setTestActivityBoth(searchCriteria2);
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName("NON TEST CMP");
        searchDataResult3.setId("10003");
        searchDataResult3.setTestActivityYes(searchCriteria2);
        searchDataResult3.setTestActivityNo(searchCriteria1);
        searchDataResult3.setTestActivityBoth(searchCriteria2);
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setTestActivityYes(searchCriteria2);
        searchDataResult4.setTestActivityNo(searchCriteria2);
        searchDataResult4.setTestActivityBoth(searchCriteria1);
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2010-01-01 11:06"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        return new Object[][]{
                {searchDataResult1, Arrays.asList(searchDataResult1, searchDataResult2), 2},
                {searchDataResult3, Arrays.asList(searchDataResult3), 1},
                {searchDataResult4, Arrays.asList(searchDataResult1, searchDataResult2, searchDataResult3), 3}
        };
    }
    
@DataProvider()
    public static Object[][] getCreatedByUserData() {
        String searchString1 = "Denys_M";
        String searchString2 = "GNimmakayala";
        String searchString3 = "Gavin_Harris";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName("Test search by user1");
        searchDataResult1.setId("10001");
        searchDataResult1.setStatus("Approved");
        searchDataResult1.setCreatedUpdatedApprovedBy("Created");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult1.setInstance("playtech81004");

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName("Test search by user2");
        searchDataResult2.setId("10002");
        searchDataResult2.setStatus("Approved");
        searchDataResult2.setCreatedUpdatedApprovedBy("Created");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult2.setInstance("playtech81004");

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName("Test search by user3");
        searchDataResult3.setId("10003");
        searchDataResult3.setStatus("Approved");
        searchDataResult3.setCreatedUpdatedApprovedBy("Created");
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult3.setInstance("playtech81004");

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName("Test search by user4");
        searchDataResult4.setId("10004");
        searchDataResult4.setStatus("Approved");
        searchDataResult4.setCreatedUpdatedApprovedBy("Created");
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));
        searchDataResult4.setInstance("playtech81004");


        return new Object[][]{
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString1, Arrays.asList(searchDataResult1, searchDataResult4), 2},
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString2, Arrays.asList(searchDataResult2), 1},
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString3, Arrays.asList(searchDataResult3), 1},
        };
    }

    @DataProvider()
        public static Object[][] getUpdatedByUserData() {
        String searchString1 = "GNimmakayala";
        String searchString2 = "Denys_M";
        String searchString3 = "Gavin_Harris";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName("Test search by user1");
        searchDataResult1.setId("10001");
        searchDataResult1.setUser(searchString1);
        searchDataResult1.setStatus("Approved");
        searchDataResult1.setCreatedUpdatedApprovedBy("Updated");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName("Test search by user2");
        searchDataResult2.setId("10002");
        searchDataResult2.setUser(searchString2);
        searchDataResult2.setStatus("Approved");
        searchDataResult2.setCreatedUpdatedApprovedBy("Updated");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName("Test search by user3");
        searchDataResult3.setId("10003");
        searchDataResult3.setUser(searchString3);
        searchDataResult3.setStatus("Approved");
        searchDataResult3.setCreatedUpdatedApprovedBy("Updated");
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName("Test search by user4");
        searchDataResult4.setId("10004");
        searchDataResult4.setStatus("Approved");
        searchDataResult4.setCreatedUpdatedApprovedBy("Created");
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00:00"));

        return new Object[][]{
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString1, Arrays.asList(searchDataResult1), 1},
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString2, Arrays.asList(searchDataResult2, searchDataResult4), 2},
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString3, Arrays.asList(searchDataResult3), 1}
        };
    }

    @DataProvider()
    public static Object[][] getApprovedByUserData() {
        String searchString1 = "Gavin_Harris";
        String searchString2 = "GNimmakayala";
        String searchString3 = "Denys_M";

        SearchData searchDataResult1 = new SearchData();
        searchDataResult1.setName("Test search by user1");
        searchDataResult1.setId("10001");
        searchDataResult1.setStatus("Approved");
        searchDataResult1.setCreatedUpdatedApprovedBy("Approved");
        searchDataResult1.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult1.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        SearchData searchDataResult2 = new SearchData();
        searchDataResult2.setName("Test search by user2");
        searchDataResult2.setId("10002");
        searchDataResult2.setStatus("Approved");
        searchDataResult2.setCreatedUpdatedApprovedBy("Approved");
        searchDataResult2.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult2.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        SearchData searchDataResult3 = new SearchData();
        searchDataResult3.setName("Test search by user3");
        searchDataResult3.setId("10003");
        searchDataResult3.setStatus("Approved");
        searchDataResult3.setCreatedUpdatedApprovedBy("Approved");
        searchDataResult3.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult3.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        SearchData searchDataResult4 = new SearchData();
        searchDataResult4.setName("Test search by user4");
        searchDataResult4.setId("10004");
        searchDataResult4.setStatus("Approved");
        searchDataResult4.setCreatedUpdatedApprovedBy("Approved");
        searchDataResult4.setStartDate(Utils.getConvertedDateToCommonFormat("2019-01-01 00:00"));
        searchDataResult4.setEndDate(Utils.getConvertedDateToCommonFormat("2020-01-01 00:00"));

        return new Object[][]{
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString1, Arrays.asList(searchDataResult1), 1},
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString2, Arrays.asList(searchDataResult2), 1},
                { searchDataResult1.getCreatedUpdatedApprovedBy(), searchString3, Arrays.asList(searchDataResult3, searchDataResult4), 2}
        };
    }

    @DataProvider(name = "getAllData")
    public static Object[][] getAllData(){

        SearchData searchData = new SearchData();
        searchData.setId("18009");
        searchData.setName("CName");
        searchData.setDescription("Description");
        searchData.setTags("CTag");
        searchData.setStatus("In Design");
        searchData.setCategory("Acquisition");
        searchData.setInstance("playtech81004");
        searchData.setStartDate("2012-08-29 13:41");
        searchData.setEndDate("2020-01-01 00:00");
        searchData.setBetweenDateFirst("2012-09-04");
        searchData.setBetweenDateLast("2012-09-06");
        searchData.setUser("Denys_M");
        searchData.setCreatedUpdatedApprovedBy("Updated");
        searchData.setTestActivityBoth(false);
        searchData.setTestActivityNo(true);
        searchData.setPublicationDate("2012-08-29");

        SearchData expectedData = new SearchData();
        expectedData.setId("18009");
        expectedData.setName("CName");
        expectedData.setDescription("Description");
        expectedData.setTags("CTag");
        expectedData.setStatus("In Design");
        expectedData.setCategory("Acquisition");
        expectedData.setInstance("playtech81004");
        expectedData.setStartDate(Utils.getConvertedDateToCommonFormat(searchData.getStartDate()));
        expectedData.setEndDate(Utils.getConvertedDateToCommonFormat(searchData.getEndDate()));
        expectedData.setBetweenDateFirst("2012-09-04");
        expectedData.setBetweenDateLast("2012-09-06");
        expectedData.setUser("Denys_M");
        expectedData.setCreatedUpdatedApprovedBy("Updated");
        expectedData.setTestActivityBoth(false);
        expectedData.setTestActivityNo(true);
        expectedData.setPublicationDate("2012-08-29");

        return new Object[][]{
                {searchData, Arrays.asList(expectedData)},

        };
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getInstance() {
        return this.instance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCreatedUpdatedApprovedBy() {
        return createdUpdatedApprovedBy;
    }

    public void setCreatedUpdatedApprovedBy(String createdUpdatedApprovedBy) {
        this.createdUpdatedApprovedBy = createdUpdatedApprovedBy;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBetweenDateFirst() {
        return betweenDateFirst;
    }

    public void setBetweenDateFirst(String betweenDateFirst) {
        this.betweenDateFirst = betweenDateFirst;
    }

    public String getBetweenDateLast() {
        return betweenDateLast;
    }

    public void setBetweenDateLast(String betweenDateLast) {
        this.betweenDateLast = betweenDateLast;
    }

    public Boolean getIncludeArchived() {
        return includeArchived;
    }

    public void setIncludeArchived(Boolean includeArchived) {
        this.includeArchived = includeArchived;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getTestActivityNo() {
        return testActivityNo;
    }

    public void setTestActivityNo(Boolean testActivityNo) {
        this.testActivityNo = testActivityNo;
    }

    public Boolean getTestActivityYes() {
        return testActivityYes;
    }

    public void setTestActivityYes(Boolean testActivityYes) {
        this.testActivityYes = testActivityYes;
    }

    public Boolean getTestActivityBoth() {
        return testActivityBoth;
    }

    public void setTestActivityBoth(Boolean testActivityBoth) {
        this.testActivityBoth = testActivityBoth;
    }
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchData that = (SearchData) o;

        if (category != null ? !category.equals(that.category) : that.category != null) {
           System.out.println("This category: "+ this.category+" That category: "+ that.category );
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            System.out.println("This endDate: "+ this.endDate+" That endDate: "+ that.endDate );
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            System.out.println("This id: "+ this.id+" That id: "+ that.id );
            return false;
        }
        if (instance != null ? !instance.equals(that.instance) : that.instance != null) {
            System.out.println("This instance:"+ this.instance+" That instance: "+ that.instance );
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            System.out.println("This name: "+ this.name+" That name: "+ that.name );
            return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
            System.out.println("This startDate: "+ this.startDate+" That startDate: "+ that.startDate );
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null){
            System.out.println("This status: "+ this.status+" That status: "+ that.status );
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
