package com.playtech.cm;

import com.google.common.base.Function;
import com.playtech.cm.elements.CampaignRow;
import com.playtech.cm.elements.CampaignTable;
import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.pages.dashboard.LoginPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.search.SearchPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.ConfigLoader;
import com.playtech.cm.utils.ExtendedWebDriver.ExtendedWebDriver;
import com.playtech.cm.utils.MyDiff;
import com.playtech.cm.utils.db.HibernateUtils;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 17/04/12
 * Time: 12:35
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Listeners({MyTestListener.class})
public class BaseTest extends AbstractTestNGSpringContextTests {
    public static HomePage homePage;
    public static ExtendedWebDriver driver;
    private int screenCount = 1;
    private static Cookie storedCookie;
    public static CMDashboardPage cmDashboardPage;

    @Autowired
    protected HibernateUtils hibernateUtils;

    private void loadConfig(ITestContext context) {
        new ConfigLoader() {
            @Override
            public <T> String getConfigValue(T source, String configField) {
                return ((ITestContext) source).getCurrentXmlTest().getParameter(configField);
            }
        }.load(Config.class, context);
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp(ITestContext context) throws Exception{
        deleteDir(new File(context.getOutputDirectory())); //clean  screens directory
        loadConfig(context);
        driver = new ExtendedWebDriver();
        driver.get(Config.loginUrl);
        if (getDebugCookie() == null) {
            homePage = login();
            driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return (driver.manage().getCookieNamed("admin_secret")!=null);
                }
            });
            storedCookie = driver.manage().getCookieNamed("admin_secret");
            saveDebugCookie();
        } else {
            storedCookie = getDebugCookie();
            driver.manage().addCookie(storedCookie);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        if(driver!=null){
            driver.quit();
//            Config.killAllFirefoxApplications();
        }
//        TestLinkMarker.fixResultsXML("target\\surefire-reports\\testng-results.xml");
    }

    @BeforeMethod(alwaysRun = true)
    public void tearUpDriver(){
        if(driver==null){
            driver = new ExtendedWebDriver();
            driver.get(Config.loginUrl);
            driver.manage().addCookie(storedCookie);
        }
    }

    @AfterMethod
    public void saveFailedDataAndLogOut(ITestContext c, ITestResult r, Method m) {
        driver.setNullOnBeforeUnload();
        if (r.isSuccess()) {
            System.out.println("Test " + r.getTestClass().getName() + "." + m.getName() + " done.");
        }else{
            saveFailedTestCaseData(c, r);
            driver.close();
            driver = null;
        }
    }

    @AfterGroups(alwaysRun = true, groups = "TestWithActiveCampaign")
    public void tearDown2() {
        //Pause all Active campaign
        cmDashboardPage = goToCMDashboardDirectly();
        SearchPage searchPage = cmDashboardPage.getSearchPage();
        searchPage.expand();
        searchPage.clickClear();
        searchPage.selectStatus("Active");
        searchPage.clickOnSearchButton();
        CampaignTable campaignTable = cmDashboardPage.getCampaignTable();
        for(CampaignRow campaignRow : campaignTable.getCampaignRowList()){
            if (campaignRow.getPause().isDisplayed()) {
                campaignRow.clickPauseAndAcceptAlert();
            }
        }
    }

    public HomePage login() {
        LoginPage loginPage =  PageFactory.initElements(driver.getDriver(), LoginPage.class);
        loginPage.login(Config.login, Config.password);
        homePage = PageFactory.initElements(driver.getDriver(), HomePage.class);
        return homePage;
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    private void saveFailedTestCaseData(ITestContext c, ITestResult r) {
        acceptAlert();
        try {
            if (!r.isSuccess()) {
                //create filed test case folder structore in test_output folder
                String pathToFailedCasesFolder = c.getOutputDirectory() + "/"
                        + "failed-tests" + "/"
                        + r.getTestClass().getName() + "/"
                        + r.getName();

                File failedTestCaseFolder = new File(pathToFailedCasesFolder);
                failedTestCaseFolder.mkdirs();

                //take screenshot
                File scrFile = getScreenshotAs(OutputType.FILE);
                //save it to temp folder
                FileUtils.copyFile(scrFile, new File(failedTestCaseFolder, "screenshot" + screenCount + ".png"));

                //if test case failed, we save source of page to temp folder
                FileOutputStream fos = new FileOutputStream(new File(failedTestCaseFolder, "page_source" + screenCount +".html"));
                fos.write(driver.getPageSource().getBytes());
                fos.close();

                Reporter.log(r.getName() + " test case  fail", true);
            }
            screenCount++;
        } catch (Exception e) {
            Reporter.log("saveFailedTestCaseData exception: " + e.getMessage(), true);
        }
    }

    public void saveSkippedTestCaseData(ITestResult r) {
        try {
            //create filed test case folder structore in test_output folder
            String pathToFailedCasesFolder = "target/surefire-reports/skipped-tests" + "/" + r.getTestClass().getName() + "/" + r.getName();

            File failedTestCaseFolder = new File(pathToFailedCasesFolder);
            failedTestCaseFolder.mkdirs();

            //take screenshot
            File scrFile = getScreenshotAs(OutputType.FILE);
            //save it to temp folder
            FileUtils.copyFile(scrFile, new File(failedTestCaseFolder, "screenshot" + screenCount + ".png"));

            //if test case failed, we save source of page to temp folder
//            FileOutputStream fos = new FileOutputStream(new File(failedTestCaseFolder, "page_source" + screenCount +".html"));
//            fos.write(driver.getPageSource().getBytes());
//            fos.close();

            Reporter.log(r.getName() + " - SKIPPED.", true);
            screenCount++;
        } catch (Exception e) {
            Reporter.log("saveSkippedTestCaseData exception: " + e.getMessage(), true);
        }
    }

    public File getScreenshotAs(OutputType<File> file) {
        return ((TakesScreenshot) driver.getDriver()).getScreenshotAs(file);
    }

    public void acceptAlert(){
        driver.sleep(1000);
        if (driver.isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    public static void assertObjectsEquals(final Object expected, final Object actual) {
        if (!EqualsBuilder.reflectionEquals(expected, actual)) {
            assertEquals(
                            reflectionToString(expected, ToStringStyle.SHORT_PREFIX_STYLE),
                            reflectionToString(actual, ToStringStyle.SHORT_PREFIX_STYLE)
                        );
            fail("expected: <" + expected + ">  actual: <" + actual + ">");
        }
    }

    public void relogin(){
        if(driver!=null){
            driver.close();
        }
        driver = new ExtendedWebDriver();
        driver.get(Config.loginUrl);
        homePage = login();
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return (driver.manage().getCookieNamed("admin_secret")!=null);
            }
        });
        storedCookie = driver.manage().getCookieNamed("admin_secret");
    }

    public CMDashboardPage goToCMDashboardDirectly() {
        acceptAlert();
        driver.get(Config.cmDashboardUrl);
        acceptAlert();
        return StalePageFactory.initElements(driver.getDriver(), CMDashboardPage.class);
    }

    public static void hideMenuElements() {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
            List<WebElement> list = driver.findElements(By.xpath("//*[contains(@class, 'yui-overlay-hidden') and not(contains(@style, 'top'))]"));
            System.out.print("Elements size: " + list.size());
            if (list.size() != 0){
                for (WebElement el : list) {
                    String style= el.getAttribute("style");
                    driver.setAttribute(el, "style", style + " top : 205px;");
                }
            }
        }
        catch (Exception e) {e.printStackTrace();}
        finally {
            driver.manage().timeouts().implicitlyWait(Config.longTimeoutS, TimeUnit.SECONDS);
        }
    }

    public static Cookie getDebugCookie(){
        File cookieFile = new File(Config.getWorkDir()+"/storedSessionCookie");
        Cookie storedCookie = null;
        if(Config.isDebug){
            String cooName = "admin_secret", cooValue;
            try {
                if(cookieFile.exists()){
                    cooValue = FileUtils.readFileToString(cookieFile);
                    storedCookie = new Cookie(cooName,cooValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return storedCookie;
    }

    public static void saveDebugCookie(){
        File cookieFile = new File(Config.getWorkDir()+"/storedSessionCookie");
        if(Config.isDebug){
            try{
                cookieFile.createNewFile();
                FileUtils.writeStringToFile(cookieFile, storedCookie.getValue());
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            cookieFile.delete();
        }
    }

    public void waitCampaignActivated(final String campaignID) {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (hibernateUtils.getCampaignStatus(campaignID).equals("ACTIVE"));
            }
        });
    }

    public void waitCampaignApproved(final String campaignID) {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (hibernateUtils.getCampaignStatus(campaignID).equals("APPROVED"));
            }
        });
    }

    public Document parse(InputStream is) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        Document doc = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(is);
            doc.normalizeDocument();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return doc;
    }

    public void compareXML(Document expected, Document actual) {
        Assert.assertNotNull(expected, "Unable to find expected result file");
        Assert.assertNotNull(actual, "Unable to find actual result file");
        XMLUnit.setIgnoreWhitespace(true);

        Diff myDiff = new Diff(expected, actual);

        myDiff.overrideDifferenceListener(new MyDiff("activityId"));
        myDiff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
        Assert.assertTrue(myDiff.similar(), myDiff.toString());
    }
}
