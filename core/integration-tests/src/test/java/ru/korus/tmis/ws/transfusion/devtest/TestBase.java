package ru.korus.tmis.ws.transfusion.devtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
public class TestBase {
    private static final String HOST = "localhost";// "10.128.225.66"
    protected static final String JDBC_MYSQL_URL = "jdbc:mysql://" + HOST + "/s11r64";
    protected final static Integer TRFU_ACTION_CREATED_PERSON_ID = 183; // TODO create tester person

    protected final static Integer ACTION_ID = 100000000;
    protected final static Integer ACTION_MOVING_ID = 100000001;

    protected final static Integer ACTION_PORP_ID_BASE = 100000000;

    protected final static Integer ACTION_MOVING_TYPE = 113;
    private static final PropType[] propMovingConstants = { PropType.PATIENT_ORG_STRUCT };

    static Connection conn = null;

    protected static void initTestCase(Integer actionTypeId, PropType[] propConstants) {
        try {
            final String userName = "root";
            final String password = "root";
            final String url = JDBC_MYSQL_URL;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Database connection established");
            initPropId(actionTypeId, propConstants);
            initPropId(ACTION_MOVING_TYPE, propMovingConstants);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    protected static void closeTestCase() {
        if (conn != null) {
            try {
                conn.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initPropId(final Integer actionTypeId, final PropType[] propConstants) {
        try {
            final Statement s = conn.createStatement();
            for (final PropType propType : propConstants) {
                final String sqlActionPropTypeSelectTpl = "SELECT id FROM ActionPropertyType WHERE code = '%s' AND actionType_id = %d";
                s.executeQuery(String.format(sqlActionPropTypeSelectTpl, propType.getCode(), actionTypeId));
                final ResultSet rs = s.getResultSet();
                if (rs.next()) {
                    propType.setId(rs.getInt("id"));
                } else {
                    throw new SQLException(String.format("The action property type for code %s has been not found", propType.getCode()));
                }
            }
            s.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws SQLException
     * 
     */
    protected void clearDB(final PropType[] propConstants) throws SQLException {
        final Statement s = conn.createStatement();
        s.executeUpdate("DELETE FROM trfuFinalVolume WHERE action_id=" + ACTION_ID);
        s.executeUpdate("DELETE FROM trfuLaboratoryMeasure WHERE action_id=" + ACTION_ID);
        s.executeUpdate("DELETE FROM trfuOrderIssueResult WHERE action_id=" + ACTION_ID);
        s.executeUpdate("DELETE FROM ActionProperty WHERE action_id=" + ACTION_ID);
        for (final PropType propType : propConstants) {
            s.executeUpdate("DELETE FROM ActionProperty WHERE id=" + getPropValueId(propType));
            final String sql = "DELETE FROM ActionProperty_" + propType.getType() + " WHERE id=" + getPropValueId(propType);
            s.executeUpdate(sql);
        }
        for (final PropType propType : propMovingConstants) {
            s.executeUpdate("DELETE FROM ActionProperty WHERE id=" + getPropValueId(propType));
            final String sql = "DELETE FROM ActionProperty_" + propType.getType() + " WHERE id=" + getPropValueId(propType);
            s.executeUpdate(sql);
        }
        s.executeUpdate("DELETE FROM Action WHERE id=" + ACTION_ID);
        s.executeUpdate("DELETE FROM Action WHERE id=" + ACTION_MOVING_ID);
    }

    /**
     * @param propType
     * @return
     */
    protected int getPropValueId(final PropType propType) {
        return ACTION_PORP_ID_BASE + propType.getId();
    }

    /**
     * @param s
     * @param actionTypeId
     * @param propConstants
     * @throws SQLException
     */
    protected void createActionWithProp(final Integer actionTypeId, PropType[] propConstants) throws SQLException {
        final Statement s = conn.createStatement();
        // TODO create test Event
        final String sqlActionInsertTpl =
                "INSERT INTO Action (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `actionType_id`, `event_id`, `idx`, `directionDate`, `status`, `setPerson_id`, `isUrgent`, `begDate`, `plannedEndDate`, `endDate`, `note`, `person_id`, `office`, `amount`, `uet`, `expose`, `payStatus`, `account`, `finance_id`, `prescription_id`, `takenTissueJournal_id`, `contract_id`, `coordDate`, `coordAgent`, `coordInspector`, `coordText`, `hospitalUidFrom`, `pacientInQueueType`, `version`, `parentAction_id`, `uuid_id`) "
                        + " VALUES (%d, CURRENT_TIMESTAMP, %d, CURRENT_TIMESTAMP, %d, 0, %d, 1, 0, CURRENT_TIMESTAMP, 0, %d, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, '', %d, '', 0, 0, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '', '', '', '0', 0, 3, NULL, 284056);";
        s.executeUpdate(String.format(sqlActionInsertTpl, ACTION_ID, TRFU_ACTION_CREATED_PERSON_ID, TRFU_ACTION_CREATED_PERSON_ID, actionTypeId,
                TRFU_ACTION_CREATED_PERSON_ID,
                TRFU_ACTION_CREATED_PERSON_ID));
        s.executeUpdate(String.format(sqlActionInsertTpl, ACTION_MOVING_ID, TRFU_ACTION_CREATED_PERSON_ID, TRFU_ACTION_CREATED_PERSON_ID, ACTION_MOVING_TYPE,
                TRFU_ACTION_CREATED_PERSON_ID,
                TRFU_ACTION_CREATED_PERSON_ID));
        final String sqlActionPropInsertTpl =
                "INSERT INTO ActionProperty "
                        + "(`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) "
                        + "VALUES " + "(%d, CURRENT_TIMESTAMP, %d, CURRENT_TIMESTAMP, %d, 0, %d, %d, NULL, '', 0, NULL, 0);";
        for (final PropType propType : propConstants) {
            s.executeUpdate(String.format(sqlActionPropInsertTpl, getPropValueId(propType), TRFU_ACTION_CREATED_PERSON_ID, TRFU_ACTION_CREATED_PERSON_ID,
                    ACTION_ID, propType.getId()));
        }
        for (final PropType propType : propMovingConstants) {
            s.executeUpdate(String.format(sqlActionPropInsertTpl, getPropValueId(propType), TRFU_ACTION_CREATED_PERSON_ID, TRFU_ACTION_CREATED_PERSON_ID,
                    ACTION_MOVING_ID, propType.getId()));
        }
    }

    /**
     * @return
     * @throws InterruptedException
     */
    protected String waitOrderRequestId() throws InterruptedException {
        String res = null;
        for (int count = 0; res == null && count < 90; ++count) {
            try {
                System.out.print(".");
                res = getValue(PropType.ORDER_REQUEST_ID);
            } catch (final SQLException ex) {
                Thread.sleep(1000);
            }
        }
        System.out.println();
        System.out.println("Order request result: '" + res + "'");
        return res;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getValue(final PropType propType) throws SQLException {
        final Statement s = conn.createStatement();
        final Integer id = getPropValueId(propType);
        final String sql = "SELECT value FROM ActionProperty_" + propType.getType() + " WHERE id=" + id;
        s.executeQuery(sql);
        final ResultSet rs = s.getResultSet();
        if (rs.next()) {
            return (T) rs.getObject("value");
        } else {
            throw new SQLException(String.format("The action property type for code %s has been not found", propType.getCode()));
        }
    }

    /**
     * @param diagnosis
     * @param string
     * @throws SQLException
     */
    public <T> void setValue(final PropType propType, final T value) throws SQLException {
        final Statement s = conn.createStatement();
        final Integer id = getPropValueId(propType);
        final String sql = "INSERT INTO ActionProperty_" + propType.getType() + " (`id`, `index`, `value`) VALUES (" + id + ", 0, '" + value + "')";
        s.executeUpdate(sql);
    }
}
