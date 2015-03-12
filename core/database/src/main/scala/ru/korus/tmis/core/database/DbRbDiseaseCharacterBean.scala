package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.core.exception.CoreException
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbDiseaseCharacter
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 05.08.13
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */


@Stateless
class DbRbDiseaseCharacterBean extends DbRbDiseaseCharacterBeanLocal
                                with Logging
                                with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getDiseaseCharacterById (id: Int) = {
    val result =  em.createQuery(DiseaseCharacterByIdQuery, classOf[RbDiseaseCharacter])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(ConfigManager.ErrorCodes.RbDiseaseCharacterNotFound,
          i18n("error.rbDiseaseCharacterNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val DiseaseCharacterByIdQuery = """
    SELECT s
    FROM RbDiseaseCharacter s
    WHERE
      s.id = :id
                            """

}