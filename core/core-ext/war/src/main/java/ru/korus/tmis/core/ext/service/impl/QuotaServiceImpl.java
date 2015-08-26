package ru.korus.tmis.core.ext.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.korus.tmis.core.ext.entities.s11r64.*;
import ru.korus.tmis.core.ext.entities.s11r64.vmp.Client_Quoting;
import ru.korus.tmis.core.ext.entities.s11r64.vmp.QuotaType;
import ru.korus.tmis.core.ext.entities.s11r64.vmp.RbPacientModel;
import ru.korus.tmis.core.ext.entities.s11r64.vmp.VMPQuotaDetail;
import ru.korus.tmis.core.ext.model.AuthData;
import ru.korus.tmis.core.ext.model.IdCodeName;
import ru.korus.tmis.core.ext.model.IdCodeNames;
import ru.korus.tmis.core.ext.model.quote.QuotaData;
import ru.korus.tmis.core.ext.model.quote.QuotaDataContainer;
import ru.korus.tmis.core.ext.repositories.s11r64.ClientQuotaRepository;
import ru.korus.tmis.core.ext.repositories.s11r64.EventRepository;
import ru.korus.tmis.core.ext.repositories.s11r64.MkbRepository;
import ru.korus.tmis.core.ext.repositories.s11r64.VMPQuotaDetailRepository;
import ru.korus.tmis.core.ext.service.QuotaService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 14:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class QuotaServiceImpl implements QuotaService {

    @Autowired
    VMPQuotaDetailRepository vmpQuotaDetailRepository;

    @Autowired
    ClientQuotaRepository clientQuotaRepository;

    @Autowired
    MkbRepository mkbRepository;

    @Autowired
    EventRepository eventRepository;

    @Override
    public IdCodeNames getQuotaType(Integer mkbId, Integer eventId) {
        final Event event = eventId == null ? null : eventRepository.findOne(eventId);
        final Integer financeId = event == null ? null :
                (event.getEventType() == null ? null :
                        (event.getEventType().getRbFinance() == null ? null :
                                event.getEventType().getRbFinance().getId()));
        final Date date = event == null ? null : event.getSetDate();
        List<QuotaType> quotaTypeList = vmpQuotaDetailRepository.findByMkb(mkbId, financeId, date);
        return toIdCodeNames(quotaTypeList);
    }

    private <T extends ReferenceBook> IdCodeNames  toIdCodeNames(List<T> quotaTypeList) {
        IdCodeNames res = new IdCodeNames();
        res.setData(new LinkedList<IdCodeName>());
        for(ReferenceBook quotaType : quotaTypeList) {
            res.getData().add(new IdCodeName(quotaType, true));
        }
        return res;
    }

    @Override
    public IdCodeNames getPatientModel(Integer mkbId, Integer quotaTypeId) {
        List<RbPacientModel> res = mkbId == null ? vmpQuotaDetailRepository.findByQuotaType(quotaTypeId):
                vmpQuotaDetailRepository.findByMkbAndQuotaType(mkbId, quotaTypeId);
        return toIdCodeNames(res);
    }

    @Override
    public IdCodeNames getTreatment(Integer patientModelId) {
        List<RbTreatment> res = vmpQuotaDetailRepository.findTreatmentByPatientModelId(patientModelId);
        return toIdCodeNames(res);
    }

    @Override
    public QuotaDataContainer saveQuota(Integer eventId, QuotaDataContainer quotaData, AuthData authData) {

        Event event = eventRepository.findOne(eventId);
        VMPQuotaDetail vmpQuotaDetail = getVmpQuotaDetail(quotaData.getData().getPacientModel_id(),
                quotaData.getData().getTreatment_id());
        QuotaDataContainer res = new QuotaDataContainer();
        if(event != null && vmpQuotaDetail != null) {
            Client_Quoting clientQuoting = new Client_Quoting();
            Date now = new Date();

            clientQuoting.setCreateDatetime(now);
            clientQuoting.setCreatePerson_id(authData.getUserId());
            updateQuota(quotaData, authData, event, vmpQuotaDetail, clientQuoting, now);

            clientQuotaRepository.saveAndFlush(clientQuoting);
            res = toQuotaData(clientQuoting);
        }
        return res;
    }

    private void updateQuota(QuotaDataContainer quotaData,
                             AuthData authData,
                             Event event,
                             VMPQuotaDetail vmpQuotaDetail,
                             Client_Quoting clientQuoting,
                             Date now) {
        clientQuoting.setModifyPerson_id(authData.getUserId());
        clientQuoting.setModifyDatetime(now);

        clientQuoting.setClient(event.getClient());

        clientQuoting.setStatus(2);

        clientQuoting.setVmpquotaDetail(vmpQuotaDetail);
        clientQuoting.setEvent(event);
        List<Mkb> mkbList = mkbRepository.findByDiagID(quotaData.getData().getMKB());
        if(!mkbList.isEmpty()) {
            clientQuoting.setMkb(mkbList.get(0));
        }

        clientQuoting.setAmount(0);
        clientQuoting.setDateEnd(new Date(0));
        clientQuoting.setDateRegistration(new Date(0));
        clientQuoting.setDeleted(false);
        clientQuoting.setDirectionDate(new Date(0));
        clientQuoting.setRequest(0);
        clientQuoting.setVersion(0);
    }

    @Override
    public QuotaDataContainer getQuota(Integer eventId) {
        List<Client_Quoting> clientQuotingList = clientQuotaRepository.findByEventId(eventId);
        return clientQuotingList.isEmpty() ? new QuotaDataContainer() : toQuotaData(clientQuotingList.get(0));
    }

    @Override
    public QuotaDataContainer getQuotaPrev(Integer eventId) {
        Event event = eventRepository.findOne(eventId);
        QuotaDataContainer res = null;
        if(event != null && event.getClient() != null) {
            List<Client_Quoting> clientQuotingList = clientQuotaRepository.findByClientIdOrderByTime(event.getClient().getId());
            if  (!clientQuotingList.isEmpty()) {
                res = toQuotaData(clientQuotingList.get(0));
            }
        }
        return res == null ?  new QuotaDataContainer() : res ;
    }

    @Override
    public QuotaDataContainer updateQuota(Integer eventId, QuotaDataContainer quotaData, AuthData authData) {
        Event event = eventRepository.findOne(eventId);
        VMPQuotaDetail vmpQuotaDetail = getVmpQuotaDetail(quotaData.getData().getPacientModel_id(),
                quotaData.getData().getTreatment_id());
        QuotaDataContainer res = new QuotaDataContainer();
        Client_Quoting clientQuoting = quotaData.getData().getId() == null ? null :
                clientQuotaRepository.findOne(quotaData.getData().getId());
        if(event != null && vmpQuotaDetail != null && clientQuoting != null) {
            updateQuota(quotaData, authData, event, vmpQuotaDetail, clientQuoting, new Date());
            clientQuotaRepository.saveAndFlush(clientQuoting);
            res = toQuotaData(clientQuoting);
        }
        return res;
    }

    @Override
    public void removeQuota(Integer quotaId) {
        Client_Quoting quota = clientQuotaRepository.findOne(quotaId);
        if(quota != null) {
            quota.setDeleted(true);
            clientQuotaRepository.save(quota);
        }
    }

    private QuotaDataContainer toQuotaData(Client_Quoting clientQuoting) {
        QuotaDataContainer res = new QuotaDataContainer();
        QuotaData quotaData = new QuotaData();
        quotaData.setId(clientQuoting.getId());
        quotaData.setCreateDatetime(clientQuoting.getCreateDatetime());
        quotaData.setCreatePerson_id(clientQuoting.getCreatePerson_id());
        quotaData.setEvent_id(clientQuoting.getEvent().getId());
        Client client = clientQuoting.getEvent().getClient();
        quotaData.setMaster_id(client == null ? null : client.getId());
        Mkb mkb = clientQuoting.getMkb();
        if (mkb != null) {
            quotaData.setMKB(mkb.getDiagID());
            quotaData.setMkbId(mkb.getId());
            quotaData.setDiagName(mkb.getDiagName());
        }

        quotaData.setModifyDatetime(clientQuoting.getModifyDatetime());

        VMPQuotaDetail vmpquotaDetail = clientQuoting.getVmpquotaDetail();
        if(vmpquotaDetail != null) {
            RbPacientModel patientModel = vmpquotaDetail.getRbPacientModel();
            if(patientModel != null) {
                quotaData.setPacientModel_id(patientModel.getId());
                quotaData.setPatientModelName(patientModel.getName());
            }
            QuotaType quotaType = vmpquotaDetail.getQuotaType();
            if(quotaType != null) {
                quotaData.setQuotaType_id(quotaType.getId());
                quotaData.setQuotaTypeName(quotaType.getName());
                quotaData.setQuotaTypeCode(quotaType.getCode());
            }
            RbTreatment rbTreatment = vmpquotaDetail.getRbTreatment();
            if(rbTreatment != null) {
                quotaData.setTreatment_id(rbTreatment == null ? null : rbTreatment.getId());
                quotaData.setTreatmentName(rbTreatment == null ? null : rbTreatment.getName());
            }
        }
        quotaData.setStatus(clientQuoting.getStatus());
        res.setData(quotaData);
        return res;
    }

    private VMPQuotaDetail getVmpQuotaDetail( Integer pacientModelId, Integer treatmentId) {
        List<VMPQuotaDetail> vmpQuotaDetailList = vmpQuotaDetailRepository.findByPatientModelIdAndTreatmentId(pacientModelId, treatmentId);
        return vmpQuotaDetailList.isEmpty() ? null : vmpQuotaDetailList.get(0);
    }


}

