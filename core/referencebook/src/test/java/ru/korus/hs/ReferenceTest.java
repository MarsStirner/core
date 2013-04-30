package ru.korus.hs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.korus.tmis.hs.ReferenceBook;

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
        referenceBook.loadF015FedOkrug();
        referenceBook.loadV012Ishod();
        referenceBook.loadV003LicUsl();
        referenceBook.loadV004Medspec();
        referenceBook.loadM001MKB10();
        referenceBook.loadF003Mo();
        referenceBook.loadV001NomeclR();
        referenceBook.loadV007NomMO();
        referenceBook.loadO002Okato();
        referenceBook.loadO004Okfs();
        referenceBook.loadO005Okopf();
        referenceBook.loadO001OKSM();
        referenceBook.loadO003Okved();
        referenceBook.loadV005Pol();
        referenceBook.loadV002ProfOt();
        referenceBook.loadV009Rezult();
        referenceBook.loadF002Smo();
        referenceBook.loadV010Sposob();
        referenceBook.loadF009StatZL();
        referenceBook.loadF010Subekti();
        referenceBook.loadF001Tfoms();
        referenceBook.loadF011Tipdoc();
        referenceBook.loadF008TipOMS();
        referenceBook.loadV006UslMp();
        referenceBook.loadF007Vedom();
        referenceBook.loadV008VidMp();

    }


    public static void main(String[] args) {
        ReferenceTest rt = new ReferenceTest();
        rt.start();
        rt.loadV005();
    }

}
