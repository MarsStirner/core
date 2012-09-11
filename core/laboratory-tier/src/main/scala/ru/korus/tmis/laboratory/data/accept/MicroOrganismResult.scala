package ru.korus.tmis.laboratory.data.accept

import ru.korus.tmis.laboratory.data.lis.{accept => lab}
import ru.korus.tmis.laboratory.data.lis2.{accept => lab2}

final object MicroOrganismResult{
  implicit def fromLab1 (v: lab.MicroOrganismResult) = {
    MicroOrganismResult(Option(v.getOrganismCode), Option(v.getOrganismName), Option(v.getOrganismConcentration))
  }

  implicit def fromLab2 (v: lab2.MicroOrganismResult) = {
    MicroOrganismResult(Option(v.getOrganismCode), Option(v.getOrganismName), Option(v.getOrganismConcentration))
  }
}

/**
 * Результат анализа микроорганизмов
 */
final case class MicroOrganismResult (
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

