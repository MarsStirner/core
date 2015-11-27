package ru.korus.tmis.communication;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.communication.thriftgen.Address;
import ru.korus.tmis.communication.thriftgen.Schedule;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Speciality;
import ru.korus.tmis.core.entity.model.communication.QueueTicket;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.schedule.*;
import ru.korus.tmis.schedule.PersonSchedule;
import ru.korus.tmis.schedule.Ticket;

import java.util.ArrayList;

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
        ru.korus.tmis.communication.thriftgen.Patient result = new ru.korus.tmis.communication.thriftgen.Patient()
                .setFirstName(item.getFirstName())
                .setLastName(item.getLastName())
                .setPatrName(item.getPatrName())
                .setSex(item.getSex())
                .setBirthDate(DateConvertions.convertDateToUTCMilliseconds(item.getBirthDate()))
                .setSnils(item.getSnils())
                .setId(item.getId());
        for (ClientDocument currentDocument : item.getActiveClientDocuments()) {
            if (ConfigManager.codes().getValue("rbDocumentType.passport").equalsIgnoreCase(currentDocument.getDocumentType().getCode())) {
                result.addToDocuments(parseDocument(currentDocument));
            }
        }
        for (ClientPolicy currentPolicy : item.getActiveClientPolicies()) {
            if (ConfigManager.codes().getValueList("rbPolicyType.oms").contains(currentPolicy.getPolicyType().getCode())) {
                result.addToPolicies(parsePolicy(currentPolicy));
            }
        }
        return result;
    }

    private static Policy parsePolicy(ClientPolicy item) {
        if (item != null) {
            final Policy result = new Policy();
            result.setNumber(item.getNumber());
            result.setSerial(item.getSerial());
            result.setTypeCode(item.getPolicyType().getCode());
            return result;
        }
        return null;
    }

    private static Document parseDocument(ClientDocument item) {
        if (item != null) {
            final Document result = new Document();
            result.setNumber(item.getNumber());
            result.setSerial(item.getSerial());
            result.setTypeCode(item.getDocumentType().getCode());
            return result;
        }
        return null;
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
        result.setAvailableForExternal(item.getAvailableForExternal() != 0);
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
        if (item.getSnils() != null) {
            result.setSnils(item.getSnils());
        }
        return result;
    }

    public static ru.korus.tmis.communication.thriftgen.Speciality parseSpeciality(final QuotingBySpeciality item) {
        return new ru.korus.tmis.communication.thriftgen.Speciality()
                .setTicketsAvailable(item.getCouponsRemaining())
                .setTicketsPerMonths(item.getCouponsQuote())
                .setSpeciality(item.getSpeciality().getName())
                .setId(item.getSpeciality().getId());
    }

    public static ru.korus.tmis.communication.thriftgen.Speciality parseSpeciality(Speciality item) {
        return new ru.korus.tmis.communication.thriftgen.Speciality()
                .setTicketsAvailable(Integer.MAX_VALUE)
                .setTicketsPerMonths(Integer.MAX_VALUE)
                .setSpeciality(item.getName())
                .setId(item.getId());
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
        final ScheduleClientTicket clientTicket = item.getTicket();
        final Patient client = clientTicket.getClient();
        final ScheduleTicket ticket = clientTicket.getTicket();
        final ru.korus.tmis.communication.thriftgen.Patient patient =
                new ru.korus.tmis.communication.thriftgen.Patient(client.getId())
                        .setBirthDate(DateConvertions.convertDateToUTCMilliseconds(client.getBirthDate()))
                        .setLastName(client.getLastName())
                        .setFirstName(client.getFirstName())
                        .setPatrName(client.getPatrName())
                        .setSex(client.getSex());
        final QueueCoupon coupon = new QueueCoupon()
                .setUuid(clientTicket.getId().toString())
                .setPersonId(ticket.getSchedule().getPerson().getId())
                .setPatient(patient)
                .setBegDateTime(DateConvertions.convertDateToUTCMilliseconds(ticket.getBegTime()))
                .setEndDateTime(DateConvertions.convertDateToUTCMilliseconds(ticket.getEndTime()));
        if (ticket.getSchedule().getOffice() != null && !ticket.getSchedule().getOffice().getName().isEmpty()) {
            coupon.setOffice(ticket.getSchedule().getOffice().getName());
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

    public static TTicket parseTTicket(final PersonScheduleBean.PersonSchedule schedule, final Ticket ticket) {
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

    public static Schedule parsePersonSchedule(final PersonScheduleBean.PersonSchedule schedule) {
        final Schedule result = new Schedule();
        result.setAvailable(schedule.isAvailable());
        result.setBegTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getBegTime()));
        result.setEndTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getEndTime()));
        result.setOffice(schedule.getOffice());
        result.setPlan(schedule.getPlan());
        result.setDate(DateConvertions.convertDateToUTCMilliseconds(schedule.getAmbulatoryDate()));
        for (Ticket currentTicket : schedule.getTickets()) {
            result.addToTickets(parseTTicket(schedule, currentTicket));
        }
        return result;
    }

    @Deprecated
    public static Amb parsePersonScheduleToAmb(final PersonScheduleBean.PersonSchedule schedule) {
        final Amb result = new Amb();
        result.setAvailable(schedule.isAvailable() ? 1 : 0);
        result.setBegTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getBegTime()));
        result.setEndTime(DateConvertions.convertDateToUTCMilliseconds(schedule.getEndTime()));
        result.setOffice(schedule.getOffice());
        result.setPlan(schedule.getPlan());
        for (Ticket currentTicket : schedule.getTickets()) {
            result.addToTickets(parseTicket(currentTicket));
        }
        return result;
    }

    @Deprecated
    private static ru.korus.tmis.communication.thriftgen.Ticket parseTicket(Ticket ticket) {
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

    public static EnqueuePatientStatus parseEnqueuePatientResult(final EnqueuePatientResult item) {
        final EnqueuePatientStatus result = new EnqueuePatientStatus();
        result.setIndex(item.getIndex()).setMessage(item.getMessage()).setQueueId(item.getQueueId()).setSuccess(item.isSuccess());
        return result;
    }

    public static Address parseAddress(final OrgStructure orgStructure, final OrgStructureAddress orgStructureAddress) {
        final AddressHouse adrHouse = orgStructureAddress.getAddressHouseList();
        if (adrHouse == null) {
            return null;
        }
        return new Address().setOrgStructureId(orgStructure.getId())
                .setPointKLADR(adrHouse.getKLADRCode())
                .setStreetKLADR(adrHouse.getKLADRStreetCode())
                .setCorpus(adrHouse.getCorpus()).setNumber(adrHouse.getNumber())
                .setFirstFlat(orgStructureAddress.getFirstFlat())
                .setLastFlat(orgStructureAddress.getLastFlat());
    }


    public static Contact parseContact(final ClientContact item) {
        final Contact result = new Contact();
        if (item.getContactType() != null) {
            result.setType(item.getContactType().getName())
                    .setCode(item.getContactType().getCode());
        }
        result.setContact(item.getContact())
                .setNote(item.getNotes());
        return result;
    }

    public static ReasonOfAbsenceException parseReasonOfAbsence(final RbReasonOfAbsence item) {
        return new ReasonOfAbsenceException(item.getName(), item.getCode());
    }


    public static Queue parseQueue(final ScheduleClientTicket item) {
        final ru.korus.tmis.core.entity.model.ScheduleTicket itemTicket = item.getTicket();
        final ru.korus.tmis.core.entity.model.Schedule itemSchedule = itemTicket.getSchedule();
        final Queue result = new Queue();
        final LocalDateTime itemBegDateTime = new LocalDate(itemSchedule.getDate()).toLocalDateTime(new LocalTime(itemTicket.getBegTime()));
        result.setQueueId(item.getId());
        result.setPersonId(itemSchedule.getPerson().getId());
        if (item.getNote() != null) {
            result.setNote(item.getNote());
        }
        if (item.getCreatePerson() != null) {
            result.setEnqueuePersonId(item.getCreatePerson().getId());
        }
        result.setEnqueueDateTime(DateConvertions.convertDateToUTCMilliseconds(item.getCreateDateTime()));
        result.setDateTime(DateConvertions.convertDateToUTCMilliseconds(itemBegDateTime.toDate()));
        return result;
    }

    public static Schedule parsePersonSchedule(final PersonSchedule item) {
        final Schedule result = new Schedule();
        result.setAvailable(item.isAvailable());
        result.setBegTime(DateConvertions.convertLocalTimeToUTCMilliseconds(item.getBegTime()));
        result.setDate(DateConvertions.convertLocalDateToUTCMilliseconds(item.getScheduleDate()));
        result.setEndTime(DateConvertions.convertLocalTimeToUTCMilliseconds(item.getEndTime()));
        result.setOffice(item.getOffice());
        result.setPlan(item.getPlan());
        result.setTickets(new ArrayList<TTicket>(item.getTickets().size()));
        for (ScheduleTicketExtended ticket : item.getTickets()) {
            result.addToTickets(parseTTicket(ticket, result.getDate()));
        }
        return result;
    }

    private static TTicket parseTTicket(final ScheduleTicketExtended item, final long date) {
        final TTicket result = new TTicket();
        final ScheduleTicket ticket = item.getTicket();
        result.setAvailable(item.isAvailable());
        result.setBegTime(DateConvertions.convertLocalTimeToUTCMilliseconds(item.getBegTime()));
        result.setEndTime(DateConvertions.convertLocalTimeToUTCMilliseconds(item.getEndTime()));
        result.setDate(date);
        if (ticket.getSchedule().getOffice() != null) {
            result.setOffice(ticket.getSchedule().getOffice().getName());
        }
        if (ticket.isFree()) {
            result.setFree(true);
        } else {
            result.setFree(false);
            final ScheduleClientTicket sct = ticket.getActiveClientTicketList().iterator().next();
            final Patient patient = sct.getClient();
            result.setPatientId(patient.getId());
            result.setPatientInfo(patient.getFullName());
        }
        return result;
    }

    public static TTicket parseTTicket(final ScheduleTicket item) {
        final TTicket result = new TTicket();
        result.setBegTime(DateConvertions.convertDateToUTCMilliseconds(item.getBegTime()));
        result.setEndTime(DateConvertions.convertDateToUTCMilliseconds(item.getEndTime()));
        result.setDate(DateConvertions.convertLocalDateToUTCMilliseconds(new LocalDate(item.getSchedule().getDate())));
        if (item.getSchedule().getOffice() != null) {
            result.setOffice(item.getSchedule().getOffice().getName());
        }
        if (item.isFree()) {
            result.setFree(true);
            result.setAvailable(true);
        } else {
            result.setFree(false);
            result.setAvailable(true);
            final ScheduleClientTicket sct = item.getActiveClientTicketList().iterator().next();
            final Patient patient = sct.getClient();
            if(patient != null) {
                result.setPatientId(patient.getId());
                result.setPatientInfo(patient.getFullName());
            } else {
                logger.error("Сколько можно творить неконстстентные данные? Уже FK кинули, а вы все за старое...");
            }
        }
        return result;
    }


}
