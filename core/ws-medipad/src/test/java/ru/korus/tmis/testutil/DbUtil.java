package ru.korus.tmis.testutil;

import ru.korus.tmis.util.TestUtilCommon;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.02.2013, 12:16:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 *
 */
public class DbUtil {
    private static final String HOST = "localhost";// "10.128.225.66"
    protected static final String JDBC_MYSQL_URL = "jdbc:mysql://" + HOST + "/core_test";

    private Connection conn = null;
    final String tables[] = {
            "`ActionProperty_Action`",
            "`ActionProperty_Date`",
            "`ActionProperty_Double`",
            "`ActionProperty_FDRecord`",
            "`ActionProperty_HospitalBed`",
            "`ActionProperty_HospitalBedProfile`",
            "`ActionProperty_Image`",
            "`ActionProperty_ImageMap`",
            "`ActionProperty_Integer`",
            "`ActionProperty_Job_Ticket`",
            "`ActionProperty_MKB`",
            "`ActionProperty_Organisation`",
            "`ActionProperty_OtherLPURecord`",
            "`ActionProperty_Person`",
            "`ActionProperty_rbBloodComponentType`",
            "`ActionProperty_rbFinance`",
            "`ActionProperty_rbReasonOfAbsence`",
            "`ActionProperty_String`",
            "`ActionProperty_Time`",
            "`ActionProperty`",
            "`DrugChart`",
            "`DrugComponent`",
            "`Event`",
            "`Action`",
            "`Person`"};

    final Map<String, Integer> maxIndexMap = new HashMap<String, Integer>() {{
        put("`DrugChart`", 4);
        put("`DrugComponent`", 1);
        put("`Event`", 254);
        put("`Action`", 259);
        put("`ActionProperty`", 1456);
        put("`ActionProperty_Action`", 1456);
        put("`ActionProperty_Date`", 1456);
        put("`ActionProperty_Double`", 1456);
        put("`ActionProperty_FDRecord`", 1456);
        put("`ActionProperty_HospitalBed`", 1456);
        put("`ActionProperty_HospitalBedProfile`", 1456);
        put("`ActionProperty_Image`", 1456);
        put("`ActionProperty_ImageMap`", 1456);
        put("`ActionProperty_Integer`", 1456);
        put("`ActionProperty_Job_Ticket`", 1456);
        put("`ActionProperty_MKB`", 1456);
        put("`ActionProperty_Organisation`", 1456);
        put("`ActionProperty_OtherLPURecord`", 1456);
        put("`ActionProperty_Person`", 1456);
        put("`ActionProperty_rbBloodComponentType`", 1456);
        put("`ActionProperty_rbFinance`", 1456);
        put("`ActionProperty_rbReasonOfAbsence`", 1456);
        put("`ActionProperty_String`", 1456);
        put("`ActionProperty_Time`", 1456);
        put("`Person`", 25);
    }};


    public void prepare() {
        initConnection();
        System.out.println("Database connection established");
        try {
            clear();
            initDb("./src/test/resources/sql/init.sql");
            initDb("./src/test/resources/sql/OrgStructure_ActionType.sql");
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void initDb(String fileName) throws SQLException {
        final Statement s = conn.createStatement();
        String[] sqlList = TestUtilCommon.getSqlFromFile(fileName);
        for(String sql : sqlList) {
            if(!sql.trim().isEmpty()) {
                System.out.println("update DB: " + sql);
                s.executeUpdate(sql);
            }
        }

    }

    private void clear() throws SQLException {
        final Statement s = conn.createStatement();
        for (String tableName : tables) {
            System.out.print("Clear table" + tableName);
            s.executeUpdate("DELETE FROM " + tableName + " WHERE `id` > " + maxIndexMap.get(tableName));
            System.out.println("A rows with has been removed. Table: " + tableName + " max index: " + maxIndexMap.get(tableName));
        }
    }

    /**
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     */
    private void initConnection() {
        final String userName = "root";
        final String password = "root";
        final String url = JDBC_MYSQL_URL;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
