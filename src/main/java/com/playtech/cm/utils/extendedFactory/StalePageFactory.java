package com.playtech.cm.utils.extendedFactory;

import com.playtech.cm.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * User: Denis Veselovskiy
 * Date: 9/24/12 * Time: 5:34 PM
 */
public class StalePageFactory {

    @SuppressWarnings("unchecked")
    public static <T> T initElements(WebDriver driver, Class<T> pageClass) {
        Object page = createInstance(driver, pageClass);
        PageFactory.initElements(new StaleReferenceAwareFieldDecorator(new AjaxElementLocatorFactory(driver, 1)), page);
        BaseTest.hideMenuElements();
        return (T) page;
    }

    public static <T> T createInstance(WebDriver driver, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
                return constructor.newInstance(driver);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}