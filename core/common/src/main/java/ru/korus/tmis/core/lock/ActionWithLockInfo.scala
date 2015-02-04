package ru.korus.tmis.core.lock

import ru.korus.tmis.core.entity.model.{Action, Staff}

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.05.14, 16:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
class ActionWithLockInfo(val action: Action,
                         val lockInfo: EntityLockInfo) {
}

class EntityLockInfo(val lockId: Int,
               val person: Staff,
               val clientType: String) {
}

