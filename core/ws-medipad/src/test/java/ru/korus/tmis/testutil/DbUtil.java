package ru.korus.tmis.testutil;

import ru.korus.tmis.util.TestUtilCommon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    private static class TableMaxIndex {
        final private String tableName;
        final private int maxIndex;

        private TableMaxIndex(String tableName, int maxIndex) {
            this.tableName = tableName;
            this.maxIndex = maxIndex;
        }
    }

    private static final String HOST = "localhost";//"10.1.2.10";//"localhost";// "10.128.225.66"
    protected static final String JDBC_MYSQL_URL = "jdbc:mysql://" + HOST + "/core_test?characterEncoding=utf8";

    private Connection conn = null;
    final TableMaxIndex tables[] = {
            new TableMaxIndex("`trfuOrderIssueResult`", 1456),
            new TableMaxIndex("`ActionProperty`", 1456),
            new TableMaxIndex("`ActionProperty_Action`", 1456),
            new TableMaxIndex("`ActionProperty_Date`", 1456),
            new TableMaxIndex("`ActionProperty_Double`", 1456),
            new TableMaxIndex("`ActionProperty_FDRecord`", 1456),
            new TableMaxIndex("`ActionProperty_HospitalBed`", 1456),
            new TableMaxIndex("`ActionProperty_HospitalBedProfile`", 1456),
            new TableMaxIndex("`ActionProperty_Image`", 1456),
            new TableMaxIndex("`ActionProperty_ImageMap`", 1456),
            new TableMaxIndex("`ActionProperty_Integer`", 1456),
            new TableMaxIndex("`ActionProperty_Job_Ticket`", 1456),
            new TableMaxIndex("`ActionProperty_MKB`", 1456),
            new TableMaxIndex("`ActionProperty_Organisation`", 1456),
            new TableMaxIndex("`ActionProperty_OtherLPURecord`", 1456),
            new TableMaxIndex("`ActionProperty_Person`", 1456),
            new TableMaxIndex("`ActionProperty_rbBloodComponentType`", 1456),
            new TableMaxIndex("`ActionProperty_rbFinance`", 1456),
            new TableMaxIndex("`ActionProperty_rbReasonOfAbsence`", 1456),
            new TableMaxIndex("`ActionProperty_String`", 1456),
            new TableMaxIndex("`ActionProperty_Time`", 1456),
            new TableMaxIndex("`Diagnostic`", 57),
            new TableMaxIndex("`Diagnosis`", 202),
            new TableMaxIndex("`ActionProperty_Diagnosis`", 0),
            new TableMaxIndex("`rbBloodComponentType`", 0),
            new TableMaxIndex("`bbtOrganism_SensValues`", 0),
            new TableMaxIndex("`bbtResult_Organism`", 0),
            new TableMaxIndex("`bbtResult_Text`", 0),
            new TableMaxIndex("`bbtResponse`", 0),
            new TableMaxIndex("`DrugChart`", 4),
            new TableMaxIndex("`DrugComponent`", 1),
            new TableMaxIndex("`Action`", 259),
            new TableMaxIndex("`Event`", 254),
            new TableMaxIndex("`Person`", 25),
            //new TableMaxIndex("`Event_LocalContract`", 0),
            new TableMaxIndex("`Event_ClientRelation`", 0),
    };

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
        for (String sql : sqlList) {
            if (!sql.trim().isEmpty()) {
                System.out.println("update DB: " + sql);
                s.executeUpdate(sql);
            }
        }

    }

    private void clear() throws SQLException {
        final Statement s = conn.createStatement();
        s.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        for (TableMaxIndex table : tables) {
            System.out.print("Clear table" + table.tableName);
            s.executeUpdate("DELETE FROM " + table.tableName + " WHERE `id` > " + table.maxIndex);
            System.out.println("A rows with has been removed. Table: " + table.tableName + " max index: " + table.maxIndex);
            if(!"`rbBloodComponentType`".equals(table.tableName)) {
                s.executeUpdate("ALTER TABLE " + table.tableName + "AUTO_INCREMENT=" + table.maxIndex);
            }
        }
        s.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
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
