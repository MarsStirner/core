package ru.korus.tmis.core.ext.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korus.tmis.core.ext.entities.s11r64.Event;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 14:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface EventRepository extends JpaRepository<Event, Integer> {

}
