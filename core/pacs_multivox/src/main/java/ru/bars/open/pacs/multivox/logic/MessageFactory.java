package ru.bars.open.pacs.multivox.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.korus.tmis.core.entity.model.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 22.08.2016, 16:12 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MessageFactory {

    private static final Logger log = LoggerFactory.getLogger("PACS");

    public static Document constructDocument(
            final Action action,
            final Event event,
            final Patient client,
            final Staff doctor,
            final OrgStructure orgStructure,
            final OrgStructure orgStructureDirection,
            final Staff sender,
            final boolean isStationaryEvent
    ) {

        final Document doc = createXMLDocument();
        if (doc == null) {
            return null;
        }
        doc.setXmlStandalone(true);
        // ORM_O01 [root]
        final Element root = doc.createElement("ORM_O01");
        root.setAttribute("xmlns", "urn:hl7-org:v2xml");
        //---- MSH
        final Element msh = doc.createElement("MSH");
        constructMSHSection(doc, msh, action);
        root.appendChild(msh);
        //---- ORM_O01.PATIENT
        final Element orm_patient = doc.createElement("ORM_O01.PATIENT");
        constructPatientSection(doc, orm_patient, action, event, client, doctor, isStationaryEvent);
        root.appendChild(orm_patient);
        //---- ORM_O01.ORCOBRRQDRQ1ODSODTRXONTEDG1OBXNTECTIBLG
        final Element orm_abrakadabra = doc.createElement("ORM_O01.ORCOBRRQDRQ1ODSODTRXONTEDG1OBXNTECTIBLG");
        constructAbracadabraSection(doc, orm_abrakadabra, action, event, client, doctor, orgStructure, orgStructureDirection);
        root.appendChild(orm_abrakadabra);
        doc.appendChild(root);
        return doc;
    }

    private static Document createXMLDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            log.error("Cannot initialize XML Document from Factory", e);
            return null;
        }
    }

    private static void constructPatientSection(
            final Document doc,
            final Element root,
            final Action action,
            final Event event,
            final Patient client,
            final Staff doctor,
            final boolean isStationaryEvent
    ) {
        // PID [Patient Identification Data]
        final Element pid = doc.createElement("PID");
        constructPIDSection(doc, pid, action, event, client, doctor, isStationaryEvent);
        root.appendChild(pid);
        // ORM_O01.PATIENT_VISIT
        final Element patient_visit = doc.createElement("ORM_O01.PATIENT_VISIT");
        constructPatientVisitSection(doc, patient_visit, action, event, client, doctor, isStationaryEvent);
        root.appendChild(patient_visit);
    }

    private static void constructPatientVisitSection(
            final Document doc,
            final Element root,
            final Action action,
            final Event event,
            final Patient client,
            final Staff doctor,
            final boolean isStationaryEvent
    ) {
        // PV1.1 [Segment number]
        final Element pv1_1 = doc.createElement("PV1.1");
        pv1_1.appendChild(doc.createTextNode("1"));
        root.appendChild(pv1_1);
        // PV1.2 [Patient class {"I" - for stationary, "O" - for ambulatory}]
        final Element pv1_2 = doc.createElement("PV1.2");
        pv1_2.appendChild(doc.createTextNode(isStationaryEvent ? "I" : "O"));
        root.appendChild(pv1_2);
        // PV1.4 [Admission Type Should be A for accident, E for emergency, L for labor and delivery and R for routine]    //TODO
        final Element pv1_4 = doc.createElement("PV1.4");
        pv1_4.appendChild(doc.createTextNode("R"));
        root.appendChild(pv1_4);
        // PV1.8 [Doctor info]
        final Element pv1_8 = doc.createElement("PV1.8");
        //---- XCN.1 [Doctor code]
        final Element pv1_8_xсn_1 = doc.createElement("XСN.1");
        pv1_8_xсn_1.appendChild(doc.createTextNode(doctor.getCode()));
        pv1_8.appendChild(pv1_8_xсn_1);
        //---- XCN.2 [LastName]
        final Element pv1_8_xсn_2 = doc.createElement("XСN.2");
        //---- ---- FN.1
        final Element pv1_8_xсn_2_fn_1 = doc.createElement("FN.1");
        pv1_8_xсn_2_fn_1.appendChild(doc.createTextNode(doctor.getLastName()));
        pv1_8_xсn_2.appendChild(pv1_8_xсn_2_fn_1);
        pv1_8.appendChild(pv1_8_xсn_2);
        //---- XCN.3 [FirstName]
        final Element pv1_8_xсn_3 = doc.createElement("XСN.3");
        pv1_8_xсn_3.appendChild(doc.createTextNode(doctor.getFirstName()));
        pv1_8.appendChild(pv1_8_xсn_3);
        //---- XCN.4 [PatrName]
        final Element pv1_8_xсn_4 = doc.createElement("XСN.4");
        pv1_8_xсn_4.appendChild(doc.createTextNode(doctor.getPatrName()));
        pv1_8.appendChild(pv1_8_xсn_4);
        root.appendChild(pv1_8);
    }

    private static void constructPIDSection(
            final Document doc,
            final Element root,
            final Action action,
            final Event event,
            final Patient client,
            final Staff doctor,
            final boolean isStationaryEvent
    ) {
        // PID.1
        final Element pid_1 = doc.createElement("PID.1");
        pid_1.appendChild(doc.createTextNode("1"));
        root.appendChild(pid_1);
        // PID.2 [Patient card]
        final Element pid_2 = doc.createElement("PID.2");
        //---- CX.1 [Current patient card (year after slash)]
        final Element pid_2_cx_1 = doc.createElement("CX.1");
        pid_2_cx_1.appendChild(doc.createTextNode(isStationaryEvent ? event.getExternalId() : String.valueOf(client.getId())));
        pid_2.appendChild(pid_2_cx_1);
        //---- CX.5 [Card Type]
        final Element pid_2_cx_5 = doc.createElement("CX.5");
        pid_2_cx_5.appendChild(doc.createTextNode(isStationaryEvent ? "H" : "A"));
        pid_2.appendChild(pid_2_cx_5);
        root.appendChild(pid_2);
        // PID.3 [Another patient cards]
        final Element pid_3 = doc.createElement("PID.3");
        //---- CX.1 [Internal patient identifier]
        final Element pid_3_cx_1 = doc.createElement("CX.1");
        pid_3_cx_1.appendChild(doc.createTextNode(String.valueOf(client.getId())));
        pid_3.appendChild(pid_3_cx_1);
        //---- CX.5 [Card Type (PI = internal system identifier)]
        final Element pid_3_cx_5 = doc.createElement("CX.5");
        pid_3_cx_5.appendChild(doc.createTextNode("PI"));
        pid_3.appendChild(pid_3_cx_5);
        root.appendChild(pid_3);
        // PID.5 [Patient personal data]
        final Element pid_5 = doc.createElement("PID.5");
        //---- XPN.1 [LastName]
        final Element pid_5_xpn_1 = doc.createElement("XPN.1");
        //---- ---- FN.1
        final Element pid_5_xpn_1_fn_1 = doc.createElement("FN.1");
        pid_5_xpn_1_fn_1.appendChild(doc.createTextNode(client.getLastName()));
        pid_5_xpn_1.appendChild(pid_5_xpn_1_fn_1);
        pid_5.appendChild(pid_5_xpn_1);
        //---- XPN.2 [FirstName]
        final Element pid_5_xpn_2 = doc.createElement("XPN.2");
        pid_5_xpn_2.appendChild(doc.createTextNode(client.getFirstName()));
        pid_5.appendChild(pid_5_xpn_2);
        //---- XPN.3 [PatrName]
        final Element pid_5_xpn_3 = doc.createElement("XPN.3");
        pid_5_xpn_3.appendChild(doc.createTextNode(client.getPatrName()));
        pid_5.appendChild(pid_5_xpn_3);
        root.appendChild(pid_5);
        // PID.7
        final Element pid_7 = doc.createElement("PID.7");
        //---- TS.1 [Patient birth date in format 'yyyyMMdd']
        final Element pid_7_ts_1 = doc.createElement("TS.1");
        pid_7_ts_1.appendChild(doc.createTextNode(new SimpleDateFormat("yyyyMMdd").format(client.getBirthDate())));
        pid_7.appendChild(pid_7_ts_1);
        root.appendChild(pid_7);
        // PID.8 [Patient sex {M, F, U}]
        final Element pid_8 = doc.createElement("PID.8");
        pid_8.appendChild(doc.createTextNode(client.getSex() == 1 ? "M" : client.getSex() == 2 ? "F" : "U"));
        root.appendChild(pid_8);
    }



    private static void constructMSHSection(final Document doc, final Element root, final Action action) {
        // MSH.1
        final Element msh_1 = doc.createElement("MSH.1");
        msh_1.appendChild(doc.createTextNode("|"));
        root.appendChild(msh_1);
        // MSH.2
        final Element msh_2 = doc.createElement("MSH.2");
        msh_2.appendChild(doc.createTextNode("^~\\&"));
        root.appendChild(msh_2);
        // MSH.3
        final Element msh_3 = doc.createElement("MSH.3");
        //---- HD.1 [Sending application]
        final Element msh_3_hd_1 = doc.createElement("HD.1");
        msh_3_hd_1.appendChild(doc.createTextNode("MIS"));
        msh_3.appendChild(msh_3_hd_1);
        root.appendChild(msh_3);
        // MSH.5
        final Element msh_5 = doc.createElement("MSH.5");
        //---- HD.1 [Receiving application]
        final Element msh_5_hd_1 = doc.createElement("HD.1");
        msh_5_hd_1.appendChild(doc.createTextNode("RIS"));
        msh_5.appendChild(msh_5_hd_1);
        root.appendChild(msh_5);
        // MSH.7
        final Element msh_7 = doc.createElement("MSH.7");
        //---- TS.1 [Message DateTime in format 'YYYYMMDDHHMMSS']
        final Element msh_7_ts_1 = doc.createElement("TS.1");
        msh_7_ts_1.appendChild(doc.createTextNode(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
        msh_7.appendChild(msh_7_ts_1);
        root.appendChild(msh_7);
        // MSH.8 [Empty]
        final Element msh_8 = doc.createElement("MSH.8");
        root.appendChild(msh_8);
        // MSH.9
        final Element msh_9 = doc.createElement("MSH.9");
        //----  MSG.1 [MessageType]
        final Element msh_9_msg_1 = doc.createElement("MSG.1");
        msh_9_msg_1.appendChild(doc.createTextNode("ORM"));
        //----  MSG.2 [MessageType]
        final Element msh_9_msg_2 = doc.createElement("MSG.2");
        msh_9_msg_2.appendChild(doc.createTextNode("O01"));
        msh_9.appendChild(msh_9_msg_1);
        msh_9.appendChild(msh_9_msg_2);
        root.appendChild(msh_9);
        // MSH.10  [Unique Message identifier (UUID)]
        final Element msh_10 = doc.createElement("MSH.10");
        msh_10.appendChild(doc.createTextNode(action.getUuid().toString().toUpperCase()));
        root.appendChild(msh_10);
        // MSH.11
        final Element msh_11 = doc.createElement("MSH.11");
        //---- PT.1 [Processing ID (constant)]
        final Element msh_11_pt_1 = doc.createElement("PT.1");
        msh_11_pt_1.appendChild(doc.createTextNode("P"));
        msh_11.appendChild(msh_11_pt_1);
        root.appendChild(msh_11);
        // MSH.12
        final Element msh_12 = doc.createElement("MSH.12");
        //---- VID.1 [Version ID (constant)]
        final Element msh_12_vid_1 = doc.createElement("VID.1");
        msh_12_vid_1.appendChild(doc.createTextNode("2.3.3."));
        msh_12.appendChild(msh_12_vid_1);
        root.appendChild(msh_12);
        // MSH.13 [Empty]
        final Element msh_13 = doc.createElement("MSH.13");
        root.appendChild(msh_13);
        // MSH.14 [Empty]
        final Element msh_14 = doc.createElement("MSH.14");
        root.appendChild(msh_14);
        // MSH.15 [Accept aknowlegment type]
        final Element msh_15 = doc.createElement("MSH.15");
        msh_15.appendChild(doc.createTextNode("NE"));
        root.appendChild(msh_15);
        // MSH.16 [Application aknowlegment type]
        final Element msh_16 = doc.createElement("MSH.16");
        msh_16.appendChild(doc.createTextNode("NE"));
        root.appendChild(msh_16);
    }

    private static void constructAbracadabraSection(
            final Document doc,
            final Element root,
            final Action action,
            final Event event,
            final Patient client,
            final Staff doctor,
            final OrgStructure orgStructure,
            final OrgStructure orgStructureDirection
    ) {
        // ORC
        final Element orc = doc.createElement("ORC");
        constructORCSection(doc, orc, action, doctor, orgStructure);
        root.appendChild(orc);
        // ORM_O01.OBRRQDRQ1ODSODTRXONTEDG1OBXNTE
        final Element obr_with_long_abracadabra = doc.createElement("ORM_O01.OBRRQDRQ1ODSODTRXONTEDG1OBXNTE");
        //---- ORM_O01.OBRRQDRQ1ODSODTRXO
        final Element obr_with_abracadabra = doc.createElement("ORM_O01.OBRRQDRQ1ODSODTRXO");
        //---- ---- OBR
        final Element obr = doc.createElement("OBR");
        constructOBRSection(doc, obr, action, orgStructureDirection);
        obr_with_abracadabra.appendChild(obr);
        obr_with_long_abracadabra.appendChild(obr_with_abracadabra);
        root.appendChild(obr_with_long_abracadabra);
    }

    private static void constructOBRSection(
            final Document doc,
            final Element root,
            final Action action,
            final OrgStructure orgStructureDirection
    ) {
        final ActionType actionType = action.getActionType();
        // http://hl7.cgmpolska.pl/HL7/ch400024.htm
        // ORB.1 [Номер сегмента]
        final Element orb_1 = doc.createElement("ORB.1");
        orb_1.appendChild(doc.createTextNode("1"));
        root.appendChild(orb_1);
        // ORB.4 [Описание исследования]
        final Element orb_4 = doc.createElement("ORB.4");
        //---- CE.1 [Код исследования по справочникам МИС]
        final Element orb_4_ce_1 = doc.createElement("CE.1");
        orb_4_ce_1.appendChild(doc.createTextNode(actionType.getCode()));
        orb_4.appendChild(orb_4_ce_1);
        //---- CE.2 [Название исследования/услуги]
        final Element orb_4_ce_2 = doc.createElement("CE.2");
        orb_4_ce_2.appendChild(doc.createTextNode(actionType.getName()));
        orb_4.appendChild(orb_4_ce_2);
        //---- CE.4 [В поле альтернативного кода исследования передаем модальность.] TODO
        final Element orb_4_ce_4 = doc.createElement("CE.4");
        orb_4_ce_4.appendChild(doc.createTextNode(actionType.getMnemonic()));
        orb_4.appendChild(orb_4_ce_4);
        //---- CE.5 [В поле альтернативного названия исследования передаем тип/категорию исследования] TODO
        final Element orb_4_ce_5 = doc.createElement("CE.5");
        orb_4_ce_5.appendChild(doc.createTextNode(DicomModality.getByCode(actionType.getMnemonic()).getValue()));
        orb_4.appendChild(orb_4_ce_5);
        root.appendChild(orb_4);
        // OBR.17 [Empty]
        final Element orb_17 = doc.createElement("ORB.17");
        root.appendChild(orb_17);
        // OBR.18 [Номер протокола у заказчика]
        final Element orb_18 = doc.createElement("ORB.18");
        //TODO  orb_18.appendChild(doc.createTextNode("P1234-09"));
        root.appendChild(orb_18);
        // OBR.27 [Время и приоритет назначения]
        final Element orb_27 = doc.createElement("ORB.27");
        //---- TQ.4 [Время назначения]
        final Element orb_27_tq_4 = doc.createElement("TQ.4");
        //---- ---- TS.1 [Время назначения 'yyyyMMddHHmmss']
        final Element orb_27_tq_4_ts_1 = doc.createElement("TS.1");
        orb_27_tq_4_ts_1.appendChild(doc.createTextNode(new SimpleDateFormat("yyyyMMddHHmmss").format(action.getPlannedEndDate())));
        orb_27_tq_4.appendChild(orb_27_tq_4_ts_1);
        orb_27.appendChild(orb_27_tq_4);
        //---- TQ.6 [Приоритет R - routine, S - HIGH, A - MED ?????]
        final Element orb_27_tq_6 = doc.createElement("TQ.6");
        orb_27_tq_6.appendChild(doc.createTextNode(action.getIsUrgent() ? "S" : "R"));
        orb_27.appendChild(orb_27_tq_6);
        root.appendChild(orb_27);
        // OBR.34 [Место проведения исследования]
        final Element orb_34 = doc.createElement("ORB.34");
        //---- NDL.5 [Комната]
        final Element orb_34_ndl_5 = doc.createElement("NDL.5");
        orb_34_ndl_5.appendChild(doc.createTextNode(orgStructureDirection.getAddress()));
        orb_34.appendChild(orb_34_ndl_5);
        //---- NDL.7 [Отделение]
        final Element orb_34_ndl_7 = doc.createElement("NDL.7");
        //---- ---- HD.1
        final Element orb_34_ndl_7_hd_1 = doc.createElement("HD.1");
        orb_34_ndl_7_hd_1.appendChild(doc.createTextNode(orgStructureDirection.getCode()));
        orb_34_ndl_7.appendChild(orb_34_ndl_7_hd_1);
        //---- ---- HD.2
        final Element orb_34_ndl_7_hd_2 = doc.createElement("HD.2");
        orb_34_ndl_7_hd_2.appendChild(doc.createTextNode(orgStructureDirection.getName()));
        orb_34_ndl_7.appendChild(orb_34_ndl_7_hd_2);
        orb_34.appendChild(orb_34_ndl_7);
        //---- NDL.10 [Корпус]
        final Element orb_34_ndl_10 = doc.createElement("NDL.10");
        orb_34_ndl_10.appendChild(doc.createTextNode("1"));
        orb_34.appendChild(orb_34_ndl_10);
        //---- NDL.11 [Этаж]
        final Element orb_34_ndl_11 = doc.createElement("NDL.11");
        orb_34_ndl_11.appendChild(doc.createTextNode("1"));
        orb_34.appendChild(orb_34_ndl_11);
        root.appendChild(orb_34);

    }



    private static void constructORCSection(
            final Document doc,
            final Element root,
            final Action action,
            final Staff doctor,
            final OrgStructure orgStructure
    ) {
        // ORC.1 [Order control: NW - new order; XO – change order, CA - canceled; SC - status changed]
        final Element orc_1 = doc.createElement("ORC.1");
        orc_1.appendChild(doc.createTextNode("NW"));
        root.appendChild(orc_1);
        // ORC.2 [Id external (for this code means internal =))]
        final Element orc_2 = doc.createElement("ORC.2");
        //---- EI.1
        final Element orc_2_ei_1 = doc.createElement("EI.1");
        orc_2_ei_1.appendChild(doc.createTextNode(String.valueOf(action.getId())));
        orc_2.appendChild(orc_2_ei_1);
        //---- EI.2
        final Element orc_2_ei_2 = doc.createElement("EI.2");
        orc_2_ei_2.appendChild(doc.createTextNode("MIS"));
        orc_2.appendChild(orc_2_ei_2);
        root.appendChild(orc_2);
        // ORC.5 (SC) [если ORC.1 == SC то сообщаем сдесь о статусе заказа Order status: SC - scheduled; IP - in process; CM - completed; CA - canceled; (PA - patient arrival ?)]
        // ORC.6 [Response flag. Посылающий говорит принимающему о предполагаемом уровне подробности ответов. N - Only the MSA segment is returned (много не требуем)]
        final Element orc_6 = doc.createElement("ORC.6");
        orc_6.appendChild(doc.createTextNode("N"));
        root.appendChild(orc_6);
        // ORC.12 [Направивший врач] ((используем того же что в PV1.8))
        final Element orc_12 = doc.createElement("ORC.12");
        //---- XCN.1 [Doctor code]
        final Element orc_12_xсn_1 = doc.createElement("XСN.1");
        orc_12_xсn_1.appendChild(doc.createTextNode(doctor.getCode()));
        orc_12.appendChild(orc_12_xсn_1);
        //---- XCN.2 [LastName]
        final Element orc_12_xсn_2 = doc.createElement("XСN.2");
        //---- ---- FN.1
        final Element orc_12_xсn_2_fn_1 = doc.createElement("FN.1");
        orc_12_xсn_2_fn_1.appendChild(doc.createTextNode(doctor.getLastName()));
        orc_12_xсn_2.appendChild(orc_12_xсn_2_fn_1);
        orc_12.appendChild(orc_12_xсn_2);
        //---- XCN.3 [FirstName]
        final Element orc_12_xсn_3 = doc.createElement("XСN.3");
        orc_12_xсn_3.appendChild(doc.createTextNode(doctor.getFirstName()));
        orc_12.appendChild(orc_12_xсn_3);
        //---- XCN.4 [PatrName]
        final Element orc_12_xсn_4 = doc.createElement("XСN.4");
        orc_12_xсn_4.appendChild(doc.createTextNode(doctor.getPatrName()));
        orc_12.appendChild(orc_12_xсn_4);
        root.appendChild(orc_12);
        // ORC.13 [Направившее отделение]
        final Element orc_13 = doc.createElement("ORC.13");
        //---- PL.4
        final Element orc_13_pl_4 = doc.createElement("PL.4");
        //---- ---- HD.1
        final Element orc_13_pl_4_hd_1 = doc.createElement("HD.1");
        orc_13_pl_4_hd_1.appendChild(doc.createTextNode(orgStructure.getCode()));
        orc_13_pl_4.appendChild(orc_13_pl_4_hd_1);
        //---- ---- HD.2
        final Element orc_13_pl_4_hd_2 = doc.createElement("HD.2");
        orc_13_pl_4_hd_2.appendChild(doc.createTextNode(orgStructure.getName()));
        orc_13_pl_4.appendChild(orc_13_pl_4_hd_2);
        orc_13.appendChild(orc_13_pl_4);
        root.appendChild(orc_13);
        // ORC.14 [Телефон для обратной связи с направившим врачом]
        final Element orc_14 = doc.createElement("ORC.14");
        //---- XTN.1
        final Element orc_14_xtn_1 = doc.createElement("XTN.1");
        orc_14_xtn_1.appendChild(doc.createTextNode(doctor.getFederalCode()));
        orc_14.appendChild(orc_14_xtn_1);
        root.appendChild(orc_14);
    }

    /**
     * Сериализация w3c документа в строку
     *
     * @param doc   документ на вход
     * @param logId
     * @return строка / null когда нельзя спарсить в строку
     */
    public static String getDocumentAsString(final Document doc, final String logId) {
        if (doc == null || !doc.hasChildNodes()) {
            log.warn("{} w3c.Document is null or empty", logId);
            return null;
        }
        //http://stackoverflow.com/questions/10356258/how-do-i-convert-a-org-w3c-dom-document-object-to-a-string
        final DOMSource domSource = new DOMSource(doc);
        final StringWriter writer = new StringWriter();
        final StreamResult result = new StreamResult(writer);
        final TransformerFactory tf = TransformerFactory.newInstance();
        try {
            final Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException e) {
            log.error("{} Error: while transformation from w3c.Document to String", logId, e);
            return null;
        }
    }


    public enum DicomModality{
        CT("CT","компьютерная томография"),
        MR("MR", "магнитнорезонансная томография"),
        US("US", "ультразвук"),
        XA("XA", "ангиография"),
        MG("MG", "мамография"),
        ES("ES", "эндоскопия"),
        CR("CR", "компьютерная радиография");

        private final String code;
        private final String value;

        DicomModality(String code , String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static DicomModality getByCode(String code){
            for (DicomModality dicomModality : values()) {
                if(code.equalsIgnoreCase(dicomModality.getCode())){
                    return dicomModality;
                }
            }
            return null;
        }


    }

}
