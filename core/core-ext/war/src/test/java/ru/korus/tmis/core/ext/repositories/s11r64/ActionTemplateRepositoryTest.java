package ru.korus.tmis.core.ext.repositories.s11r64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import ru.korus.tmis.core.ext.config.MainSpringConfiguration;
import ru.korus.tmis.core.ext.entities.s11r64.Action;
import ru.korus.tmis.core.ext.entities.s11r64.ActionTemplate;

import java.util.Date;
import java.util.List;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;


/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2015, 18:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@ContextConfiguration(classes = {MainSpringConfiguration.class})
@WebAppConfiguration
public class ActionTemplateRepositoryTest  extends AbstractTestNGSpringContextTests {

    @Autowired
    private ActionTemplateRepository actionTemplateRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Test
    @Rollback(true)
    public void testCreateActionTemplate() throws Exception {
        ActionTemplate actionTemplate = new ActionTemplate();
        Action action = actionRepository.findOne(259);
        actionTemplate.setAction(action);
        Date now = new Date();
        actionTemplate.setCreateDatetime(now);
        actionTemplate.setModifyDatetime(now);
        actionTemplate.setModifyPerson_id(1);
        actionTemplate.setName("тест");
        actionTemplateRepository.saveAndFlush(actionTemplate);
        assertNotNull(actionTemplate.getId());
    }

    @Test
    @Rollback(true)
    public void testFindActionTemplate() throws Exception {
        ActionTemplate actionTemplate = actionTemplateRepository.findOne(1);
        assertNotNull(actionTemplate);
        List<ActionTemplate> res = actionTemplateRepository.findByActionTypeAndPersonAdnGroupAndSspeciality(123, null, null, null);
        assertFalse(res.isEmpty());
    }
}
