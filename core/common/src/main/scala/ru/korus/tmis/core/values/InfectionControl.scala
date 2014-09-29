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
  val drugTherapyProperties = Set(
    "infectDrugName_1", "infectDrugBeginDate_1", "infectDrugEndDate_1", "infectTherapyType_1",
    "infectDrugName_2", "infectDrugBeginDate_2", "infectDrugEndDate_2", "infectTherapyType_2",
    "infectDrugName_3", "infectDrugBeginDate_3", "infectDrugEndDate_3", "infectTherapyType_3",
    "infectDrugName_4", "infectDrugBeginDate_4", "infectDrugEndDate_4", "infectTherapyType_4",
    "infectDrugName_5", "infectDrugBeginDate_5", "infectDrugEndDate_5", "infectTherapyType_5",
    "infectDrugName_6", "infectDrugBeginDate_6", "infectDrugEndDate_6", "infectTherapyType_6",
    "infectDrugName_7", "infectDrugBeginDate_7", "infectDrugEndDate_7", "infectTherapyType_7",
    "infectDrugName_8", "infectDrugBeginDate_8", "infectDrugEndDate_8", "infectTherapyType_8"
  )

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

  // ActionPropertyType.code свойства-чекбокса "Локальная инфекция"
  val localInfectionChecker = "infectLocal"

  // Префиксы всех инфекций
  val allInfectPrefixes = infectPrefixes ++ localInfectPrefixes

  // Основные постфиксы
  val beginDatePostfix = "BeginDate"
  val endDatePostfix = "EndDate"
  val etiologyPostfix = "Etiology"

  // Разделитель префикса и постфикса
  val separator = '-'

}
