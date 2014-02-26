package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbPrintTemplate
import javax.ejb.Stateless

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/21/14
 * Time: 8:33 PM
 */
@Stateless
class RbPrintTemplateBean extends DbRbPrintTemplateBeanLocal{

  def getRbPrintTemplateById(id: Int): RbPrintTemplate = ???

  def getRbPrintTemplatesByContext(context: String): List[RbPrintTemplate] = ???

}
