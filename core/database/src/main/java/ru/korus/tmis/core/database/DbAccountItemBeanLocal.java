package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.AccountItem;
import ru.korus.tmis.core.entity.model.RbPayRefuseType;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.09.13, 13:05 <br>
 * Company: Korus Consulting IT <br>
 * Description: Интерфейс для работы с позициями счета<br>
 */
@Local
public interface DbAccountItemBeanLocal {
    /**
     * Получение позиции счета по идентификатору
     * @param id  идентификатор позиции счета
     * @return  Позиция счета \ null
     */
    public AccountItem getById(int id);

    /**
     * Получение позиций счета по идентификатору счета
     * @param accountId  идентификатор счета
     * @return  список позиций счета для заданного счета \ пустой список
     */
    public List<AccountItem> getByAccountId(int accountId);

    /**
     * Получение позиций счета по идентификатору действия
     * @param actionId  идентификатор действия
     * @return  список позиций счета для заданного действия \ пустой список
     */
    public List<AccountItem> getByActionId(int actionId);

    /**
     * Сохранение новой позиции счета в БД
     * @param toPersist новая позиция счета
     * @return сохраненная позиция счета (с присвоеным идентификатором) (merge)
     */
    public AccountItem persistNewItem(AccountItem toPersist);

    /**
     * Перевыствление позиций счета
     * @param newItemId идентификатор новой позиции счета
     * @param actionId действие, для которого требуется перевыставить позиции счета
     * @return  количество позициий счета в которых было изменено поле AccountItem.reexposeItemId
     */
    int reexposeItems(final int newItemId, final int actionId);

    /**
     * отказ в оплате позиции счета и сохранение отказа в БД
     * @param accountItem позиция счета
     * @param refuseType причина отказа в оплате
     * @param number Номер документа подтверждающего оплату или отказ
     * @param payDate   Дата оплаты или отказа
     */
    void refuse(final AccountItem accountItem, final RbPayRefuseType refuseType, final String number, final Date payDate, final String comment);

    /**
     * оплата позиции счета
     * @param accountItem  позиция счета
     * @param number   Номер документа подтверждающего оплату или отказ
     * @param payDate  Дата оплаты или отказа
     */
    void pay(final AccountItem accountItem, final String number, final Date payDate, final String comment);

    /**
     * Меняет сущность в БД
     * @param item  сущность, на которую надо заменить
     * @return измененная сущность
     */
    AccountItem merge(final AccountItem item);
}
