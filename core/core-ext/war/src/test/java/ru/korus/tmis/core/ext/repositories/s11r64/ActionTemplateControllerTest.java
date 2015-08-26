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
import ru.korus.tmis.core.ext.model.templates.ActionTemplateData;
import ru.korus.tmis.core.ext.model.templates.ActionTemplateDataContainer;
import ru.korus.tmis.core.ext.utilities.TestUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2015, 18:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@ContextConfiguration(classes = {MainSpringConfiguration.class})
@WebAppConfiguration
public class ActionTemplateControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext wac;


  private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreateTemplates() throws Exception {
        ActionTemplateData actionTemplateData = new ActionTemplateData();
        actionTemplateData.setActionId(259);
        actionTemplateData.setName("ActionTemplateControllerTest.testCreateTemplates");
        String res = mockMvc.perform(post("/template").param("callback", "callback")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.toJson(actionTemplateData)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        ActionTemplateData actionTemplateDataRes = TestUtil.fromJsonWithPadding(res, ActionTemplateData.class);
        assertNotNull(res.isEmpty());
        assertTrue(actionTemplateDataRes.getId() > 0);
    }


    @Test
    public void testGetTemplates() throws Exception {
        String res = mockMvc.perform(get("/template").param("actionTypeId", "123").param("callback", "callback"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        ActionTemplateDataContainer actionTemplateDataContainer =
                TestUtil.fromJsonWithPadding(res, ActionTemplateDataContainer.class);
        assertFalse(actionTemplateDataContainer.getActionTemplateList().isEmpty());

    }
}
