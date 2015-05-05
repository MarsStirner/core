package ru.korus.tmis.vmp.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.korus.tmis.vmp.entities.s11r64.Event;
import ru.korus.tmis.vmp.entities.s11r64.Mkb;

import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 15:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface MkbRepository extends JpaRepository<Mkb, Integer> {
    @Query("SELECT mkb FROM Mkb mkb WHERE mkb.diagID = :diagId")
    List<Mkb> findByDiagID(@Param(value = "diagId") String diagId);
}
