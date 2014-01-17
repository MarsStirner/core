package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import scala.beans.BeanProperty
import java.util
import ru.korus.tmis.core.entity.model.bak.{BbtOrganismSensValues, BbtResultOrganism, BbtResultText, BbtResponse}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 1/15/14
 * Time: 8:36 PM
 */
@XmlType(name = "bakLabResultDataContainer")
@XmlRootElement(name = "bakLabResultDataContainer")
class BakLabResultDataContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var textResults: util.List[BakLabResultText] = new util.ArrayList[BakLabResultText]()

  @BeanProperty
  var organismResults: util.List[BakLabResultOrganism] = new util.ArrayList[BakLabResultOrganism]()

  def this(response: BbtResponse,
           textResults: Iterable[BbtResultText],
           organismResults: Iterable[BbtResultOrganism]) = {
    this()
    id = response.getId
    textResults.foreach(e => this.textResults.add(new BakLabResultText(e)))
    organismResults.foreach(e => this.organismResults.add(new BakLabResultOrganism(e, e.getSensValues)))
  }

}


@XmlType(name = "bakLabResultText")
@XmlRootElement(name = "bakLabResultText")
class BakLabResultText {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var actionId: Int = _

  @BeanProperty
  var textValue: String = _

  def this(textResult: BbtResultText) = {
    this
    id = textResult.getId
    actionId = textResult.getActionId
    textValue = textResult.getValueText
  }
}

@XmlType(name = "bakLabResultOrganism")
@XmlRootElement(name = "bakLabResultOrganism")
class BakLabResultOrganism {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var organismId: Int = _

  @BeanProperty
  var concentration: String = _

  @BeanProperty
  var sensValues: util.List[OrganismSensValue] = new util.ArrayList[OrganismSensValue]()

  def this(organismResult: BbtResultOrganism, sensValues: java.lang.Iterable[BbtOrganismSensValues]) = {
    this
    id = organismResult.getId
    organismId = organismResult.getOrganismId
    concentration = organismResult.getConcentration
    val i = sensValues.iterator()
    while(i.hasNext) this.sensValues.add(new OrganismSensValue(i.next()))
  }

}

@XmlType(name = "organismSensValue")
@XmlRootElement(name = "organismSensValue")
class OrganismSensValue {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var antibioticId: Int = _

  @BeanProperty
  var mic: String = _

  @BeanProperty
  var activity: String = _

  def this(sensValue: BbtOrganismSensValues) = {
    this
    id = sensValue.getId
    antibioticId = sensValue.getAntibioticId
    mic = sensValue.getMic
    activity = sensValue.getActivity
  }

}