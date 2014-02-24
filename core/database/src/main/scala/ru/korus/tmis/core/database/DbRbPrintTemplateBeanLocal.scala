package ru.korus.tmis.core.database

import javax.ejb.Local
import ru.korus.tmis.core.entity.model.RbPrintTemplate

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/21/14
 * Time: 8:30 PM
 */
@Local
trait DbRbPrintTemplateBeanLocal {

  def getRbPrintTemplateByIds(id : Array[Int]): List[RbPrintTemplate]

  def getRbPrintTemplatesByContexts(contexts: Array[String]): List[RbPrintTemplate]

}
