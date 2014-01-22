package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbFDFieldBean extends DbFDFieldBeanLocal
with Logging
with I18nable {

}
