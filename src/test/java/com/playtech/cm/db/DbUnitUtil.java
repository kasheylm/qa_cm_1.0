package com.playtech.cm.db;

import com.sun.rowset.CachedRowSetImpl;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Init database for tests.
 *
 * @author Yuriy Tumakha
 */
public class DbUnitUtil {
    public static final String JDBC_DRIVER_PROPERTY = "jdbc.driver";
    public static final String JDBC_URL_PROPERTY = "jdbc.url";
    public static final String JDBC_USERNAME_PROPERTY = "jdbc.username";
    public static final String JDBC_PASSWORD_PROPERTY = "jdbc.password";

    private static final String NULL_LABEL = "[NULL]";
    private static final String GET_TABLENAMES_SQL = "SELECT table_name FROM user_tables order by table_name";
    private static final List<String> IGNORE_TABLE_PREFIXES = Arrays.asList("ACTIVEMQ_");

    /**
     * Return DbUnit DatabaseTester.
     *
     * DatabaseTester should be started by databaseTester.onSetup(); and closed
     * by databaseTester.onTearDown();
     */
    public static IDatabaseTester getDatabaseTester(
            final DataSource dataSource, final String datasetFile)
            throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(
                dataSource);
        initData(databaseTester, datasetFile);
        return databaseTester;
    }

    /**
     * Refresh entities.
     */
    public static IDatabaseTester refreshData(final String jdbcPropertiesFile,
                                              final String datasetFile, final String dtdFile) throws Exception {
        IDatabaseTester databaseTester = getJdbcTester(jdbcPropertiesFile);
        initData(databaseTester, datasetFile, dtdFile);
        return databaseTester;
    }

    private static void initData(final IDatabaseTester databaseTester,
                                 final String datasetFile) throws Exception {
        initData(databaseTester, datasetFile, null);
    }

    private static void initData(final IDatabaseTester databaseTester,
                                 final String datasetFile, final String dtdFile) throws Exception {
        IDatabaseConnection connection= setUpConnection(databaseTester.getConnection());

        // initialize dataset
        InputSource xmlSource = new InputSource(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(datasetFile));

        FlatXmlDataSet flatXmlDataSet;
        if (dtdFile != null) {
            InputStream dtdStream = Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(dtdFile);
            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            flatXmlDataSetBuilder.setMetaDataSetFromDtd(dtdStream);
            flatXmlDataSet = flatXmlDataSetBuilder.build(xmlSource);
        } else {
            flatXmlDataSet = new FlatXmlDataSet(new FlatXmlProducer(xmlSource));
        }

        ReplacementDataSet replacementDataSet = new ReplacementDataSet(
                flatXmlDataSet);
        replacementDataSet.addReplacementObject(NULL_LABEL, null);
        databaseTester.setDataSet(replacementDataSet);
        DatabaseOperation.CLEAN_INSERT.execute(connection, replacementDataSet);
        connection.close();
    }

    private static IDatabaseTester getJdbcTester(final String jdbcPropertiesFile)
            throws Exception {
        Properties jdbcProperties = new Properties();
        jdbcProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(jdbcPropertiesFile));

        IDatabaseTester databaseTester = new JdbcDatabaseTester(

                (String) jdbcProperties.get(JDBC_DRIVER_PROPERTY),
                (String) jdbcProperties.get(JDBC_URL_PROPERTY),
                (String) jdbcProperties.get(JDBC_USERNAME_PROPERTY),
                (String) jdbcProperties.get(JDBC_PASSWORD_PROPERTY));
        return databaseTester;

    }

    public static void generateDTD(final String jdbcPropertiesFile,
                                   final String dtdOutputFile) throws Exception {
        IDatabaseTester databaseTester = getJdbcTester(jdbcPropertiesFile);
        IDatabaseConnection connection= setUpConnection(databaseTester.getConnection());
        String[] tableNames = getTableNames(connection.getConnection());
        FlatDtdDataSet.write(connection.createDataSet(tableNames),
                new FileOutputStream(dtdOutputFile));
        connection.close();
    }

    private static String[] getTableNames(final Connection jdbcConnection)
            throws SQLException {
        List<String> tableNames = new ArrayList<String>();
        Statement statement = jdbcConnection.createStatement();
        try {
            ResultSet rs = statement.executeQuery(GET_TABLENAMES_SQL);
            try {
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    if (checkTablename(tableName)) {
                        tableNames.add(tableName);
                    }
                }
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        String[] arr = new String[tableNames.size()];
        tableNames.toArray(arr);
        return arr;
    }

    private static boolean checkTablename(final String tablename) {
        for (String prefix : IGNORE_TABLE_PREFIXES) {
            return !tablename.startsWith(prefix);
        }
        return true;
    }

    public static void exportDatabase(final String jdbcPropertiesFile,
                                      final String dataOutputFile) throws Exception {
        IDatabaseTester databaseTester = getJdbcTester(jdbcPropertiesFile);
        IDatabaseConnection connection= setUpConnection(databaseTester.getConnection());
        String[] tableNames = getTableNames(connection.getConnection());
        FlatXmlDataSet.write(connection.createDataSet(tableNames),
                new FileOutputStream(dataOutputFile));
        connection.close();
    }

    public static void clean(final String jdbcPropertiesFile){
        try{
            String SQL_CLEAN_SCRIPT = "begin\n" +
                    "  for r in (select table_name from user_tables) loop\n" +
                    "    execute immediate 'truncate table ' || r.table_name;\n" +
                    "  end loop;\n" +
                    "end;";
            try{
                enableAllConstraints(false);
                IDatabaseTester databaseTester = getJdbcTester(jdbcPropertiesFile);
                IDatabaseConnection connection= setUpConnection(databaseTester.getConnection());
                connection.getConnection().prepareStatement(SQL_CLEAN_SCRIPT).execute();
                connection.close();
            } finally {
                enableAllConstraints(true);
            }
        }
      catch (Exception e){
          e.printStackTrace();
      }
    }

    public static void enableAllConstraints(Boolean enable) throws Exception {
        IDatabaseTester databaseTester = getJdbcTester("demodb-jdbc.properties");
        IDatabaseConnection connection= setUpConnection(databaseTester.getConnection());
        ArrayList<String> constraintQueries = new ArrayList<String>();
        Statement stat = connection.getConnection().createStatement();

        stat.setQueryTimeout(20);
        String queryCreatorSql = "select 'alter table ' || owner || '.' ||'\"'|| table_name ||'\"'|| ' disable constraint ' ||'\"'||constraint_name||'\"'  from user_constraints where table_name not in(select object_name from user_recyclebin) order by constraint_type desc";
        if(enable){
            queryCreatorSql = "select 'alter table ' || owner || '.' ||'\"'|| table_name ||'\"'|| ' enable constraint ' ||'\"'||constraint_name||'\"'  from user_constraints where table_name not in(select object_name from user_recyclebin) order by constraint_type asc";
        }
        ResultSet rs = stat.executeQuery(queryCreatorSql);
        while(rs.next()){
            constraintQueries.add(rs.getString(1));
        }
        rs.close();
        for(String query:constraintQueries){
            stat.executeQuery(query);
        }
        stat.close();
        connection.close();
    }

    public static ResultSet executeQuery(String sqlQuery) throws Exception{
        IDatabaseTester databaseTester = getJdbcTester("demodb-jdbc.properties");
        IDatabaseConnection connection= setUpConnection(databaseTester.getConnection());
        Statement stat = connection.getConnection().createStatement();
        ResultSet rs = stat.executeQuery(sqlQuery);
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.populate(rs);
        rs.close();
        stat.close();
        connection.close();
        return rowSet;
    }

    public static IDatabaseConnection setUpConnection(IDatabaseConnection connection) {
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
        return connection;
    }

}



