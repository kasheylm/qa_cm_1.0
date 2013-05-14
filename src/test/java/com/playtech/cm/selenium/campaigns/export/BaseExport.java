package com.playtech.cm.selenium.campaigns.export;

import com.google.common.base.Function;
import com.playtech.cm.BaseTest;
import com.playtech.cm.utils.Config;
import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/22/12, Time: 4:24 PM
 */
public class BaseExport extends BaseTest{
    String testName;
    //map of preset data for each test(test_name, xml-file name)
    final static Map linksToData = new HashMap();
        static{
            linksToData.put("exportAllTestGroups", "campaignExport/CampaignWithTestActivityExport.xml");
            linksToData.put("exportOneTestGroup", "campaignExport/CampaignWithTestActivityExport.xml");
            linksToData.put("doNotSelectTestGroup", "campaignExport/CampaignWithTestActivityExport.xml");
            linksToData.put("notMetActionedDateTest", "campaignExport/TwoCampaignsTestAndNonTest.xml");
            linksToData.put("selectMetActionedDateTest", "campaignExport/TwoCampaignsTestAndNonTest.xml");
            linksToData.put("validateFileNameField", "campaignExport/CampaignWithNonTestActivityExport.xml");
            linksToData.put("validateDifferentFieldSelection", "campaignExport/TwoCampaignsTestAndNonTest.xml");
    };


    public void setTestCaseName(Method m){
        testName = m.getName();
    }

    public void waitUntilFileDownloaded(String fileName, String fileExtension){
        String downloadFolderPath = Config.getExportFilesDownloadPath();
        java.io.File file = new java.io.File(downloadFolderPath+"\\"+fileName+"."+fileExtension);
        long end = System.currentTimeMillis()+Config.longTimeoutS*5000;
        while(System.currentTimeMillis() <= end){
            if(file.exists()){
                break;
            }
        }
        waitUntilDownloadWindowClosed();
        assertTrue(file.exists(), "File '" + fileName + "." + fileExtension + "' is not downloaded into '" + downloadFolderPath + "' in " + Config.longTimeoutS * 5 + " seconds.");
    }

    public void waitUntilDownloadWindowClosed(){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                return (handles.size() < 2);
            }
        });
    }

    public void compareXML(String fileName){
        String dowloadFolderPath = Config.getExportFilesDownloadPath();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String expectedFilePath = Config.getWorkDir()+"/src/test/resources/campaignExport/fileSamples/"+fileName+".xml";
        String actualFilePath = dowloadFolderPath+"\\"+fileName+".xml";
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        Document doc1 = null, doc2=null;
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc1 = db.parse(new File(expectedFilePath));
            doc1.normalizeDocument();
            doc2 = db.parse(new File(actualFilePath));
            doc2.normalizeDocument();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertNotNull(doc1, "Unable to find Export's expected result file: "+expectedFilePath);
        Assert.assertNotNull(doc2, "Unable to find Export's actual result file: "+actualFilePath);
        Diff myDiff = new Diff(doc1, doc2);
        myDiff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
        Assert.assertTrue(myDiff.similar(), myDiff.toString());
    }

    public void compareCSV(String fileName){
        String dowloadFolderPath = Config.getExportFilesDownloadPath();
        String expectedFilePath = Config.getWorkDir()+"/src/test/resources/campaignExport/fileSamples/"+fileName+".csv";
        String actualFilePath = dowloadFolderPath+"\\"+fileName+".csv";
        File expectedFile = new File(expectedFilePath);
        File actualFile = new File(actualFilePath);
        String expectedString = null;
        String actualString = null;
        try{
            expectedString = FileUtils.readFileToString(expectedFile);
            actualString = FileUtils.readFileToString(actualFile);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertNotNull(expectedString, "Unable to find Export's expected result file: "+expectedFilePath);
        Assert.assertNotNull(actualString, "Unable to find Export's actual result file: "+actualFilePath);

        Collection<String> listExpected = new ArrayList<String>();
        Collection<String> listActual = new ArrayList<String>();
        Collection<String> listActualCopy = new ArrayList<String>();

        listExpected.addAll(Arrays.asList(expectedString.split("\n")));
        listActualCopy.addAll(Arrays.asList(actualString.split("\n")));
        listActual.addAll(Arrays.asList(actualString.split("\n")));

        listActual.removeAll(listExpected);
        listExpected.removeAll(listActualCopy);

        Assert.assertEquals(listActual, new ArrayList<String>(),"CSV file contains extra lines: "+listActual);
        Assert.assertEquals(listExpected,new ArrayList<String>(),"Expected lines missed in CSV file: "+listExpected);
    }

    @AfterMethod
    public void saveFailedDataAndLogOut(ITestContext c, ITestResult r, Method m){
        super.saveFailedDataAndLogOut(c,r,m);
        if (!r.isSuccess()){
            Config.killAllFirefoxApplications();
        }
    }
}
