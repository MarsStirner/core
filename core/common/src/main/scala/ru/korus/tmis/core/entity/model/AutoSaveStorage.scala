package ru.korus.tmis.core.entity.model

import javax.persistence._
import java.util.Date
import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/21/14
 * Time: 3:01 PM
 */
@IdClass(value = classOf[AutosaveStoragePK])
@Entity
@Table(name = "AutoSaveStorage")
@NamedQueries(Array(
  new NamedQuery(name = "AutoSaveStorage.findAll", query = "SELECT s FROM AutoSaveStorage s"),
  new NamedQuery(name = "AutoSaveStorage.findByIdAndUser", query = "SELECT s FROM AutoSaveStorage s WHERE s.id = :id AND s.userId = :userId"),
  new NamedQuery(name = "AutoSaveStorage.findBeforeDate", query = "SELECT s FROM AutoSaveStorage s WHERE s.modifyDatetime < :date")
))
class AutoSaveStorage() extends Serializable {

  @Id
  @Basic(optional = false)
  @BeanProperty var id: String = _

  @Id
  @Basic(optional = false)
  @Column(name = "user_id")
  @BeanProperty var userId: Int = _

  @Basic(optional = false)
  @Temporal(TemporalType.TIMESTAMP)
  @BeanProperty var modifyDatetime: Date = _

  @Basic(optional = false)
  @BeanProperty var text: String = _

  def this(id: String, userId: Int, modifyDatetime: Date,  text: String) = {
    this()
    this.id = id
    this.userId = userId
    this.modifyDatetime = modifyDatetime
    this.text = text
  }

}
