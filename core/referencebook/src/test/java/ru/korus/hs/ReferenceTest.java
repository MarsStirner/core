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
    public void loadM001MKB10() {
        referenceBook.loadM001MKB10();
    }

    @Test
    public void loadV001NomeclR() {
        referenceBook.loadV001();
    }

    @Test
    public void loadV002ProfOt() {
        referenceBook.loadV002ProfOt();
    }

    @Test
    public void loadV003LicUsl() {
        referenceBook.loadV003LicUsl();
    }

    @Test
    public void loadV004Medspec() {
        referenceBook.loadV004Medspec();
    }

    @Test
    public void loadV005Pol() {
        referenceBook.loadV005();
    }

    @Test
    public void loadV006UslMp() {
        referenceBook.loadV006UslMp();
    }

    @Test
    public void loadV007NomMO() {
        referenceBook.loadV007();
    }

    @Test
    public void loadV008VidMp() {
        referenceBook.loadV008VidMp();
    }

    @Test
    public void loadV009Rezult() {
        referenceBook.loadV009Rezult();
    }

    @Test
    public void loadF001Tfoms() {
        referenceBook.loadF001();
    }

    @Test
    public void loadF002Smo() {
        referenceBook.loadF002();
    }

    @Test
    public void loadF003Mo() {
        referenceBook.loadF003();
    }

    @Test
    public void loadF007Vedom() {
        referenceBook.loadF007();
    }

    @Test
    public void loadF008TipOMS() {
        referenceBook.loadF008TipOMS();
    }

    @Test
    public void loadF009StatZL() {
        referenceBook.loadF009StatZL();
    }

    @Test(enabled = false)
    public void loadV005() {
        referenceBook.loadF015FedOkrug();
        referenceBook.loadV012Ishod();
        referenceBook.loadV003LicUsl();
        referenceBook.loadV004Medspec();
        referenceBook.loadM001MKB10();
        referenceBook.loadF003();
        referenceBook.loadV001();
        referenceBook.loadV007();
        referenceBook.loadO002Okato();
        referenceBook.loadO004Okfs();
        referenceBook.loadO005Okopf();
        referenceBook.loadO001OKSM();
        referenceBook.loadO003Okved();
        referenceBook.loadV005();
        referenceBook.loadV002ProfOt();
        referenceBook.loadV009Rezult();
        referenceBook.loadF002();
        referenceBook.loadV010Sposob();
        referenceBook.loadF009StatZL();
        referenceBook.loadF010Subekti();
        referenceBook.loadF001();
        referenceBook.loadF011Tipdoc();
        referenceBook.loadF008TipOMS();
        referenceBook.loadV006UslMp();
        referenceBook.loadF007();
        referenceBook.loadV008VidMp();

    }


    public static void main(String[] args) {
        ReferenceTest rt = new ReferenceTest();
        rt.start();
        rt.loadV005();
    }

}
