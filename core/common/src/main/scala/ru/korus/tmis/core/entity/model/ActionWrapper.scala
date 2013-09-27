package ru.korus.tmis.core.entity.model

import ru.korus.tmis.core.data.CommonAttribute
import ru.korus.tmis.util.{StringId, ConfigManager}

import grizzled.slf4j.Logging
import java.lang.Boolean
import java.lang.Short
import java.text.ParseException
import java.util.{Calendar, GregorianCalendar}

import scala.collection.JavaConversions._

class ActionWrapper(a: Action)
  extends Logging {

  val AWI = ConfigManager.AWI.immutable
  val APWI = ConfigManager.APWI.immutable
  val CMDF = ConfigManager.DateFormatter

  def get(name: StringId) = {
    val (getter, aType) = AWI(name)

    val maps = getter match {
      case AWI.Id => {
        List(
          Map(APWI.Value.toString -> this.a.getId.toString)
        )
      }
      case AWI.Name => {
        List(
          Map(APWI.Value.toString -> this.a.getActionType.getName)
        )
      }

      case AWI.BeginDate => {
        this.a.getBegDate match {
          case null => {
            List(
              Map(APWI.Value.toString -> "")
            )
          }
          case date => {
            List(
              Map(APWI.Value.toString -> CMDF.format(date))
            )
          }
        }
      }
      case AWI.EndDate => {
        this.a.getEndDate match {
          case null => {
            List(
              Map(APWI.Value.toString -> "")
            )
          }
          case date => {
            List(
              Map(APWI.Value.toString -> CMDF.format(date))
            )
          }
        }
      }

      case AWI.Dates => {
        this.a.getAssignmentHours.foldLeft(List[Map[String, String]]())(
          (list, hour) => {
            val fixedDatetime = new GregorianCalendar
            fixedDatetime.setTime(hour.getId.getCreateDatetime)
            fixedDatetime.add(Calendar.HOUR_OF_DAY, hour.getId.getHour)

            Map(APWI.Value.toString -> CMDF.format(fixedDatetime.getTime),
              APWI.Completed.toString -> hour.isComplete.toString) :: list
          })
      }

      case AWI.ExecutorLastName => {
        if (this.a.getExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getExecutor.getLastName)
          )
        } else if (this.a.getActionType.getDefaultExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getActionType.getDefaultExecutor.getLastName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.ExecutorFirstName => {
        if (this.a.getExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getExecutor.getFirstName)
          )
        } else if (this.a.getActionType.getDefaultExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getActionType.getDefaultExecutor.getFirstName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.ExecutorMiddleName => {
        if (this.a.getExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getExecutor.getPatrName)
          )
        } else if (this.a.getActionType.getDefaultExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getActionType.getDefaultExecutor.getPatrName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.ExecutorSpecs => {
        if (this.a.getExecutor != null && this.a.getExecutor.getSpeciality != null) {
          List(
            Map(APWI.Value.toString -> this.a.getExecutor.getSpeciality.getName)
          )
        } else if (this.a.getActionType.getDefaultExecutor != null && this.a.getActionType.getDefaultExecutor.getSpeciality != null) {
          List(
            Map(APWI.Value.toString -> this.a.getActionType.getDefaultExecutor.getSpeciality.getName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.ExecutorPost => {
        if (this.a.getExecutor != null && this.a.getExecutor.getPost != null) {
          List(
            Map(APWI.Value.toString -> this.a.getExecutor.getPost.getName,
                APWI.ValueId.toString -> this.a.getExecutor.getId.toString,
                APWI.Code.toString -> this.a.getExecutor.getCode)
          )
        } else if (this.a.getActionType.getDefaultExecutor != null && this.a.getActionType.getDefaultExecutor.getPost != null) {
          List(
            Map(APWI.Value.toString -> this.a.getActionType.getDefaultExecutor.getPost.getName,
                APWI.ValueId.toString -> this.a.getActionType.getDefaultExecutor.getPost.getId.toString,
                APWI.Code.toString -> this.a.getActionType.getDefaultExecutor.getPost.getCode)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "",
                APWI.ValueId.toString -> "",
                APWI.Code.toString -> "")
          )
        }
      }

      case AWI.AssignerLastName => {
        if (this.a.getAssigner != null) {
          List(
            Map(APWI.Value.toString -> this.a.getAssigner.getLastName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.AssignerFirstName => {
        if (this.a.getAssigner != null) {
          List(
            Map(APWI.Value.toString -> this.a.getAssigner.getFirstName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.AssignerMiddleName => {
        if (this.a.getAssigner != null) {
          List(
            Map(APWI.Value.toString -> this.a.getAssigner.getPatrName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.AssignerSpecs => {
        if (this.a.getAssigner != null && this.a.getAssigner.getSpeciality != null) {
          List(
            Map(APWI.Value.toString -> this.a.getAssigner.getSpeciality.getName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }
      case AWI.AssignerPost => {
        if (this.a.getAssigner != null && this.a.getAssigner.getPost != null) {
          List(
            Map(APWI.Value.toString -> this.a.getAssigner.getPost.getName)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }

      case AWI.Status => {
        List(
          Map(APWI.Value.toString -> this.a.getStatus.toString)
        )
      }
      case AWI.Urgent => {
        List(
          Map(APWI.Value.toString -> this.a.getIsUrgent.toString)
        )
      }
      case AWI.Multiplicity => {
        //кратность только для get
        List(
          Map(APWI.Value.toString -> "1")
        )
      }
      case AWI.Finance => {
        if (this.a.getFinanceId != null) {
          List(
            Map(APWI.Value.toString -> this.a.getFinanceId.toString)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "")
          )
        }
      }

      case AWI.PlannedEndDate => {
        this.a.getPlannedEndDate match {
          case null => {
            List(
              Map(APWI.Value.toString -> "")
            )
          }
          case date => {
            List(
              Map(APWI.Value.toString -> CMDF.format(date))
            )
          }
        }
      }
      case AWI.AssignerId => {
        if (this.a.getAssigner != null) {
          List(
            Map(APWI.Value.toString -> this.a.getAssigner.getId.toString)
          )
        } else {
          List(
            Map(APWI.Value.toString -> "-1")
          )
        }
      }
      case AWI.ExecutorId => {
        if (this.a.getExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getExecutor.getId.toString)
          )
        } else if (this.a.getActionType.getDefaultExecutor != null) {
          List(
            Map(APWI.Value.toString -> this.a.getActionType.getDefaultExecutor.getId.toString)
          )
        }
        else {
          List(
            Map(APWI.Value.toString -> "-1")
          )
        }
      }
      /*case AWI.ToOrder => {
       List(
         Map(APWI.Value.toString -> this.a.getToOrder.toString)
       )
     } */

      case _ => {
        debug("Cannot get <" + name + ">")
        List()
      }
    }

    maps.foldLeft(List[CommonAttribute]())(
      (list, map) => {
        new CommonAttribute(
          this.a.getId,
          this.a.getVersion.intValue,
          name.toString,
          aType,
          null,
          map) :: list
      }
    )
  }

  def set(attribute: CommonAttribute): Unit = {
    val name = StringId(attribute.name)

    val value = attribute.properties.get(APWI.Value.toString) match {
      case None | Some("") => {
        error("Cannot set <" + name + "> to NONE")
        return
      }
      case Some(x) => x
    }

    val (getter, aType) = AWI(name)

    getter match {
      case AWI.Dates => {
        val completed = attribute.properties.get(APWI.Completed.toString) match {
          case None => "false"
          case Some(x) => x
        }

        try {
          val ah = new AssignmentHour(this.a.getId.intValue(),
            CMDF.parse(value))
          ah.setComplete(Boolean.parseBoolean(completed))

          this.a.getAssignmentHours.find {
            _ == ah
          } match {
            case None => this.a.addAssignmentHour(ah)
            case Some(eah) => eah.setComplete(ah.isComplete)
          }
        } catch {
          case ex: ParseException => {
            error("Cannot parse <" + value + "> as Date")
          }
        }
      }

      case AWI.BeginDate => {
        try {
          this.a.setBegDate(CMDF.parse(value))
        } catch {
          case ex: NumberFormatException => {
            error("Cannot parse <" + value + "> as Date")
          }
        }
      }

      case AWI.EndDate => {
        try {
          this.a.setEndDate(CMDF.parse(value))
        } catch {
          case ex: NumberFormatException => {
            error("Cannot parse <" + value + "> as Date")
          }
        }
      }

      case AWI.Status => {
        try {
          this.a.setStatus(Short.parseShort(value))
        } catch {
          case ex: NumberFormatException => {
            error("Cannot parse <" + value + "> as short")
          }
        }
      }

      case AWI.Urgent => {
        try {
          this.a.setIsUrgent(Boolean.parseBoolean(value))
        } catch {
          case ex: NumberFormatException => {
            error("Cannot parse <" + value + "> as short")
          }
        }
      }

      case _ => {
        debug("AW: Cannot set <" + name + "> to <" + value + ">")
      }
    }
  }
}
