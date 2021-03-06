package ru.korus.tmis.scala.util

import ru.korus.tmis.util.reflect.Configuration
import scala.language.reflectiveCalls

object ActionWrapperInfo extends Configuration {
  awi =>

  def Types = ConfigManager.Types

  //////////////////////////////////////////////////////////////////////////////
  // Internal Ids
  private var Id = StringId("actionId")
  private var Name = StringId("actionName")
  private var BeginDate = StringId("actionBeginDate")
  private var EndDate = StringId("actionEndDate")
  private var Dates = StringId("actionAssignmentHours")
  private var ExecutorLastName = StringId("actionExecutorLastName")
  private var ExecutorFirstName = StringId("actionExecutorFirstName")
  private var ExecutorMiddleName = StringId("actionExecutorMiddleName")
  private var ExecutorSpecs = StringId("actionExecutorSpecs")
  private var ExecutorPost = StringId("actionExecutorPost")
  private var AssignerLastName = StringId("actionAssignerLastName")
  private var AssignerFirstName = StringId("actionAssignerFirstName")
  private var AssignerMiddleName = StringId("actionAssignerMiddleName")
  private var AssignerSpecs = StringId("actionAssignerSpecs")
  private var AssignerPost = StringId("actionAssignerPost")
  var Status = StringId("actionStatus")
  private var Urgent = StringId("urgent")
  var Finance = StringId("finance")
  var PlannedEndDate = StringId("plannedEndDate")
  var AssignerId = StringId("assignerId")
  var ExecutorId = StringId("executorId")
  var PacientInQueueType = StringId("pacientInQueueType")
  //var ToOrder = StringId("toOrder")

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
  def TypedAssignerLastName = (AssignerLastName, Types.String)
  def TypedAssignerFirstName = (AssignerFirstName, Types.String)
  def TypedAssignerMiddleName = (AssignerMiddleName, Types.String)
  def TypedAssignerSpecs = (AssignerSpecs, Types.String)
  def TypedAssignerPost = (AssignerPost, Types.String)
  def TypedStatus = (Status, Types.Integer)
  def TypedUrgent = (Urgent, Types.Boolean)
  def TypedFinance = (Finance, Types.String)
  def TypedPlannedEndDate = (PlannedEndDate, Types.Datetime)
  def TypedPacientInQueueType = (PacientInQueueType, Types.Integer)
  //def TypedToOrder = (ToOrder, Types.Boolean)
  def TypedAssignerId = (AssignerId, Types.Integer)
  def TypedExecutorId = (ExecutorId, Types.Integer)

  // External Ids
  var assessmentId = StringId("assessmentId")
  var diagnosticId = StringId("diagnosticId")
  var assessmentName = StringId("assessmentName")
  var diagnosticName = StringId("diagnosticName")
  private var treatmentName = StringId("treatmentName")
  var assessmentDate = StringId("assessmentDate")
  var assessmentBeginDate = StringId("assessmentBeginDate")
  var assessmentEndDate = StringId("assessmentEndDate")
  var diagnosticDate = StringId("diagnosticDate")
  private var treatmentBeginDate = StringId("treatmentBeginDate")
  private var treatmentEndDate = StringId("treatmentEndDate")
  private var treatmentDates = StringId("treatmentDates")
  var doctorLastName = StringId("doctorLastName")
  var executorLastName = StringId("executorLastName")
  var doctorFirstName = StringId("doctorFirstName")
  var executorFirstName = StringId("executorFirstName")
  var doctorMiddleName = StringId("doctorMiddleName")
  var executorMiddleName = StringId("executorMiddleName")
  var doctorSpecs = StringId("doctorSpecs")
  var executorPost = StringId("executorPost")
  var assignerLastName = StringId("assignerLastName")
  var assignerFirstName = StringId("assignerFirstName")
  var assignerMiddleName = StringId("assignerMiddleName")
  var assignerSpecs = StringId("assignerSpecs")
  var assignerPost = StringId("assignerPost")
  var actionStatus = StringId("actionStatus")
  var urgent = StringId("urgent")
  var finance = StringId("finance")
  var plannedEndDate = StringId("plannedEndDate")
  var assignerId = StringId("assignerId")
  var executorId = StringId("executorId")
  private var pacientInQueueType = StringId("pacientInQueueType")
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

    assignerLastName -> TypedAssignerLastName,
    assignerFirstName -> TypedAssignerFirstName,
    assignerMiddleName -> TypedAssignerMiddleName,
    assignerSpecs -> TypedAssignerSpecs,
    assignerPost -> TypedAssignerPost,

    actionStatus -> TypedStatus,
    pacientInQueueType -> TypedPacientInQueueType,
    urgent -> TypedUrgent,
    finance -> TypedFinance,
    plannedEndDate -> TypedPlannedEndDate,
    assignerId -> TypedAssignerId,
    executorId -> TypedExecutorId
    //toOrder -> TypedToOrder
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
    val AssignerLastName = awi.AssignerLastName
    val AssignerFirstName = awi.AssignerFirstName
    val AssignerMiddleName = awi.AssignerMiddleName
    val AssignerSpecs = awi.AssignerSpecs
    val AssignerPost = awi.AssignerPost
    val Status = awi.Status
    val Urgent = awi.Urgent
    val Finance = awi.Finance
    val PlannedEndDate = awi.PlannedEndDate
    val AssignerId = awi.AssignerId
    val ExecutorId = awi.ExecutorId
    val PacientInQueueType = awi.PacientInQueueType


    def apply(key: StringId) = awi(key)
  }
}
