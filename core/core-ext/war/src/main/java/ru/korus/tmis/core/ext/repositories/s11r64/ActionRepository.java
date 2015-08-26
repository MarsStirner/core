package ru.korus.tmis.core.ext.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.korus.tmis.core.ext.entities.s11r64.Action;
import ru.korus.tmis.core.ext.entities.s11r64.vmp.Client_Quoting;

import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 14:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface ActionRepository extends JpaRepository<Action, Integer> {

}
