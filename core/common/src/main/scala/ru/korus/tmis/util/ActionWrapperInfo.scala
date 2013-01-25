package ru.korus.tmis.util

import reflect.Configuration

object ActionWrapperInfo extends Configuration {
  awi =>

  def Types = ConfigManager.Types

  //////////////////////////////////////////////////////////////////////////////
  // Internal Ids
  var Id = StringId("actionId")
  var Name = StringId("actionName")
  var BeginDate = StringId("actionBeginDate")
  var EndDate = StringId("actionEndDate")
  var Dates = StringId("actionAssignmentHours")
  var ExecutorLastName = StringId("actionExecutorLastName")
  var ExecutorFirstName = StringId("actionExecutorFirstName")
  var ExecutorMiddleName = StringId("actionExecutorMiddleName")
  var ExecutorSpecs = StringId("actionExecutorSpecs")
  var ExecutorPost = StringId("actionExecutorPost")
  var Status = StringId("actionStatus")
  var Urgent = StringId("urgent")
  var Multiplicity = StringId("multiplicity")
  var Finance = StringId("finance")
  var PlannedEndDate = StringId("plannedEndDate")
  var ToOrder = StringId("toOrder")

  // Typed internal Ids
  def TypedId = (Id, Types.Integer)

  def TypedName = (Name, Types.String)

  def TypedBeginDate = (BeginDate, Types.Datetime)

  def TypedEndDate = (EndDate, Types.Datetime)

  def TypedDates = (Dates, Types.Datetime)

  def TypedExecutorLastName = (ExecutorLastName, Types.String)

  def TypedExecutorFirstName = (ExecutorFirstName, Types.String)

  def TypedExecutorMiddleName = (ExecutorMiddleName, Types.String)

  def TypedExecutorSpecs = (ExecutorSpecs, Types.String)

  def TypedExecutorPost = (ExecutorPost, Types.String)

  def TypedStatus = (Status, Types.Integer)

  def TypedUrgent = (Urgent, Types.Boolean)

  def TypedMultiplicity = (Multiplicity, Types.Integer)

  def TypedFinance = (Finance, Types.String)

  def TypedPlannedEndDate = (PlannedEndDate, Types.Datetime)

  def TypedToOrder = (ToOrder, Types.Boolean)

  // External Ids
  var assessmentId = StringId("assessmentId")
  var diagnosticId = StringId("diagnosticId")
  var assessmentName = StringId("assessmentName")
  var diagnosticName = StringId("diagnosticName")
  var treatmentName = StringId("treatmentName")
  var assessmentDate = StringId("assessmentDate")
  var assessmentBeginDate = StringId("assessmentBeginDate")
  var assessmentEndDate = StringId("assessmentEndDate")
  var diagnosticDate = StringId("diagnosticDate")
  var treatmentBeginDate = StringId("treatmentBeginDate")
  var treatmentEndDate = StringId("treatmentEndDate")
  var treatmentDates = StringId("treatmentDates")
  var doctorLastName = StringId("doctorLastName")
  var executorLastName = StringId("executorLastName")
  var doctorFirstName = StringId("doctorFirstName")
  var executorFirstName = StringId("executorFirstName")
  var doctorMiddleName = StringId("doctorMiddleName")
  var executorMiddleName = StringId("executorMiddleName")
  var doctorSpecs = StringId("doctorSpecs")
  var executorPost = StringId("executorPost")
  var actionStatus = StringId("actionStatus")
  var urgent = StringId("urgent")
  var multiplicity = StringId("multiplicity")
  var finance = StringId("finance")
  var plannedEndDate = StringId("plannedEndDate")
  var toOrder = StringId("toOrder")
  //////////////////////////////////////////////////////////////////////////////

  def map = Map(
    assessmentId -> TypedId,
    diagnosticId -> TypedId,

    assessmentName -> TypedName,
    diagnosticName -> TypedName,
    treatmentName -> TypedName,

    assessmentDate -> TypedBeginDate,
    assessmentBeginDate -> TypedBeginDate,
    diagnosticDate -> TypedBeginDate,
    treatmentBeginDate -> TypedBeginDate,

    treatmentEndDate -> TypedEndDate,
    assessmentEndDate -> TypedEndDate,

    treatmentDates -> TypedDates,

    doctorLastName -> TypedExecutorLastName,
    executorLastName -> TypedExecutorLastName,

    doctorFirstName -> TypedExecutorFirstName,
    executorFirstName -> TypedExecutorFirstName,

    doctorMiddleName -> TypedExecutorMiddleName,
    executorMiddleName -> TypedExecutorMiddleName,

    doctorFirstName -> TypedExecutorFirstName,
    executorFirstName -> TypedExecutorFirstName,

    doctorSpecs -> TypedExecutorSpecs,

    executorPost -> TypedExecutorPost,

    actionStatus -> TypedStatus,

    urgent -> TypedUrgent,
    multiplicity -> TypedMultiplicity,
    finance -> TypedFinance,
    plannedEndDate -> TypedPlannedEndDate,
    toOrder -> TypedToOrder
  )

  def apply(key: StringId) = {
    map(key)
  }

  def isSupported(key: String) = {
    map.keySet(StringId(key))
  }

  def immutable = new {
    val Id = awi.Id
    val Name = awi.Name
    val BeginDate = awi.BeginDate
    val EndDate = awi.EndDate
    val Dates = awi.Dates
    val ExecutorLastName = awi.ExecutorLastName
    val ExecutorFirstName = awi.ExecutorFirstName
    val ExecutorMiddleName = awi.ExecutorMiddleName
    val ExecutorSpecs = awi.ExecutorSpecs
    val ExecutorPost = awi.ExecutorPost
    val Status = awi.Status
    val Urgent = awi.Urgent
    val Multiplicity = awi.Multiplicity
    val Finance = awi.Finance
    val PlannedEndDate = awi.PlannedEndDate
    val ToOrder = awi.ToOrder

    def apply(key: StringId) = awi(key)
  }
}
