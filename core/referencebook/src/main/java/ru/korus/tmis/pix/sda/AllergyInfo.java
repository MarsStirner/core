package ru.korus.tmis.pix.sda;

import java.util.Arrays;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.ClientAllergy;
import ru.korus.tmis.util.EntityMgr;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 08.07.13, 13:18 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */
public class AllergyInfo {

    /**
     * Краткое наименование ЛПУ
     */
    // TODO Совпадает с SendingFacility и EventInfo.irgName
    final private String orgName;

    /**
     * Дата/время фиксации данных
     */
    final private XMLGregorianCalendar createDate;

    /**
     * Расшифровка вида аллергии по справочнику МИС
     */
    final private String nameSubstance;

    /**
     * Идентификатор степени тяжести аллергической реакции по справочнику МИ
     */
    final private Integer severityCode;

    /**
     * Расшифровка степени тяжести аллергической реакции по справочнику МИС
     */
    final private String severityDescription;

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @return the createDate
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * @return the nameSubstance
     */
    public String getNameSubstance() {
        return nameSubstance;
    }

    /**
     * @return the severityCode
     */
    public Integer getSeverityCode() {
        return severityCode;
    }

    /**
     * @return the severityDescription
     */
    public String getSeverityDescription() {
        return severityDescription;
    }

    public AllergyInfo(ClientAllergy clientAllergy, String orgName) {
        this.orgName = orgName;
        XMLGregorianCalendar createDate = null;
        try {
            createDate = EntityMgr.toGregorianCalendar(clientAllergy.getCreateDate());
        } catch (DatatypeConfigurationException e) {
        }
        this.createDate = createDate;
        this.nameSubstance = clientAllergy.getNameSubstance();
        this.severityCode = clientAllergy.getPower();
        String severityDescription = null;
        if (severityCode != null) {
            List<String> vals = Arrays.asList(new String[] { "не известно", "малая", "средняя", "высокая", "строгая" });
            if (severityCode < vals.size()) {
                severityDescription = vals.get(severityCode);
            }
        }
        this.severityDescription = severityDescription;
    }
}
