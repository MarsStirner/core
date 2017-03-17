package ru.korus.tmis.scala.util

import ru.korus.tmis.util.reflect.Configuration
import scala.language.reflectiveCalls


object AppealPropertyWrapperInfo extends Configuration {
  apwi =>

  var Id = StringId("id")
  var Name = StringId("name")
  var Start = StringId("start")
  var End = StringId("end")

  var AssignmentDate = StringId("assignmentDate")
  var Number = StringId("number")
  var Directed = StringId("directed")
  var Doctor = StringId("doctor")

  def map = Map(
    Id -> Id,
    Name -> Name,
    Start -> Start,
    End -> End,
    AssignmentDate -> AssignmentDate,
    Number -> Number,
    Directed -> Directed,
    Doctor -> Doctor
  )

  //возможно тут надо наладить связь с мапой аппиал врапер инфо
  def map_property = Map(
    ConfigManager.Messages("appeal.db.actionPropertyType.name.assignmentDate") -> AssignmentDate,
    ConfigManager.Messages("appeal.db.actionPropertyType.name.number") -> Number,
    ConfigManager.Messages("appeal.db.actionPropertyType.name.directed") -> Directed,
    ConfigManager.Messages("appeal.db.actionPropertyType.name.doctor") -> Doctor
  )

  def apply(key: StringId): StringId = {
    map(key)
  }

  def isSupported(key: StringId): Boolean = {
    map.keySet(key)
  }

  def apply_property(key: String): StringId = {
    if (map_property.keySet(key) == true) {
      map_property(key)
    }
    else
      null
  }

  def immutable = new {
    val Id = apwi.Id
    val Name = apwi.Name
    val Start = apwi.Start
    val End = apwi.End
    val AssignmentDate = apwi.AssignmentDate
    val Number = apwi.Number
    val Directed = apwi.Directed
    val Doctor = apwi.Doctor

    def apply(key: StringId): StringId = apwi(key)

    def apply_property(key: String): StringId = apwi.apply_property(key)
  }
}