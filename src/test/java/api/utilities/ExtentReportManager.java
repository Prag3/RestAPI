package api.utilities;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager  extends TestListenerAdapter {
	
	ExtentSparkReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	public void onStart(ITestContext testContext) {
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") +"/ExtentReports/ExtReport_"+new SimpleDateFormat("ddMMyy_HHmmss").format(new Date())+".html");	
//		try {
//			htmlReporter.loadXMLConfig("./extent-config.xml");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host", "localHost");
		extent.setSystemInfo("OS", "Win 11");
		extent.setSystemInfo("User", "QA");
		extent.setSystemInfo("Env", "Test");
		htmlReporter.config().setDocumentTitle("Demo SDET");
		htmlReporter.config().setReportName("Demo For Report");
		htmlReporter.config().setTheme(Theme.DARK);
	}
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());		
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));		//send pass information		
	}
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
		
		String screenshotPath = "./screenshots//"+result.getName()+".png";
		File file = new File(screenshotPath);
		
		if(file.exists()) {
			test.fail("Screenshot is below : "+test.addScreenCaptureFromPath(screenshotPath));
		}
	}
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.YELLOW));
	}
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
