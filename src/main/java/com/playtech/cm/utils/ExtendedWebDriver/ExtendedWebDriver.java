package com.playtech.cm.utils.ExtendedWebDriver;

import com.google.common.base.Function;
import com.playtech.cm.utils.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 User: Denis Veselovskiy
 * Date: 4/25/12
 * Time: 5:51 PM
 */

public class ExtendedWebDriver implements WebDriver {
    WebDriver driver;
    private final Wait<WebDriver> wait;
    private final long longTimeoutS = Config.longTimeoutS;
    private final long shortTimeoutMS = Config.shortTimeoutMS;
    private final long pullUpIntervalMS = Config.pullUpIntervalMS;

    public WebDriver getDriver() {
        return driver;
    }

    public ExtendedWebDriver() {
        this(Browser.FIREFOX);
    }

    public ExtendedWebDriver(Browser browser) {
        this(browser, null);
    }

    public ExtendedWebDriver(Browser browser, File downloadFolder) {
        if (downloadFolder == null) downloadFolder = new File(System.getProperty("java.io.tmpdir"));
        if (!downloadFolder.exists()) downloadFolder.mkdirs();
        switch (browser) {
            case FIREFOX: {
//                FirefoxProfile profile = new FirefoxProfile(new File("d:\\work\\var\\prof.def"));
                FirefoxProfile profile = new FirefoxProfile();

//                try {
//                    profile.addExtension(new  File(getClass().getClassLoader().getResource("firebug-1.11.2.xpi").getPath()));
//                    profile.addExtension(new  File(getClass().getClassLoader().getResource("firepath-0.9.7-fx.xpi").getPath()));
//                    profile.setPreference("extensions.firebug.showFirstRunPage", false);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                profile.setEnableNativeEvents(true);
                profile.setPreference( "intl.accept_languages", "no,en-us,en" );
                profile.setPreference("browser.download.dir", Config.getExportFilesDownloadPath());
                profile.setPreference("browser.download.folderList",2);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel, text/xml"); //Add the MIME code of your file here. text/xml,application/xml,application/csv,text/csv
                profile.setPreference("browser.download.manager.showWhenStarting", false);
                profile.setPreference("browser.download.useDownloadDir", true);
                driver = new FirefoxDriver(profile);
                break;
            }
            case CHROME: {
                driver = new ChromeDriver();
                break;
            }
            case IE: {
                DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
                dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                driver = new InternetExplorerDriver(dc);
            }
            default:
                driver = new HtmlUnitDriver();
                break;
        }
        turnImplicitlyWaitOn();
        wait = new WebDriverWait(driver, longTimeoutS, pullUpIntervalMS);
        maximiseWindow();
    }

    public enum Browser {
        FIREFOX("firefox"), CHROME("chrome"), SAFARI("safari"), IE("ie");
        private final String browser;

        Browser(String browser) {
            this.browser = browser;
        }

        @Override
        public String toString() {
            return browser;
        }

        public static Browser getByValue(String value) {
            for (Browser element : Browser.values()) {
                if (element.toString().equalsIgnoreCase(value)) {
                    return element;
                }
            }
            throw new IllegalArgumentException(value);
        }
    }



    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public java.util.List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public WebDriver.TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public WebDriver.Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public WebDriver.Options manage() {
        return driver.manage();
    }

    private void turnImplicitlyWaitOff() {
        this.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    private void turnImplicitlyWaitOn() {
        this.manage().timeouts().implicitlyWait(shortTimeoutMS, TimeUnit.MILLISECONDS);
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) driver;
    }

//    public static void typeUploadFilePath(File file) {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//
//            Robot robot = new Robot();
//
//            for (char c : file.getAbsolutePath().toCharArray()) {
//                robot.delay(50);
//                if (c == ':') {
//                    robot.keyPress(KeyEvent.VK_SHIFT);
//                    robot.keyPress(KeyEvent.VK_SEMICOLON);
//                    robot.keyRelease(KeyEvent.VK_SHIFT);
//                } else if (c == '/') {
//                    robot.keyPress(KeyEvent.VK_BACK_SLASH);
//                } else if (c == '_') {
//                    robot.keyPress(KeyEvent.VK_SHIFT);
//                    robot.keyPress(KeyEvent.VK_MINUS);
//                    robot.keyRelease(KeyEvent.VK_SHIFT);
//                } else {
//                    robot.keyPress(KeyStroke.getKeyStroke(Character.toUpperCase(c), 0).getKeyCode());
//                    robot.keyRelease(KeyStroke.getKeyStroke(Character.toUpperCase(c), 0).getKeyCode());
//                }
//            }
//            robot.keyPress(KeyEvent.VK_ENTER);
//            robot.keyRelease(KeyEvent.VK_ENTER);
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void uploadFile(final File file, WebElement element) {
//
//        Thread upload = new Thread() {
//            @Override
//            public void run() {
//                ExtendedWebDriver.typeUploadFilePath(file);
//            }
//        };
//
//        upload.start();
//        element.click();
//        sleep(5000);
//        try {
//            upload.join();
//        } catch (InterruptedException ie) {ie.printStackTrace();}
//
//    }

    public void typeClean(By locatorOfInpitField, String text) {
        WebElement input = this.findElement(locatorOfInpitField);
        if (input.getAttribute("value").equals(text)) return;
        input.clear();
        if (text == null) return;
        if (text.isEmpty()) return;
        input.sendKeys(text);
    }

    public void click(By locator) {
        this.findElement(locator).click();
    }

    public Boolean waitUntilElementDisappear(By by) {
        turnImplicitlyWaitOff();
        Boolean result;
        try {
            result = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntilElementDisappear " +
                    by.toString(), timeException.getCause());
        }
        turnImplicitlyWaitOn();
        return result;
    }

    public WebElement waitUntilElementAppearVisible(By by) {
        turnImplicitlyWaitOff();
        WebElement result;
        try {
            result = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntilElementAppearVisible " +
                    by.toString(), timeException.getCause());
        }
        turnImplicitlyWaitOn();
        return result;
    }

    public WebElement waitUntilElementAppearVisible(WebElement webElement) {
        turnImplicitlyWaitOff();
        WebElement result;
        try {
            result = wait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntilElementAppearVisible " +
                    webElement.toString(), timeException.getCause());
        }
        turnImplicitlyWaitOn();
        return result;
    }

    public Boolean waitUntilElementDisappear(WebElement webElement) {
        turnImplicitlyWaitOff();
        Boolean result;
        try {
            result = wait.until(ExpectedConditions.stalenessOf(webElement));
        } catch (TimeoutException timeException) {
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntilElementDisappear " +
                    webElement.toString(), timeException.getCause());
        }
        finally {
            turnImplicitlyWaitOn();
        }
        return result;
    }

    public WebElement waitUntilElementAppear(By by) {
        turnImplicitlyWaitOff();
        WebElement result;
        try {
            result = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntilElementAppear " +
                    by.toString(), timeException.getCause());
        }
        turnImplicitlyWaitOn();
        return result;
    }

    public void addAttributeToElement(WebElement element, String attrName, String attrValue){
        getJavascriptExecutor().executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", element, attrName, attrValue);
    }

    public Alert waitUntilAlertAppear() {
        sleep(1000);
        Alert result;
        try {
            result = wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                      "\nTimeOut while waitUntilAlert " , timeException.getCause());
        }
        return result;
    }

    public Boolean waitUntil(Function<WebDriver, Boolean> function) {
        turnImplicitlyWaitOff();
        boolean result;
        try {
            result = wait.until(function);
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntil ", timeException.getCause());
        }
        turnImplicitlyWaitOn();
        return result;
    }

    public boolean isAlertPresent() {
        try {
            this.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public boolean isElementVisible(By locator) {
        try {
            WebElement element = this.findElement(locator);
            if (element.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean isDisplayed(By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement element = this.findElement(locator);
            if (element.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
        return false;
    }

    public boolean isDisplayed(WebElement element) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            if (element.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
        return false;
    }

    public boolean isElementPresent(By locator) {
        try {
            this.findElement(locator);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private void maximiseWindow() {
        driver.manage().window().setPosition(new Point(0, 0));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenResolution = new Dimension((int) toolkit.getScreenSize().getWidth(), (int) toolkit.getScreenSize().getHeight());
        driver.manage().window().setSize(screenResolution);
    }

    public void sleep(Integer i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickAndWait(WebElement clickable, String waiting) {
        int count = 0;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            while (!isElementPresent(By.xpath(waiting)) && count<10){
                count++;
                clickable.click();
                sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
    }

    public void
    clickAndWaitVisible(WebElement clickable, String waiting) {
        int count = 0;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            while (!isElementVisible(By.xpath(waiting)) && count<3){
                count++;
                clickable.click();
                sleep(1000);
            }
        } catch (Exception e) { }
        finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
    }

    public void setNullOnBeforeUnload() {
        String script = "return window.onbeforeunload = null;";
        getJavascriptExecutor().executeScript(script);
    }

    public void waitForAjax() {
        try {
        waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return getJavascriptExecutor().executeScript("return Ajax.activeRequestCount").toString().equals("0");
            }
        });   }
        catch (Exception e) {};
    }

    public void setAttribute(WebElement element, String attribute, String value) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('" + attribute + "',arguments[1]);", element, value);
    }


}

