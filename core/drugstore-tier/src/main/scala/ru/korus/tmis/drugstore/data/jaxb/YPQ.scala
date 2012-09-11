package ru.korus.tmis.drugstore.data.jaxb

import org.hl7.v3._
import ru.korus.tmis.core.entity.model.{Nomenclature, ActionProperty, APValue}

import javax.xml.bind.annotation.XmlTransient

import scala.collection.JavaConversions._

class YDrugDosagePQ(@XmlTransient drug: Nomenclature,
                    @XmlTransient d: (ActionProperty, java.util.List[APValue]))
  extends IVLPQ {
  val factory = new ObjectFactory

  val pq = factory.createPQ()
  val pqr = factory.createPQR()
  val ed = factory.createED()

  def getDosageValue(str: String) = {
    val re = "([0-9]*).*".r
    str match {
      case re(dosage) => dosage
      case _ => "1"
    }
  }

  d._2.size match {
    case 0 => {}
    case _ => {
      val dosageString = d._2.head.getValueAsString

      ed.getContent.add(drug.getForm)

      pqr.setCodeSystemName("RLS")
      pqr.setOriginalText(ed)
      pq.setValue(getDosageValue(dosageString))
      pq.getTranslation.add(pqr)

      getRest.add(factory.createIVLPQCenter(pq))
    }
  }

}
