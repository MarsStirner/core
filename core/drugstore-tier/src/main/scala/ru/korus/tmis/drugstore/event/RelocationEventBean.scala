package ru.korus.tmis.drugstore.event

import ru.korus.tmis.core.database.DbActionTypeBeanLocal
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.event.{CreateActionNotification, Notification}
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.drugstore.data.{YRelocationDocument, YRelocationDocumentBuilder}
import ru.korus.tmis.drugstore.sync.RefResolverBeanLocal
import ru.korus.tmis.drugstore.util.SoapAcknowlegement
import ru.korus.tmis.util.I18nable

import java.util.{Map => JMap, List => JList}
import javax.ejb.{EJB, Asynchronous, Stateless}
import javax.enterprise.event.Observes
import javax.interceptor.Interceptors

import grizzled.slf4j.Logging
import ru.korus.tmis.drugstore.util.{SoapAcknowlegement, Soaping}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class RelocationEventBean
  extends RelocationEventBeanLocal
  with SoapAcknowlegement
  with Logging
  with I18nable {

  @EJB
  var dbActionType: DbActionTypeBeanLocal = _

  @EJB
  var refResolver: RefResolverBeanLocal = _

  val docBuilder = YRelocationDocumentBuilder

  val IN = i18n("db.action.admissionFlatCode")
  val MOVE = i18n("db.action.movingFlatCode")
  val OUT = i18n("db.action.leavingFlatCode")

  val apIN = i18n("db.actionProperty.moving.toName")
  val apOUT = i18n("db.actionProperty.moving.fromName")

  def actionProperty(a: Action,
                     name: String,
                     valueMap: JMap[ActionProperty, JList[APValue]]): Option[APValue] = {
    import collection.JavaConversions._

    lazy val vMap = asScalaMap(valueMap)
    val props = asScalaBuffer(a.getActionProperties)

    props //                              List[ActionProperty]
      .find(_.getType.getName == name) //   Option[ActionProperty]
      .flatMap(vMap.get(_)) //              Option[List[APValue]]
      .flatMap(_.headOption) //             Option[APValue]
  }

  @Asynchronous
  def trigger(@Observes n: Notification): Unit = {
    def yes[A](v: A): Option[A] = Some(v)
    def no(mes: String): Option[Nothing] = {
      error(mes);
      None
    }
    def doNotKnow: Option[Nothing] = None

    import docBuilder._

    def orgStrucToRef = refResolver.getDepartmentRef _

    val doc: Option[YRelocationDocument] =
      n match {
        case can: CreateActionNotification => {
          def act = can.a
          act.getActionType.getFlatCode match {
            // TODO: What to do?
            //  case IN => {
            //   def vl = actionProperty(can.a, "", can.values) match {
            //      case apos: APValueOrgStructure => apos.getValue
            //      case _ => error("Action #" + act.getId.toString + " has no orgStructure property"); null
            //    }
            //    docBuiler.createIncoming(act, vl)
            //  }
            case MOVE => {
              val in = actionProperty(can.a, apIN, can.values)
                .collect {
                case apos: APValueOrgStructure => apos.getValue
              }
              val out = actionProperty(can.a, apOUT, can.values)
                .collect {
                case apos: APValueOrgStructure => apos.getValue
              }
              (in, out) match {
                case (Some(i), Some(o)) =>
                  yes(createMoving(act, orgStrucToRef(o), orgStrucToRef(i)))
                case (Some(i), None) =>
                  yes(createIncoming(act, orgStrucToRef(i)))
                case _ =>
                  no("Action #" + act.getId.toString + " has no orgStructure property " + "\"" + apIN + "\"")
              }
            }
            case OUT => yes(docBuilder.createOutgoing(act))
            case _ => doNotKnow
          }
        }
        case _ => doNotKnow
      }
    doc match {
      case None => {}
      case Some(msg) => sendSoapMessage(msg.toXmlDom,
        msg.rootElement,
        msg.soapAction,
        msg.soapOperation,
        msg.xsiType)
    }
  }
}
