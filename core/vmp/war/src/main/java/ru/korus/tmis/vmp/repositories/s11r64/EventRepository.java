package ru.korus.tmis.vmp.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.korus.tmis.vmp.entities.s11r64.Event;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 15:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
}
