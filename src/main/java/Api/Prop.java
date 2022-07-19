package Api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Prop {
    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    public static void main(String[] args) {

        FileInputStream fileInputStream;

        Properties prop = new Properties();

        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);

            String site = prop.getProperty("site");
            String loginToSite = prop.getProperty("login");
            String passwordToSite = prop.getProperty("password");

            System.out.println(
                    "site: " + site
                            + "\nloginToSite: " + loginToSite
                            + "\npasswordToSite: " + passwordToSite
            );

        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }

    }
}
