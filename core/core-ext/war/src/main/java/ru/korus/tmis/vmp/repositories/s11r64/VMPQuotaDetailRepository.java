package ru.korus.tmis.vmp.repositories.s11r64;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.korus.tmis.vmp.entities.s11r64.QuotaType;
import ru.korus.tmis.vmp.entities.s11r64.RbPacientModel;
import ru.korus.tmis.vmp.entities.s11r64.RbTreatment;
import ru.korus.tmis.vmp.entities.s11r64.VMPQuotaDetail;

import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 15:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface VMPQuotaDetailRepository extends JpaRepository<VMPQuotaDetail, Integer> {
    @Query("SELECT DISTINCT qt.quotaType FROM VMPQuotaDetail qt LEFT JOIN qt.mkbVmpquotaFilters f " +
           "WHERE f.mkb.id = :mkbId")
    List<QuotaType> findByMkb(@Param(value = "mkbId") Integer mkbId);

    @Query("SELECT DISTINCT qt.rbPacientModel FROM VMPQuotaDetail qt LEFT JOIN qt.mkbVmpquotaFilters f " +
            "WHERE f.mkb.id = :mkbId AND qt.quotaType.id = :quotaTypeId")
    List<RbPacientModel> findByMkbAndQuotaType(@Param(value = "mkbId") Integer mkbId,
                                               @Param(value = "quotaTypeId") Integer quotaTypeId);

    @Query("SELECT DISTINCT qt.rbPacientModel FROM VMPQuotaDetail qt LEFT JOIN qt.mkbVmpquotaFilters f " +
            "WHERE qt.quotaType.id = :quotaTypeId")
    List<RbPacientModel> findByQuotaType(@Param(value = "quotaTypeId") Integer quotaTypeId);

    @Query("SELECT DISTINCT qt.rbTreatment FROM VMPQuotaDetail qt WHERE qt.rbPacientModel.id = :patientModelId")
    List<RbTreatment> findTreatmentByPatientModelId(@Param(value = "patientModelId") Integer patientModelId);


    @Query("SELECT qt FROM VMPQuotaDetail qt " +
            "WHERE qt.rbPacientModel.id = :patientModelId AND qt.rbTreatment.id = :treatmentId")
    List<VMPQuotaDetail> findByPatientModelIdAndTreatmentId(@Param(value = "patientModelId") Integer patientModelId,
                                              @Param(value = "treatmentId") Integer treatmentId);

}
