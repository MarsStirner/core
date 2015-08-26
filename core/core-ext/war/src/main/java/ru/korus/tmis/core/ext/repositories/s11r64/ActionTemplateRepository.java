package ru.korus.tmis.core.ext.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.korus.tmis.core.ext.entities.s11r64.ActionTemplate;

import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.05.2015, 18:55 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface ActionTemplateRepository  extends JpaRepository<ActionTemplate, Integer> {

    @Query("SELECT at FROM ActionTemplate at WHERE (:actionTypeId is null OR at.action.actionType_id = :actionTypeId)" +
            "AND (:personId is null OR at.ownerId = :personId) " +
            "AND (:groupId is null OR at.group.id = :groupId) " +
            "AND (:specialityId is null OR at.specialityId = :specialityId)")
    List<ActionTemplate> findByActionTypeAndPersonAdnGroupAndSspeciality(@Param("actionTypeId") Integer actionTypeId,
                                                                   @Param("personId") Integer personId,
                                                                   @Param("groupId") Integer groupId,
                                                                   @Param("specialityId") Integer specialityId);
}
