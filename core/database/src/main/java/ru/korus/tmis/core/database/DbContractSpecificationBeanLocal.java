package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.ContractSpecification;
import ru.korus.tmis.core.entity.model.EventType;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 09.08.13, 16:35 <br>
 * Company: Korus Consulting IT <br>
 * Description: Бин для работы со спецификациями контракта<br>
 */
@Local
public interface DbContractSpecificationBeanLocal {

    /**
     * Получение спецификации по ее идентификатору
     * @param id  идентификатор спецификации
     * @return    спецификация контракта \ NULL
     */
    public ContractSpecification getById(int id);

    /**
     * Получение спецификаций для заданного контракта
     * @param contract  заданный контракт
     * @return список спецификаций \ пустой список
     */
    public List<ContractSpecification> getActiveByContract(final Contract contract);

    /**
     * Получение списка типов обращений (EventType) по неудаленным записям спецификации контракта
     * @param contract контракт, спецификации которого будут использованы для выбора типов обращений
     * @return список типов обращений \ пустой список
     */
    public List<EventType> getEventTypeListByContract(final Contract contract);

}
