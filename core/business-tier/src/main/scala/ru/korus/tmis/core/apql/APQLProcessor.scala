package ru.korus.tmis.core.apql

import java.util.Date
import javax.ejb.{EJB, Stateless}

import ru.korus.tmis.core.database.common.{DbActionPropertyBeanLocal, DbActionBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConverters._

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/15/14
 * Time: 7:16 PM
 */
@Stateless
class APQLProcessor {

  @EJB private var actionBean: DbActionBeanLocal = _
  @EJB private var actionPropertyBean: DbActionPropertyBeanLocal = _

  def process(expr: IfThenExpr): Option[List[APValue]] = {
    val result = expr.condition match {
      case c: ORCondition =>
        c.expressions.map(processExpression).map {
          case b: BooleanValue => b.value
          case _ => throw new CoreException("Invalid type of expression in IF cause.")
        }.contains(true)

      case c: ANDCondition => !c.expressions.map(processExpression).map {
        case b: BooleanValue => b.value
        case _ => throw new CoreException("Invalid type of expression in IF cause.")
      }.contains(false)
    }

    if (result)
      processExpression(expr.value) match {
        case v: ActionPropertyValues => Option(v.value)
        case _ => None // Unknown situation
      }
    else
      None
  }

  private def processExpression(expr: Expr): ExpressionValue = expr match {
    case method: MethodCall =>
      val args = method.args.map(processExpression)
      method.expr match {
        case Some(x) => processExpression(x).apply(method.name, args)
        case None => GlobalObject(method.name, args)
      }
    case l: NumericLiteral => new IntegerValue(Integer.valueOf(l.value))
    case l: StringLiteral => new StringValue(l.value)
    case l: BooleanLiteral => new BooleanValue(l.value)
  }

  private object GlobalObject extends ExpressionValue {

    val getActionsByEventId = Method( "getActionsByEvent", List(classOf[IntegerValue]),
      (args: List[ExpressionValue]) =>  getActionsByEvent(args.head.asInstanceOf[IntegerValue].value))

    val getActionsByEventIdAndFilterByCode = Method( "getActionsByEvent", List(classOf[IntegerValue], classOf[StringValue]),
      (args: List[ExpressionValue]) =>
        getActionsByEvent(args.head.asInstanceOf[IntegerValue].value, args.last.asInstanceOf[StringValue].value))


    override def methods: List[APQLProcessor.this.GlobalObject.Method] = List(getActionsByEventId, getActionsByEventIdAndFilterByCode)

    private def getActionsByEvent(id: Int): ActionList = {
      new ActionList(actionBean.getActionsByEvent(id).asScala.toList)
    }

    private def getActionsByEvent(id: Int, actionTypeCode: String): ActionList = {
      new ActionList(actionBean.getActionsByEvent(id).asScala.toList.filter(p => p.getActionType.getCode.equals(actionTypeCode)))
    }

  }


  private trait ExpressionValue {
    def apply(method: String, args: List[ExpressionValue]): ExpressionValue = {
      methods.find(m => m.name.equals(method) && m.argsTypes.equals(args.map(_.getClass))).
        getOrElse[Method](noSuchMethod(method, args)).body(args)
    }

    def methods: List[Method]

    def noSuchMethod(method: String, args: List[ExpressionValue]) =
      throw new CoreException("No such method " + method + " with args " + args)

    case class Method (
      name: String,
      argsTypes: List[java.lang.reflect.Type],
      body: (List[ExpressionValue]) => ExpressionValue
    )

  }

  private class StringValue(val value: String) extends ExpressionValue {
    def methods = Nil
  }

  private class IntegerValue(val value: Int) extends ExpressionValue {
    def methods = Nil
  }

  private class BooleanValue(val value: Boolean) extends ExpressionValue {
    def methods = Nil
  }

  private class DateValue(val value: Date) extends ExpressionValue {
    def methods = Nil
  }


  private class ActionList(val value: List[Action]) extends ExpressionValue {

    val first = Method("first", Nil, (args: List[ExpressionValue]) =>  new ActionValue(value.head))

    override def methods: List[Method] = List(first)

  }

  private class ActionValue(val value: Action) extends ExpressionValue {

    val properties = Method("properties", Nil,
      (args: List[ExpressionValue]) =>  new ActionPropertyList(value.getActionProperties.asScala.toList))

    override def methods: List[Method] = List(properties)
  }

  private class ActionPropertyList(val value: List[ActionProperty]) extends ExpressionValue {

    val containsValueOf = Method(
      "containsValueOf",
      List(classOf[StringValue]),
      (args: List[ExpressionValue]) => {
        val prop = value.find(p => p.getType.getCode != null && p.getType.getCode.equals(args.head.asInstanceOf[StringValue].value))
        new BooleanValue(prop.isDefined && !actionPropertyBean.getActionPropertyValue(prop.get).isEmpty)
      })

    val getValueOf = Method(
      "getValueOf",
      List(classOf[StringValue]),
      (args: List[ExpressionValue]) => {
        value.find(p => p.getType.getCode != null && p.getType.getCode.equals(args.head.asInstanceOf[StringValue].value)) match {
          case Some(x) => new ActionPropertyValues(actionPropertyBean.getActionPropertyValue(x).asScala.toList)
          case None => throw new CoreException(this + " hasn't property with code " + args.head.asInstanceOf[StringValue].value)
      }})

    override def methods: List[Method] = List(containsValueOf, getValueOf)
  }

  private class ActionPropertyValues(val value: List[APValue]) extends ExpressionValue {

    val first = Method("first", Nil,
      (args: List[ExpressionValue]) => value match {
      case x :: xs => x match {
        case y: APValueDate => new DateValue(y.getValue)
        case y: APValueString => new StringValue(y.getValue)
        case y: APValueInteger => new IntegerValue(y.getValue)
        case _ => throw new CoreException("Unsupported ActionPropertyValue type - " + x.getClass)
      }
      case Nil => throw new CoreException("Cannot get first element of empty list")
    })

    override def methods: List[Method] = List(first)
  }

  private class Void extends ExpressionValue {
    def methods = Nil
  }

}