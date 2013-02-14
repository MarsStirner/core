package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import java.util.Date
import ru.korus.tmis.core.entity.model.{RbTestTubeType, Patient}
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.util.{I18nable, ConfigManager}
import collection.mutable

/**
 * Контейнер с данными о заборе биоматериала
 * Author: idmitriev Systema-Soft
 * Date: 2/12/13 3:37 PM
 * Since: 1.0.0.64
 */
@XmlType(name = "takingOfBiomaterialData")
@XmlRootElement(name = "takingOfBiomaterialData")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialData {

  @BeanProperty
  var requestData: TakingOfBiomaterialRequesData = _
  @BeanProperty
  var data: mutable.LinkedList[TakingOfBiomaterialEntry] = mutable.LinkedList.empty[TakingOfBiomaterialEntry]

}

@XmlType(name = "takingOfBiomaterialRequesData")
@XmlRootElement(name = "takingOfBiomaterialRequesData")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialRequesData {

  @BeanProperty
  var filter: TakingOfBiomaterialRequesDataFilter = _
  @BeanProperty
  var sortingField: String = _
  @BeanProperty
  var sortingMethod: String = _
  @BeanProperty
  var recordsCount: Int = 0
  @BeanProperty
  var coreVersion: String = _

  def this(sortingField: String,
           sortingMethod: String,
           filter: TakingOfBiomaterialRequesDataFilter){
    this()
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.filter = filter
  }
}

@XmlType(name = "takingOfBiomaterialRequesDataFilter")
@XmlRootElement(name = "takingOfBiomaterialRequesDataFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialRequesDataFilter {

  @BeanProperty
  var departmentId: Int = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var status: Short = 0
  @BeanProperty
  var biomaterial: String = ""

  def this( departmentId: Int,
            beginDate: Long,
            endDate: Long,
            status: Short,
            biomaterial:String) {
    this()
    this.departmentId = departmentId
    this.status = status
    this.biomaterial = biomaterial
    //TODO: Анализ дат как в форме 007
  }

}

@XmlType(name = "takingOfBiomaterialEntry")
@XmlRootElement(name = "takingOfBiomaterialEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialEntry {

  @BeanProperty
  var id: Int = -1                              //Action.id
  @BeanProperty
  var actionType: IdNameContainer = _           //ActionType.id + ActionType.name
  @BeanProperty
  var date: Date = _                            //Дата и время
  @BeanProperty
  var patient: PatientInfoDataContainer = _     //Основная информация о пациенте
  @BeanProperty
  var urgent: Boolean = false                   //Срочность
  @BeanProperty
  var status: Short = _                         //Статус
  @BeanProperty
  var tubeType: TestTubeTypeInfoContainer = _   //Тип пробирки
  @BeanProperty
  var assigner: DoctorContainer = _             //Основная информация о назначевшем забор враче
  @BeanProperty
  var laboratory: IdNameContainer = _           //Лаборатория
  @BeanProperty
  var label: String = _                         //Пометка(номер пробы)
  @BeanProperty
  var note: String = _                          //Примечание
}

/**
 * Контейнер с данными об пациенте (ФИО, дата рождения, пол)
 */
@XmlType(name = "patientInfoDataContainer")
@XmlRootElement(name = "patientInfoDataContainer")
class PatientInfoDataContainer extends TmisLogging
                               with I18nable {

  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer()
  @BeanProperty
  var birthDate: Date = _
  @BeanProperty
  var sex: String = _

  /**
   * Конструктор класса PatientInfoDataContainer
   * @param patient Информация о пациенте как Patient entity
   * @see Patient
   */
  def this(patient: Patient) {
    this()
    if(patient!=null){
      this.id = patient.getId.intValue()
      this.name = new PersonNameContainer(patient)
      this.birthDate = patient.getBirthDate
      this.sex = patient.getSex match {
        case 1 => "male"
        case 2 => "female"
        case _ => "unknown"
      }
    } else {
      logTmis.setLoggerType(logTmis.LoggingTypes.Debug)
      logTmis.warning("code " + ConfigManager.ErrorCodes.PatientIsNull +
        "PatientInfoDataContainer не заполнен данными: " + i18n("error.patientIsNull"))
    }

  }
}

@XmlType(name = "testTubeTypeInfoContainer")
@XmlRootElement(name = "testTubeTypeInfoContainer")
class TestTubeTypeInfoContainer extends TmisLogging
                                with I18nable {
  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var name: String = ""
  @BeanProperty
  var volume: Double = 0.0
  @BeanProperty
  var covCol: String = ""

  /**
   * Конструктор класса TestTubeTypeInfoContainer
   * @param tubeType Информация о типе пробирки
   * @see RbTestTubeType
   */
  def this(tubeType: RbTestTubeType) {
    this()
    if(tubeType!=null) {
      this.id = tubeType.getId.intValue()
      this.name = tubeType.getName
      this.volume = tubeType.getVolume
      this.covCol = tubeType.getCovCol
    } else {
      logTmis.setLoggerType(logTmis.LoggingTypes.Debug)
      logTmis.warning("code " + ConfigManager.ErrorCodes.RbTestTubeTypeIsNull +
        "TestTubeTypeInfoContainer не заполнен данными: " + i18n("error.rbTestTubeTypeIsNull"))
    }
  }
}
