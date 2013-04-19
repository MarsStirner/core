package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model.Diagnostic
import java.util.Date
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import collection.JavaConversions._

/**
 * Контейнер для хранения списка диагнозов
 * Author: idmitriev Systema-Soft
 * Date: 4/10/13 11:35 PM
 * Since: 1.0.0.74
 */
@XmlType(name = "diagnosesListData")
@XmlRootElement(name = "diagnosesListData")
class DiagnosesListData {
  //@BeanProperty
  //var requestData: DiagnosesListRequestData = _
  @BeanProperty
  var data: java.util.LinkedList[DiagnosisExContainer] = new java.util.LinkedList[DiagnosisExContainer]

  def this(diagnostics: java.util.List[Diagnostic]) = {
    this ()
    //this.requestData = new DiagnosesListRequestData()
    diagnostics.foreach(diagnostic => this.data.add(new DiagnosisExContainer(diagnostic)))
  }
}

/**
 * Контейнер для хранения данных о диагнозе
 */
@XmlType(name = "diagnosisExContainer")
@XmlRootElement(name = "diagnosisExContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DiagnosisExContainer {
  @BeanProperty
  var diagnosticId: Int = -1
  @BeanProperty
  var diagnosisKind: String = _
  @BeanProperty
  var datetime: Date = _
  @BeanProperty
  var description: String = _
  @BeanProperty
  var injury: String = _
  @BeanProperty
  var doctor: DoctorContainer = new DoctorContainer()
  @BeanProperty
  var mkb: MKBContainer = new MKBContainer()

  /**
   * Конструктор DiagnosisContainer
   * @param diagnostic Данные о диагнозе как Diagnostic entity
   */
  def this(diagnostic: Diagnostic){
    this()
    if(diagnostic!=null) {
      this.diagnosticId = diagnostic.getId.intValue()
      this.diagnosisKind =
        if(diagnostic.getDiagnosisType.getFlatCode.isEmpty)
          diagnostic.getDiagnosisType.getName
        else diagnostic.getDiagnosisType.getFlatCode
      this.datetime = diagnostic.getSetDate
      this.description = diagnostic.getNotes
      this.injury = if(diagnostic.getTraumaType!=null) {diagnostic.getTraumaType.getName} else {""}
      this.doctor = new DoctorContainer(diagnostic.getPerson)
      this.mkb =
        if(diagnostic.getDiagnosis!=null && diagnostic.getDiagnosis.getMkb!=null)
           new MKBContainer(diagnostic.getDiagnosis.getMkb)
        else new MKBContainer()

    }
  }
}