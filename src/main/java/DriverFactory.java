import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;

public class DriverFactory {

    WebDriver driver;
    Properties prop;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver init_driver() {
        String browserName = prop.getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            tlDriver.set(new ChromeDriver());
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();

            tlDriver.set(new FirefoxDriver());


        }
        getDriver().manage().window().fullscreen();
        getDriver().manage().deleteAllCookies();

        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * This method is used to initialize the properties from config file.
     *
     * @return returns Properties prop
     */

    public Properties init_prop() {
        FileInputStream ip = null;
        prop = new Properties();
        String env = System.getProperty("env");
        System.out.println("Running on Environment -->: " + env);

        if (env == null) {
            try {
                ip = new FileInputStream("./src/test/resources/config/config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {

            try {
                switch (env) {
                    case "qa":
                        ip = new FileInputStream("./src/test/resources/config/dev.properties");
                        break;
                    case "ete":
                        ip = new FileInputStream("./src/test/resources/config/ete.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream("./src/test/resources/config/qa.properties");
                        break;

                    default:
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            prop.load(ip);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }

}
