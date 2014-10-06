package ru.korus.tmis.util;

import org.custommonkey.xmlunit.Diff;
import org.xml.sax.SAXException;
import ru.korus.tmis.core.entity.model.RbFinance;
import ru.korus.tmis.core.entity.model.bak.BbtResponse;
import ru.korus.tmis.core.entity.model.fd.FDRecord;
import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.PublicClonable;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.01.14, 13:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilCommon implements TestUtil {

    @Override
    public Package[] getPackagesForTest() {
        final Package[] res = { CoreException.class.getPackage(),
                PublicClonable.class.getPackage(),
                RbFinance.class.getPackage(),
                FDRecord.class.getPackage(),
                LayoutAttributeValue.class.getPackage(),
                Kladr.class.getPackage(),
                DrugChart.class.getPackage(),
                BbtResponse.class.getPackage()};
        return res;
    }

    public static boolean checkArgument(Object value, String pathExcept, String contextPath) throws Exception {
        String res = Utils.marshallMessage(value, contextPath);
        final String pathToExceptMessage = pathExcept;
        return checkArgument(res, pathToExceptMessage);
    }

    public static boolean checkArgument(String res, String pathToExceptMessage) throws IOException, SAXException {
        String except = readAllBytes(pathToExceptMessage);
        Diff diff = new Diff(except, res);
        if( !diff.identical() ) {
            System.out.println("Argument:");
            System.out.println(res);
            System.out.println("Except:");
            System.out.println(except);
            System.out.println("Diff with " + pathToExceptMessage + " :");
            System.out.println(diff.toString());
        }
        return diff.identical();
    }

    public static String readAllBytes(String sqlFileNAme) throws IOException {
        return (new String(Files.readAllBytes(Paths.get(sqlFileNAme)), "UTF-8"));
    }

    public static void executeQuery(final EntityManager em, String sqlFileName) {
        final String[] sqlFromFile = getSqlFromFile(sqlFileName);
        for(String sql : sqlFromFile) {
            System.out.println("execute SQL : " + sql);
            em.createNativeQuery(sql).executeUpdate();
        }
    }

    public static String[] getSqlFromFile(String sqlFileName) {
        try {
            return TestUtilCommon.readAllBytes(sqlFileName).split(";");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
