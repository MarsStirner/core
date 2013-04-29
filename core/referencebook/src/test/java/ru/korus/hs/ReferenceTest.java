package ru.korus.hs;

import nsi.F007Type;
import nsi.V007Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.korus.tmis.hs.ReferenceBook;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.04.13, 14:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ReferenceTest {
    private static final Logger logger = LoggerFactory.getLogger(ReferenceTest.class);

    private ReferenceBook referenceBook;

    @BeforeSuite
    void start() {
        referenceBook = new ReferenceBook();
    }

    @Test
    public void loadV005() {
        referenceBook.loadPol();
        referenceBook.loadVedom();
        referenceBook.loadTfoms();
        referenceBook.loadTipOMS();
        referenceBook.loadMo();
        referenceBook.loadSmo();
        referenceBook.loadNomMO();
        referenceBook.loadFedOkrug();
        referenceBook.loadOKSM();
        referenceBook.loadTipdoc();
    }


    public static void main(String[] args) {
        ReferenceTest rt = new ReferenceTest();
        rt.start();
        rt.loadV005();
    }

}
