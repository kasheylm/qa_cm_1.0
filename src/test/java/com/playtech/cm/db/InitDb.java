package com.playtech.cm.db;


import junit.framework.Assert;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Yuriy Tumakha
 */
public class InitDb {

    private static final String JDBC_PROPERTIES_FILE = "demodb-jdbc.properties";
    private static final String DTD_FILE = "dbunit-dataset.dtd";

    public static void main(String[] args) {
        // Export full com.playtech.cm.db entities
        try {
            DbUnitUtil.exportDatabase(JDBC_PROPERTIES_FILE, "./target/db-entities.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("InitDB done.");
    }

    public InitDb(String dataFile) {
        try {
            DbUnitUtil.clean(JDBC_PROPERTIES_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate DTD if com.playtech.cm.db structure changed
        try {
            DbUnitUtil.generateDTD(JDBC_PROPERTIES_FILE, "src/test/resources/" + DTD_FILE);

            DbUnitUtil.refreshData(JDBC_PROPERTIES_FILE, dataFile, DTD_FILE);
            // Export full com.playtech.cm.db entities
            //DbUnitUtil.exportDatabase(JDBC_PROPERTIES_FILE, "./target/db-entities.xml");
            //System.out.println("InitDB done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
