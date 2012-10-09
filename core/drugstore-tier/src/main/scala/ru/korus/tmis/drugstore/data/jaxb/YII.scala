package ru.korus.tmis.drugstore.data.jaxb

import org.hl7.v3.II
import ru.korus.tmis.core.entity.model.{Action, Patient, Event, Staff}
import ru.korus.tmis.drugstore.data.{YUUID, YClinicalDocument}

import javax.xml.bind.annotation.XmlTransient

class YClinicalDocumentId(@XmlTransient ycd: YClinicalDocument) extends II {
  setRoot(YUUID.generateRandom())
}

class YPatientId(@XmlTransient p: Patient) extends II {
  setRoot(YUUID.generate(p, p.getId))
}

class YStaffId(@XmlTransient s: Staff) extends II {
  setRoot(YUUID.generate(s, s.getId))
}

class YEventId(@XmlTransient e: Event) extends II {
  setRoot(YUUID.generate(e, e.getId))
  e.getExternalId match {
    case null | "" => setExtension("НЕТ")
    case id => setExtension(id)
  }
}

class YDrugId(@XmlTransient a: Action) extends II {
  setRoot(YUUID.generate(a, a.getId))
}

class YFinanceId extends II {
  setExtension("ОМС")
}

class YCustomId(@XmlTransient s: String) extends II {
  setRoot(s)
}

class YNullFlavorId extends II {
  getNullFlavor.add("NI")
}
