package com.firefly.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CurrentVersionUtil {

    private static final Logger log = Logger.getLogger(CurrentVersionUtil.class);

    private static String currentVersion = null;

    static {
        initCurrentVersion();
    }

    public static void initCurrentVersion() {
        InputStream propStream = null;
        try {
            propStream = CurrentVersionUtil.class.getResourceAsStream("/codeversion.properties");
            if (propStream != null) {
                Properties prop = new Properties();
                prop.load(propStream);

                currentVersion = prop.getProperty("currentversion");
                if (!currentVersion.contains("{")) {
                    return;
                }
            }
        } catch (IOException e) {
            log.error(e);
        } finally {
            try {
                propStream.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
        currentVersion = "0";
    }

    public static String getCurrentVersion() {
        return currentVersion;
    }
}
