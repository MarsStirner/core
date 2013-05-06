package ru.korus.tmis.hs;

import nsi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.referencebook.V005Pol;
import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;
import wsdl.NsiService;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.Holder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.04.13, 16:48 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class ReferenceBook implements ReferenceBookLocal {
    private static final Logger logger = LoggerFactory.getLogger(ReferenceBook.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    private NsiService service;

    @Schedule(minute = "*/3", hour = "*")
    public void loadReferenceBooks() {
        for (V005Pol pol : loadV005Pol()) {
            em.persist(pol);
        }
    }


    public ReferenceBook() {
        service = new NsiService();
        service.setHandlerResolver(new AuthentificationHeaderHandlerResolver());
    }

    /**
     * Загрузка справочника M001MKB10 - международный классификатор болезней
     *
     * @return
     */
    public List<M001Type> loadM001MKB10() {
        final StringBuilder sb = new StringBuilder("loadM001MKB10");
        final Holder<List<M001Type>> list = new Holder<List<M001Type>>();
        service.getNsiServiceSoap().m001(list);
        if (logger.isDebugEnabled()) {
            for (M001Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} M001MKB10 items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника NomeclR - номенклатура работ и услуг в здравоохранении
     *
     * @return
     */
    public List<V001Type> loadV001NomeclR() {
        final StringBuilder sb = new StringBuilder("loadV001NomeclR");
        final Holder<List<V001Type>> list = new Holder<List<V001Type>>();
        service.getNsiServiceSoap().v001(list);
        if (logger.isDebugEnabled()) {
            for (V001Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V001NomeclR items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V002ProfOt - классификатор профилей оказанной медицинской помощи
     *
     * @return
     */
    public List<V002Type> loadV002ProfOt() {
        final StringBuilder sb = new StringBuilder("loadV002ProfOt");
        final Holder<List<V002Type>> list = new Holder<List<V002Type>>();
        service.getNsiServiceSoap().v002(list);
        if (logger.isDebugEnabled()) {
            for (V002Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V002ProfOt items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V003LicUsl - классификатор работ при лицензированной мед.помощи
     *
     * @return
     */
    public List<V003Type> loadV003LicUsl() {
        final StringBuilder sb = new StringBuilder("loadV003LicUsl");
        final Holder<List<V003Type>> list = new Holder<List<V003Type>>();
        service.getNsiServiceSoap().v003(list);
        if (logger.isDebugEnabled()) {
            for (V003Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V003LicUsl items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V004Medspec - классификатор медицинских специальностей
     *
     * @return
     */
    public List<V004Type> loadV004Medspec() {
        final StringBuilder sb = new StringBuilder("loadV004Medspec");
        final Holder<List<V004Type>> list = new Holder<List<V004Type>>();
        service.getNsiServiceSoap().v004(list);
        if (logger.isDebugEnabled()) {
            for (V004Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V004Medspec items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }


    /**
     * Загрузка справочника V005 - классификатор пола застрахованного
     *
     * @return
     */
    public List<V005Pol> loadV005Pol() {
        final StringBuilder sb = new StringBuilder("loadV005Pol");
        final Holder<List<V005Type>> list = new Holder<List<V005Type>>();
        service.getNsiServiceSoap().v005(list);
        final List<V005Pol> polList = new ArrayList<V005Pol>(list.value.size());
        if (logger.isDebugEnabled()) {
            for (V005Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
                polList.add(new V005Pol(type.getIDPOL(), type.getPOLNAME()));
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V005Pol items", list.value.size());
        }
        return polList;
    }

    /**
     * Загрузка справочника V006 - классификатор условий оказания мед.помощи
     *
     * @return
     */
    public List<V006Type> loadV006UslMp() {
        final StringBuilder sb = new StringBuilder("loadV006UslMp");
        final Holder<List<V006Type>> list = new Holder<List<V006Type>>();
        service.getNsiServiceSoap().v006(list);
        if (logger.isDebugEnabled()) {
            for (V006Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V006UslMp items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V007 - номенклатура МО
     *
     * @return
     */
    public List<V007Type> loadV007NomMO() {
        final StringBuilder sb = new StringBuilder("loadV007NomMO");
        final Holder<List<V007Type>> list = new Holder<List<V007Type>>();
        service.getNsiServiceSoap().v007(list);
        if (logger.isDebugEnabled()) {
            for (V007Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V007NomMO items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V008 - классификатор видов мед.помощи
     *
     * @return
     */
    public List<V008Type> loadV008VidMp() {
        final StringBuilder sb = new StringBuilder("loadV008VidMp");
        final Holder<List<V008Type>> list = new Holder<List<V008Type>>();
        service.getNsiServiceSoap().v008(list);
        if (logger.isDebugEnabled()) {
            for (V008Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V008VidMp items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V009 - классификатор результатов обращения за мед.помощью
     *
     * @return
     */
    public List<V009Type> loadV009Rezult() {
        final StringBuilder sb = new StringBuilder("loadV009Rezult");
        final Holder<List<V009Type>> list = new Holder<List<V009Type>>();
        service.getNsiServiceSoap().v009(list);
        if (logger.isDebugEnabled()) {
            for (V009Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V009Rezult items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V010 - классификатор способов оплаты мед.помощи
     *
     * @return
     */
    public List<V010Type> loadV010Sposob() {
        final StringBuilder sb = new StringBuilder("loadV010Sposob");
        final Holder<List<V010Type>> list = new Holder<List<V010Type>>();
        service.getNsiServiceSoap().v010(list);
        if (logger.isDebugEnabled()) {
            for (V010Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V010Sposob items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника V012 - классификатор исходов заболевания
     *
     * @return
     */
    public List<V012Type> loadV012Ishod() {
        final StringBuilder sb = new StringBuilder("loadV012Ishod");
        final Holder<List<V012Type>> list = new Holder<List<V012Type>>();
        service.getNsiServiceSoap().v012(list);
        if (logger.isDebugEnabled()) {
            for (V012Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} V012Ishod items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F001 - справочник территориальных фондов ОМС
     *
     * @return
     */
    public List<F001Type> loadF001Tfoms() {
        final StringBuilder sb = new StringBuilder("loadF001Tfoms");
        final Holder<List<F001Type>> list = new Holder<List<F001Type>>();
        service.getNsiServiceSoap().f001(list);
        if (logger.isDebugEnabled()) {
            for (F001Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F001Tfoms items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F002 - единый реестр мед.организаций,
     * осуществляющих деятельность в сфере обязательного мед.страхования
     *
     * @return
     */
    public List<F002Type> loadF002Smo() {
        final StringBuilder sb = new StringBuilder("loadF002Smo");
        final Holder<List<F002Type>> list = new Holder<List<F002Type>>();
        service.getNsiServiceSoap().f002(list);
        if (logger.isDebugEnabled()) {
            for (F002Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F002Smo items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F003 - единый реестр мед.организаций,
     * осуществляющих деятельность в сфере обязательного мед.страхования
     *
     * @return
     */
    public List<F003Type> loadF003Mo() {
        final StringBuilder sb = new StringBuilder("loadF003Mo");
        final Holder<List<F003Type>> list = new Holder<List<F003Type>>();
        service.getNsiServiceSoap().f003(list);
        if (logger.isDebugEnabled()) {
            for (F003Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F003Mo items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F007 - классификатор ведомственной принадлежности медицинской организации
     *
     * @return
     */
    public List<F007Type> loadF007Vedom() {
        final StringBuilder sb = new StringBuilder("loadF007Vedom");
        final Holder<List<F007Type>> list = new Holder<List<F007Type>>();
        service.getNsiServiceSoap().f007(list);
        if (logger.isDebugEnabled()) {
            for (F007Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F007Vedom items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F008 - классификатор типов документов, подтверждающих факт страхования по ОМС
     *
     * @return
     */
    public List<F008Type> loadF008TipOMS() {
        final StringBuilder sb = new StringBuilder("loadF008TipOMS");
        final Holder<List<F008Type>> list = new Holder<List<F008Type>>();
        service.getNsiServiceSoap().f008(list);
        if (logger.isDebugEnabled()) {
            for (F008Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F008TipOMS items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F009 - классификатор статуса застрахованного лица
     *
     * @return
     */
    public List<F009Type> loadF009StatZL() {
        final StringBuilder sb = new StringBuilder("loadF009StatZL");
        final Holder<List<F009Type>> list = new Holder<List<F009Type>>();
        service.getNsiServiceSoap().f009(list);
        if (logger.isDebugEnabled()) {
            for (F009Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F009StatZL items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F010 - классификатор субъектов РФ
     *
     * @return
     */
    public List<F010Type> loadF010Subekti() {
        final StringBuilder sb = new StringBuilder("loadF010Subekti");
        final Holder<List<F010Type>> list = new Holder<List<F010Type>>();
        service.getNsiServiceSoap().f010(list);
        if (logger.isDebugEnabled()) {
            for (F010Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F010Subekti items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F011 - классификатор типов документов, удостоверяющих личность
     *
     * @return
     */
    public List<F011Type> loadF011Tipdoc() {
        final StringBuilder sb = new StringBuilder("loadF011Tipdoc");
        final Holder<List<F011Type>> list = new Holder<List<F011Type>>();
        service.getNsiServiceSoap().f011(list);
        if (logger.isDebugEnabled()) {
            for (F011Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F011Tipdoc items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника F015 - классификатор федеральных округов
     *
     * @return
     */
    public List<F015Type> loadF015FedOkrug() {
        final StringBuilder sb = new StringBuilder("loadF015FedOkrug");
        final Holder<List<F015Type>> list = new Holder<List<F015Type>>();
        service.getNsiServiceSoap().f015(list);
        if (logger.isDebugEnabled()) {
            for (F015Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} F015FedOkrug items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника O001 - общероссийский классификатор стран мира
     *
     * @return
     */
    public List<O001Type> loadO001OKSM() {
        final StringBuilder sb = new StringBuilder("loadO001OKSM");
        final Holder<List<O001Type>> list = new Holder<List<O001Type>>();
        service.getNsiServiceSoap().o001(list);
        if (logger.isDebugEnabled()) {
            for (O001Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} O001OKSM items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника O002 - классификатор общероссийский административно-территориального деления
     *
     * @return
     */
    public List<O002Type> loadO002Okato() {
        final StringBuilder sb = new StringBuilder("loadO002Okato");
        final Holder<List<O002Type>> list = new Holder<List<O002Type>>();
        service.getNsiServiceSoap().o002(list);
        if (logger.isDebugEnabled()) {
            for (O002Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} O002Okato items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника O003 - классификатор общероссийский видов экономической деятельности
     *
     * @return
     */
    public List<O003Type> loadO003Okved() {
        final StringBuilder sb = new StringBuilder("loadO003Okved");
        final Holder<List<O003Type>> list = new Holder<List<O003Type>>();
        service.getNsiServiceSoap().o003(list);
        if (logger.isDebugEnabled()) {
            for (O003Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} O003Okved items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника O004 - классификатор общероссийский форм собственности
     *
     * @return
     */
    public List<O004Type> loadO004Okfs() {
        final StringBuilder sb = new StringBuilder("loadO004Okfs");
        final Holder<List<O004Type>> list = new Holder<List<O004Type>>();
        service.getNsiServiceSoap().o004(list);
        if (logger.isDebugEnabled()) {
            for (O004Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} O004Okfs items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    /**
     * Загрузка справочника O005 - классификатор общероссийский организационных-правовых форм
     *
     * @return
     */
    public List<O005Type> loadO005Okopf() {
        final StringBuilder sb = new StringBuilder("loadO005Okopf");
        final Holder<List<O005Type>> list = new Holder<List<O005Type>>();
        service.getNsiServiceSoap().o005(list);
        if (logger.isDebugEnabled()) {
            for (O005Type type : list.value) {
                sb.append(getAllFields(type)).append("\n");
            }
            logger.debug(sb.toString());
        } else {
            logger.info("loading {} O005Okopf items", list.value != null ? list.value.size() : 0);
        }
        return list.value;
    }

    private String getAllFields(final Object obj) {
        StringBuilder sb = new StringBuilder("[");
        if (obj != null) {
            Class<?> c = obj.getClass();
            try {
                Field[] chap = c.getDeclaredFields();
                for (Field f : chap) {
                    f.setAccessible(true);
                    final Object o = f.get(obj);
                    if (o != null) {
                        sb.append(o.toString()).append(", ");
                    } else {
                        sb.append("null, ");
                    }
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
        } else {
            sb.append("null");
        }
        sb.append("]");
        return sb.toString();
    }
}