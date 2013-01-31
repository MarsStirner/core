package ru.korus.tmis.ws.transfusion.order;

import ru.korus.tmis.ws.transfusion.IssueResult;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.2013, 10:25:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Регистрация извещения о резульатах выполнения требования КК
 */
public class RegOrderIssueResult {

    enum BloodCompError {
        OK(""), ERR_TYPE_ID("blood type id"), ERR_GROUP_ID("blood group"), ERR_RHESUS_FACTOR("rhesus factor");
        String descr;

        BloodCompError(String descr) {
            this.descr = descr;
        }

        @Override
        public String toString() {
            return descr;
        }

    }

    /**
     * Регистрация извещения о резульатах выполнения требования КК
     * 
     * @param orderIssue
     *            - входные данные от подсистемы ТРФУ
     * @return результат регистрации
     */
    public IssueResult save(OrderIssueInfo orderIssue) {
        IssueResult res = new IssueResult();
        if (orderIssue == null) {
            res.setDescription("illegal input argument");
            return res;
        }

        res.setRequestId(orderIssue.getRequestId());
        Integer actionId = getAction(orderIssue.getRequestId());

        if (actionId == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The issue for requestId %i has been not found in MIS", orderIssue.getRequestId()));
            return res;
        }

        BloodCompError er = checkBloodComponentProp(orderIssue);
        if (er != BloodCompError.OK) { // некорректные паспортные данные
                                       // выданных
                                       // компонентов крови
            res.setDescription(String.format("Incorrect value for blood component '%s'", er.toString()));
            return res;
        }

        update(actionId, orderIssue);

        return res;
    }

    /**
     * @param actionId
     * @param orderIssue
     */
    private void update(Integer actionId, OrderIssueInfo orderIssue) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param orderIssue
     * @return
     */
    private BloodCompError checkBloodComponentProp(OrderIssueInfo orderIssue) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param requestId
     * @return
     */
    private Integer getAction(Integer requestId) {
        // TODO Auto-generated method stub
        return null;
    }
}
