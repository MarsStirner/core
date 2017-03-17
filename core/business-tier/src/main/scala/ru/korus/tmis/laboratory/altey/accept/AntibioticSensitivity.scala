package ru.korus.tmis.laboratory.altey.accept

import ru.korus.tmis.laboratory.altey.{accept2 => lab}
import scala.language.implicitConversions

final object AntibioticSensitivity {
  implicit def fromLab1(v: ru.korus.tmis.laboratory.altey.accept2.AntibioticSensitivity): AntibioticSensitivity = {
    AntibioticSensitivity(Option(v.getCode), Option(v.getName), Option(v.getValue), Option(v.getMic))
  }
}

/**
 * Чувствительность микроорганизма к антибиотикам
 */
final case class AntibioticSensitivity(
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

