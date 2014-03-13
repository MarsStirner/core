package ru.korus.tmis.testutil;

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
            "`Event`",
            "`Action`",};
    final Map<String, Integer> maxIndexMap = new HashMap<String, Integer>();

    public DbUtil() {
        initConnection();
        System.out.println("Database connection established");
        saveState();
    }

    public void saveState() {
        try {
            for (final String tableName : tables) {
                final Statement s = conn.createStatement();
                final String sql = "SELECT max(`id`) FROM " + tableName;
                s.executeQuery(sql);
                final ResultSet rs = s.getResultSet();
                if (rs.next()) {
                    maxIndexMap.put(tableName, rs.getInt("max(`id`)"));
                } else {
                    throw new SQLException(String.format("Cannot init max index. SQL: %s", sql));
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void restore() {
        try {
            final Statement s = conn.createStatement();
            for (String tableName : tables) {
                s.executeUpdate("DELETE FROM " + tableName + " WHERE `id` >= " + maxIndexMap.get(tableName));
            }
        } catch (final Exception e) {
            e.printStackTrace();
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

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
