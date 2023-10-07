package org.otsoi.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader(){
        String env = System.getProperty("app.env") == null ? "PROD" : System.getProperty("app.env");
        String propertyFilePath = String.format("src/test/resources/environments/%s.properties", env);
        BufferedReader reader;
        try {

            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public Duration getTimeout() {
        String timeout = properties.getProperty("timeout");
        if(timeout != null) return Duration.parse(timeout);
        else throw new RuntimeException("timeout not specified in the Configuration.properties file.");
    }
    public Duration getPolling() {
        String polling = properties.getProperty("polling");
        if(polling != null) return Duration.parse(polling);
        else throw new RuntimeException("polling not specified in the Configuration.properties file.");
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if(url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }
}
