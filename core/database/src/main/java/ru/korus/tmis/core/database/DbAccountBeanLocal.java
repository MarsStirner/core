package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Account;
import ru.korus.tmis.core.entity.model.AccountAktInfo;
import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.OrgStructure;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.09.13, 12:18 <br>
 * Company: Korus Consulting IT <br>
 * Description: Интерфейс для работы со счетами<br>
 */
@Local
public interface DbAccountBeanLocal {

    /**
     * Получение счета по идентификатору
     * @param id   идентификатор
     * @return   Счет или null если не найдено
     */
    public Account getById(int id);

    /**
     * Получение всех счетов (даже где deleted = 1)
     * @return Список всех счетов  / пустой список
     */
    public List<Account> getAll();


    /**
     * Получение всех неудаленных счетов (deleted = 0)
     * @return Список  счетов / пустой список
     */
    public List<Account> getUndeletedOnly();

    /**
     * Удаление счета (проставление флага deleted = true)
     * @param toDelete  счет который необходимо удалить
     * @return  сущность
     */
    public Account deleteAccount(final Account toDelete);

    /**
     * Пересчитать данные счета (такие как amount, sum, uet) по сформированным позициям
     * @param account  счет, который будет пересчитан
     * @return  пересчитанный счет
     */
    Account recalculateAccount(final Account account);

    /**
     * Удаление счета из БД (@see @NamedQuery Account.deleteAccount)
     * @param toDelete  счет который необходимо удалить
     * @return  сущность
     */
    void removeAccount(final Account toDelete);

    /**
     * Получение счета по его номеру
     * @param number номер счета
     * @return счет \ null если нету счета с таким номером
     */
    Account getByNumber(final String number);

    /**
     * Запись в БД данных об оплате счета
     * @param account  счет
     * @param payDate  дата оплаты
     * @param payedSum оплаченная сумма
     * @param payedAmount количество оплаченных позиций
     * @param refusedSum отказанная сумма
     * @param refusedAmount количество отказанных позиций
     * @param comment комментарий
     */
    void pay(final Account account,
            final Date payDate,
            final double payedSum,
            final int payedAmount,
            final double refusedSum,
            final int refusedAmount,
            final String comment
    );

    /**
     * Создает и сохраняет в БД новый счет с заданными параметрами
     * @param contract  Контракт
     * @param orgStructure  Подразделение ЛПУ
     * @param payerId  Плательщик
     * @param formDate Дата формирования счета
     * @param number  Номер счета
     * @param amount Количество оказанных услуг (SLUCH count)
     * @param uet  Общее количество UET
     * @param summ Общая сумма счета
     * @param exposeDate Дата отправки результата в ТФОМС
     * @param begDate Начало периода за который сформирован счет
     * @param endDate Конец периода за который сформирован счет
     * @param format  Формат счета
     * @return
     */
    ru.korus.tmis.core.entity.model.Account  createNewAccount(
            final Contract contract,
            final OrgStructure orgStructure,
            final int payerId,
            final Date formDate,
            final String number,
            final double amount,
            final double uet,
            final double summ,
            final Date exposeDate,
            final Date begDate,
            final Date endDate,
            final Object format);

    /**
     * Метод вычисляет номер пакета
     * @param contractId Контракт
     * @param beginDate Дата начала интервала за который идет выгрузка
     * @param endDate Дата конца интервала за который идет выгрузка
     * @return номер пакета
     */
    int getPacketNumber(
            final int contractId,
            final Date beginDate,
            final Date endDate);

    void persistAccountAktInfo(final AccountAktInfo accountAktInfo);
}
