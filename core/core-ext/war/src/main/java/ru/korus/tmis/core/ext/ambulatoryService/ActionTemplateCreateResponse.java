package ru.korus.tmis.core.ext.ambulatoryService;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Author: Upatov Egor <br>
 * Date: 29.10.2015, 14:05 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description:
 * {
 *      "meta": {
 *          "code": 200,
 *          "name": "OK"
 *      },
 *      "result": {
 *          "aid": null,
 *          "con": {
 *              "age": "",
 *              "sex": 0
 *          },
 *          "gid": null,
 *          "id": 3,
 *          "name": "Персианцева М.И."
 *      }
 * }
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTemplateCreateResponse {
    private Meta meta;
    private ActionTemplateCreateResult result;

    public ActionTemplateCreateResponse() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(final Meta meta) {
        this.meta = meta;
    }

    public ActionTemplateCreateResult getResult() {
        return result;
    }

    public void setResult(final ActionTemplateCreateResult result) {
        this.result = result;
    }
}



