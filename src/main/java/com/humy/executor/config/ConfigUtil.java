package com.humy.executor.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Humy
 * @date 2020/9/16 0:05
 */
public class ConfigUtil {

    public static Object getConfigValue(String key) {

        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Properties properties = new Properties();
        try {
            properties.load(bufferedReader);
            return properties.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
