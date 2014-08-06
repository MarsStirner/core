package ru.korus.tmis.ws.transfusion;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.08.14, 15:18 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
//@RequestMapping("/transfusionBean")
public class TransfusionRest {
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public String getMovie(@PathVariable String name) {
        return "hello:" + name;
    }
}
