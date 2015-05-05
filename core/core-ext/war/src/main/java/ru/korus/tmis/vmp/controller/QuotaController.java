package ru.korus.tmis.vmp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.korus.tmis.vmp.model.AuthData;
import ru.korus.tmis.vmp.model.IdCodeNames;
import ru.korus.tmis.vmp.model.quote.QuotaDataContainer;
import ru.korus.tmis.vmp.service.AuthService;
import ru.korus.tmis.vmp.service.QuotaService;
import ru.korus.tmis.vmp.utilities.MyJsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 12:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Controller
@RequestMapping(value = "quota")
@Scope("session")
public class QuotaController implements Serializable {

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private AuthService authService;


    @RequestMapping(method = RequestMethod.GET, value = "quotaType"/*, produces =
            { MediaType.APPLICATION_JSON_VALUE, "application/javascript",  },
            headers="Accept=application/json"*/)
    @ResponseBody
    //TODO убрать "ручную" сенрриализацию в json и преобразование в utf-8
    public byte[] getQuotaType(@RequestParam Integer mkbId,
                           @RequestParam(required = false) String callback,
                           @RequestParam(required = false) String sortingField,
                           @RequestParam(required = false) String sortingMethod,
                           @RequestParam(required = false) String limit,
                           @RequestParam(required = false) String page,
                           @RequestParam(required = false) String recordsCount,
                           @RequestParam(required = false) String reqDateTime) {
        IdCodeNames quotaType = quotaService.getQuotaType(mkbId); return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "pacient_model")
    @ResponseBody
    public byte[] getPatientModel(@RequestParam(required = false) Integer mkbId,
                                  @RequestParam Integer quotaTypeId,
                               @RequestParam(required = false) String callback,
                               @RequestParam(required = false) String sortingField,
                               @RequestParam(required = false) String sortingMethod,
                               @RequestParam(required = false) String limit,
                               @RequestParam(required = false) String page,
                               @RequestParam(required = false) String recordsCount,
                               @RequestParam(required = false) String reqDateTime) {


        IdCodeNames quotaType = quotaService.getPatientModel(mkbId, quotaTypeId);
        return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "treatment")
    @ResponseBody
    public byte[] getTreatment(@RequestParam Integer pacientModelId,
                                  @RequestParam(required = false) String callback,
                                  @RequestParam(required = false) String sortingField,
                                  @RequestParam(required = false) String sortingMethod,
                                  @RequestParam(required = false) String limit,
                                  @RequestParam(required = false) String page,
                                  @RequestParam(required = false) String recordsCount,
                                  @RequestParam(required = false) String reqDateTime) {


        IdCodeNames quotaType = quotaService.getTreatment(pacientModelId);
        return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }


    @RequestMapping(method = RequestMethod.POST, consumes ="application/json", value = "/{eventId}/client_quoting")
    @ResponseBody
    public byte[] saveQuota(@PathVariable Integer eventId,
                            @RequestParam(required = false) String callback,
                            @RequestBody QuotaDataContainer quotaData,
                            HttpServletRequest request) {

        final AuthData authData = authService.getAuthData(request);
        QuotaDataContainer quotaType = quotaService.saveQuota(eventId, quotaData, authData);
        return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes ="application/json", value = "/{eventId}/client_quoting/{quotaId}")
    @ResponseBody
    public byte[] updateQuota(@PathVariable Integer eventId,
                              @PathVariable Integer quotaId,
                            @RequestParam(required = false) String callback,
                            @RequestBody QuotaDataContainer quotaData,
                            HttpServletRequest request) {

        final AuthData authData = authService.getAuthData(request);
        quotaData.getData().setId(quotaId);
        QuotaDataContainer quotaType = quotaService.updateQuota(eventId, quotaData, authData);
        return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{eventId}/client_quoting")
    @ResponseBody
    public byte[] getQuota(@PathVariable Integer eventId,
                           @RequestParam(required = false) String callback) {

        QuotaDataContainer quotaType = quotaService.getQuota(eventId);
        return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{eventId}/client_quoting/prev")
    @ResponseBody
    public byte[] getQuotaPrev(@PathVariable Integer eventId,
                           @RequestParam(required = false) String callback) {

        QuotaDataContainer quotaType = quotaService.getQuotaPrev(eventId);
        return MyJsonUtils.toJsonWithPadding(callback, quotaType);
    }

}
