package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface DbClientPolicyBeanLocal {

    Iterable<ClientPolicy> getAllPolicies(int patientId)
            throws CoreException;

    ClientPolicy getClientPolicyById(int id)
            throws CoreException;

    ClientPolicy insertOrUpdateClientPolicy(int id,
                                            int rbPolicyTypeId,
                                            int insurerId,
                                            String number,
                                            String serial,
                                            Date startDate,
                                            Date endDate,
                                            String name,
                                            String note,
                                            Patient patient,
                                            Staff sessionUser)
            throws CoreException;

    void deleteClientPolicy(int id, Staff sessionUser) throws CoreException;

    Boolean checkPolicyNumber(String number, String serial, int typeId) throws CoreException;

    ClientPolicy findBySerialAndNumberAndType(String serial, String number, int typeId);

    /**
     * Сохраняет в БД новый полис
     * @param policy полис для сохранений
     * @return сохраненная сущность
     */
    ClientPolicy persistNewPolicy(final ClientPolicy policy);

    /**
     * Поиск всех полисов, у которых совпадает серия, номер и код типа полиса
     * @param serial серия полиса
     * @param number номер полиса
     * @param typeCode код типа полиса
     * @return список полисов, удовлетворяющих этому условию \ пустой список
     */
    List<ClientPolicy> findBySerialAndNumberAndTypeCode(
            final String serial,
            final String number,
            final String typeCode);

    /**
     * Удаление всех полисов пациента в БД заданного типа (флажок deleted =1)
     * @param patientId идетификатор пациента
     * @param policyTypeCode код типа полиса
     * @return количество полисов у которых был проставлен флажок удаления
     */
    int deleteAllClientPoliciesByType(int patientId, String policyTypeCode);
}
