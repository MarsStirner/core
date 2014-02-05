package ru.korus.tmis.pix.sda;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        05.02.14, 11:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class RbManagerTest {

    @Test
    public void toJson() {
        RbManager.Response json = RbManager.toJson("{\"oid\": \"1.2.643.5.1.13.2.1.1.178\", \"data\": {\"code\": \"26919\", \"name\": \"ГБУЗ «Кузнецкая городская детская больница»\"}}");
        Assert.assertEquals("1.2.643.5.1.13.2.1.1.178", json.getCodingSystem());
        Assert.assertEquals("26919", json.getCode());
        Assert.assertEquals("ГБУЗ «Кузнецкая городская детская больница»", json.getName());
    }
}
