package ru.korus.tmis.core.values

/**
 * Данный класс описывает переменные для работы с инфекционным контролем.
 *
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/29/14
 * Time: 5:50 PM
 */
object InfectionControl {

  // Список ActionType.code документов, содержащих раздел "Инфекционный контроль"
  val documents = Set("1_2_18", "1_2_19", "1_2_20", "1_2_22", "1_2_23")

  // Список ActionPropertyType.code свойств, описывающих лекарственную терапию
  val EmpiricTherapyProperties = Set(
    "infectEmpiricName_1", "infectEmpiricBeginDate_1", "infectEmpiricEndDate_1",
    "infectEmpiricName_2", "infectEmpiricBeginDate_2", "infectEmpiricEndDate_2",
    "infectEmpiricName_3", "infectEmpiricBeginDate_3", "infectEmpiricEndDate_3",
    "infectEmpiricName_4", "infectEmpiricBeginDate_4", "infectEmpiricEndDate_4",
    "infectEmpiricName_5", "infectEmpiricBeginDate_5", "infectEmpiricEndDate_5",
    "infectEmpiricName_6", "infectEmpiricBeginDate_6", "infectEmpiricEndDate_6",
    "infectEmpiricName_7", "infectEmpiricBeginDate_7", "infectEmpiricEndDate_7",
    "infectEmpiricName_8", "infectEmpiricBeginDate_8", "infectEmpiricEndDate_8"
  )
  val TelicTherapyProperties = Set(
    "infectTelicName_1", "infectTelicBeginDate_1", "infectTelicEndDate_1",
    "infectTelicName_2", "infectTelicBeginDate_2", "infectTelicEndDate_2",
    "infectTelicName_3", "infectTelicBeginDate_3", "infectTelicEndDate_3",
    "infectTelicName_4", "infectTelicBeginDate_4", "infectTelicEndDate_4",
    "infectTelicName_5", "infectTelicBeginDate_5", "infectTelicEndDate_5",
    "infectTelicName_6", "infectTelicBeginDate_6", "infectTelicEndDate_6",
    "infectTelicName_7", "infectTelicBeginDate_7", "infectTelicEndDate_7",
    "infectTelicName_8", "infectTelicBeginDate_8", "infectTelicEndDate_8"
  )
  val ProphylaxisTherapyProperties = Set(
    "infectProphylaxisName_1", "infectProphylaxisBeginDate_1", "infectProphylaxisEndDate_1",
    "infectProphylaxisName_2", "infectProphylaxisBeginDate_2", "infectProphylaxisEndDate_2",
    "infectProphylaxisName_3", "infectProphylaxisBeginDate_3", "infectProphylaxisEndDate_3",
    "infectProphylaxisName_4", "infectProphylaxisBeginDate_4", "infectProphylaxisEndDate_4",
    "infectProphylaxisName_5", "infectProphylaxisBeginDate_5", "infectProphylaxisEndDate_5",
    "infectProphylaxisName_6", "infectProphylaxisBeginDate_6", "infectProphylaxisEndDate_6",
    "infectProphylaxisName_7", "infectProphylaxisBeginDate_7", "infectProphylaxisEndDate_7",
    "infectProphylaxisName_8", "infectProphylaxisBeginDate_8", "infectProphylaxisEndDate_8"
  )
  val drugTherapyProperties: Set[String] = EmpiricTherapyProperties ++ TelicTherapyProperties ++ ProphylaxisTherapyProperties

  val infectDrugNamePrefix = "infectDrugName"
  val infectDrugBeginDatePrefix = "infectDrugBeginDate"
  val infectDrugEndDatePrefix = "infectDrugEndDate"
  val infectTherapyTypePrefix = "infectTherapyType"

  // Список префиксов наименований не локальных инфекций
  val infectPrefixes = Set("infectFever", "infectBacteremia", "infectSepsis","infectSepticShok")

  // Список префиксов локальных инфекций
  val localInfectPrefixes = Set(
    "infectCephalopyosis",    "infectMeningitis",                 "infectMeningoencephalitis",    "infectEncephalitis",
    "infectConjunctivitis",   "infectPeriorbital",                "infectBlepharitis",            "infectChorioretinitis",
    "infectSkinLight",        "infectSkinHard",                   "infectMucositis12",            "infectMucositis34",
    "infectEsophagitis",      "infectGingivitis",                 "infectRhinitis",               "infectTonsillitis",
    "infectOtitis",           "infectDefeatPPN",                  "infectBronchitis",             "infectInterstitialPneumonia",
    "infectLobarPneumonia",   "infectPleurisy",                   "infectPericarditis",           "infectMioardit",
    "infectEndocarditis",     "infectGastritis",                  "infectPancreatitis",           "infectCholecystitis",
    "infecThepatitis",        "infectGepatolienalnyCandidiasis",  "infectAbscess",                "infectEnterocolitis",
    "infectCecitis",          "infectAppendicitis",               "infectPeritonitis",            "infectGlomerulonephritis",
    "infectPyelonephritis",   "infectCystitis",                   "infectUrethritis",             "infectEndometritis",
    "infectAdnexitis",        "infectVulvovaginitis",             "infectOsteomyelitis",          "infectMyositis",
    "infectCNSComment",       "infectEyeComment",                 "infectSkinComment",            "infectMucousComment",
    "infectLORComment",       "infectLungsComment",               "infectHeartComment",           "infectAbdomenComment",
    "infectUrogenitalComment","infectMusculoskeletalComment"
  )

  // Постфикс кода инфекций, название которых вводится пользователем
  val customInfectionPostfix = "Comment"

  // ActionPropertyType.code свойства-чекбокса "Локальная инфекция"
  val localInfectionChecker = "infectLocal"

  // Префиксы всех инфекций
  val allInfectPrefixes: Set[String] = infectPrefixes ++ localInfectPrefixes

  // Основные постфиксы
  val beginDatePostfix = "BeginDate"
  val endDatePostfix = "EndDate"
  val etiologyPostfix = "Etiology"

  // Разделитель префикса и постфикса
  val separator = '-'

  def isInfectTherapyProperties(actionPropCode: String): Boolean = {
    actionPropCode != null && (
      actionPropCode.startsWith("infectProphylaxisName") ||
      actionPropCode.startsWith("infectEmpiricName") ||
      actionPropCode.startsWith("infectTelicName") )
  }

}
