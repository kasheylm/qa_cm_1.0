package com.playtech.cm.utils.dataProviders.conditions;

import com.playtech.cm.utils.dataProviders.BaseData;
import com.playtech.cm.utils.entities.conditions.Filter;
import org.testng.annotations.DataProvider;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12 * Time: 2:52 PM
 */
public class LapsedPlayerReportData extends BaseData {

    @DataProvider
    public static Object[][] getClientPlatforms() {
        Object[][] text = {
                {"Flash"},
                {"Admin"},
                {"Download"}
        };
        return(text);
    }

    @DataProvider
    public static Object[][] getClientTypes() {
        Object[][] text = {
                {"Admin"},
                {"Casino"}
        };
        return(text);
    }

    @DataProvider
    public static Object[][] getFilters() {
        Object[][] text = {
                {Filter.YES},
                {Filter.NO},
                {Filter.ALL}
        };
        return(text);
    }

}
