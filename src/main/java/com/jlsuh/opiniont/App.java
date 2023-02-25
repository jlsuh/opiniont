package com.jlsuh.opiniont;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(
                    "Usage: mvn clean install " +
                            "&& " +
                            "mvn exec:java " +
                            "-Dexec.mainClass=com.jlsuh.opiniont.App " +
                            "-Dexec.cleanupDaemonThreads=false " +
                            "-Dexec.args=\"arg1 arg2\""
            );
            System.exit(-1);
        }
        WebDriver driver = new ChromeDriver();
        new NoOpinion(
                driver,
                new WebDriverWait(driver, Duration.ofMillis(5000))
        ).noOpinion(args[0], args[1]);
    }

}
