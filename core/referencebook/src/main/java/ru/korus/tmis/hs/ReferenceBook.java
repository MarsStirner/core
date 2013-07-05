package ru.korus.tmis.hs;

import nsi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.dao.*;
import ru.korus.tmis.entity.*;
import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;
import wsdl.NsiService;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.xml.ws.Holder;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.04.13, 16:48 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class ReferenceBook implements ReferenceBookLocal {
    private static final Logger logger = LoggerFactory.getLogger(ReferenceBook.class);

    private NsiService service;

    @EJB
    private V001DAOLocal v001dao;

    @EJB
    private V002DAOLocal v002dao;

    @EJB
    private V003DAOLocal v003dao;

    @EJB
    private V004DAOLocal v004dao;

    @EJB
    private V005DAOLocal v005dao;

    @EJB
    private V006DAOLocal v006dao;

    @EJB
    private V007DAOLocal v007dao;

    @EJB
    private V008DAOLocal v008dao;

    @EJB
    private V009DAOLocal v009dao;

    @EJB
    private V010DAOLocal v010dao;

    @EJB
    private V012DAOLocal v012dao;

    @EJB
    private F001DAOLocal f001dao;

    @EJB
    private F002DAOLocal f002dao;

    @EJB
    private F003DAOLocal f003dao;

    @EJB
    private F007DAOLocal f007dao;

    @EJB
    private F008DAOLocal f008dao;

    @EJB
    private F009DAOLocal f009dao;

    @EJB
    private F010DAOLocal f010dao;

    @EJB
    private F011DAOLocal f011dao;

    @EJB
    private F015DAOLocal f015dao;

    @EJB
    private M001DAOLocal m001dao;

    @EJB
    private O001DAOLocal o001dao;

    @EJB
    private O002DAOLocal o002dao;

    @EJB
    private O003DAOLocal o003dao;

    @EJB
    private O004DAOLocal o004dao;

    @EJB
    private O005DAOLocal o005dao;

    /**
     * Загрузка справочников по расписанию
     */
    @Override
    @Schedule(minute = "*/1", hour = "*")
    public void loadReferenceBooks() {
        logger.info("Start loading reference book...");
        try {
//            loadV001();
//            loadV002();
//            loadV003();
//            loadV004();
//            loadV005();
//            loadV006();
//            loadV007();
//            loadV008();
//            loadV009();
//            loadV010();
//            loadV012();

//            loadF001();
            loadF002();
//            loadF003();
//            loadF007();
//            loadF008();
//            loadF009();
//            loadF010();
//            loadF011();
//            loadF015();

//            loadM001();

//            loadO001();
//            loadO002();
//            loadO003();
//            loadO004();
//            loadO005();
            logger.info("Stop loading reference book...");
        } catch (Exception e) {
            logger.warn("Failed loading!!! e: " + e);
        }
    }

    public ReferenceBook() {
        try {
            service = new NsiService();
            service.setHandlerResolver(new AuthentificationHeaderHandlerResolver());
        } catch (Exception e) {
            logger.error("Error init ReferenceBook " + e, e);
        }
    }

    /**
     * Загрузка справочника M001MKB10 - международный классификатор болезней
     *
     * @return
     */
    public void loadM001() {
        final StringBuilder sb = new StringBuilder("loadM001");
        int added = 0;
        final Holder<M001> list = new Holder<M001>();
        service.getNsiServiceSoap().m001(list);

        for (M001Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!m001dao.isExist(type.getIDDS())) {
                m001dao.insert(M001MKB10.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("M001 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника NomeclR - номенклатура работ и услуг в здравоохранении
     *
     * @return
     */
    public void loadV001() {
        final StringBuilder sb = new StringBuilder("loadV001");
        int added = 0;
        V001 v001 = new V001();
        v001.setToRow(10L);
        final Holder<V001> list = new Holder<V001>(v001);
        service.getNsiServiceSoap().v001(list);

        for (V001Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v001dao.isExist(type.getIDRB())) {
                v001dao.insert(V001Nomerclr.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V001 loading {} item(s), {} added", list.value.getRec().size(), added);
    }


    /**
     * Загрузка справочника V002ProfOt - классификатор профилей оказанной медицинской помощи
     *
     * @return
     */
    public void loadV002() {
        final StringBuilder sb = new StringBuilder("loadV002");
        int added = 0;
        final Holder<V002> list = new Holder<V002>();
        service.getNsiServiceSoap().v002(list);

        for (V002Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v002dao.isExist(type.getIDPR())) {
                v002dao.insert(V002ProfOt.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V002 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V003LicUsl - классификатор работ при лицензированной мед.помощи
     *
     * @return
     */
    public void loadV003() {
        final StringBuilder sb = new StringBuilder("loadV003");
        int added = 0;
        final Holder<V003> list = new Holder<V003>();
        service.getNsiServiceSoap().v003(list);

        for (V003Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v003dao.isExist(type.getIDRL())) {
                v003dao.insert(V003LicUsl.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V003 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V004Medspec - классификатор медицинских специальностей
     *
     * @return
     */
    public void loadV004() {
        final StringBuilder sb = new StringBuilder("loadV004");
        int added = 0;
        final Holder<V004> list = new Holder<V004>();
        service.getNsiServiceSoap().v004(list);

        for (V004Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v004dao.isExist(type.getIDMSP())) {
                final V004Medspec item = V004Medspec.getInstance(type);
                v004dao.insert(item);
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V004 loading {} item(s), {} added", list.value.getRec().size(), added);
    }


    /**
     * Загрузка справочника V005 - классификатор пола застрахованного
     *
     * @return
     */
    public void loadV005() {
        final StringBuilder sb = new StringBuilder("loadV005");
        int added = 0;
        final Holder<V005> list = new Holder<V005>();
        service.getNsiServiceSoap().v005(list);

        for (V005Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v005dao.isExist(type.getIDPOL())) {
                v005dao.insert(V005Pol.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V005 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V006 - классификатор условий оказания мед.помощи
     *
     * @return
     */
    public void loadV006() {
        final StringBuilder sb = new StringBuilder("loadV006");
        int added = 0;
        final Holder<V006> list = new Holder<V006>();
        service.getNsiServiceSoap().v006(list);

        for (V006Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v006dao.isExist(type.getIDUMP())) {
                v006dao.insert(V006UslMp.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V006 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V007 - номенклатура МО
     *
     * @return
     */
    public void loadV007() {
        final StringBuilder sb = new StringBuilder("loadV007");
        int added = 0;
        final Holder<V007> list = new Holder<V007>();
        service.getNsiServiceSoap().v007(list);

        for (V007Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v007dao.isExist(type.getIDNMO())) {
                v007dao.insert(V007NomMO.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V007 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V008 - классификатор видов мед.помощи
     *
     * @return
     */
    public void loadV008() {
        final StringBuilder sb = new StringBuilder("loadV008");
        int added = 0;
        final Holder<V008> list = new Holder<V008>();
        service.getNsiServiceSoap().v008(list);

        for (V008Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v008dao.isExist(type.getIDVMP())) {
                v008dao.insert(V008VidMp.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V008 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V009 - классификатор результатов обращения за мед.помощью
     *
     * @return
     */
    public void loadV009() {
        final StringBuilder sb = new StringBuilder("loadV009");
        int added = 0;
        final Holder<V009> list = new Holder<V009>();
        service.getNsiServiceSoap().v009(list);

        for (V009Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v009dao.isExist(type.getIDRMP())) {
                v009dao.insert(V009Rezult.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V009 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V010 - классификатор способов оплаты мед.помощи
     *
     * @return
     */
    public void loadV010() {
        final StringBuilder sb = new StringBuilder("loadV010");
        int added = 0;
        final Holder<V010> list = new Holder<V010>();
        service.getNsiServiceSoap().v010(list);

        for (V010Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v010dao.isExist(type.getIDSP())) {
                v010dao.insert(V010Sposob.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V010 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника V012 - классификатор исходов заболевания
     *
     * @return
     */
    public void loadV012() {
        final StringBuilder sb = new StringBuilder("loadV012");
        int added = 0;
        final Holder<V012> list = new Holder<V012>();
        service.getNsiServiceSoap().v012(list);

        for (V012Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!v012dao.isExist(type.getIDIZ())) {
                v012dao.insert(V012Ishod.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("V012 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F001 - справочник территориальных фондов ОМС
     *
     * @return
     */
    public void loadF001() {
        final StringBuilder sb = new StringBuilder("loadF001");
        int added = 0;
        final Holder<F001> list = new Holder<F001>();
        service.getNsiServiceSoap().f001(list);

        for (F001Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f001dao.isExist(type.getTfKod())) {
                f001dao.insert(F001Tfoms.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F001 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F002 - единый реестр мед.организаций,
     * осуществляющих деятельность в сфере обязательного мед.страхования
     *
     * @return
     */
    public void loadF002() {
        final StringBuilder sb = new StringBuilder("loadF002");
        int added = 0;
        final F002 f002 = new F002();
        f002.setFromRow(0L);
        f002.setToRow(1000L);
        final Holder<F002> holder = new Holder<F002>(f002);
        service.getNsiServiceSoap().f002(holder);

        for (F002Type type : holder.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f002dao.isExist(type.getSmocod())) {   //todo
                f002dao.insert(F002Smo.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F002 loading {} item(s), {} added", holder.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F003 - единый реестр мед.организаций,
     * осуществляющих деятельность в сфере обязательного мед.страхования
     *
     * @return
     */
    public void loadF003() {
        final StringBuilder sb = new StringBuilder("loadF003");
        int added = 0;
        final Holder<F003> list = new Holder<F003>();
        service.getNsiServiceSoap().f003(list);

        for (F003Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f003dao.isExist(type.getMcod())) {   //todo
                f003dao.insert(F003Mo.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F003 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F007 - классификатор ведомственной принадлежности медицинской организации
     *
     * @return
     */
    public void loadF007() {
        final StringBuilder sb = new StringBuilder("loadF007");
        int added = 0;
        final Holder<F007> list = new Holder<F007>();
        service.getNsiServiceSoap().f007(list);

        for (F007Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f007dao.isExist(type.getIDVED())) {
                f007dao.insert(F007Vedom.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F007 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F008 - классификатор типов документов, подтверждающих факт страхования по ОМС
     *
     * @return
     */
    public void loadF008() {
        final StringBuilder sb = new StringBuilder("loadF008");
        int added = 0;
        final Holder<F008> list = new Holder<F008>();
        service.getNsiServiceSoap().f008(list);

        for (F008Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f008dao.isExist(type.getIDDOC())) {
                f008dao.insert(F008TipOMS.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F008 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F009 - классификатор статуса застрахованного лица
     *
     * @return
     */
    public void loadF009() {
        final StringBuilder sb = new StringBuilder("loadF009");
        int added = 0;
        final Holder<F009> list = new Holder<F009>();
        service.getNsiServiceSoap().f009(list);

        for (F009Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f009dao.isExist(type.getIDStatus())) {
                f009dao.insert(F009StatZL.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F009 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F010 - классификатор субъектов РФ
     *
     * @return
     */
    public void loadF010() {
        final StringBuilder sb = new StringBuilder("loadF010");
        int added = 0;
        final Holder<F010> list = new Holder<F010>();
        service.getNsiServiceSoap().f010(list);

        for (F010Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f010dao.isExist(type.getKODTF())) {
                f010dao.insert(F010Subekti.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F010 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F011 - классификатор типов документов, удостоверяющих личность
     *
     * @return
     */
    public void loadF011() {
        final StringBuilder sb = new StringBuilder("loadF011");
        int added = 0;
        final Holder<F011> list = new Holder<F011>();
        service.getNsiServiceSoap().f011(list);

        for (F011Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f011dao.isExist(type.getIDDoc())) {
                f011dao.insert(F011Tipdoc.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F011 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника F015 - классификатор федеральных округов
     *
     * @return
     */
    public void loadF015() {
        final StringBuilder sb = new StringBuilder("loadF015");
        int added = 0;
        final Holder<F015> list = new Holder<F015>();
        service.getNsiServiceSoap().f015(list);

        for (F015Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!f015dao.isExist(type.getKODOK())) {
                f015dao.insert(F015FedOkr.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("F015 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника O001 - общероссийский классификатор стран мира
     *
     * @return
     */
    public void loadO001() {
        final StringBuilder sb = new StringBuilder("loadO001");
        int added = 0;
        final Holder<O001> list = new Holder<O001>();
        service.getNsiServiceSoap().o001(list);

        for (O001Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!o001dao.isExist(type.getKOD())) {
                o001dao.insert(O001Oksm.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("O001 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника O002 - классификатор общероссийский административно-территориального деления
     *
     * @return
     */
    public void loadO002() {
        final StringBuilder sb = new StringBuilder("loadO002");
        int added = 0;
        final Holder<O002> list = new Holder<O002>();
        service.getNsiServiceSoap().o002(list);

        for (O002Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!o002dao.isExist(type.getTER())) {
                o002dao.insert(O002Okato.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("O002 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника O003 - классификатор общероссийский видов экономической деятельности
     *
     * @return
     */
    public void loadO003() {
        final StringBuilder sb = new StringBuilder("loadO003");
        int added = 0;
        final Holder<O003> list = new Holder<O003>();
        service.getNsiServiceSoap().o003(list);

        for (O003Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!o003dao.isExist(type.getKOD())) {
                o003dao.insert(O003Okved.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("O003 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника O004 - классификатор общероссийский форм собственности
     *
     * @return
     */
    public void loadO004() {
        final StringBuilder sb = new StringBuilder("loadO004");
        int added = 0;
        final Holder<O004> list = new Holder<O004>();
        service.getNsiServiceSoap().o004(list);

        for (O004Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!o004dao.isExist(type.getKOD())) {
                o004dao.insert(O004Okfs.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("O004 loading {} item(s), {} added", list.value.getRec().size(), added);
    }

    /**
     * Загрузка справочника O005 - классификатор общероссийский организационных-правовых форм
     *
     * @return
     */
    public void loadO005() {
        final StringBuilder sb = new StringBuilder("loadO005");
        int added = 0;
        final Holder<O005> list = new Holder<O005>();
        service.getNsiServiceSoap().o005(list);

        for (O005Type type : list.value.getRec()) {
            if (logger.isDebugEnabled()) {
                sb.append(getAllFields(type)).append("\n");
            }
            if (!o005dao.isExist(type.getKOD())) {
                o005dao.insert(O005Okopf.getInstance(type));
                added++;
            }
        }
        logger.debug(sb.toString());
        logger.info("O005 loading {} item(s), {} added", list.value.getRec().size(), added);
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