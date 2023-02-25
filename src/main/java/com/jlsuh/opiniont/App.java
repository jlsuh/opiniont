package com.jlsuh.opiniont;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        new NoOpinion(
                driver,
                new WebDriverWait(driver, Duration.ofMillis(5000))
        ).noOpinion(args[0], args[1]);
    }

}
