package ru.korus.tmis.laboratory.across.accept

import ru.korus.tmis.laboratory.across.{accept2 => lab2}

final object ImageValue {

  implicit def fromLab2(v: lab2.ImageValue) = {
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

