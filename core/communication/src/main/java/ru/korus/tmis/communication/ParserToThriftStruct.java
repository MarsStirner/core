package ru.korus.tmis.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.communication.QueueTicket;
import ru.rorus.tmis.schedule.DateConvertions;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 16:45<br>
 * Company Korus Consulting IT<br>
 */

/**
 * Класс предназначен для конвертации сущностей из БД в возвращаемые КС (компонентой связи) значения
 */
public final class ParserToThriftStruct {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(ParserToThriftStruct.class);

    private ParserToThriftStruct() {
    }

    public static ru.korus.tmis.communication.thriftgen.Patient parsePatient(final Patient item) {
        if (item == null) {
            logger.warn("Parser: NullPointer patient item. Return \"null\"");
            return null;
        }
        final ru.korus.tmis.communication.thriftgen.Patient result = new ru.korus.tmis.communication.thriftgen.Patient()
                .setFirstName(item.getFirstName())
                .setLastName(item.getLastName())
                .setPatrName(item.getPatrName())
                .setSex(item.getSex())
                .setBirthDate(DateConvertions.convertDateToUTCMilliseconds(item.getBirthDate()))
                .setId(item.getId());
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.OrgStructure parseOrgStructure(final ru.korus.tmis.core.entity.model.OrgStructure item) {
        if (item == null) {
            logger.warn("Parser: NullPointer OrgStructure item. Return \"null\"");
            return null;
        }
        final ru.korus.tmis.communication.thriftgen.OrgStructure result = new ru.korus.tmis.communication.thriftgen.OrgStructure()
                .setId(item.getId()).setCode(item.getCode());
        if (item.getAddress() != null) {
            result.setAddress(item.getAddress());
        }
        if (item.getName() != null) {
            result.setName(item.getName());
        }
        if (item.getParentId() != null) {
            result.setParent_id(item.getParentId());
        }
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.Person parseStaff(final Staff item) {
        if (item == null) {
            logger.warn("Parser: NullPointer Staff item. Return \"null\"");
            return null;
        }
        final ru.korus.tmis.communication.thriftgen.Person result = new ru.korus.tmis.communication.thriftgen.Person()
                .setId(item.getId()).setCode(item.getCode()).setOffice(item.getOffice() + "\t" + item.getOffice2());
        result.setLastName(item.getLastName()).setFirstName(item.getFirstName()).setPatrName(item.getPatrName());
        final ru.korus.tmis.core.entity.model.Speciality speciality = item.getSpeciality();
        if (speciality != null) {
            result.setSpeciality(speciality.getName()).setSpecialityRegionalCode(speciality.getRegionalCode()).setSpecialityOKSOCode(speciality.getOKSOCode());
        }
        switch (Sex.valueOf(item.getSex())) {
            case MEN: {
                result.setSexFilter("М");
                break;
            }
            case WOMEN: {
                result.setSexFilter("Ж");
                break;
            }
            case UNDEFINED: {
                result.setSexFilter("");
                break;
            }
            default: {
                result.setSexFilter("");
                break;
            }
        }
        if (item.getPost() != null) {
            result.setPost(item.getPost().getName());
        }
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.Speciality parseQuotingBySpeciality(final QuotingBySpeciality item) {
        if (item == null) {
            logger.warn("Parser: NullPointer Speciality item. Return \"null\"");
            return null;
        }
        return new ru.korus.tmis.communication.thriftgen.Speciality()
                .setTicketsAvailable(item.getCouponsRemaining())
                .setTicketsPerMonths(item.getCouponsQuote())
                .setSpeciality(item.getSpeciality().getName())
                .setId(item.getSpeciality().getId());
    }

    public static ru.korus.tmis.communication.thriftgen.Organization parseOrganisation(final Organisation item) {
        if (item == null) {
            logger.warn("Parser: NullPointer Organisation item. Return \"null\"");
            return null;
        }
        return new Organization()
                .setFullName(item.getFullName()).setAddress(item.getAddress()).setInfisCode(item.getInfisCode())
                .setShortName(item.getShortName());
    }

    public static QueueCoupon parseQueueCoupon(final QueueTicket item) {
        if (item == null || item.getPerson() == null || item.getPatient() == null) {
            logger.warn("Parser: NullPointer Speciality item. Return \"null\"");
            return null;
        }
        final ru.korus.tmis.communication.thriftgen.Patient patient =
                new ru.korus.tmis.communication.thriftgen.Patient(item.getPatient().getId())
                        .setBirthDate(DateConvertions.convertDateToUTCMilliseconds(item.getPatient().getBirthDate()))
                        .setLastName(item.getPatient().getLastName())
                        .setFirstName(item.getPatient().getFirstName())
                        .setPatrName(item.getPatient().getPatrName())
                        .setSex(item.getPatient().getSex());
        final QueueCoupon coupon = new QueueCoupon()
                .setUuid(item.getQueueAction().getId().toString())
                .setPersonId(item.getPerson().getId())
                .setPatient(patient)
                .setBegDateTime(DateConvertions.convertDateToUTCMilliseconds(item.getBegDateTime()))
                .setEndDateTime(DateConvertions.convertDateToUTCMilliseconds(item.getEndDateTime()));
        if (item.getOffice() != null && !item.getOffice().isEmpty()) {
            coupon.setOffice(item.getOffice());
        }
        if (QueueTicket.Status.NEW.toString().equals(item.getStatus())) {
            coupon.setStatus(CouponStatus.NEW);
        } else if (QueueTicket.Status.CANCELLED.toString().equals(item.getStatus())) {
            coupon.setStatus(CouponStatus.CANCELLED);
        } else {
            logger.error("QueueTicket[{}] has unknown Status = {}", item.getId(), item.getStatus());
        }
        return coupon;
    }

    public static TTicket parseTTicket(final PersonSchedule schedule, final ru.rorus.tmis.schedule.Ticket ticket) {
        final TTicket result = new TTicket();
        result.setDate(DateConvertions.convertDateToUTCMilliseconds(schedule.getAmbulatoryDate()));
        result.setBegTime(DateConvertions.convertDateToUTCMilliseconds(ticket.getBegTime()));
        result.setEndTime(DateConvertions.convertDateToUTCMilliseconds(ticket.getEndTime()));
        result.setOffice(schedule.getOffice());
        result.setAvailable(ticket.isAvailable());
        result.setFree(ticket.isFree());
        result.setTimeIndex(ticket.getTimeCellIndex());
        final Patient patient = ticket.getPatient();
        if (patient != null) {
            result.setPatientId(patient.getId());
            result.setPatientInfo(new StringBuilder(patient.getLastName()).append(' ')
                    .append(patient.getFirstName()).append(' ').append(patient.getPatrName()).toString());
        } else {
            result.setPatientId(0);
        }
        return result;
    }

    public static Schedule parsePersonSchedule(final PersonSchedule schedule) {
        final Schedule result = new Schedule();
        result.setAvailable(schedule.isAvailable());
        result.setBegTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getBegTime()));
        result.setEndTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getEndTime()));
        result.setOffice(schedule.getOffice());
        result.setPlan(schedule.getPlan());
        for (ru.rorus.tmis.schedule.Ticket currentTicket : schedule.getTickets()) {
            result.addToTickets(parseTTicket(schedule, currentTicket));
        }
        return result;
    }

    @Deprecated
    public static Amb parsePersonScheduleToAmb(final PersonSchedule schedule) {
        final Amb result = new Amb();
        result.setAvailable(schedule.isAvailable() ? 1 : 0);
        result.setBegTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getBegTime()));
        result.setEndTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getEndTime()));
        result.setOffice(schedule.getOffice());
        result.setPlan(schedule.getPlan());
        for (ru.rorus.tmis.schedule.Ticket currentTicket : schedule.getTickets()) {
            result.addToTickets(parseTicket(currentTicket));
        }
        return result;
    }

    @Deprecated
    private static ru.korus.tmis.communication.thriftgen.Ticket parseTicket(ru.rorus.tmis.schedule.Ticket ticket) {
        final ru.korus.tmis.communication.thriftgen.Ticket result = new ru.korus.tmis.communication.thriftgen.Ticket();
        result.setAvailable(ticket.isAvailable() ? 1 : 0);
        result.setFree(ticket.isFree() ? 1 : 0);
        result.setTime(DateConvertions.convertDateToUTCMilliseconds(ticket.getBegTime()));
        final Patient patient = ticket.getPatient();
        if (patient != null) {
            result.setPatientId(patient.getId());
            result.setPatientInfo(new StringBuilder(patient.getLastName()).append(' ')
                    .append(patient.getFirstName()).append(' ').append(patient.getPatrName()).toString());
        } else {
            result.setPatientId(0);
        }
        return result;
    }
}
