package ru.korus.tmis.laboratory.across.accept

import ru.korus.tmis.laboratory.across.{accept2 => lab2}
import scala.language.implicitConversions

final object AntibioticSensitivity {

  implicit def fromLab2(v: lab2.AntibioticSensitivity) = {
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

