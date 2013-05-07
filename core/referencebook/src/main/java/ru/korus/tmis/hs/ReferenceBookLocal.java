package ru.korus.tmis.hs;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 16:06 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface ReferenceBookLocal {

    void loadReferenceBooks();
}
