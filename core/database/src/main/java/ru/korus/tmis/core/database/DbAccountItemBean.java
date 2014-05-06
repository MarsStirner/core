package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.AccountItem;
import ru.korus.tmis.core.entity.model.RbPayRefuseType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 06.09.13, 13:11 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbAccountItemBean implements DbAccountItemBeanLocal {
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public AccountItem getById(final int id) {
        return em.find(AccountItem.class, id);
    }

    @Override
    public List<AccountItem> getByAccountId(final int accountId) {
        return em.createNamedQuery("AccountItem.getByAccountId", AccountItem.class)
                .setParameter("ACCOUNTID", accountId)
                .getResultList();
    }

    @Override
    public List<AccountItem> getByActionId(final int actionId) {
        return em.createNamedQuery("AccountItem.getByActionId", AccountItem.class)
                .setParameter("ACTIONID", actionId)
                .getResultList();
    }

    @Override
    public AccountItem persistNewItem(final AccountItem toPersist) {
        em.persist(toPersist);
        em.flush();
        return toPersist;
    }

    @Override
    public int reexposeItems(final int newItemId, int actionId) {
        return em.createNamedQuery("AccountItem.reexposeAllByActionId")
                .setParameter("actionId", actionId)
                .setParameter("reexposeId", newItemId)
                .executeUpdate();
    }

    @Override
    public void refuse(
            final AccountItem accountItem,
            final RbPayRefuseType refuseType,
            final String number,
            final Date payDate,
            final String comment) {
        accountItem.setNumber(number);
        accountItem.setDate(payDate);
        accountItem.setRefuseType(refuseType);
        accountItem.setNote(comment != null ? comment : "");
        em.merge(accountItem);
    }

    @Override
    public void pay(final AccountItem accountItem, final String number, final Date payDate, final String comment) {
        accountItem.setNumber(number);
        accountItem.setDate(payDate);
        accountItem.setNote(comment != null ? comment : "");
        em.merge(accountItem);
    }

    /**
     * Меняет сущность в БД
     *
     * @param item сущность, на которую надо заменить
     * @return измененная сущность
     */
    @Override
    public AccountItem merge(final AccountItem item) {
        final AccountItem merged = em.merge(item);
        em.flush();
        return merged;
    }

}
