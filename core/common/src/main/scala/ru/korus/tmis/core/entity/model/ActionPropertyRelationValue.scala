package ru.korus.tmis.core.entity.model

import javax.persistence._
import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/28/14
 * Time: 5:07 PM
 */
@Entity
@Table(name = "APRelations_Value")
class ActionPropertyRelationValue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @BeanProperty var id: Int = _

  @ManyToOne
  @JoinColumn(name = "ActionPropertyRelations_id")
  @BeanProperty var actionPropertyRelation: ActionPropertyRelation = _

  @Column(name = "RefType")
  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @BeanProperty var refType: ActionPropertyRelationValueRefType = _

  @Basic
  @BeanProperty
  var valueReference: java.lang.Integer = _

  @Basic
  @Temporal(TemporalType.TIMESTAMP)
  @BeanProperty
  var valueDate: java.util.Date = _

  @Basic
  @BeanProperty
  var valueFloat: java.lang.Float = _

  @Basic
  @BeanProperty
  var valueString: String = _

}
