package com.alex.study.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class RgsScenarioTest extends BaseClass {

    @ParameterizedTest
    @MethodSource("com.alex.study.tests.RgsParametrized#personNames")
    public void test(String lastName, String name, String middleName) {

        String frameCovidXPath = "//iframe[@class='flocktory-widget']";
        String buttonFrameCovidCloseXPath = "//button[@class= 'CloseButton']";
        closeFrame(By.xpath(frameCovidXPath), By.xpath(buttonFrameCovidCloseXPath));

        String cookieOkXPath = "//div[@class='btn btn-default text-uppercase']";
        WebElement cookieOk = waitUtilElementToBeVisible(By.xpath(cookieOkXPath));
        waitUtilElementToBeClickable(cookieOk);
        cookieOk.click();

        String menuXPath = "//a[@class='hidden-xs' and contains(text(),'Меню')]";
        WebElement dropdownMenu = waitUtilElementToBeVisible(By.xpath(menuXPath));
        waitUtilElementToBeClickable(dropdownMenu);
        dropdownMenu.click();

        String companiesXPath = "//a[contains(text(),'Компаниям')]";
        WebElement buttonCompanies = waitUtilElementToBeVisible(By.xpath(companiesXPath));
        waitUtilElementToBeClickable(buttonCompanies);
        buttonCompanies.click();

        switchToNewWindow(By.xpath("//a[contains(text(),'Страхование здоровья')]"));

        String voluntaryMedicalInsuranceXPath = "//a[contains(@class, 'adv-analytics-navigation') and contains(@href, '/health/dms')]";
        WebElement voluntaryMedicalInsurance = waitUtilElementToBeVisible(By.xpath(voluntaryMedicalInsuranceXPath));
        waitUtilElementToBeClickable(voluntaryMedicalInsurance);
        voluntaryMedicalInsurance.click();

        String titleVoluntaryMedicalInsuranceXPath = "//h1[@class='content-document-header']";
        WebElement titleVoluntaryMedicalInsurance = waitUtilElementToBeVisible(By.xpath(titleVoluntaryMedicalInsuranceXPath));
        String titleVoluntaryMedicalInsuranceMassage = "Заголовок \"Добровольное медицинское страхование\", отсутствует";
        Assertions.assertEquals("Добровольное медицинское страхование",
                titleVoluntaryMedicalInsurance.getText(), titleVoluntaryMedicalInsuranceMassage);

        String sendRequestXPath = "//a[contains(@class, 'btn btn-default text-uppercase')]";
        WebElement buttonSendRequest = waitUtilElementToBeVisible(By.xpath(sendRequestXPath));
        waitUtilElementToBeClickable(buttonSendRequest);
        buttonSendRequest.click();

        String dialogWindowXPath = "//div[@class='modal-dialog']";
        String titleDialogWindowXPath = "//b[@data-bind = 'text: options.title']";
        WebElement dialogWindow = waitUtilElementToBeVisible(By.xpath(dialogWindowXPath));
        WebElement titleDialogWindow = waitUtilElementToBeVisible(By.xpath(titleDialogWindowXPath));
        Assertions.assertAll("Диалоговое окно заявки на ДМС не открылось или не содержит необходимое значение",
                () -> Assertions.assertTrue(dialogWindow.isDisplayed()),
                () -> Assertions.assertTrue(titleDialogWindow.getText().contains("Заявка на добровольное медицинское страхование"))
        );

        String frameStockXPath = "//iframe[@class='flocktory-widget' and contains(@style, 'display')]";
        String buttonFrameStockCloseXPath = "//div[@class = 'widget__close js-collapse-login']";
        closeFrame(By.xpath(frameStockXPath), By.xpath(buttonFrameStockCloseXPath));

        String fieldLastNameXPath = "//input[@name= 'LastName']";
        WebElement fieldLastName = webDriver.findElement(By.xpath(fieldLastNameXPath));
        fillInputField(fieldLastName, lastName);

        String fieldNameXpath = "//input[@name= 'FirstName']";
        WebElement fieldName = webDriver.findElement(By.xpath(fieldNameXpath));
        fillInputField(fieldName, name);

        String fieldMiddleNameXpath = "//input[@name= 'MiddleName']";
        WebElement fieldMiddleName = webDriver.findElement(By.xpath(fieldMiddleNameXpath));
        fillInputField(fieldMiddleName, middleName);

        Select region = new Select(webDriver.findElement(By.xpath("//select[@name = 'Region']")));
        region.selectByVisibleText("Москва");

        String fieldPhoneXPath = "//input[contains(@data-bind, 'Phone')]";
        WebElement fieldPhone = webDriver.findElement(By.xpath(fieldPhoneXPath));
        actions.sendKeys(fieldPhone, "9998885533").sendKeys(Keys.ENTER).build().perform();

        String fieldEmailXPath = "//input[@name= 'Email']";
        WebElement fieldEmail = webDriver.findElement(By.xpath(fieldEmailXPath));
        fieldEmail.click();
        fillInputField(fieldEmail, "qwertyqwerty");

        String fieldDateXPath = "//input[@name= 'ContactDate']";
        WebElement fieldDate = webDriver.findElement(By.xpath(fieldDateXPath));
        fieldDate.click();
        fieldDate.sendKeys("25.10.2021");
        fieldDate.sendKeys(Keys.ENTER);

        String fieldCommentXPath = "//textarea[@class= 'popupTextarea form-control']";
        WebElement fieldComment = webDriver.findElement(By.xpath(fieldCommentXPath));
        fillInputField(fieldComment, "aaa");

        String checkBoxIAcceptXPath = "//input[@type='checkbox']";
        WebElement checkBoxIAccept = webDriver.findElement(By.xpath(checkBoxIAcceptXPath));
        checkBoxIAccept.click();

        String buttonSendXPath = "//button[@id='button-m']";
        WebElement buttonSend = webDriver.findElement(By.xpath(buttonSendXPath));
        buttonSend.click();


        WebElement emailError = fieldEmail.findElement(By.xpath("./..//span[@class='validation-error-text']"));
        String emailErrorMessage = "Пропускает возможность заполнить поле не валидным значением";
        Assertions.assertEquals("Введите адрес электронной почты", emailError.getText(), emailErrorMessage);
    }

}
