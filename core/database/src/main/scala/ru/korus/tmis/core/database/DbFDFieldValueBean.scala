package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable

@Stateless
class DbFDFieldValueBean extends DbFDFieldValueBeanLocal
with Logging
with I18nable {

}
