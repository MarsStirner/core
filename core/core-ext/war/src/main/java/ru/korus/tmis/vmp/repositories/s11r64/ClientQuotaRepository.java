package ru.korus.tmis.vmp.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.korus.tmis.vmp.entities.s11r64.Client_Quoting;

import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 15:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface ClientQuotaRepository extends JpaRepository<Client_Quoting, Integer> {
    @Query("SELECT cq FROM Client_Quoting cq WHERE cq.event.id = :eventId")
    List<Client_Quoting> findByEventId(@Param(value = "eventId") Integer eventId);

    @Query("SELECT cq FROM Client_Quoting cq WHERE cq.event.client.id = :clientId ORDER BY cq.event.createDatetime ASC")
    List<Client_Quoting> findByClientIdOrderByTime(@Param(value = "clientId") Integer clientId);
}
