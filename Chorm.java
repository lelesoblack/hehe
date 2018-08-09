/**
 * @(#)Chorm.java, 2018年7月26日. Copyright 2018 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64.Decoder;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author heqiang
 */
public class Chorm {

    public static void main(String[] args)
            throws UnsupportedEncodingException, InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "/Users/heqiang/Downloads/chromedriver");
        WebDriver dr = new ChromeDriver();
        dr.get("https://www.baidu.com");
        Options manage = dr.manage();
        Cookie cookie;
        String a ="t=286573CC7491089570712F6B072C41FC; "
                + "NOWCODERUID=6E6B86282662699CA7B98A1E1A4EB38A;"
                + "NOWCODERCLINETID=A34E219C4F4315FA8EEEAFD558730FAA;"
                + "Hm_lvt_a808a1326b6c06c437de769d1b85b870=1531461532,1531461592,1533526544,1533716141;"
                + "Hm_lpvt_a808a1326b6c06c437de769d1b85b870=1533727320;"
                + "SERVERID=04b0d01c5f76391d48534b2801b3cad1|1533728425|1533722848";
                
        cookie = 
        manage.addCookie(cookie);

    }

}
