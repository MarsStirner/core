package ru.korus.tmis.drugstore.data

import java.util.Date
import ru.korus.tmis.core.entity.model.Action


abstract class YAbstractActionBasedRelocationDocument(action: Action)
  extends YRelocationDocument {
  protected lazy val begDate = action.getBegDate
  protected lazy val endDate = Option(action.getEndDate).getOrElse(new Date())
  protected lazy val event = action.getEvent
  protected lazy val patient = event.getPatient
  protected lazy val snils = Option(patient.getSnils).getOrElse("НЕТУ")
  protected lazy val cardId = Option(event.getExternalId).getOrElse("НЕТУ")

  protected lazy val formattedDate = timeFormat.format(new Date())
  protected lazy val formattedBegDate = dateFormat.format(begDate)
  protected lazy val formattedEndDate = dateFormat.format(endDate)
  protected lazy val formattedBirthDate = dateFormat.format(patient.getBirthDate)

  protected lazy val gender = patient.getSex match {
    case 1 => ("M")
    case 2 => ("F")
    case _ => ("UN")
  }

  // UUIDs

  import YUUID._

  protected lazy val xUUID = generateRandom()
  protected lazy val patientUUID = generateById(patient)
  protected lazy val eventUUID = generateById(event)
}









