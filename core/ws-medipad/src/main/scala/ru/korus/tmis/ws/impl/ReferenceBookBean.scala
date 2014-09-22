package ru.korus.tmis.ws.impl

import javax.ejb.{EJB, Stateless}

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.database.DbRbPolicyTypeBeanLocal

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/22/14
 * Time: 5:39 PM
 */
@Stateless
class ReferenceBookBean {

  @EJB private var DbRbPolicyTypeBean: DbRbPolicyTypeBeanLocal = _

  def getPolicyTypes(auth: AuthData) = { DbRbPolicyTypeBean.getAllRbPolicyTypes }

}
