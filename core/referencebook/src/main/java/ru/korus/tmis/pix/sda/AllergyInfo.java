package ru.korus.tmis.pix.sda;

import java.util.Arrays;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.ClientAllergy;
import ru.korus.tmis.core.entity.model.Staff;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 08.07.13, 13:18 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */
public class AllergyInfo {

    /**
     * Дата/время фиксации данных
     */
    final private XMLGregorianCalendar createDate;

    /**
     * Расшифровка вида аллергии по справочнику МИС
     *  Аллергия/непереносимость (название препарата, шерсть животных, продукт питания, пыль, ...)
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
     * Идентификатор в МИС
     */
    private final String id;

    /**
     * Автор записи (Врач)
     */
    private final EmployeeInfo createdPerson;

    /**
     * Дополнительная информация
     */
    private final String note;

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

    public AllergyInfo(ClientAllergy clientAllergy) {
        XMLGregorianCalendar createDate = null;
        try {
            createDate = Database.toGregorianCalendar(clientAllergy.getCreateDate());
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
        this.id = String.valueOf(clientAllergy.getId());
        this.createdPerson = EmployeeInfo.newInstance(clientAllergy.getCreatePerson());
        this.note = clientAllergy.getNotes();
    }

    public String getId() {
        return id;
    }

    public EmployeeInfo getCreatedPerson() {
        return createdPerson;
    }

    public String getNote() {
        return note;
    }
}
