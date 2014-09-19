package ru.korus.tmis.core.database

import java.util.Date
import ru.korus.tmis.core.entity.model.{AutosaveStoragePK, AutoSaveStorage}
import javax.persistence.{EntityManager, PersistenceContext}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.ConfigManager
import ru.korus.tmis.core.data.AutoSaveOutputDataContainer
import scala.language.reflectiveCalls

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/21/14
 * Time: 8:15 PM
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbAutoSaveStorage extends  DbAutoSaveStorageLocal {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def save(id: String, userId: Int, text: String) = {
    try {
      if (id.length > 40)
        throw new IllegalArgumentException("Идентификатор записи не может быть больше 40 символов")

      val entity = new AutoSaveStorage(id, userId, new Date(), text)
      em.merge(entity)
    } catch {
      case e: IllegalArgumentException => throw new CoreException(ConfigManager.ErrorCodes.UnknownError, e.getMessage, e)
      case e: Throwable => throw new CoreException(ConfigManager.ErrorCodes.UnknownError, e.getMessage, e)
    }
  }

  def load(id: String, userId: Int): AutoSaveOutputDataContainer = {
    try {
      val elem = Option(em.find(classOf[AutoSaveStorage], new AutosaveStoragePK(id, userId)))
        .getOrElse(new AutoSaveStorage(id, userId, new Date, ""))
      em.detach(elem)
      new AutoSaveOutputDataContainer(elem)
    } catch {
      case e: Throwable => throw new CoreException(e)
    }
  }


  def delete(id: String, userId: Int): Unit = {
    try {
      Option(em.find(classOf[AutoSaveStorage], new AutosaveStoragePK(id, userId)))
        .foreach(em.remove(_))
    } catch {
      case e: Throwable => throw new CoreException(e)
    }
  }

  def clean(date: Date): Unit = {
    try {
      em.createQuery("DELETE FROM AutoSaveStorage s WHERE s.modifyDatetime < :date")
        .setParameter("date", date)
        .executeUpdate()
    }  catch {
      case e: Throwable => throw new CoreException(e)
    }
  }

}
