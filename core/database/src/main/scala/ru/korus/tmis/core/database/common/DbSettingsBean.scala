package ru.korus.tmis.core.database.common

import javax.interceptor.Interceptors

import grizzled.slf4j.Logging
import javax.persistence._
import javax.annotation.PostConstruct
import javax.ejb.{Schedule, TransactionManagementType, TransactionManagement, Startup, Singleton}
import ru.korus.tmis.core.entity.model.Setting
import collection.mutable.Buffer

import java.util.{List => JList}
import java.util
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import ru.korus.tmis.scala.util.ConfigManager._

@Startup
//
@Singleton
class DbSettingsBean extends DbSettingsBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "tmis_core")
  var tmis_core: EntityManager = _

  @PersistenceContext(unitName = "s11r64")
  var s11r64: EntityManager = _


  @PostConstruct
  def init() = {
    load_settings
  }

  def load_settings = {
    import ConfigManager._
    import collection.JavaConversions._
    val settings: Buffer[Setting] = tmis_core.createNamedQuery[Setting]("Setting.findAll", classOf[Setting]).getResultList.toBuffer

    settings.foreach {
      setting =>
        info("Setting \"" + setting.getPath + "\" to \"" + setting.getValue + "\"")
        if (!setSetting(setting.getPath, setting.getValue)) {
          warn("Could not set setting '" + setting.getPath + "' to value '" + setting.getValue + "'")
        } else {
          info("Successfully changed setting")
        }
    }
  }

  def getSettingByPath(path: String): Setting  = getSetting(tmis_core, path)

  private def getSetting(em: EntityManager, path: String ): Setting = {
    val result = em.createQuery("SELECT s FROM Setting s WHERE s.path = :path", classOf[Setting]).setParameter("path", path)
    var s: Setting = new Setting
    try {
      s = result.getSingleResult
    } catch {
      case e: NoResultException => // TODO Write log about lost entry
      case e: NonUniqueResultException => // TODO Write log about invalid DB state
      case e: QueryTimeoutException => new Setting // TODO Write log about DB timeout
    }
    s
  }

  def getAllSettings: util.List[Setting] = {
    val resultList: util.List[Setting] = tmis_core.createNamedQuery("Setting.findAll", classOf[Setting]).getResultList
    if (resultList == null) {
      new util.ArrayList(0)
    }
    resultList
  }

  def updateSetting(path: String, value: String): Boolean = {
    if (setSetting(path, value)) {
      info("Successfully changed setting: " + path + " : " + value)
      var setting: Setting = getSetting(tmis_core, path)
      if (setting == null) {
        setting = new Setting
        setting.setPath(path)
        tmis_core.persist(setting)
      }
      setting.setValue(value)
      tmis_core.flush()
      true
    }
    false
  }
}