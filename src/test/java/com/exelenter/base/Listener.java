package com.exelenter.base;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.exelenter.utils.CommonMethods;
import com.exelenter.utils.Constants;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Listener implements ITestListener {
    ExtentSparkReporter reporter;
    ExtentReports reports;
    ExtentTest test;
    Instant startTime;

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started."+result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed."+result.getName());

        test =reports.createTest(result.getName());
        test.pass("Test Case Passed" + result.getName());
        //optionally, you can capture screenshot here,for each success test case.Not recommended).
        //test.addScreenCaptureFromPath(CommonMethods.takeScreenshot("PASS/" + result.getName()));
        test.log(Status.PASS,"Test Passed. This is coming from the log status");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed."+result.getName());

        test= reports.createTest(result.getName());
        test.fail("Test Case Failed: " +result.getName());
        test.addScreenCaptureFromPath(CommonMethods.takeScreenshot("FAIL/"+ result.getName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test Skipped:" + result.getName());
        test= reports.createTest(result.getName());
        test.skip("Test Case Skipped:" + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("=== Test Started."+context.getName()+  " | " + context.getStartDate());

        // Adding reports(start tracking & recording when test starts).ExtentReports library required.
        //ExtentHTMLreporter <===deprecated, no longer supported after 5.06or above
        reporter= new ExtentSparkReporter(Constants.REPORT_FILEPATH);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("Exelenter Test Report");
        reporter.config().setReportName("Exelenter Project Test Report");
         reports= new ExtentReports();
        reports.attachReporter(reporter);
        startTime= Instant.now();
    }

    @Override
    public void onFinish(ITestContext context) {

        System.out.println("\n****************\n===End of Test ." + context.getName() + " | " + context.getEndDate());

        reports.flush(); // Erases previous (old) data and creates new one.
        Instant endTime = Instant.now();
        Duration totalTime = Duration.between(startTime, endTime);
        int milliseconds = totalTime.getNano() / 1_000_000;

//        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime.toMillis());
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime.toMillis());
//        long hours = TimeUnit.MILLISECONDS.toHours(totalTime.toMillis());
//        long days = TimeUnit.MILLISECONDS.toDays(totalTime.toMillis());

        long seconds = totalTime.toSeconds();
        long minutes = totalTime.toMinutes();
        long hours = totalTime.toHours();
        long days = totalTime.toDays();


        System.out.println("\nTotal Test Completion Time: \nDays: " + days +
                "\nHours: " + hours +
                "\nMinutes: " + minutes +
                "\nSeconds: " + seconds +
                "\nMilliseconds: " + milliseconds);

        System.out.printf("Total Test Completion Time: %d Minutes %d Seconds, and %3d Milliseconds", minutes, seconds, milliseconds);
    }
}
