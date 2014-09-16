package ru.korus.tmis.core.apql

import javax.ejb.{EJB, Stateless}

import ru.korus.tmis.core.database.common.{DbActionPropertyBean, DbActionBeanLocal}
import ru.korus.tmis.core.entity.model.{ActionProperty, Action, APValue}
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConverters._

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/15/14
 * Time: 7:16 PM
 */
@Stateless
class APQLProcessor {

  @EJB var actionBean: DbActionBeanLocal = _
  @EJB var actionPropertyBean: DbActionPropertyBean = _

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
        case _ => ???
      }
    else
      None
  }

  def processExpression(expr: Expr): ExpressionValue = expr match {
    case method: MethodCall =>
      val args = method.args.map(processExpression)
      method.expr match {
        case Some(x) => processExpression(x).apply(method.name, args)
        case None => GlobalObject(method.name, args)
      }
    case l: NumericLiteral => new IntegerValue(Integer.valueOf(l.value))
    case l: StringLiteral => new StringValue(l.value)
  }

  private object GlobalObject extends ExpressionValue {

    override def apply(method: String, args: List[ExpressionValue]): ExpressionValue = {
      method match {
        case "getActionsByEvent" => args.size match {
          case 1 => args.head match {
            case v: IntegerValue => getActionsByEvent(v.value)
            case _ => throw new CoreException("Unknown method " + method + " with args " + args)
          }
          case 2 => (args.head, args.last) match {
            case (x: IntegerValue, y: StringValue) => getActionsByEvent(x.value, y.value)
          }
          case _ => throw new CoreException("Unknown method " + method + " with args " + args)
        }
      }
    }

    private def getActionsByEvent(id: Int): ActionList = {
      new ActionList(actionBean.getActionsByEvent(id).asScala.toList)
    }

    private def getActionsByEvent(id: Int, actionTypeCode: String): ActionList = {
      new ActionList(actionBean.getActionsByEvent(id).asScala.toList.filter(p => p.getActionType.getCode.equals(actionTypeCode)))
    }

  }


  private trait ExpressionValue {
    def apply(method: String, args: List[ExpressionValue]): ExpressionValue

    def noSuchMethod(method: String, args: List[ExpressionValue]) =
      throw new CoreException("No such method " + method + " with args " + args)
  }

  private class StringValue(val value: String) extends ExpressionValue {
    def apply(method: String, args: List[ExpressionValue]) = method match {
      case _ => noSuchMethod(method, args)
    }
  }

  private class IntegerValue(val value: Int) extends ExpressionValue {
    def apply(method: String, args: List[ExpressionValue]) = method match {
      case _ => noSuchMethod(method, args)
    }
  }

  private class BooleanValue(val value: Boolean) extends ExpressionValue {
    def apply(method: String, args: List[ExpressionValue]) = method match {
      case _ => noSuchMethod(method, args)
    }
  }

  private class ActionList(val value: List[Action]) extends ExpressionValue {
    def apply(method: String, args: List[ExpressionValue]) = method match {
      case "first" => args match {
        case Nil => value match {
          case x :: xs => new ActionValue(x)
          case Nil => throw new CoreException("Cannot get first element of empty list")
        }
        case _ => throw new CoreException("Unknown error on call first() at " + this.value + " with args " + args)
      }
      case _ => noSuchMethod(method, args)
    }
  }

  private class ActionValue(val value: Action) extends ExpressionValue {

    override def apply(method: String, args: List[ExpressionValue]): ExpressionValue = method match {
      case "properties" => args match {
        case 0 => new ActionPropertyList(value.getActionProperties.asScala.toList)
        case _ => noSuchMethod(method, args)
      }
      case _ => noSuchMethod(method, args)
    }

  }

  private class ActionPropertyList(val value: List[ActionProperty]) extends ExpressionValue {
    override def apply(method: String, args: List[ExpressionValue]): ExpressionValue = method match {
      case "containsValueOf" => args match {
        case 1 => args.head match {
          case x: StringValue =>
            val prop = value.find(p => p.getType.getCode.equals(x.value))
            new BooleanValue(prop.isDefined && !actionPropertyBean.getActionPropertyValue(prop.get).isEmpty)
          case _ => noSuchMethod(method, args)
        }
        case _ => noSuchMethod(method, args)
      }
      case "getValueOf" => args match {
        case 1 => args.head match {
          case arg: StringValue => value.find(p => p.getType.getCode.equals(arg.value)) match {
            case Some(x) => new ActionPropertyValues(actionPropertyBean.getActionPropertyValue(x).asScala.toList)
            case None => throw new CoreException(this + " hasn't property with code " + arg.value)
          }
          case _ => noSuchMethod(method, args)
        }
        case _ => noSuchMethod(method, args)
      }
      case _ => noSuchMethod(method, args)
    }
  }

  private class ActionPropertyValues(val value: List[APValue]) extends ExpressionValue {

    override def apply(method: String, args: List[ExpressionValue]): Unit = method match {
      case _ => noSuchMethod(method, args)
    }

  }

  private class Void extends ExpressionValue {
    override def apply(method: String, args: List[ExpressionValue]): ExpressionValue = throw new CoreException("Null Pointer Exception")
  }

}