import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Search Phrases
 1.	hospitals in Nashville TN
 2.	largest hospital corporation
 3.	physician jobs in Nashville

 Company Names and Internet Domains
 1.	HCA = hcahealthcare.com
 2.	St Thomas = sthealth.com
 3.	Vanderbilt = vanderbilthealth.com

 */

public class Searchphrase {

	public static String Domain;

	public static void main(String[] args) throws Exception {

		//System.setProperty("webdriver.chrome.driver",
			//	"//Users//himabindu//Downloads//chromedriver 2");
		 String Root_Path = System.getProperty("user.dir");
		 String GoogleChromeExePath = Root_Path+"/chromedriver 2";
		System.setProperty("webdriver.chrome.driver",GoogleChromeExePath);
		


		// Initialize browser
		WebDriver driver = new ChromeDriver();

		// Open facebook
		driver.get("http://www.google.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Maximize browser
		driver.manage().window().maximize();

		String Data[] = new String[] { "hospitals in Nashville TN",
				"largest hospital corporation", "physician jobs in Nashville" };
		/*
		 * Data[0] = "hospitals in Nashville TN"; Data[1] =
		 * "largest hospital corporation"; Data[2] =
		 * "physician jobs in Nashville";
		 */

		for (String searchstring : Data) {
			int Pagecount = 1;
			driver.findElement(By.name("q")).clear();
			driver.findElement(By.name("q")).sendKeys(searchstring);
			driver.findElement(By.xpath("//*[@id='_fZl']")).click();
			Thread.sleep(4000);

			List<WebElement> lnkMatch = new ArrayList<WebElement>();

			if (searchstring == "hospitals in Nashville TN") {
				Domain = "hcahealthcare.com";
			} else if (searchstring == "largest hospital corporation") {
				Domain = "sthealth.com";
			} else if (searchstring == "physician jobs in Nashville") {
				Domain = "vanderbilthealth.com";
			}

			Main: while (Pagecount < 4) {
				// List<WebElement> lnkElements =
				// driver.findElements(By.xpath("//*[@id='rso']//h3/a")); //
				// partialLinkText("HCA"));
				List<WebElement> lnkElements = driver.findElements(By
						.xpath("//*[@id='rso']//cite"));
				for (WebElement ele : lnkElements) {

					lnkMatch.add(ele); // Adds element containing the links
					if (ele.getText().contains(Domain)) {
						if (lnkMatch.size() <= 30) {

							System.out.println("-------------------");
							System.out.println("Search phrase:" + searchstring);
							System.out.println("Domain:" + Domain);
							System.out.println("Rank:" + lnkMatch.size());
							System.out.println("Status: Pass");
						}

						break Main;
					}

				}
				Pagecount++;
				driver.findElement(By.xpath("//*[@id='pnnext']/span[2]"))
						.click();
				Thread.sleep(2000);

			}

			if (Pagecount > 3) {
				System.out.println("-------------------");
				System.out.println("Search phrase:" + searchstring);
				System.out.println("Domain:" + Domain);
				System.out.println("Rank: Greater than 30");
				System.out.println("Status: Fail");

			}
		}
		
		driver.quit();
	}
}



