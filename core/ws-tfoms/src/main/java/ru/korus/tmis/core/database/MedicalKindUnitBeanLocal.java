package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 26.06.13, 13:59 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface MedicalKindUnitBeanLocal {
    /**
     * Возвращает все строки из Таблицы MedicalKindUnit, у которых eventType_id = запрошенному
     *
     * @param eventType запрошенный тип события
     * @return Список строк подходящих по типу события
     */
    public List<MedicalKindUnit> getByEventType(EventType eventType);


    /**
     * Возвращает все строки из Таблицы MedicalKindUnit, у которых rbMedicalKind_id = запрошенному
     *
     * @param kind запрошенный вид медицинской помощи
     * @return Список строк подходящих по виду медицинской помощи
     * @throws CoreException
     */
    public List<MedicalKindUnit> getByMedicalKind(RbMedicalKind kind)
            throws CoreException;


    public List<MedicalKindUnit> getByEventTypeAndUnitCode(EventType eventType, String unitCode);

    /**
     * Получение записей типов тарифа по виду мед помощи и единице учета  мед помощи
     * @param medicalKind  вид мед помощи
     * @param aidUnit  удиница учета мед помощи
     * @return список типов тарифа \ пустой список
     */
    public List<RbTariffType> getTariffTypeListByMedicalKindAndMedicalAidUnit(final RbMedicalKind medicalKind, final RbMedicalAidUnit aidUnit);

    /**
     * Получение списка связей категории помощи и способов оплат (MedicalKindUnit) по категории помощи и единице учета
     * @param medicalKind  категория помощи
     * @param aidUnit   единица учета мед помощи
     * @return   список\ пустой список
     */
    public List<MedicalKindUnit> getMedicalKindUnitListByMedicalKindAndMedicalAidUnit(
            final RbMedicalKind medicalKind,
            final RbMedicalAidUnit aidUnit
    );

    /**
     * Получение типа тарификации по типу обращения (EventType), категории помощи (RbMedicalKind) и
     * единице учета мед помощи (RbMedicalAidUnit)
     * @param eventType   тип обращения
     * @param medicalKind  категория помощи
     * @param aidUnit единица учета мед помощи
     * @return  тип тарификации, если найдено несколько различных типов или не найдено ни одного - null
     */
    RbTariffType getTariffTypeByEventTypeAndMedicalKindAndMedicalAidUnit(
            final EventType eventType,
            final RbMedicalKind medicalKind,
            final RbMedicalAidUnit aidUnit
    );
}
