package ru.korus.tmis.core.ext.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korus.tmis.core.ext.entities.s11r64.Event;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 15:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
}
