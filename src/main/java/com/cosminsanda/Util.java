package com.cosminsanda;

import com.typesafe.config.Config;

import java.util.Properties;
import java.util.Random;

public class Util {

    public static Properties toProperties(Config config) {
        var properties = new Properties();
        config.entrySet().forEach(e -> properties.setProperty(e.getKey(), config.getString(e.getKey())));
        return properties;
    }

    public static String randomUpper(String str, double p) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c) && Math.random() < p)
                c = Character.toUpperCase(c);
            sb.append(c);
        }
        return sb.toString();
    }

    public static String messXml(String xml, Random random) {
        return new StringBuilder(xml).deleteCharAt(random.nextInt(xml.length() - 1)).toString();
    }

}
