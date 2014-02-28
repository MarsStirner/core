package ru.korus.tmis.core.entity.model

import javax.persistence._
import scala.beans.BeanProperty
import javax.xml.bind.annotation.{XmlElement, XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties

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
class RbPrintTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  @BeanProperty
  var id: Integer = _

  @Column(name = "code")
  @Basic(optional = false)
  @BeanProperty
  var code: String = _

  @Column(name = "name")
  @Basic(optional = false)
  @BeanProperty
  var name: String = _

  @Column(name = "context")
  @Basic(optional = false)
  @BeanProperty
  var context: String = _

  @Column(name = "fileName")
  @Basic(optional = false)
  @BeanProperty
  var filename: String = _


  @Column(name = "default")
  @Basic(optional = false)
  @BeanProperty
  var default: String = _

  @Column(name = "dpdAgreement")
  @Basic(optional = false)
  @BeanProperty
  var dpdAgreement: Integer = _

  @Column(name = "render")
  @Basic(optional = false)
  @BeanProperty
  var render: Integer = _

}
