package ru.korus.tmis.prescription;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.09.13, 14:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface BalanceOfGoodsInfo {
    boolean update(List<Integer> drugIds);
}
