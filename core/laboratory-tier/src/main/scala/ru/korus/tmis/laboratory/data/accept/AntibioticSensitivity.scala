package ru.korus.tmis.laboratory.data.accept

import ru.korus.tmis.laboratory.data.lis.{accept => lab}
import ru.korus.tmis.laboratory.data.lis2.{accept => lab2}

final object AntibioticSensitivity {
   implicit def fromLab1 (v: lab.AntibioticSensitivity) = {
     AntibioticSensitivity(Option(v.getCode), Option(v.getName), Option(v.getValue), Option(v.getMic))
   }

  implicit def fromLab2 (v: lab2.AntibioticSensitivity) = {
    AntibioticSensitivity(Option(v.getCode), Option(v.getName), Option(v.getValue), Option(v.getMic))
  }
}

/**
 * Чувствительность микроорганизма к антибиотикам
 */
final case class AntibioticSensitivity (
  /**
   * Идентификатор антибиотика по БД ЛИС
   */
  code: Option[String],
  /**
   * Название антибиотика
   */
  name: Option[String],
  /**
   * Чувствительность
   */
  value: Option[String],
  /**
   * Количественная характеристика чувствительности, если используется анализатор
   */
  mic: Option[String]
)

