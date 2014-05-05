package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ContractTariff;
import ru.korus.tmis.core.entity.model.RbMedicalKind;
import ru.korus.tmis.core.entity.model.RbService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 15:14 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class TariffSpecificBean implements TariffSpecificBeanLocal {

    @EJB(beanName = "DbRbMedicalKindBean")
    private DbRbMedicalKindBeanLocal rbMedicalKindBeanLocal = null;
    private RbMedicalKind policlinicMedicalKind = null;

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    private final String getRbServiceByMedicalKindCodeAndPartsOfInfisCodesQuery =
            "SELECT serv FROM RbService serv WHERE SUBSTRING(serv.infis, 3, 5) IN :infisparts " +
                    "AND serv.medicalKind.code = :kindCode";
    private String specificQuery = "SELECT serv FROM RbService serv WHERE " +
            // совпадение по категории помощи
            "serv.medicalKind.id = :medicalKindId " +
            // совпадение (или 00) типа услуги
            "AND :serviceType IN ('00', SUBSTRING(serv.infis, 1, 2)) " +
            //совпадение (или 00) класса услуги
            "AND :serviceClass IN ('000', SUBSTRING(serv.infis, 3, 3)) " +
            // сщвпадение (или 00) вида услуги\n" +
            "AND :serviceKind IN ('00', SUBSTRING(serv.infis, 6, 2)) " +
            "OR ( " +
            // это загрузка стомтаологических услуг
            ":stomatologyPart = '000000' " +
            " AND :lastCode <> '0'" +
            " AND SUBSTRING(serv.code, 11,2) = CONCAT('_', :lastCode) )";




    @Override
    public List<RbService> getRbServiceByMedicalKindCodeAndPartsOfInfisCodes(Set<String> infisCodesParts, String medicalKindCode) {
        return em.createQuery(getRbServiceByMedicalKindCodeAndPartsOfInfisCodesQuery, RbService.class)
                .setParameter("kindCode", medicalKindCode)
                .setParameter("infisparts", infisCodesParts)
                .getResultList();
    }


    private static final String getServicesForDispanserizationQuery =
            "SELECT s " +
                    "FROM RbService s " +
                    "WHERE SUBSTRING(s.infis, 3, 3) = :ctarFirstPart " +
                    "AND ( SUBSTRING(s.infis, 6, 2) = :ctarSecondPart OR :ctarSecondPart = '00' )" +
                    "AND s.medicalKind = :medicalKind";

    @Override
    public List<RbService> getServicesForDispanserization(final String ctar) {
        return em.createQuery(getServicesForDispanserizationQuery, RbService.class)
                .setParameter("ctarFirstPart", ctar.substring(11,14))
                .setParameter("ctarSecondPart", ctar.substring(14,16))
                .setParameter("medicalKind", getPoliclinicMedicalKind())
                .getResultList();
    }


    private RbMedicalKind getPoliclinicMedicalKind() {
        if(policlinicMedicalKind == null){
           policlinicMedicalKind = rbMedicalKindBeanLocal.getMedicalKindByCode("P");
        }
        return policlinicMedicalKind;
    }

    @Override
    public List<RbService> getRbServiceByMedicalKindCodeAndSpecificServiceInfis(int medicalKindId, String ctar) {
        return em.createQuery(specificQuery, RbService.class)
                .setParameter("medicalKindId", medicalKindId)
                .setParameter("serviceType", ctar.substring(9, 11))
                .setParameter("serviceClass", ctar.substring(11, 14))
                .setParameter("serviceKind", ctar.substring(14, 16))
                .setParameter("stomatologyPart", ctar.substring(9, 15))
                .setParameter("lastCode", String.valueOf(ctar.charAt(15)))
                .getResultList();
    }


    /*
    Тарифы совпадают если совпадают следующие поля:
    1) master_id [Contract]
    2) eventType_id [EventType] либо оба IS NULL
    3) service_id [rbService] либо оба IS NULL
    4) begDate
    5) endDate
    6) sex
    7) age
    8) unit_id [rbMedicalAidUnit]
    9) amount (с точностью до 0.01)
    10) uet (с точностью до 0.01)
    11) price (с точностью до 0.01)
    12) MKB
    13) rbServiceFinance_id [rbServiceFinance] либо оба IS NULL
     */
    private static final String alikeTariffQuery =
            "SELECT ct FROM ContractTariff ct " +
            "WHERE " +
            //совпадает контракт
            "ct.masterId = :contractId " +
            //Совпадает тип обращения или он NULL
            "AND ct.eventTypeId %s " +
            //Совпадает услуга
            "AND ct.service %s "+
            //Совпадает дата начала
            "AND ct.begDate = :begDate "+
            //Совпадает дата окончания
            "AND ct.endDate = :endDate " +
            // Совпадает пол (или wildcard)
            "AND (ct.sex = 0 OR ct.sex = :sex) " +
            // Совпадает возраст
            "AND ct.age = :age " +
            // Совпадает единица измерения
            "AND ct.unit = :unit " +
            //Совпадает количество
            "AND ABS(ct.amount - :amount) < 0.01 " +
            //Совпадает uet
            "AND ABS(ct.uet - :uet) < 0.01 " +
            // Совпадает цена
            "AND ABS(ct.price - :price) < 0.01 " +
            // Совпадает МКБ
            "AND ct.mkb = :mkb " +
            // Совпадает источник финансирования
            "AND ct.serviceFinance = :serviceFinance";



    @Override
    public List<ContractTariff> getAlikeTariff(final ContractTariff tfomsTariff) {
        final String eventTypeStringFormat = tfomsTariff.getEventTypeId() == null ? "IS NULL" : "= :eventType";
        final String serviceStringFormat = tfomsTariff.getService() == null ? "IS NULL" : "= :service";
        final TypedQuery<ContractTariff> query =
                em.createQuery(String.format(alikeTariffQuery, eventTypeStringFormat, serviceStringFormat), ContractTariff.class)
                .setParameter("contractId", tfomsTariff.getMasterId())
                .setParameter("sex", tfomsTariff.getSex())
                .setParameter("age", tfomsTariff.getAge())
                .setParameter("uet", tfomsTariff.getUet())
                .setParameter("amount", tfomsTariff.getAmount())
                .setParameter("mkb", tfomsTariff.getMkb())
                .setParameter("serviceFinance", tfomsTariff.getServiceFinance())
                .setParameter("begDate", tfomsTariff.getBegDate(), TemporalType.DATE)
                .setParameter("endDate", tfomsTariff.getEndDate(), TemporalType.DATE)
                .setParameter("unit", tfomsTariff.getUnit())
                .setParameter("price", tfomsTariff.getPrice());
        if(tfomsTariff.getEventTypeId() != null){
            query.setParameter("eventType", tfomsTariff.getEventTypeId());
        }
        if(tfomsTariff.getService() != null){
            query.setParameter("service", tfomsTariff.getService());
        }
       return  query.getResultList();

    }

    private static final String getServicesForSimpleTariffQuery =
            "SELECT s " +
            "FROM RbService s " +
            "WHERE s.medicalKind = :medicalKind " +
            "AND (" +
                " :ctarMainPart = '0000000' " +
                "OR" +
                "(" +
                    "(" +
                        "SUBSTRING(s.infis,1,2) = :ctarFirstPart " +
                        "OR :ctarFirstPart = '00'" +
                    ")" +
                    "AND SUBSTRING(s.infis,3,3) = :ctarSecondPart " +
                    "AND" +
                    "(" +
                        "SUBSTRING(s.infis,6,2) = :ctarThirdPart  "+
                        "OR :ctarThirdPart = '00'" +
                    ")" +
                ")" +
                "OR" +
                "(" +
                    ":ctarBigPart = '000000' " +
                    "AND :lastSymbol <> '0' " +
                    "AND s.code LIKE '%_%'" +
                    "AND :lastSymbol = SUBSTRING(s.code,12,1) " +
                ")" +
            ") ";

    @Override
    public List<RbService> getServicesForSimpleTariff(final String ctar, final RbMedicalKind medicalKind) {
        return em.createQuery(getServicesForSimpleTariffQuery, RbService.class)
                .setParameter("ctarMainPart", ctar.substring(9, 16))
                .setParameter("ctarFirstPart", ctar.substring(9,11))
                .setParameter("ctarSecondPart", ctar.substring(11,14))
                .setParameter("ctarThirdPart", ctar.substring(14,16))
                .setParameter("ctarBigPart", ctar.substring(9,15))
                .setParameter("lastSymbol", ctar.substring(15,16))
                .setParameter("medicalKind", medicalKind)
                .getResultList();

    }
}
