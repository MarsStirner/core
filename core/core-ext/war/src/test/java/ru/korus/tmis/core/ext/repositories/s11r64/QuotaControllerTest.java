package ru.korus.tmis.core.ext.repositories.s11r64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.korus.tmis.core.ext.config.MainSpringConfiguration;
import ru.korus.tmis.core.ext.entities.s11r64.ActionTemplate;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.testng.Assert.assertNotNull;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2015, 18:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@ContextConfiguration(classes = {MainSpringConfiguration.class})
@WebAppConfiguration
public class QuotaControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext wac;


  private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetQuotaType() throws Exception {
        mockMvc.perform(get("/quota/quotaType").param("mkbId", "1"));
        //TODO add check!
    }
}
