package ru.korus.tmis.laboratory.altey.accept

import java.util.Date
import ru.korus.tmis.laboratory.altey.{accept2 => lab}

import ru.korus.tmis.scala.util.Variant
import Variant.{EitherOf4, Variant3, Variant2, Variant1, Variant0}
import scala.collection.JavaConversions._
import scala.language.implicitConversions


object AnalysisResult {

  type AnalysisValue = EitherOf4[String, List[ImageValue], List[MicroOrganismResult], List[AntibioticSensitivity]]

  final object ValueType extends Enumeration {
    type ValueType = Value
    val TEXT = Value(1)
    val IMAGE = Value(2)
    val CONCENTRATION = Value(3)
    val SENSITIVITY = Value(4)
  }

  type ValueType = ValueType.ValueType

  def genValue(tp: ValueType, sv: String, iv: List[ImageValue], morv: List[MicroOrganismResult], asv: List[AntibioticSensitivity]): AnalysisValue = {

    import ValueType._

    tp match {
      case TEXT => Variant0(sv)
      case IMAGE => Variant1(iv)
      case CONCENTRATION => Variant2(morv)
      case SENSITIVITY => Variant3(asv)
    }
  }

  implicit def fromLab1(v: ru.korus.tmis.laboratory.altey.accept2.AnalysisResult): AnalysisResult = {
    AnalysisResult(
      Option(v.getCode),
      Option(v.getName),
      Option(v.getDeviceName),
      genValue(
        ValueType(v.getValueTypeNum),
        v.getTextValue,
        v.getImageValues.map {
          ImageValue.fromLab1(_)
        }.toList,
        v.getMicroValues.map {
          MicroOrganismResult.fromLab1(_)
        }.toList,
        v.getMicroSensitivity.map {
          AntibioticSensitivity.fromLab1(_)
        }.toList
      ),
      Option(v.getNorm),
      Option(v.getNormalityIndex),
      Option(v.getUnitCode),
      Option(v.getEndDate),
      Option(v.getResultStatus),
      Option(v.getComment)
    )
  }
}

sealed case class AnalysisResult(
                                  /**
                                   * Код методики/показателя (Основной)
                                   */
                                  code: Option[String],

                                  /**
                                   * Название методики/показателя
                                   */
                                  name: Option[String],

                                  /**
                                   * Название прибора, на котором выполнялось исследование
                                   */
                                  deviceName: Option[String],

                                  /**
                                   * Тип значения
                                   */
                                  value: AnalysisResult.AnalysisValue,

                                  /**
                                   * Норма (для числового результата)
                                   */
                                  norm: Option[String],

                                  /**
                                   * Значение результата относительно нормы (не опр./норма/ниже критической/ниже/выше критической/выше)
                                   */
                                  normalityIndex: Option[Float],

                                  /**
                                   * Единица измерения
                                   */
                                  unitCode: Option[String],

                                  /**
                                   * Дата выполнения
                                   */
                                  endDate: Option[Date],

                                  /**
                                   * Статус результата. В это поле выводится причина в случае отсутствия результата
                                   */
                                  resultStatus: Option[String],

                                  /**
                                   * Комментарий к методике
                                   */
                                  comment: Option[String]
                                  )

