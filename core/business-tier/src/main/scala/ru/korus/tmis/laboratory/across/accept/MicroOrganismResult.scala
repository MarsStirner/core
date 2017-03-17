package ru.korus.tmis.laboratory.across.accept

import ru.korus.tmis.laboratory.across.{accept2 => lab2}
import scala.language.implicitConversions

final object MicroOrganismResult {

  implicit def fromLab2(v: lab2.MicroOrganismResult): MicroOrganismResult = {
    MicroOrganismResult(Option(v.getOrganismCode), Option(v.getOrganismName), Option(v.getOrganismConcentration))
  }
}

/**
 * Результат анализа микроорганизмов
 */
final case class MicroOrganismResult(
                                      /**
                                       * идентификатор организма по БД ЛИС
                                       */
                                      organismCode: Option[String],

                                      /**
                                       * название организма
                                       */
                                      organismName: Option[String],

                                      /**
                                       * концентрация организма
                                       */
                                      organismConcentration: Option[String]
                                      )

