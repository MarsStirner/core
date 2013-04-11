package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model.Diagnostic


//TODO: !Пока не делаем!
/**
 * Контейнер для информации о диагнозе
 * Спецификация: https://docs.google.com/document/d/1bC63TjdR9wwv4IRgGu2ExX0mGG1Wx1ubvzmYPUtZN8M/edit#heading=h.33nbgznlpmqy
 * пункт 2.1 Запись данных
 * Author: idmitriev Systema-Soft
 * Date: 4/10/13 14:35 PM
 * Since: 1.0.0.74
 */
@XmlType(name = "diagnosisData")
@XmlRootElement(name = "diagnosisData")
@JsonIgnoreProperties(ignoreUnknown = true)
class DiagnosisData {

  @BeanProperty
  var data: DiagnosisEntry = _

  def this(diagnostic: Diagnostic) = {
    this()
    this.data = new DiagnosisEntry(diagnostic)
  }
}

@XmlType(name = "diagnosisEntry")
@XmlRootElement(name = "diagnosisEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class DiagnosisEntry {

  @BeanProperty
  var diagnosticId: Int = -1         //Идентивикатор записи в таблице Diagnostic
  @BeanProperty
  var eventId: Int = _               //Идентификатор обращения
  @BeanProperty
  var diagnosisKind: String = _      //Мнемоника типа диагноза
  @BeanProperty
  var description: String = _        //Описание
  @BeanProperty
  var diseaseCharacterId: Int = 3    //Идентификатор характера заболевания (по умолчанию =3)
  @BeanProperty
  var sanatorium: Int = 0            //TODO: Нет описания как используется
  @BeanProperty
  var hospital: Int = 0              //TODO: Нет описания как используется
  @BeanProperty
  var mkb: MKBContainer = new MKBContainer()    //Диагноз по МКБ

  def this(diagnostic: Diagnostic) = {
    this()
  }
}