package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.core.exception.CoreException
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbDiseaseStage
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 05.08.13
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbDiseaseStageBean extends DbRbDiseaseStageBeanLocal
                            with Logging
                            with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getDiseaseStageById (id: Int) = {
    val result =  em.createQuery(DiseaseStageByIdQuery, classOf[RbDiseaseStage])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(ConfigManager.ErrorCodes.RbDiseaseStageNotFound,
          i18n("error.rbDiseaseStageNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val DiseaseStageByIdQuery = """
    SELECT s
    FROM RbDiseaseStage s
    WHERE
      s.id = :id
                            """

}