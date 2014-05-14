package ru.korus.tmis.core.entity.model

import javax.persistence._
import scala.beans.BeanProperty
import java.util.Date

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/14/14
 * Time: 5:33 PM
 */

@Entity
@Table(name = "vNomen")
@NamedQueries(Array(new NamedQuery(name = "Nomenclature.findAll", query = "SELECT n FROM Nomenclature n")))
class NomenclatureNew {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @BeanProperty
  var id: Int = _

  @Basic
  @BeanProperty
  var tradeName: String = _

  @Basic
  @BeanProperty
  var tradeLocalName: String = _

  @Basic
  @BeanProperty
  var tradeName_id: Int = _

  @Basic
  @BeanProperty
  var actMattersName: String = _

  @Basic
  @BeanProperty
  var actMattersLocalName: String = _

  @Basic
  @BeanProperty
  var actMatters_id: Int = _

  @Basic
  @BeanProperty
  var form: String = _

  @Basic
  @BeanProperty
  var packing: String = _

  @Basic
  @BeanProperty
  var filling: String = _

  @Basic
  @BeanProperty
  var unit_id: Int = _

  @Basic
  @BeanProperty
  var unitCode: String = _

  @Basic
  @BeanProperty
  var unitName: String = _

  @Basic
  @BeanProperty
  var dosageValue: String = _

  @Basic
  @BeanProperty
  var dosageUnit_id: Int = _

  @Basic
  @BeanProperty
  var dosageUnitCode: String = _

  @Basic
  @BeanProperty
  var dosageUnitName: String = _

  @Basic
  @BeanProperty
  @Temporal(TemporalType.DATE)
  var regDate: Date = _

  @Basic
  @BeanProperty
  @Temporal(TemporalType.DATE)
  var annDate: Date = _

}
