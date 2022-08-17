package org.sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebPageLoginTest {

	static WebDriver driver;
	static List<String> rowList = new ArrayList<>();
	static List<String> columnList = new ArrayList<>();

	private static void getDataFromXl() throws IOException {

		FileInputStream inputStream = new FileInputStream("C:\\Users\\dell\\Desktop\\testdata.xlsx");
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row rowObj = rowIterator.next();
			Iterator<Cell> cellIterator = rowObj.iterator();
			int temp = 2;

			while (cellIterator.hasNext()) {
				Cell next = cellIterator.next();

				String stringCellValue = next.getStringCellValue();
				if (temp % 2 == 0) {
					rowList.add(stringCellValue);
				} else {
					columnList.add(stringCellValue);
				}
				temp++;

			}
		}
	}

// ======================================================================= 	
	private static void openBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.facebook.com/");
		System.out.println("Browser Opened");

	}

// ======================================================================= 

	private static void testCaseRunning() throws InterruptedException {
		for (int i = 0; i < rowList.size(); i++) {
			launchTestCase(rowList.get(i), columnList.get(i));
			Thread.sleep(300);
		}
	}

	private static void launchTestCase(String userId, String passId) {

		System.out.println("FaceBook Launched....for combination .." + userId + " " + passId);
		WebElement txtEmail = driver.findElement(By.id("email"));
		WebElement txtPass = driver.findElement(By.id("pass"));
		txtEmail.clear();
		txtPass.clear();
		txtEmail.sendKeys(userId);
		txtPass.sendKeys(passId);

	}
// ======================================================================= 

	private static void closeBrowser() {
		System.out.println("Browser Closed");
		driver.quit();
	}

// ======================================================================= 
	public static void main(String[] args) throws IOException, InterruptedException {

		getDataFromXl();
		openBrowser();
		testCaseRunning();
		closeBrowser();

	}

}
