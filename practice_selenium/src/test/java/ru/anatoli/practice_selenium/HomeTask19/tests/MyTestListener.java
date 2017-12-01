package ru.anatoli.practice_selenium.HomeTask19.tests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.anatoli.practice_selenium.HomeTask19.app.Application;
import ru.yandex.qatools.allure.annotations.Attachment;

public class MyTestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Application app = (Application) result.getTestContext().getAttribute("app");
        saveScreenshot(app.takeScreenshot());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Application app = (Application) result.getTestContext().getAttribute("app");
        saveScreenshot(app.takeScreenshot());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }
}
