package ru.korus.tmis.drugstore.data.jaxb

import org.hl7.v3.CE
import ru.korus.tmis.core.entity.model.{Nomenclature, Patient}

import javax.xml.bind.annotation.XmlTransient

class YEmptyCodeCE extends CE {
  // Should be empty
}

class YAdministrationCodeCE extends CE {
  setCode("18610-6")
  setCodeSystem("2.16.840.1.113883.6.1")
  setCodeSystemName("LOINC")
  setDisplayName("MEDICATION ADMINISTERED")
}

class YConfidentialityCodeCE extends CE {
  setCode("N")
  setCodeSystem("2.16.840.1.113883.5.25")
  setDisplayName("Normal")
}

class YGenderCodeCE(@XmlTransient p: Patient) extends CE {
  p.getSex match {
    case 1 => setCode("M")
    case 2 => setCode("F")
    case _ => setCode("UN")
  }
  setCodeSystem("2.16.840.1.113883.5.1")
}

class YDrugCodeCE(@XmlTransient drug: Nomenclature) extends CE {
  setCode(drug.getCode.toString)
  setCodeSystem("1.2.643.2.0")
  setCodeSystemName("RLS_NOMEN")
}

class YRouteCodeCE() extends CE {
  setCode("PO")
  setCodeSystem("2.16.840.1.113883.5.112")
  setCodeSystemName("RouteOfAdministration")
}
