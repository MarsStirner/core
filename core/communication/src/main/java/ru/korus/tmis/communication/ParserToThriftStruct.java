package ru.korus.tmis.communication;

import ru.korus.tmis.communication.thriftgen.Organization;
import ru.korus.tmis.communication.thriftgen.PatientInfo;
import ru.korus.tmis.core.entity.model.*;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 16:45<br>
 * Company Korus Consulting IT<br>
 */
public final class ParserToThriftStruct {

    public static PatientInfo parsePatientInfo(final Patient item) {
        if (item == null) return null;
        PatientInfo result = new PatientInfo().setFirstName(item.getFirstName()).setLastName(item.getLastName()).setPatrName(item.getPatrName());
        result.setSex(item.getSex()).setBirthDate(item.getBirthDate().getTime());
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.Patient parsePatient(final Patient item) {
        if (item == null) return null;
        ru.korus.tmis.communication.thriftgen.Patient result = new ru.korus.tmis.communication.thriftgen.Patient().setFirstName(item.getFirstName()).setLastName(item.getLastName()).setPatrName(item.getPatrName());
        result.setSex(item.getSex()).setBirthDate(item.getBirthDate().getTime()).setId(item.getId());
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.OrgStructure parseOrgStructure(final ru.korus.tmis.core.entity.model.OrgStructure item) {
        ru.korus.tmis.communication.thriftgen.OrgStructure result = new ru.korus.tmis.communication.thriftgen.OrgStructure()
                .setId(item.getId()).setCode(item.getCode());
        if (item.getAddress() != null) result.setAddress(item.getAddress());
        if (item.getName() != null) result.setName(item.getName());
        if (item.getParentId() != null) result.setParent_id(item.getParentId());
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.Person parseStaff(final Staff item) {
        ru.korus.tmis.communication.thriftgen.Person result = new ru.korus.tmis.communication.thriftgen.Person()
                .setId(item.getId()).setCode(item.getCode()).setOffice(item.getOffice() + "\t" + item.getOffice2());
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

    public static ru.korus.tmis.communication.thriftgen.Speciality parseQuotingBySpeciality(final QuotingBySpeciality item) {
        ru.korus.tmis.communication.thriftgen.Speciality speciality = new ru.korus.tmis.communication.thriftgen.Speciality()
                .setTicketsAvailable(item.getCouponsRemaining()).setTicketsPerMonths(item.getCouponsQuote()).setSpeciality(item.getSpeciality().getName());
        return speciality;
    }

    public static ru.korus.tmis.communication.thriftgen.Organization parseOrganisation(Organisation item) {
        ru.korus.tmis.communication.thriftgen.Organization result = new Organization()
                .setFullName(item.getFullName()).setAddress(item.getAddress()).setInfisCode(item.getInfisCode())
                .setShortName(item.getShortName());
        return result;
    }


    public static String convertDotPatternToSQLLikePattern(String dotPattern) {
        if (dotPattern != null && dotPattern.length() > 0)
            return dotPattern.replaceAll("(\\.{3})|(\\*)", "%").replaceAll("\\.", "_");
        else return "";
    }
}