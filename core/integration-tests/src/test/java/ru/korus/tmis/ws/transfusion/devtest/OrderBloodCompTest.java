package ru.korus.tmis.ws.transfusion.devtest;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ru.korus.tmis.ws.transfusion.devtest.wsimport.IssueResult;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.OrderIssueInfo;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.TransfusionService;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.TransfusionServiceImpl;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.02.2013, 12:10:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class OrderBloodCompTest extends TestBase {

    protected final static Integer TRFU_ACTION_TYPE_ID = 3580;// 3689; // TODO init by flatCode

    private static final PropType[] propConstants = { PropType.DIAGNOSIS, // Основной клинический диагноз
            PropType.BLOOD_COMP_TYPE, // Требуемый компонент крови
            PropType.TYPE, // Вид трансфузии
            PropType.VOLUME, // Объем требуемого компонента крови (все, кроме тромбоцитов)
            PropType.DOSE_COUNT, // Количество требуемых донорских доз (тромбоциты)
            PropType.ROOT_CAUSE, // Показания к проведению трансфузии
            PropType.ORDER_REQUEST_ID, // Результат передачи требования в систему ТРФУ
            PropType.ORDER_ISSUE_RES_DATE, // Дата выдачи КК
            PropType.ORDER_ISSUE_RES_TIME,// Время выдачи КК
            PropType.ORDER_ISSUE_BLOOD_COMP_PASPORT, };

    @BeforeClass
    public static void init() {
        initTestCase(TRFU_ACTION_TYPE_ID, propConstants);
    }

    @AfterClass
    public static void freeResource() {
        closeTestCase();
    }

    @Test(groups = "createNewOrder")
    public void createNewOrder() {
        try {
            clearDB(propConstants);
            final Integer actionTypeId = TRFU_ACTION_TYPE_ID;

            createActionWithProp(actionTypeId, propConstants);

            setValue(PropType.DIAGNOSIS, "tst DIAGNOSIS");
            setValue(PropType.BLOOD_COMP_TYPE, 1);
            setValue(PropType.DOSE_COUNT, 0.1);
            setValue(PropType.ROOT_CAUSE, "tst root cause");
            setValue(PropType.TYPE, "<>" + SendOrderBloodComponents.TRANSFUSION_TYPE_PLANED + "<>");
            setValue(PropType.ORDER_ISSUE_BLOOD_COMP_PASPORT, ACTION_ID);
            String res = waitOrderRequestId();
            AssertJUnit.assertTrue(res != null ? res.indexOf("Получен идентификатор в системе ТРФУ: ") == 0 : false);
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
            Assert.fail(" Thread.sleep error");
        } catch (final SQLException ex) {
            ex.printStackTrace();
            Assert.fail("SQL error");
        }
    }

    @Test(groups = "setOrderRes", dependsOnGroups = "createNewOrder")
    public void setOrderRes() {
        final TransfusionServiceImpl serv = new TransfusionServiceImpl();
        final TransfusionService wsTrfu = serv.getPortTransfusion();
        XMLGregorianCalendar now = null;
        try {
            now = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (final DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        final List<OrderIssueInfo> components = new Vector<OrderIssueInfo>();
        components.add(new OrderIssueInfo());
        components.get(0).setNumber("nnnnn_nn_NNN");
        components.get(0).setBloodGroupId(4);
        components.get(0).setComponentId(10);
        components.get(0).setComponentTypeId(20);
        components.get(0).setDonorId(589);
        components.get(0).setDoseCount(0.1);
        components.get(0).setRhesusFactorId(1);
        components.get(0).setVolume(100);

        final IssueResult res = wsTrfu.setOrderIssueResult(ACTION_ID, now, components, "comment");
        // TODO check data base! (table trfuOrderIssueResult)
        System.out.println("setOrderIssueResult return: " + res);
        Assert.assertTrue(res.isResult());
        AssertJUnit.assertTrue(res.getDescription() == null);
        AssertJUnit.assertTrue(res.getRequestId().equals(ACTION_ID));
    }

    /**
     * @param diagnosis
     * @param string
     * @throws SQLException
     */
    private <T> void setValue(final PropType propType, final T value) throws SQLException {
        final Statement s = conn.createStatement();
        final Integer id = getPropValueId(propType);
        final String sql = "INSERT INTO ActionProperty_" + propType.getType() + " (`id`, `index`, `value`) VALUES (" + id + ", 0, '" + value + "')";
        s.executeUpdate(sql);
    }

}
