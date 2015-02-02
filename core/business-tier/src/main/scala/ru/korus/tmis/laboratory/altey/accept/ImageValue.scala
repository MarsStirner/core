package ru.korus.tmis.laboratory.altey.accept

import ru.korus.tmis.laboratory.altey.{accept2 => lab}
import scala.language.implicitConversions

final object ImageValue {
  implicit def fromLab1(v: ru.korus.tmis.laboratory.altey.accept2.ImageValue) = {
    ImageValue(Option(v.getDescription), Option(v.getImageData))
  }
}

/**
 * Класс для передачи изображений
 */
final case class ImageValue(
                             /**
                              * строка описания
                              */
                             description: Option[String],

                             /**
                              * картинка, закодированная в Base64
                              */
                             imageData: Option[String]
                             )

