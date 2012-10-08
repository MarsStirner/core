package ru.korus.tmis.util

import reflect.Configuration

object ActionPropertyWrapperInfo extends Configuration {
  apwi =>

  // Internal Ids
  // External Ids are the same
  var Value = StringId("value")
  var ValueId = StringId("valueId")
  var Norm = StringId("norm")
  var Unit = StringId("unit")
  var IsAssignable = StringId("isAssignable")
  var IsAssigned = StringId("isAssigned")

  var Completed = StringId("completed")

  def map = Map(
    Value -> Value,
    ValueId -> ValueId,
    Norm -> Norm,
    Unit -> Unit,
    IsAssignable -> IsAssignable,
    IsAssigned -> IsAssigned
  )

  def apply(key: StringId) = {
    map(key)
  }

  def isSupported(key: StringId) = {
    map.keySet(key)
  }

  def immutable = new {
    val Value = apwi.Value
    val ValueId = apwi.ValueId
    val Norm = apwi.Norm
    val Unit = apwi.Unit
    val IsAssignable = apwi.IsAssignable
    val IsAssigned = apwi.IsAssigned
    val Completed = apwi.Completed

    def apply(key: StringId) = apwi(key)
  }
}
