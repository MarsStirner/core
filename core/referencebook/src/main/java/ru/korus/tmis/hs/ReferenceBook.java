package ru.korus.tmis.hs;

import nsi.*;
import nsi.Kladr;
import nsi.KladrStreet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.referencebook.*;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.dao.*;
import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;
import ru.korus.tmis.util.ConfigManager;
import ru.korus.tmis.utils.DateUtil;
import wsdl.NsiService;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.xml.ws.Holder;
import java.lang.reflect.Field;

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
    /**
     * Кол-во сообщений, которые можно запросить у HS за один сеанс
     */
    public static final long CHUNK_SIZE = 9000;

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

    @EJB
    private KladrDAOLocal kladrdao;

    @EJB
    private KladrStreetDAOLocal kladrStreetdao;

    /**
     * Загрузка справочников по расписанию
     */
    @Override
    @Schedule(minute = "*/4", hour = "*")
    public void loadReferenceBooks() {
        if (ConfigManager.HealthShare().isHealthShareReferenceBook()) {
            logger.info("Start loading reference book...");
            try {
                loadV001();
                loadV002();
                loadV003();
                loadV004();
                loadV005();
                loadV006();
                loadV007();
                loadV008();
                loadV009();
                loadV010();
                loadV012();

                loadF001();
                loadF002();
                loadF003();
                loadF007();
                loadF008();
                loadF009();
                loadF010();
                loadF011();
                loadF015();

                loadM001();

                loadO001();
                loadO002();
                loadO003();
                loadO004();
                loadO005();

//                loadKladr();
//                loadKladrStreet();

                logger.info("Complete loading reference book.");
            } catch (Exception e) {
                logger.warn("Failed loading!!! e: " + e);
            } finally {
                logger.info("Stop loading reference book...");
            }
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
        logger.info("Load M001...");
        try {

            long from = 0;
            long to = CHUNK_SIZE;
            long size = 0;

            final M001 m001 = new M001();
            do {
                int added = 0;

                m001.setFromRow(from);
                m001.setToRow(to);

                final Holder<M001> list = new Holder<M001>(m001);
                service.getNsiServiceSoap().m001(list);

                size = list.value.getRec().size();
                for (M001Type type : list.value.getRec()) {
                    if (logger.isDebugEnabled()) {
                        sb.append(getAllFields(type)).append("\n");
                    }
                    if (!m001dao.isExist(type.getIDDS())) {
                        m001dao.insert(new M001MKB10(
                                type.getIDDS(),
                                type.getDSNAME(),
                                DateUtil.getDate(type.getDATEBEG()),
                                DateUtil.getDate(type.getDATEEND())
                        ));
                        added++;
                    }
                }
                logger.debug(sb.toString());
                logger.info("M001 loading from {} to {}, loaded {} item(s), {} added", from, to, size, added);

                from = to + 1;
                to = to + CHUNK_SIZE;

            } while (size >= CHUNK_SIZE);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника NomeclR - номенклатура работ и услуг в здравоохранении
     *
     * @return
     */
    public void loadV001() {
        final StringBuilder sb = new StringBuilder("loadV001");
        logger.info("Load V001...");
        try {
            int added = 0;
            V001 v001 = new V001();
            v001.setToRow(CHUNK_SIZE);
            final Holder<V001> list = new Holder<V001>(v001);
            service.getNsiServiceSoap().v001(list);

            for (V001Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v001dao.isExist(type.getIDRB())) {
                    v001dao.insert(new V001Nomerclr(
                            type.getIDRB(),
                            type.getRBNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V001 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }


    /**
     * Загрузка справочника V002ProfOt - классификатор профилей оказанной медицинской помощи
     *
     * @return
     */
    public void loadV002() {
        final StringBuilder sb = new StringBuilder("loadV002");
        logger.info("Load V002...");
        try {
            int added = 0;
            final Holder<V002> list = new Holder<V002>();
            service.getNsiServiceSoap().v002(list);

            for (V002Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v002dao.isExist(type.getIDPR())) {
                    v002dao.insert(new V002ProfOt(
                            type.getIDPR(),
                            type.getPRNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V002 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V003LicUsl - классификатор работ при лицензированной мед.помощи
     *
     * @return
     */
    public void loadV003() {
        final StringBuilder sb = new StringBuilder("loadV003");
        logger.info("Load V003...");
        try {
            int added = 0;
            final Holder<V003> list = new Holder<V003>();
            service.getNsiServiceSoap().v003(list);

            for (V003Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v003dao.isExist(type.getIDRL())) {
                    v003dao.insert(new V003LicUsl(
                            type.getIDRL(),
                            type.getLICNAME(),
                            type.getIERARH(),
                            type.getPRIM(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V003 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V004Medspec - классификатор медицинских специальностей
     *
     * @return
     */
    public void loadV004() {
        final StringBuilder sb = new StringBuilder("loadV004");
        logger.info("Load V004...");
        try {
            int added = 0;
            final Holder<V004> list = new Holder<V004>();
            service.getNsiServiceSoap().v004(list);

            for (V004Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v004dao.isExist(type.getIDMSP())) {
                    v004dao.insert(new V004Medspec(
                            type.getIDMSP(),
                            type.getMSPNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V004 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V005 - классификатор пола застрахованного
     *
     * @return
     */
    public void loadV005() {
        final StringBuilder sb = new StringBuilder("loadV005");
        logger.info("Load V005...");
        try {
            int added = 0;
            final Holder<V005> list = new Holder<V005>();
            service.getNsiServiceSoap().v005(list);

            for (V005Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v005dao.isExist(type.getIDPOL())) {
                    v005dao.insert(new V005Pol(
                            type.getIDPOL(),
                            type.getPOLNAME()));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V005 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V006 - классификатор условий оказания мед.помощи
     *
     * @return
     */
    public void loadV006() {
        final StringBuilder sb = new StringBuilder("loadV006");
        logger.info("Load V006...");
        try {
            int added = 0;
            final Holder<V006> list = new Holder<V006>();
            service.getNsiServiceSoap().v006(list);

            for (V006Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v006dao.isExist(type.getIDUMP())) {
                    v006dao.insert(new V006UslMp(
                            type.getIDUMP(),
                            type.getUMPNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V006 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V007 - номенклатура МО
     *
     * @return
     */
    public void loadV007() {
        final StringBuilder sb = new StringBuilder("loadV007");
        logger.info("Load V007...");
        try {
            int added = 0;
            final Holder<V007> list = new Holder<V007>();
            service.getNsiServiceSoap().v007(list);

            for (V007Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v007dao.isExist(type.getIDNMO())) {
                    v007dao.insert(new V007NomMO(
                            type.getIDNMO(),
                            type.getNMONAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V007 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V008 - классификатор видов мед.помощи
     *
     * @return
     */
    public void loadV008() {
        final StringBuilder sb = new StringBuilder("loadV008");
        logger.info("Load V008...");
        try {
            int added = 0;
            final Holder<V008> list = new Holder<V008>();
            service.getNsiServiceSoap().v008(list);

            for (V008Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v008dao.isExist(type.getIDVMP())) {
                    v008dao.insert(new V008VidMp(
                            type.getIDVMP(),
                            type.getVMPNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V008 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V009 - классификатор результатов обращения за мед.помощью
     *
     * @return
     */
    public void loadV009() {
        final StringBuilder sb = new StringBuilder("loadV009");
        logger.info("Load V009...");
        try {
            int added = 0;
            final Holder<V009> list = new Holder<V009>();
            service.getNsiServiceSoap().v009(list);

            for (V009Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v009dao.isExist(type.getIDRMP())) {
                    v009dao.insert(new V009Rezult(
                            type.getIDRMP(),
                            type.getRMPNAME(),
                            type.getIDUSLOV(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V009 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V010 - классификатор способов оплаты мед.помощи
     *
     * @return
     */
    public void loadV010() {
        final StringBuilder sb = new StringBuilder("loadV010");
        logger.info("Load V010...");
        try {
            int added = 0;
            final Holder<V010> list = new Holder<V010>();
            service.getNsiServiceSoap().v010(list);

            for (V010Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v010dao.isExist(type.getIDSP())) {
                    v010dao.insert(new V010Sposob(
                            type.getIDSP(),
                            type.getSPNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V010 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника V012 - классификатор исходов заболевания
     *
     * @return
     */
    public void loadV012() {
        final StringBuilder sb = new StringBuilder("loadV012");
        logger.info("Load V012...");
        try {
            int added = 0;
            final Holder<V012> list = new Holder<V012>();
            service.getNsiServiceSoap().v012(list);

            for (V012Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!v012dao.isExist(type.getIDIZ())) {
                    v012dao.insert(new V012Ishod(
                            type.getIDIZ(),
                            type.getIZNAME(),
                            type.getIDUSLOV(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("V012 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F001 - справочник территориальных фондов ОМС
     *
     * @return
     */
    public void loadF001() {
        final StringBuilder sb = new StringBuilder("loadF001");
        logger.info("Load F001...");
        try {
            int added = 0;
            final Holder<F001> list = new Holder<F001>();
            service.getNsiServiceSoap().f001(list);

            for (F001Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f001dao.isExist(type.getTfKod())) {
                    f001dao.insert(
                            new F001Tfoms(
                                    type.getTfKod(),
                                    type.getTfOkato(),
                                    type.getTfOgrn(),
                                    type.getNameTfp(),
                                    type.getNameTfk(),
                                    type.getIndex(),
                                    type.getAddress(),
                                    type.getFamDir(),
                                    type.getImDir(),
                                    type.getOtDir(),
                                    type.getPhone(),
                                    type.getFax(),
                                    type.getEMail(),
                                    type.getKfTf(),
                                    type.getWww(),
                                    DateUtil.getDate(type.getDEdit()),
                                    DateUtil.getDate(type.getDEnd())
                            ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F001 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }


    /**
     * Загрузка справочника F002 - единый реестр мед.организаций,
     * осуществляющих деятельность в сфере обязательного мед.страхования
     *
     * @return
     */
    public void loadF002() {
        final StringBuilder sb = new StringBuilder("loadF002");
        logger.info("Load F002...");
        int added = 0;
        try {
            final F002 f002 = new F002();
            f002.setFromRow(0L);
            f002.setToRow(CHUNK_SIZE);
            final Holder<F002> holder = new Holder<F002>(f002);
            service.getNsiServiceSoap().f002(holder);

            for (F002Type type : holder.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f002dao.isExist(type.getSmocod())) {   //todo
                    f002dao.insert(new F002Smo(
                            type.getTfOkato(),
                            type.getSmocod(),
                            type.getNamSmop(),
                            type.getNamSmok(),
                            type.getInn(),
                            type.getOgrn(),
                            type.getKPP(),
                            type.getIndexJ(),
                            type.getAddrJ(),
                            type.getIndexF(),
                            type.getAddrF(),
                            type.getOkopf(),
                            type.getFamRuk(),
                            type.getImRuk(),
                            type.getOtRuk(),
                            type.getPhone(),
                            type.getFax(),
                            type.getEMail(),
                            type.getWww(),
                            type.getNDoc(),
                            DateUtil.getDate(type.getDStart()),
                            DateUtil.getDate(type.getDataE()),
                            type.getOrg(),
                            DateUtil.getDate(type.getDBegin()),
                            DateUtil.getDate(type.getDEnd()),
                            type.getNameE(),
                            type.getNalP(),
                            DateUtil.getDate(type.getDUVED()),
                            type.getKolZl(),
                            DateUtil.getDate(type.getDEdit())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F002 loading {} item(s), {} added", holder.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F003 - единый реестр мед.организаций,
     * осуществляющих деятельность в сфере обязательного мед.страхования
     *
     * @return
     */
    public void loadF003() {
        final StringBuilder sb = new StringBuilder("loadF003");
        logger.info("Load F003...");
        try {
            int added = 0;
            final Holder<F003> list = new Holder<F003>();
            service.getNsiServiceSoap().f003(list);

            for (F003Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f003dao.isExist(type.getMcod())) {   //todo
                    f003dao.insert(new F003Mo(
                            type.getTfOkato(),
                            type.getMcod(),
                            0,  //todo
                            type.getNamMop(),
                            type.getNamMok(),
                            type.getInn(),
                            type.getOgrn(),
                            type.getKPP(),
                            type.getIndexJ(),
                            type.getAddrJ(),
                            type.getOkopf(),
                            type.getVedpri(),
                            type.getOrg(),
                            type.getFamRuk(),
                            type.getImRuk(),
                            type.getOtRuk(),
                            type.getPhone(),
                            type.getFax(),
                            type.getEMail(),
                            type.getNDoc(),
                            DateUtil.getDate(type.getDStart()),
                            DateUtil.getDate(type.getDataE()),
                            type.getMp(),
                            type.getWww(),
                            DateUtil.getDate(type.getDBegin()),
                            DateUtil.getDate(type.getDEnd()),
                            type.getNameE(),
                            DateUtil.getDate(type.getDUVED()),
                            DateUtil.getDate(type.getDEdit())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F003 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F007 - классификатор ведомственной принадлежности медицинской организации
     *
     * @return
     */
    public void loadF007() {
        final StringBuilder sb = new StringBuilder("loadF007");
        logger.info("Load F007...");
        try {
            int added = 0;
            final Holder<F007> list = new Holder<F007>();
            service.getNsiServiceSoap().f007(list);

            for (F007Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f007dao.isExist(type.getIDVED())) {
                    f007dao.insert(new F007Vedom(
                            type.getIDVED(),
                            type.getVEDNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F007 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F008 - классификатор типов документов, подтверждающих факт страхования по ОМС
     *
     * @return
     */
    public void loadF008() {
        final StringBuilder sb = new StringBuilder("loadF008");
        logger.info("Load F008...");
        try {
            int added = 0;
            final Holder<F008> list = new Holder<F008>();
            service.getNsiServiceSoap().f008(list);

            for (F008Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f008dao.isExist(type.getIDDOC())) {
                    f008dao.insert(new F008TipOMS(
                            type.getIDDOC(),
                            type.getDOCNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F008 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F009 - классификатор статуса застрахованного лица
     *
     * @return
     */
    public void loadF009() {
        final StringBuilder sb = new StringBuilder("loadF009");
        logger.info("Load F009...");
        try {
            int added = 0;
            final Holder<F009> list = new Holder<F009>();
            service.getNsiServiceSoap().f009(list);

            for (F009Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f009dao.isExist(type.getIDStatus())) {
                    f009dao.insert(new F009StatZL(
                            type.getIDStatus(),
                            type.getStatusName(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F009 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F010 - классификатор субъектов РФ
     *
     * @return
     */
    public void loadF010() {
        final StringBuilder sb = new StringBuilder("loadF010");
        logger.info("Load F010...");
        try {
            int added = 0;
            final Holder<F010> list = new Holder<F010>();
            service.getNsiServiceSoap().f010(list);

            for (F010Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f010dao.isExist(type.getKODTF())) {
                    f010dao.insert(new F010Subekti(
                            type.getKODTF(),
                            type.getKODOKATO(),
                            type.getSUBNAME(),
                            type.getOKRUG(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F010 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F011 - классификатор типов документов, удостоверяющих личность
     *
     * @return
     */
    public void loadF011() {
        final StringBuilder sb = new StringBuilder("loadF011");
        logger.info("Load F011...");
        try {
            int added = 0;
            final Holder<F011> list = new Holder<F011>();
            service.getNsiServiceSoap().f011(list);

            for (F011Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f011dao.isExist(type.getIDDoc())) {
                    f011dao.insert(new F011Tipdoc(
                            type.getIDDoc(),
                            type.getDocName(),
                            type.getDocSer(),
                            type.getDocNum(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F011 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника F015 - классификатор федеральных округов
     *
     * @return
     */
    public void loadF015() {
        final StringBuilder sb = new StringBuilder("loadF015");
        logger.info("Load F015...");
        try {
            int added = 0;
            final Holder<F015> list = new Holder<F015>();
            service.getNsiServiceSoap().f015(list);

            for (F015Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!f015dao.isExist(type.getKODOK())) {
                    f015dao.insert(new F015FedOkr(
                            type.getKODOK(),
                            type.getOKRNAME(),
                            DateUtil.getDate(type.getDATEBEG()),
                            DateUtil.getDate(type.getDATEEND())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("F015 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника O001 - общероссийский классификатор стран мира
     *
     * @return
     */
    public void loadO001() {
        final StringBuilder sb = new StringBuilder("loadO001");
        logger.info("Load O001...");
        try {
            int added = 0;
            final Holder<O001> list = new Holder<O001>();
            service.getNsiServiceSoap().o001(list);

            for (O001Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!o001dao.isExist(type.getKOD())) {
                    o001dao.insert(new O001Oksm(
                            type.getKOD(),
                            type.getNAME11(),
                            type.getNAME12(),
                            type.getALFA2(),
                            type.getALFA3(),
                            type.getNOMDESCR().length() > 255 ? type.getNOMDESCR().substring(0, 255) : type.getNOMDESCR(),
                            type.getNOMAKT(),
                            type.getSTATUS(),
                            DateUtil.getDate(type.getDATAUPD())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("O001 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника O002 - классификатор общероссийский административно-территориального деления
     *
     * @return
     */
    public void loadO002() {
        final StringBuilder sb = new StringBuilder("loadO002");
        logger.info("Load O002...");
        try {
            int added = 0;
            final Holder<O002> list = new Holder<O002>();
            service.getNsiServiceSoap().o002(list);

            for (O002Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!o002dao.isExist(type.getTER())) {
                    o002dao.insert(new O002Okato(
                            type.getTER(),
                            type.getKOD1(),
                            type.getKOD2(),
                            type.getKOD3(),
                            type.getRAZDEL(),
                            type.getNAME1(),
                            type.getCENTRUM(),
                            type.getNOMDESCR(),
                            type.getNOMAKT(),
                            type.getSTATUS(),
                            DateUtil.getDate(type.getDATAUPD())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("O002 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника O003 - классификатор общероссийский видов экономической деятельности
     *
     * @return
     */
    public void loadO003() {
        final StringBuilder sb = new StringBuilder("loadO003");
        logger.info("Load O003...");
        try {
            int added = 0;
            final Holder<O003> list = new Holder<O003>();
            service.getNsiServiceSoap().o003(list);

            for (O003Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!o003dao.isExist(type.getKOD())) {
                    o003dao.insert(new O003Okved(
                            type.getRAZDEL(),
                            type.getPRAZDEL(),
                            type.getKOD(),
                            type.getNAME11(),
                            type.getNAME12(),
                            type.getNOMDESCR().length() > 255 ? type.getNOMDESCR().substring(0, 255) : type.getNOMDESCR(),
                            type.getNOMAKT(),
                            type.getSTATUS(),
                            DateUtil.getDate(type.getDATAUPD())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("O003 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника O004 - классификатор общероссийский форм собственности
     *
     * @return
     */
    public void loadO004() {
        final StringBuilder sb = new StringBuilder("loadO004");
        logger.info("Load O004...");
        try {
            int added = 0;
            final Holder<O004> list = new Holder<O004>();
            service.getNsiServiceSoap().o004(list);

            for (O004Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!o004dao.isExist(type.getKOD())) {
                    o004dao.insert(new O004Okfs(
                            type.getKOD(),
                            type.getNAME1(),
                            type.getALG(),
                            type.getNOMAKT(),
                            type.getSTATUS(),
                            DateUtil.getDate(type.getDATAUPD())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("O004 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника O005 - классификатор общероссийский организационных-правовых форм
     *
     * @return
     */
    public void loadO005() {
        final StringBuilder sb = new StringBuilder("loadO005");
        logger.info("Load O005...");
        try {
            int added = 0;
            final Holder<O005> list = new Holder<O005>();
            service.getNsiServiceSoap().o005(list);

            for (O005Type type : list.value.getRec()) {
                if (logger.isDebugEnabled()) {
                    sb.append(getAllFields(type)).append("\n");
                }
                if (!o005dao.isExist(type.getKOD())) {
                    o005dao.insert(new O005Okopf(
                            type.getKOD(),
                            type.getNAME1(),
                            type.getALG(),
                            type.getNOMAKT(),
                            type.getSTATUS(),
                            DateUtil.getDate(type.getDATAUPD())
                    ));
                    added++;
                }
            }
            logger.debug(sb.toString());
            logger.info("O005 loading {} item(s), {} added", list.value.getRec().size(), added);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника Kladr -
     *
     * @return
     */
    public void loadKladr() {
        final StringBuilder sb = new StringBuilder("loadKladr");
        logger.info("Load Kladr...");
        try {
            long from = 0;
            long to = CHUNK_SIZE;
            long size = 0;
            final Kladr kladr = new Kladr();
            do {
                int added = 0;

                kladr.setFromRow(from);
                kladr.setToRow(to);
                final Holder<Kladr> list = new Holder<Kladr>(kladr);
                service.getNsiServiceSoap().kladr(list);

                size = list.value.getRec().size();
                for (KladrType type : list.value.getRec()) {
                    if (logger.isDebugEnabled()) {
                        sb.append(getAllFields(type)).append("\n");
                    }
                    if (!kladrdao.isExist(type.getCode())) {
                        kladrdao.insert(new KladrRb(
                                type.getCode(),
                                type.getName(),
                                type.getSocr(),
                                type.getIndex(),
                                type.getGninmb(),
                                type.getUno(),
                                type.getOcatd(),
                                type.getStatus()
                        ));
                        added++;
                    } else {
                        logger.info("!!!!!!! duplicate " + type.getCode());
                    }
                }
                logger.debug(sb.toString());
                logger.info("Kladr loading from {} to {}, loaded {} item(s), {} added", from, to, size, added);

                from = to + 1;
                to = to + CHUNK_SIZE;

            } while (size >= CHUNK_SIZE);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
    }

    /**
     * Загрузка справочника KladrStreet -
     *
     * @return
     */
    public void loadKladrStreet() {
        final StringBuilder sb = new StringBuilder("loadKladrStreet");
        logger.info("Load KladrStreet...");
        try {
            long from = 0;
            long to = CHUNK_SIZE;
            long size = 0;
            final KladrStreet kladrStreet = new KladrStreet();
            do {
                int added = 0;

                kladrStreet.setFromRow(from);
                kladrStreet.setToRow(to);
                final Holder<KladrStreet> list = new Holder<KladrStreet>(kladrStreet);
                service.getNsiServiceSoap().kladrStreet(list);

                size = list.value.getRec().size();
                for (KladrStreetType type : list.value.getRec()) {
                    if (logger.isDebugEnabled()) {
                        sb.append(getAllFields(type)).append("\n");
                    }
                    if (!kladrStreetdao.isExist(type.getCode())) {
                        kladrStreetdao.insert(new ru.korus.tmis.core.entity.model.referencebook.KladrStreet(
                                type.getCode(),
                                type.getName(),
                                type.getSocr(),
                                type.getIndex(),
                                type.getGninmb(),
                                type.getUno(),
                                type.getOcatd()
                        ));
                        added++;
                    } else {
                        logger.info("!!!!!!! duplicate " + type.getCode());
                    }
                }
                logger.debug(sb.toString());
                logger.info("KladrStreet loading from {} to {}, loaded {} item(s), {} added", from, to, size, added);

                from = to + 1;
                to = to + CHUNK_SIZE;

            } while (size >= CHUNK_SIZE);
        } catch (Throwable t) {
            logger.error("Exception e: " + t, new Exception(t));
        }
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