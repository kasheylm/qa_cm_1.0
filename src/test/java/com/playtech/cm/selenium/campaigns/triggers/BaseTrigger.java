package com.playtech.cm.selenium.campaigns.triggers;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DBData;
import com.playtech.cm.utils.entities.TriggerData;

import java.util.*;

import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/27/12
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */

public class BaseTrigger extends BaseTest{

    public void assertTwoListEqualsWithoutOrder(List expectedList, List actualList){
        assertTrue(actualList.containsAll(expectedList));
        assertTrue(expectedList.containsAll(actualList));
    }

    public void assertTriggerData(TriggerData expectedData, String campaignName, String activityName){
        List<String> actualExclusionDates = DBData.getExclusionDates(campaignName, activityName);
        List<String> expectedExcludingDates = expectedData.getExcludingDates();

        List<String> actualCronList = DBData.getCronExpressions(campaignName, activityName);
        List<String> expectedCronList = expectedData.getMaskInDb();

        ArrayList<String> actualFilters = DBData.getActivityFilters(campaignName, activityName);
        ArrayList<String> expectedFilters = new ArrayList<String>(Arrays.asList(expectedData.getShownSummary()+";"+expectedData.getShownTrigger()));

        assertTwoListEqualsWithoutOrder(expectedExcludingDates, actualExclusionDates);
        assertEqualsCronExpressionLists(expectedCronList,actualCronList);
        assertEqualsFilterList(expectedFilters,actualFilters);
    }

    private void assertEqualsCronExpressionLists(List<String> expectedCronList,List<String> actualCronList){
        ArrayList<List<String>> actualRows = new ArrayList<List<String>>();
        for(String row:actualCronList){
            List<String> actualParts = Arrays.asList(row.split("(\\s\\?\\s\\*\\s)|,"));
            Collections.sort(actualParts);
            actualRows.add(actualParts);
        }
        ArrayList<List<String>> expectedRows = new ArrayList<List<String>>();
        for(String row:expectedCronList){
            List<String> expectedParts = Arrays.asList(row.split("(\\s\\?\\s\\*\\s)|,"));
            Collections.sort(expectedParts);
            expectedRows.add(expectedParts);
        }

        assertTwoListEqualsWithoutOrder(expectedRows, actualRows);

    }

    private void assertEqualsFilterList(List<String> expectedFilters,List<String> actualFilters){
        ArrayList<ArrayList<String>> actualRows = new ArrayList<ArrayList<String>>();
        for(String row:actualFilters){
            List<String> actualParts = Arrays.asList(row.split("On\\s|Every\\s|,\\s|\\sexcluding\\s|,|\\sat\\s|;"));
            Collections.sort(actualParts);
            actualRows.add(new ArrayList<String>(actualParts));
        }
        ArrayList<ArrayList<String>> expectedRows = new ArrayList<ArrayList<String>>();
        for(String row:expectedFilters){
            List<String> expectedParts = Arrays.asList(row.split("On\\s|Every\\s|,\\s|\\sexcluding\\s|,|\\sat\\s|;"));
            Collections.sort(expectedParts);
            expectedRows.add(new ArrayList<String>(expectedParts));
        }
        assertTwoListEqualsWithoutOrder(expectedRows, actualRows);
    }

    public void assertNoTriggerInDB(String campaignName, String activityName){
        List<String> actualExclusionDates = DBData.getExclusionDates(campaignName, activityName);
        List<String> actualCronList = DBData.getCronExpressions(campaignName, activityName);
        ArrayList<String> actualFilters = DBData.getActivityFilters(campaignName, activityName);

        assertTwoListEqualsWithoutOrder(new ArrayList() , actualExclusionDates);
        assertEqualsCronExpressionLists(new ArrayList(), actualCronList);
        assertEqualsFilterList(new ArrayList(), actualFilters);
    }

}
