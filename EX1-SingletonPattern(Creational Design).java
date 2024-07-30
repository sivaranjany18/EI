/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sridhar
 */
// Singleton
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private String configData;

    private ConfigurationManager() {
        // Load configuration data
        configData = "Configuration Data";
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getConfigData() {
        return configData;
    }
}

public class SingletonPattern{
    public static void main(String[] args) {
        ConfigurationManager configManager1 = ConfigurationManager.getInstance();
        ConfigurationManager configManager2 = ConfigurationManager.getInstance();

        System.out.println(configManager1.getConfigData());
        System.out.println(configManager1 == configManager2); // Should print true
    }
}
