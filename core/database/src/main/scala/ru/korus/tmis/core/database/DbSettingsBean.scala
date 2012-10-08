package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.annotation.PostConstruct
import javax.ejb.{Schedule, TransactionManagementType, TransactionManagement, Startup, Singleton}
import ru.korus.tmis.core.entity.model.Setting
import collection.mutable.Buffer

import java.util.{List => JList}
import ru.korus.tmis.util.I18nable

@Startup
@Interceptors(Array(classOf[LoggingInterceptor]))
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
class DbSettingsBean extends DbSettingsBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "tmis_core")
  var tmis_core: EntityManager = _

  @PostConstruct
  @Schedule(second = "0", minute = "0", hour = "4")
  def init() = {
    load_settings
  }

  def load_settings = {
    import ru.korus.tmis.util.ConfigManager._
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

}