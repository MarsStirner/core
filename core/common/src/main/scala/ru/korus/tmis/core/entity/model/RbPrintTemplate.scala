package ru.korus.tmis.core.entity.model

import javax.persistence._
import scala.beans.BeanProperty
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.{JsonIgnoreProperties}
/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/21/14
 * Time: 7:09 PM
 */
@Entity
@Table(name = "rbPrintTemplate")
@NamedQueries(Array(
  new NamedQuery(name = "RbPrintTemplate.findAll", query = "SELECT p FROM RbPrintTemplate p"),
  new NamedQuery(name = "RbPrintTemplate.findByIds", query = "SELECT p FROM RbPrintTemplate p WHERE p.id IN :values"),
  new NamedQuery(name = "RbPrintTemplate.findByContexts", query="SELECT p FROM RbPrintTemplate p WHERE p.context IN :values")
))
@XmlType(name = "RbPrintTemplate")
@XmlRootElement(name = "RbPrintTemplate")
@JsonIgnoreProperties(ignoreUnknown = true)
@SerialVersionUID(1L)
class RbPrintTemplate extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @BeanProperty
  var id: Integer = _

  @Basic(optional = false)
  @BeanProperty
  var code: String = _

  @Basic(optional = false)
  @BeanProperty
  var name: String = _

  @Basic(optional = false)
  @BeanProperty
  var context: String = _

  @Basic(optional = false)
  @BeanProperty
  var fileName: String = _

  @Basic(optional = false)
  @BeanProperty
  var default: String = _

  @Basic(optional = false)
  @BeanProperty
  var dpdAgreement: Integer = _

  @Basic(optional = false)
  @BeanProperty
  var render: Integer = _

  @Transient
  @BeanProperty
  var hasPopApp: java.lang.Boolean = _

  @PostLoad
  def constructHasPopApp = {
      // Ищем в тексте шаблона определенные конструкции
      val popApp = default.matches("(.*dialogs\\.dial.*)|(.*SpecialVar_.*)|(.*SpecialVariable.*)")
      if (popApp) {
        hasPopApp = true
      }
      else
        hasPopApp = false
  }

}
