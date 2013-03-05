package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.HospitalBedData;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.OrgStructureHospitalBed;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Методы для работы с коечным фондом
 */
@Local
public interface HospitalBedBeanLocal {

    /**
     * Регистрация пациента на койке.
     * @param eventId Идентификатор обращения.
     * @param data Данные об регистрации на койке как HospitalBedData.
     * @param authData Авторизационные данные регестрирующего как AuthData.
     * @return Действие в рамках которого произошла регистрация.
     * @throws CoreException
     * @see HospitalBedData
     * @see AuthData
     */
    Action registryPatientToHospitalBed(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    /**
     * Редактирование регистрации пациента на койке.
     * @param actionId Идентификатор действия пациента.
     * @param data Данные об регистрации на койке как HospitalBedData.
     * @param authData Авторизационные данные регестрирующего как AuthData.
     * @return
     * @throws CoreException
     * @see HospitalBedData
     * @see AuthData
     */
    Action modifyPatientToHospitalBed(int actionId, HospitalBedData data, AuthData authData) throws CoreException;

    /**
     * Направление/перевод пациента в отделение
     * @param eventId Идентификатор обращения.
     * @param data Данные об переводе пациента как HospitalBedData.
     * @param authData Авторизационные данные регестрирующего как AuthData.
     * @return
     * @throws CoreException
     * @see HospitalBedData
     * @see AuthData
     */
    Action movingPatientToDepartment(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    /**
     * Получение данных о действии типа 'Движение'. Основной вид.
     * @param action Действие
     * @param authData Авторизационные данные как AuthData.
     * @return Данные о действии типа 'Движение' как HospitalBedData
     * @throws CoreException
     * @see HospitalBedData
     * @see AuthData
     */
    HospitalBedData getRegistryOriginalForm(Action action, AuthData authData) throws CoreException;

    /**
     * Получение данных о действии типа 'Движение' со списком занятых/свободных коек отделения
     * @param action Действие
     * @param authData Авторизационные данные как AuthData.
     * @return Данные о действии типа 'Движение' как HospitalBedData
     * @throws CoreException
     * @see HospitalBedData
     * @see AuthData
     */
    Object getRegistryFormWithChamberList(Action action, AuthData authData) throws CoreException;

    /**
     * Запрос на занятые койки в отделении.
     * @param departmentId Идентификатор отделения.
     * @return Сортированный список идентификаторов коек и статусом их занятости.
     * @throws CoreException
     */
    java.util.LinkedHashMap<OrgStructureHospitalBed, Boolean> getCaseHospitalBedsByDepartmentId(int departmentId) throws CoreException;

    /**
     * Список движений пациента
     * @param filter Данные о фильтрации значений как Object
     * @param authData Авторизационные данные как AuthData.
     * @return
     * @throws CoreException
     * @see HospitalBedData
     * @see AuthData
     */
    HospitalBedData getMovingListByEventIdAndFilter(Object filter, AuthData authData) throws CoreException;

    /**
     * Отказ от регистрации на койке
     * @param actionId Идентификатор действия регистрации.
     * @param authData Авторизационные данные как AuthData.
     * @see AuthData
     * @return
     * @throws CoreException
     */
    boolean callOffHospitalBedForPatient(int actionId, AuthData authData) throws CoreException;

    /**
     * Запрос на последнее по ивенту действие типа Движение
     * @param eventId Идентификатор ивента.
     * @see AuthData
     * @return
     * @throws CoreException
     */
    Action getLastMovingActionForEventId(int eventId) throws CoreException;
}
