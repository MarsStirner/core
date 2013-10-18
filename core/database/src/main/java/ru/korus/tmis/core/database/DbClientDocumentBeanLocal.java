package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ClientDocument;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface DbClientDocumentBeanLocal {

    Iterable<ClientDocument> getAllDocuments(int patientId)
            throws CoreException;

    ClientDocument getClientDocumentById(int id)
            throws CoreException;


    boolean isClientDocumentExists(int id)
            throws CoreException;

    ClientDocument insertOrUpdateClientDocument(
            int id,
            int rbDocumentTypeId,
            String issued,
            String number,
            String serial,
            Date startDate,
            Date endDate,
            Patient patient,
            Staff sessionUser)
            throws CoreException;

    void deleteClientDocument(int id,
                              Staff sessionUser)
            throws CoreException;

    /**
     * Сохраняет в БД новый документ
     * @param document документ, который нужно сохранить
     * @return сохраненный документ
     */
    ClientDocument persistNewDocument(final ClientDocument document);

    /**
     * Поиск документа по серии, номеру и коду типа документа
     * @param serial серия документа
     * @param number номер документа
     * @param typeCode код типа документа
     * @return список документов \ пустой список
     */
    List<ClientDocument> findBySerialAndNumberAndTypeCode(final String serial, final String number, final String typeCode);
}
