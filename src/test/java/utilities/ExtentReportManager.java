package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentReports extent;
	public ExtentSparkReporter reporter;
	public ExtentTest test;

	String repName;

	@Override
	public void onStart(ITestContext context) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd_hh.mm.ss").format(new Date());
		repName = "TestReport-" + timeStamp + ".html";

		reporter = new ExtentSparkReporter(".\\reports\\" + repName);
		reporter.config().setDocumentTitle("Opencart Automation Report");
		reporter.config().setReportName("Opencart Functional Testing");
		reporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Application ", "Opencart");
		extent.setSystemInfo("Module ", "admin");
		extent.setSystemInfo("Sub Module ", "Customers");
		extent.setSystemInfo("User name ", System.getProperty("user.name"));
		extent.setSystemInfo("Environment ", "QA");

		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("OS ", os);

		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser ", browser);

		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups ", includedGroups.toString());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		//System.out.println("on test success test name " + result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName()+" passsed");
		System.out.println("Test Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		//System.out.println("on test failure test name " + result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.FAIL, result.getName()+" failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			System.out.println("image path : "+imgPath);
			test.addScreenCaptureFromPath(imgPath);
			//test.log(Status.FAIL, (Markup) MediaEntityBuilder.createScreenCaptureFromPath(imgPath));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Test Failed: " + result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		System.out.println("Test Skipped: " + result.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Test Execution Finished: " + context.getName());
	}
}