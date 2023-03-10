package com.jlsuh.opiniont;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NoOpinion {

    private final String ANCHOR = "a";
    private final String HREF = "href";
    private final WebDriverWait wait;
    private final WebDriver driver;

    public NoOpinion(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private String pendingSurveysViewURL() {
        return this.wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("encuestas_kolla")))
                .findElement(By.tagName(this.ANCHOR))
                .getAttribute(this.HREF);
    }

    private String iFrameFocusedURL() {
        return this.wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("ifkolla")))
                .getAttribute("src");
    }

    private List<String> surveysURL() {
        try {
            return this.wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("listado_encuestas")))
                    .findElements(By.tagName(this.ANCHOR))
                    .stream()
                    .map(survey -> survey.getAttribute(this.HREF))
                    .toList();
        } catch (TimeoutException e) {
            throw new NoPendingSurveysException("No hay encuestas pendientes");
        }
    }

    private void signIn(final String username, final String password) {
        WebElement usernameInput = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usuario")));
        WebElement passwordInput = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        passwordInput.submit();
    }

    private void consentNoOpinion() {
        ((JavascriptExecutor) this.driver).executeScript(
                """
                        [...document.getElementsByClassName("ef_radio inline")].forEach((e) => {
                          if (e.defaultValue === "1") e.checked = true;
                        });
                        """
        );
    }

    private void endSurvey() {
        this.driver.findElement(By.id("btn-terminar")).click();
    }

    private void acceptAlert() {
        this.driver.switchTo().alert().accept();
    }

    public void noOpinion(final String username, final String password) {
        this.driver.manage().window().maximize();
        final String SIGN_IN_URL = "https://guarani.frba.utn.edu.ar/autogestion/grado/acceso";
        this.driver.get(SIGN_IN_URL);
        this.signIn(username, password);
        final String pendingSurveysViewURL = this.pendingSurveysViewURL();
        this.driver.get(pendingSurveysViewURL);
        try {
            this.surveysURL().forEach(url -> {
                this.driver.get(url);
                this.driver.get(this.iFrameFocusedURL());
                this.consentNoOpinion();
                this.endSurvey();
                this.acceptAlert();
                this.driver.get(pendingSurveysViewURL);
            });
            System.out.println("Encuestas respondidas");
        } catch (NoPendingSurveysException e) {
            System.out.println(e.getMessage());
        }
        this.driver.quit();
    }

}
