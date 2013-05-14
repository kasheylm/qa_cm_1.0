package com.playtech.cm.utils.extendedFactory;

/**
 * User: Denis Veselovskiy
 * Date: 9/24/12 * Time: 5:10 PM
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;
import com.playtech.cm.utils.Config;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import static com.playtech.cm.BaseTest.driver;

public class StaleReferenceAwareFieldDecorator extends DefaultFieldDecorator {

    public StaleReferenceAwareFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }

    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new StaleReferenceAwareElementLocator(locator);

        WebElement proxy;
        proxy = (WebElement) Proxy.newProxyInstance(
                loader, new Class[] {WebElement.class, WrapsElement.class, Locatable.class}, handler);
        return proxy;
    }

    private static class StaleReferenceAwareElementLocator extends LocatingElementHandler {
        private final ElementLocator locator;

        public StaleReferenceAwareElementLocator(ElementLocator locator) {
            super(locator);
            this.locator = locator;
        }

        // here is where the magic happens. For a configurable number of times (I configured
        // 10 times, locater finds the element, and then tries to invoke the method on the element
        // In case StaleElementReferenceException is thrown, try again.
        public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
            int count = 0;
            WebElement element = null;
            while (count < 10) {

                try{
                    element = locator.findElement();
                }catch(Exception ex){
                    count++;
                    continue;
                }
                if ("getWrappedElement".equals(method.getName())) {
                    return element;
                }
                try {
                    return invokeMethod(method, element, objects);
                } catch (StaleElementReferenceException ex) {
                }
                count++;
            }
            throw new RuntimeException("Element not found!");
        }

        private Object invokeMethod(Method method, WebElement element, Object[] objects) throws Throwable {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            try {
                return method.invoke(element, objects);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            } catch (IllegalArgumentException e) {
                throw e.getCause();
            } catch (IllegalAccessException e) {
                throw e.getCause();
            }
            finally {
                driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
            }
        }
    }
}