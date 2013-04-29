package ru.korus.tmis.hs;

import nsi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;
import wsdl.NsiService;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.04.13, 16:48 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ReferenceBook {
    private static final Logger logger = LoggerFactory.getLogger(ReferenceBook.class);

    private ObjectFactory factory;
    private NsiService service;


    public ReferenceBook() {
        service = new NsiService();
        service.setHandlerResolver(new AuthentificationHeaderHandlerResolver());
    }

    /**
     * Загрузка справочника V005 - классификатор пола застрахованного
     *
     * @return
     */
    public List<V005Type> loadPol() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<V005Type>> list = new Holder<List<V005Type>>();
        service.getNsiServiceSoap().v005(list);
        for (V005Type type : list.value) {
            sb.append("loadPol[")
                    .append(type.getIDPOL()).append(",")
                    .append(type.getPOLNAME())
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника V007 - классификатор пола застрахованного
     *
     * @return
     */
    public List<V007Type> loadNomMO() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<V007Type>> list = new Holder<List<V007Type>>();
        service.getNsiServiceSoap().v007(list);
        for (V007Type type : list.value) {
            sb.append("loadNomMO[")
                    .append(type.getIDNMO()).append(",")
                    .append(type.getNMONAME()).append(",")
                    .append(type.getDATEBEG()).append(",")
                    .append(type.getDATEEND()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F001 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F001Type> loadTfoms() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F001Type>> list = new Holder<List<F001Type>>();
        service.getNsiServiceSoap().f001(list);
        for (F001Type type : list.value) {
            sb.append("loadTfoms[")
                    .append(type.getTfKod()).append(",")
                    .append(type.getTfOkato()).append(",")
                    .append(type.getTfOgrn()).append(",")
                    .append(type.getNameTfp()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F002 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F002Type> loadSmo() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F002Type>> list = new Holder<List<F002Type>>();
        service.getNsiServiceSoap().f002(list);
        for (F002Type type : list.value) {
            sb.append("loadSmo[")
                    .append(type.getTfOkato()).append(",")
                    .append(type.getSmocod()).append(",")
                    .append(type.getNamSmop()).append(",")
                    .append(type.getNamSmok()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F003 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F003Type> loadMo() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F003Type>> list = new Holder<List<F003Type>>();
        service.getNsiServiceSoap().f003(list);
        for (F003Type type : list.value) {
            sb.append("loadMo[")
                    .append(type.getTfOkato()).append(",")
                    .append(type.getMcod()).append(",")
                    .append(type.getNamMop()).append(",")
                    .append(type.getNamMok()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F007 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F007Type> loadVedom() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F007Type>> list = new Holder<List<F007Type>>();
        service.getNsiServiceSoap().f007(list);
        for (F007Type type : list.value) {
            sb.append("loadVedom[")
                    .append(type.getIDVED()).append(",")
                    .append(type.getVEDNAME()).append(",")
                    .append(type.getDATEBEG()).append(",")
                    .append(type.getDATEEND()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F008 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F008Type> loadTipOMS() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F008Type>> list = new Holder<List<F008Type>>();
        service.getNsiServiceSoap().f008(list);
        for (F008Type type : list.value) {
            sb.append("loadTipOMS[")
                    .append(type.getIDDOC()).append(",")
                    .append(type.getDOCNAME()).append(",")
                    .append(type.getDATEBEG()).append(",")
                    .append(type.getDATEEND()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F009 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F009Type> loadStatZL() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F009Type>> list = new Holder<List<F009Type>>();
        service.getNsiServiceSoap().f009(list);
        for (F009Type type : list.value) {
            sb.append("loadStatZL[")
                    .append(type.getIDStatus()).append(",")
                    .append(type.getStatusName()).append(",")
                    .append(type.getDATEBEG()).append(",")
                    .append(type.getDATEEND()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F011 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F011Type> loadTipdoc() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F011Type>> list = new Holder<List<F011Type>>();
        service.getNsiServiceSoap().f011(list);
        for (F011Type type : list.value) {
            sb.append("loadTipdoc[")
                    .append(type.getIDDoc()).append(",")
                    .append(type.getDocName()).append(",")
                    .append(type.getDocSer()).append(",")
                    .append(type.getDocNum()).append(",")
                    .append(type.getDATEBEG()).append(",")
                    .append(type.getDATEEND()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника F015 - классификатор пола застрахованного
     *
     * @return
     */
    public List<F015Type> loadFedOkrug() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<F015Type>> list = new Holder<List<F015Type>>();
        service.getNsiServiceSoap().f015(list);
        for (F015Type type : list.value) {
            sb.append("loadFedOkrug[")
                    .append(type.getKODOK()).append(",")
                    .append(type.getOKRNAME()).append(",")
                    .append(type.getDATEBEG()).append(",")
                    .append(type.getDATEEND()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }

    /**
     * Загрузка справочника O001 - классификатор пола застрахованного
     *
     * @return
     */
    public List<O001Type> loadOKSM() {
        final StringBuilder sb = new StringBuilder();
        final Holder<List<O001Type>> list = new Holder<List<O001Type>>();
        service.getNsiServiceSoap().o001(list);
        for (O001Type type : list.value) {
            sb.append("loadOKSM[")
                    .append(type.getKOD()).append(",")
                    .append(type.getNAME11()).append(",")
                    .append(type.getNAME12()).append(",")
                    .append(type.getALFA2()).append(",")
                    .append(type.getALFA3()).append(",")
                    .append(type.getNOMDESCR()).append(",")
                    .append(type.getNOMAKT()).append(",")
                    .append(type.getSTATUS()).append(",")
                    .append(type.getDATAUPD()).append(",")
                    .append("] \n");
        }
        logger.info(sb.toString());
        return list.value;
    }


}
