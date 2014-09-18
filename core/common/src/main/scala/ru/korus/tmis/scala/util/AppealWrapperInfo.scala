package ru.korus.tmis.scala.util

import ru.korus.tmis.util.reflect.Configuration
import ru.korus.tmis.scala.util.StringId
import scala.language.reflectiveCalls

object AppealWrapperInfo extends Configuration { awi =>

  def Types = ConfigManager.Types
  var Mess = ConfigManager.Messages

  //////////////////////////////////////////////////////////////////////////////
  // Internal Ids
  var Id = StringId("id")
  var Number = StringId("number")
  var AmbulanceNumber = StringId("ambulanceNumber")    //????????
  var AppealTypeId = StringId("appealType")
  var Urgent = StringId("urgent")
  var AppealDateTime = StringId("rangeAppealDateTime")
  var Patient = StringId("patient")
  var ExecutorLastName = StringId("executorLastName")
  var ExecutorFirstName = StringId("executorFirstName")
  var ExecutorMiddleName = StringId("executorMiddleName")
  var ExecutorSpecs = StringId("executorSpecs")

  var AgreedType = StringId("agreedType")
  var Assignment = StringId("assignment")
  var HospitalizationType= StringId("hospitalizationType")
  var HospitalizationPointType = StringId("hospitalizationPointType")
  var HospitalizationChannelType = StringId("hospitalizationChannelType")
  var DeliveredType = StringId("deliveredType")
  var DeliveredAfterType = StringId("deliveredAfterType")
  var StateType = StringId("stateType")
  var DrugsType = StringId("drugsType")
  var PhysicalParameters = StringId("physicalParameters")
  var BranchType = StringId("branchType")
  var PlaceType = StringId("placeType")
  var Diagnoses = StringId("diagnoses")
  var AgreedDoctor = StringId("agreedDoctor")
  var Relations = StringId("relations")
  var MKB = StringId("mkb")

  //Typed AppealData
  val ApTypedInt = StringId("Integer")
  val ApTypedString = StringId("String")
  val ApTypedBoolean = StringId("Boolean")
  val ApTypedDatePeriodContainer = StringId("DatePeriodContainer")
  val ApTypedIdValueContainer = StringId("IdValueContainer")
  val ApTypedIdNameContainer = StringId("IdNameContainer")
  val ApTypedAppealAssignmentContainer = StringId("AppealAssignmentContainer")
  val ApTypedPhysicalParameterContainer = StringId("PhysicalParameterContainer")
  val ApTypedDiagnosisContainer = StringId("DiagnosisContainer")
  val ApTypedRelationsEntryContainer = StringId("RelationsEntryContainer")
  val ApTypedMKBContainer = StringId("MKBContainer")

  // Typed internal Ids
  def TypedId = (Id, Types.Integer, ApTypedInt)
  def TypedNumber = (Number, Types.String, ApTypedString)
  def TypedAmbulanceNumber = (AmbulanceNumber, Types.String, ApTypedString)
  def TypedAppealType = (AppealTypeId, Types.Object, ApTypedIdNameContainer)
  def TypedUrgent = (Urgent, Types.Boolean, ApTypedBoolean)
  def TypedDate = (AppealDateTime, Types.Object, ApTypedDatePeriodContainer)
  def TypedPatient = (Patient, Types.Object, ApTypedIdValueContainer)
  def TypedExecutorLastName = (ExecutorLastName, Types.String, ApTypedString)
  def TypedExecutorFirstName = (ExecutorFirstName, Types.String, ApTypedString)
  def TypedExecutorMiddleName = (ExecutorMiddleName, Types.String, ApTypedString)
  def TypedExecutorSpecs = (ExecutorSpecs, Types.String, ApTypedString)

  def TypedAgreedType = (AgreedType, Types.Object, ApTypedIdNameContainer)
  def TypedAssignment = (Assignment, Types.Object, ApTypedAppealAssignmentContainer)
  def TypedHospitalizationType = (HospitalizationType, Types.Object, ApTypedIdNameContainer)
  def TypedHospitalizationPointType = (HospitalizationPointType, Types.Object, ApTypedIdNameContainer)
  def TypedHospitalizationChannelType = (HospitalizationChannelType, Types.Object, ApTypedIdNameContainer)
  def TypedDeliveredType = (DeliveredType, Types.Object, ApTypedIdNameContainer)
  def TypedDeliveredAfterType = (DeliveredAfterType, Types.Object, ApTypedIdNameContainer)
  def TypedStateType = (StateType, Types.Object, ApTypedIdNameContainer)
  def TypedDrugsType = (DrugsType, Types.Object, ApTypedIdNameContainer)
  def TypedPhysicalParameters = (PhysicalParameters, Types.Object, ApTypedPhysicalParameterContainer)
  def TypedBranchType = (BranchType, Types.Object, ApTypedIdNameContainer)
  def TypedPlaceType = (PlaceType, Types.Object, ApTypedIdNameContainer)
  def TypedDiagnoses = (Diagnoses, Types.Object, ApTypedDiagnosisContainer)
  def TypedAgreedDoctor = (AgreedDoctor, Types.Object, ApTypedIdValueContainer)
  def TypedRelations = (Relations, Types.Object, ApTypedRelationsEntryContainer)
  def TypedMKB = (MKB, Types.Object, ApTypedMKBContainer)
  //////////////////////////////////////////////////////////////////////////////

  def map = Map(

    Id -> TypedId,
    Number -> TypedNumber,
    AmbulanceNumber -> TypedAmbulanceNumber,
    AppealTypeId -> TypedAppealType,
    Urgent -> TypedUrgent,
    AppealDateTime -> TypedDate,
    Patient -> TypedPatient,
    ExecutorLastName -> TypedExecutorLastName,
    ExecutorFirstName -> TypedExecutorFirstName,
    ExecutorMiddleName -> TypedExecutorMiddleName,
    ExecutorSpecs -> TypedExecutorSpecs,

    AgreedType -> TypedAgreedType,
    Assignment -> TypedAssignment,
    HospitalizationType -> TypedHospitalizationType,
    HospitalizationPointType -> TypedHospitalizationPointType,
    HospitalizationChannelType -> TypedHospitalizationChannelType,
    DeliveredType -> TypedDeliveredType,
    DeliveredAfterType -> TypedDeliveredAfterType,
    StateType -> TypedStateType,
    DrugsType -> TypedDrugsType,
    PhysicalParameters -> TypedPhysicalParameters,
    BranchType -> TypedBranchType,
    PlaceType -> TypedPlaceType,
    Diagnoses -> TypedDiagnoses,
    AgreedDoctor -> TypedAgreedDoctor,
    Relations -> TypedRelations,
    MKB -> TypedMKB
  )

  def map_property = Map(
      AgreedType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.agreedType")),
      Assignment -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.assignmentDate"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.number"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.directed"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.doctor")),
      HospitalizationType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationType")),
      HospitalizationPointType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationPointType")),
      HospitalizationChannelType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationChannelType")),
      DeliveredType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.deliveredType")),
      DeliveredAfterType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.deliveredAfterType")),
      StateType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.stateType")),
      DrugsType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.drugsType")),
      PhysicalParameters -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.height"),
                            ConfigManager.Messages("appeal.db.actionPropertyType.name.weight"),
                            ConfigManager.Messages("appeal.db.actionPropertyType.name.temperature"),
                            ConfigManager.Messages("appeal.db.actionPropertyType.name.bloodPressure.left.ADdiast"),
                            ConfigManager.Messages("appeal.db.actionPropertyType.name.bloodPressure.left.ADsyst"),
                            ConfigManager.Messages("appeal.db.actionPropertyType.name.bloodPressure.right.ADdiast"),
                            ConfigManager.Messages("appeal.db.actionPropertyType.name.bloodPressure.right.ADsyst")),
      BranchType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.brunchType")),
      PlaceType -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.placeType")),
      Diagnoses -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assigment.code"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assignment.description"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.description"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.code"),
                        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.description")),
      AgreedDoctor -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.agreedDoctor")),
      Relations -> Set(),
      MKB -> Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assigment.code"),
        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assignment.description"),
        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code"),
        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.description"),
        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.code"),
        ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.description"))
  )

  def apply(key: StringId) = {
    map(key)
  }

  def isSupported(key: String) = {
    map.keySet(StringId(key))
  }

  def apply_property(key: StringId) = {
    if (map_property.keySet(key)) {
      map_property(key)
    }
    else
      null
  }
  def immutable = new {

    val Id = awi.Id
    val Number = awi.Number
    val AmbulanceNumber = awi.AmbulanceNumber
    val AppealTypeId = awi.AppealTypeId
    val Urgent = awi.Urgent
    val AppealDateTime = awi.AppealDateTime
    val Patient = awi.Patient
    val ExecutorLastName = awi.ExecutorLastName
    val ExecutorFirstName = awi.ExecutorFirstName
    val ExecutorMiddleName = awi.ExecutorMiddleName
    val ExecutorSpecs = awi.ExecutorSpecs

    val AgreedType = awi.AgreedType
    val Assignment = awi.Assignment
    val HospitalizationType = awi.HospitalizationType
    val HospitalizationPointType = awi.HospitalizationPointType
    val HospitalizationChannelType = awi.HospitalizationChannelType
    val DeliveredType = awi.DeliveredType
    val DeliveredAfterType = awi.DeliveredAfterType
    val StateType = awi.StateType
    val DrugsType = awi.DrugsType
    val PhysicalParameters = awi.PhysicalParameters
    val BranchType = awi.BranchType
    val PlaceType = awi.PlaceType
    val Diagnoses = awi.Diagnoses
    val AgreedDoctor = awi.AgreedDoctor
    val Relations = awi.Relations
    val MKB = awi.MKB

    val ApTypedInt = awi.ApTypedInt
    val ApTypedString = awi.ApTypedString
    val ApTypedBoolean = awi.ApTypedBoolean
    val ApTypedDatePeriodContainer = awi.ApTypedDatePeriodContainer
    val ApTypedIdValueContainer = awi.ApTypedIdValueContainer
    val ApTypedIdNameContainer = awi.ApTypedIdNameContainer
    val ApTypedAppealAssignmentContainer = awi.ApTypedAppealAssignmentContainer
    val ApTypedPhysicalParameterContainer = awi.ApTypedPhysicalParameterContainer
    val ApTypedDiagnosisContainer = awi.ApTypedDiagnosisContainer
    val ApTypedRelationsEntryContainer = awi.ApTypedRelationsEntryContainer

    def apply(key: StringId) = awi(key)
    def apply_property(key: StringId) = awi(key)
  }
}