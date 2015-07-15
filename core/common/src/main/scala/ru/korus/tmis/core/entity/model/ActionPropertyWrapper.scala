package ru.korus.tmis.core.entity.model

import ru.korus.tmis.core.data.{TableCol, CommonAttribute}

import grizzled.slf4j.Logging
import java.lang.Boolean
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{StringId, ConfigManager}
import scala.language.reflectiveCalls
import scala.collection.JavaConversions._

class ActionPropertyWrapper(ap: ActionProperty,
                            apValueConverter: (ActionPropertyType, java.util.List[APValue]) => java.util.List[TableCol],
                            apScopeConverter: ActionPropertyType => String)
  extends Logging {


  val APWI = ConfigManager.APWI.immutable

  def get(apvs: List[APValue], names: List[StringId]) = {
    val apt = ap.getType
    val map = (if(apvs.isEmpty) List(null) else apvs).foldLeft(Map[String, String]())((map, apv) => names.foldLeft(map)(
      (map, name) => {
        val id = APWI(name)
        val xmlName = name.toString
        id match {
          case APWI.Value => {
            apv match {
              case null => map
              case _ => {
                if (apt.getTypeName.compareTo("Html") == 0 || apt.getTypeName.compareTo("Text") == 0 || apt.getTypeName.compareTo("Constructor") == 0) {
                  map + (xmlName -> apv.getValue.toString)
                } else if ("Reference".equals(apt.getTypeName)) {
                  map + (xmlName -> apValueConverter(apt, apvs).get(0).values.get(0).getValue.asInstanceOf[String])
                } else {
                  map + (xmlName -> apv.getValueAsString)
                }
              }
            }
          }
          case APWI.ValueId => {
            apv match {
              case null => map
              case _ => map + (xmlName -> apv.getValueAsId)
            }
          }
          case APWI.Norm => {
            this.ap.getNorm match {
              case null | "" => {
                apt match {
                  case null => map
                  case _ => map + (xmlName -> apt.getNorm)
                }
              }
              case norm => map + (xmlName -> norm)
            }
            map + (xmlName -> this.ap.getNorm)
          }
          case APWI.Unit => {
            this.ap.getUnit match {
              case null => map
              case unit => map + (xmlName -> unit.getCode)
            }
          }
          case APWI.IsAssignable => {
            apt match {
              case null => map
              case _ => map + (xmlName -> apt.getIsAssignable.toString)
            }
          }
          case APWI.IsAssigned => {
            map + (xmlName -> this.ap.getIsAssigned.toString)
          }
          case _ => throw new CoreException("Unknown ActionPropertyWrapped field")
        }
      }
    ))

    val typeName = if ("Reference".equals(apt.getTypeName)) "String" else apt.getTypeName
    val scope = apScopeConverter(apt)
    val tableValue: java.util.List[TableCol] =
      if (("Table".equals(typeName) || "Diagnosis".equals(typeName))) {
        apValueConverter(apt, apvs)
      } else {
        null
      }
    val res = new CommonAttribute(ap.getId,
      ap.getVersion.intValue,
      apt.getName,
      apt.getCode,
      typeName,
      apt.isMandatory.toString,
      apt.isReadOnly.toString,
      scope,
      tableValue,
      map)
    res.setTypeId(apt.getId)
    res
  }

  def set(value: CommonAttribute) = {
    value.getPropertiesMap.foreach(p => {
      val (name, value) = p
      StringId(name) match {
        case APWI.IsAssigned => {
          try {
            this.ap.setIsAssigned(Boolean.parseBoolean(value))
          } catch {
            case ex: NumberFormatException => {
              error("Cannot parse <" + value + "> as boolean")
            }
          }
        }
        case APWI.Value => {}
        case _ => {
          debug("APW: Cannot set <" + name + "> to <" + value + ">")
        }
      }
    })
  }
}
