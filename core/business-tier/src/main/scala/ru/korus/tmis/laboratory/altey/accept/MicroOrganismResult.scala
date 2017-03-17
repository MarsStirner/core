package ru.korus.tmis.laboratory.altey.accept

import ru.korus.tmis.laboratory.altey.{accept2 => lab}
import scala.language.implicitConversions

final object MicroOrganismResult {
  implicit def fromLab1(v: ru.korus.tmis.laboratory.altey.accept2.MicroOrganismResult): MicroOrganismResult = {
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

