package ru.korus.tmis.core.entity.model

import ru.korus.tmis.core.data.CommonAttribute

import grizzled.slf4j.Logging
import java.lang.Boolean
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{StringId, ConfigManager}
import java.util
import scala.language.reflectiveCalls

class ActionPropertyWrapper(ap: ActionProperty, apValueConverter: (ActionPropertyType, String) => java.util.LinkedList[java.util.LinkedList[String]], apScopeConverter: ActionPropertyType => String)
  extends Logging {

  val a = ap.getAction
  val apt = ap.getType

  val APWI = ConfigManager.APWI.immutable


  def get(apv: APValue, names: List[StringId]) = {
    val map = names.foldLeft(Map[String, String]())(
      (map, name) => {
        val id = APWI(name)
        val xmlName = name.toString

        import APWI._

        id match {
          case Value => {
            apv match {
              case null => map
              case _ => {
                if (this.apt.getTypeName.compareTo("Html") == 0 || this.apt.getTypeName.compareTo("Text") == 0 || this.apt.getTypeName.compareTo("Constructor") == 0) {
                  map + (xmlName -> apv.getValue.toString)
                } else if ("Reference".equals(apt.getTypeName)) {
                  map + (xmlName -> apValueConverter(apt, apv.getValueAsString).get(0).get(0))
                } else {
                  map + (xmlName -> apv.getValueAsString)
                }
              }
            }
          }
          case ValueId => {
            apv match {
              case null => map
              case _ => map + (xmlName -> apv.getValueAsId)
            }
          }
          case Norm => {
            this.ap.getNorm match {
              case null | "" => {
                this.apt match {
                  case null => map
                  case _ => map + (xmlName -> this.apt.getNorm)
                }
              }
              case norm => map + (xmlName -> norm)
            }
            map + (xmlName -> this.ap.getNorm)
          }
          case Unit => {
            this.ap.getUnit match {
              case null => map
              case unit => map + (xmlName -> unit.getCode)
            }
          }
          case IsAssignable => {
            this.apt match {
              case null => map
              case _ => map + (xmlName -> this.apt.getIsAssignable.toString)
            }
          }
          case IsAssigned => {
            map + (xmlName -> this.ap.getIsAssigned.toString)
          }
          case _ => throw new CoreException("Unknown ActionPropertyWrapped field")
        }
      })

    val typeName = if ("Reference".equals(apt.getTypeName)) "String" else apt.getTypeName
    val scope = apScopeConverter(apt)
    val tableValue: util.LinkedList[util.LinkedList[String]] =
      if ("Table".equals(typeName) && apv != null && apv.getValueAsString != null && !"".equals(apv.getValueAsString)) {
        apValueConverter(apt, apv.getValueAsString)
      } else {
        null
      }
    new CommonAttribute(ap.getId,
      ap.getVersion.intValue,
      apt.getName,
      apt.getCode,
      typeName,
      apt.isMandatory.toString,
      apt.isReadOnly.toString,
      scope,
      tableValue,
      map)
  }

  def set(value: CommonAttribute) = {
    value.getPropertiesMap.foreach(p => {
      val (name, value) = p

      import APWI._

      StringId(name) match {
        case IsAssigned => {
          try {
            this.ap.setIsAssigned(Boolean.parseBoolean(value))
          } catch {
            case ex: NumberFormatException => {
              error("Cannot parse <" + value + "> as boolean")
            }
          }
        }
        case Value => {}
        case _ => {
          debug("APW: Cannot set <" + name + "> to <" + value + ">")
        }
      }
    })
  }
}
