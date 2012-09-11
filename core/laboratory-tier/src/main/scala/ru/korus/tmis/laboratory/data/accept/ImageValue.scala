package ru.korus.tmis.laboratory.data.accept

import ru.korus.tmis.laboratory.data.lis.{accept => lab}
import ru.korus.tmis.laboratory.data.lis2.{accept => lab2}

final object ImageValue {
  implicit def fromLab1 (v: lab.ImageValue) = {
    ImageValue(Option(v.getDescription), Option(v.getImageData))
  }


  implicit def fromLab2 (v: lab2.ImageValue) = {
    ImageValue(Option(v.getDescription), Option(v.getImageData))
  }
}

/**
 * Класс для передачи изображений
 */
final case class ImageValue (
  /**
   * строка описания
   */
  description: Option[String],
  /**
   * картинка, закодированная в Base64
   */
  imageData: Option[String]
)

