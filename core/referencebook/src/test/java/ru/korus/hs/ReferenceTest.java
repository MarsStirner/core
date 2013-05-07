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
        referenceBook.loadM001();
    }

    @Test
    public void loadV001NomeclR() {
        referenceBook.loadV001();
    }

    @Test
    public void loadV002ProfOt() {
        referenceBook.loadV002();
    }

    @Test
    public void loadV003LicUsl() {
        referenceBook.loadV003();
    }

    @Test
    public void loadV004Medspec() {
        referenceBook.loadV004();
    }

    @Test
    public void loadV005Pol() {
        referenceBook.loadV005();
    }

    @Test
    public void loadV006UslMp() {
        referenceBook.loadV006();
    }

    @Test
    public void loadV007NomMO() {
        referenceBook.loadV007();
    }

    @Test
    public void loadV008VidMp() {
        referenceBook.loadV008();
    }

    @Test
    public void loadV009Rezult() {
        referenceBook.loadV009();
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
        referenceBook.loadF008();
    }

    @Test
    public void loadF009StatZL() {
        referenceBook.loadF009();
    }

    @Test(enabled = false)
    public void loadV005() {
        referenceBook.loadF015();
        referenceBook.loadV012();
        referenceBook.loadV003();
        referenceBook.loadV004();
        referenceBook.loadM001();
        referenceBook.loadF003();
        referenceBook.loadV001();
        referenceBook.loadV007();
        referenceBook.loadO002();
        referenceBook.loadO004();
        referenceBook.loadO005();
        referenceBook.loadO001();
        referenceBook.loadO003();
        referenceBook.loadV005();
        referenceBook.loadV002();
        referenceBook.loadV009();
        referenceBook.loadF002();
        referenceBook.loadV010();
        referenceBook.loadF009();
        referenceBook.loadF010();
        referenceBook.loadF001();
        referenceBook.loadF011();
        referenceBook.loadF008();
        referenceBook.loadV006();
        referenceBook.loadF007();
        referenceBook.loadV008();

    }


    public static void main(String[] args) {
        ReferenceTest rt = new ReferenceTest();
        rt.start();
        rt.loadV005();
    }

}
