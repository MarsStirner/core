package ru.korus.tmis.communication;

import ru.korus.tmis.communication.thriftgen.OrgStructure;
import ru.korus.tmis.communication.thriftgen.PatientInfo;
import ru.korus.tmis.communication.thriftgen.Person;
import ru.korus.tmis.communication.thriftgen.Speciality;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.entity.model.Sex;
import ru.korus.tmis.core.entity.model.Staff;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 16:45<br>
 * Company Korus Consulting IT<br>
 */
public final class ParserToThriftStruct {

    public static PatientInfo parsePatient(final Patient item) {
        if (item == null) return null;
        PatientInfo result = new PatientInfo().setFirstName(item.getFirstName()).setLastName(item.getLastName()).setPatrName(item.getPatrName());
        result.setSex(item.getSex()).setBirthDate(item.getBirthDate().getTime());
        return result;
    }

    public static OrgStructure parseOrgStructure(final ru.korus.tmis.core.entity.model.OrgStructure item) {
        OrgStructure result = new OrgStructure().setId(item.getId()).setCode(item.getCode());
        if (item.getAddress() != null) result.setAddress(item.getAddress());
        if (item.getName() != null) result.setName(item.getName());
        if (item.getParentId() != null) result.setParent_id(item.getParentId());
        return result;
    }

    public static Person parseStaff(final Staff item) {
        Person result = new Person().setId(item.getId()).setCode(item.getCode()).setOffice(item.getOffice() + "\t" + item.getOffice2());
        result.setLastName(item.getLastName()).setFirstName(item.getFirstName()).setPatrName(item.getPatrName());
        ru.korus.tmis.core.entity.model.Speciality speciality = item.getSpeciality();
        result.setSpeciality(speciality.getName()).setSpecialityRegionalCode(speciality.getRegionalCode()).setSpecialityOKSOCode(speciality.getOKSOCode());
        switch (Sex.valueOf(item.getSex())) {
            case MEN:
                result.setSexFilter("М");
                break;
            case WOMEN:
                result.setSexFilter("Ж");
                break;
            case UNDEFINED:
                result.setSexFilter("");
                break;
        }
        result.setPost(item.getPost().getName());
        return result;
    }

    public static Speciality parseQuotingBySpeciality(QuotingBySpeciality item) {
        Speciality speciality = new Speciality().setTicketsAvailable(item.getCouponsRemaining()).setTicketsPerMonths(item.getCouponsQuote()).setSpeciality(item.getSpeciality().getName());
        return speciality;
    }
}
