package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.09.13, 12:24 <br>
 * Company: Korus Consulting IT <br>
 * Description: Реализация работы со счетами <br>
 */
@Stateless
public class DbAccountBean implements DbAccountBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public Account getById(final int id) {
        return em.find(Account.class, id);
    }

    @Override
    public List<Account> getAll() {
        return em.createNamedQuery("Account.getAll", Account.class).getResultList();
    }

    @Override
    public List<Account> getUndeletedOnly() {
        return em.createNamedQuery("Account.getUndeleted", Account.class).getResultList();
    }

    @Override
    public Account deleteAccount(final Account toDelete) {
        toDelete.setDeleted(true);
        final Account result = em.merge(toDelete);
        //см апдейт dbtools 155 (повешен триггер на Account)
        //        em.createNamedQuery("AccountItem.deleteByAccountId", AccountItem.class)
        //                .setParameter("ACCOUNTID", toDelete.getId())
        //                .executeUpdate();
        em.flush();
        return result;
    }

    @Override
    public Account recalculateAccount(final Account account) {
        final Object[] resultSet = (Object[]) em.createNativeQuery(
                "SELECT COUNT(DISTINCT item.action_id), ROUND(SUM(item.price), 2), " +
                        " ROUND(SUM(IF(item.unit_id = 5, item.amount,0)), 2)"
                        + " FROM Account_Item item  WHERE item.master_id = ?"
        ).setParameter(1, account.getId())
                .getSingleResult();
        if(resultSet[0] != null ){
            account.setAmount(((Number) resultSet[0]).doubleValue());
        }
        if(resultSet[1] != null ){
            account.setSum(((Number) resultSet[1]).doubleValue());
        }
        if(resultSet[2] != null ){
            account.setUet(((Number) resultSet[2]).doubleValue());
        }
        em.merge(account);
        em.flush();
        return account;
    }

    @Override
    public void removeAccount(Account toDelete) {
        em.createNamedQuery("Account.deleteAccount")
                .setParameter("id", toDelete.getId())
                .executeUpdate();

    }

    @Override
    public Account getByNumber(final String number) {
        final List<Account> accountList = em.createNamedQuery("Account.getByNumber", Account.class)
                .setParameter("number", number)
                .getResultList();
        if(accountList.isEmpty()){
            return null;
        } else {
            return accountList.get(0);
        }
    }

    @Override
    public void pay(final Account account,
                    final Date payDate,
                    final double payedSum,
                    final int payedAmount,
                    final double refusedSum,
                    final int refusedAmount,
                    final String comment) {
        //При загрузке ответного файла не следует менять дату выставления счета (Account.exposeDate).
        //Данная дата должна устанавливатьс яравной текущей дате при создании счета и более не меняться.
        //account.setExposeDate(payDate);
        account.setPayedSum(payedSum);
        account.setPayedAmount((double)payedAmount);
        account.setRefusedSum(refusedSum);
        account.setRefusedAmount((double)refusedAmount);
        account.setNote(comment);
        em.merge(account);
    }


    @Override
    public ru.korus.tmis.core.entity.model.Account createNewAccount(
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
            final Object format) {
        ru.korus.tmis.core.entity.model.Account toPersist = new ru.korus.tmis.core.entity.model.Account();
        final Date now = new Date();
        toPersist.setCreateDatetime(now);
        toPersist.setCreatePerson(null);
        toPersist.setModifyDatetime(now);
        toPersist.setModifyPerson(null);

        toPersist.setDeleted(false);
        toPersist.setContract(contract);
        toPersist.setPayer(em.find(Organisation.class, payerId));
        toPersist.setSettleDate(formDate);
        toPersist.setNumber(number);
        toPersist.setDate(formDate);
        toPersist.setAmount(amount);
        toPersist.setUet(uet);
        toPersist.setSum(summ);
        toPersist.setExposeDate(exposeDate);

        toPersist.setPayedAmount(0.0);
        toPersist.setPayedSum(0.0);
        toPersist.setRefusedAmount(0.0);
        toPersist.setRefusedSum(0.0);
        toPersist.setBegDate(begDate);
        toPersist.setEndDate(endDate);
        toPersist.setNote("");
        em.persist(toPersist);
        return em.merge(toPersist);
    }

    @Override
    public int getPacketNumber(
            final int contractId,
            final Date beginDate,
            final Date endDate) {
        return ((Number) em.createNamedQuery("Account.getPacketNumber")
                .setParameter("begDate", beginDate)
                .setParameter("endDate", endDate)
                .setParameter("contractId", contractId)
                .getSingleResult()).intValue() + 1;
    }

    @Override
    public void persistAccountAktInfo(final AccountAktInfo accountAktInfo) {
        em.persist(accountAktInfo);
    }
}
