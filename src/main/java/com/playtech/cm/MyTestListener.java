package com.playtech.cm;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * User: Denis Veselovskiy
 * Date: 3/29/13 * Time: 3:20 PM
 */
public class MyTestListener extends TestListenerAdapter {

    @Override
    public void onTestSkipped(ITestResult r) {
        super.onTestSkipped(r);
        ((BaseTest)r.getInstance()).saveSkippedTestCaseData(r);
    }
}
