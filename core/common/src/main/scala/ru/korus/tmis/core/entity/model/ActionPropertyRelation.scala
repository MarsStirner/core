package ru.korus.tmis.core.entity.model

import javax.persistence._
import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/27/14
 * Time: 8:25 PM
 */

@Entity
@Table(name = "ActionPropertyRelations")
class ActionPropertyRelation extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @BeanProperty var id: Int = _

  @ManyToOne
  @JoinColumn(name = "Subject")
  @BeanProperty var subjectType: ActionPropertyType = _

  @ManyToOne
  @JoinColumn(name = "Object")
  @BeanProperty var objectType: ActionPropertyType = _

  @Column(name = "RelationType")
  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @BeanProperty var relationType: ActionPropertyRelationType = _

  @Column(name = "RelationState")
  @Enumerated(EnumType.STRING)
  @Basic(optional = false)
  @BeanProperty var relationState: ActionPropertyRelationState = _

  @OneToMany(mappedBy = "actionPropertyRelation", cascade = Array(CascadeType.ALL))
  @BeanProperty
  var values: java.util.Set[ActionPropertyRelationValue] = _

}
