package ru.korus.tmis.core.ext.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.2015, 13:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
public class OptionsController {
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
