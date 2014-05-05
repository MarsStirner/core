package ru.korus.tmis.tfoms;

import org.apache.thrift.TException;
import ru.korus.tmis.tfoms.thriftgen.*;


/**
 * Author: Upatov Egor <br>
 * Date: 16.07.13, 15:24 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public enum TFOMSErrors {
    POLICY_ALREADY_EXISTS(new InvalidArgumentException().setCode(1).setMessage("This combination of serial/number/policyType is already exists")),
    POLICY_NO_SUCH_PATIENT(new InvalidArgumentException().setCode(2).setMessage("No such patient")),
    POLICY_NO_SUCH_POLICY_TYPE(new InvalidArgumentException().setCode(3).setMessage("No such policyTypeCode")),
    POLICY_UNKNOWN(new SQLException().setCode(99).setMessage("Unknown Exception")),
    ////////////////////////////////////////////////////////////////////////////////
    INVALID_DATES(new InvalidArgumentException().setMessage("begDate is after endDate").setCode(8)),
    ////////////////////////////////////////////////////////////////////////////////
    ACCOUNT_NOT_EXISTS(new NotFoundException().setMessage("No such Account exists").setCode(9)),
    ORGANISATION_NOT_EXISTS(new InvalidOrganizationInfisException().setMessage("Organisation with this infis code not exists in underlying database").setCode(10)),
    CONTRACT_NOT_EXISTS(new InvalidContractException().setMessage("No such Contract exists").setCode(11)),
    SMOMUMBER_INCORRECT(new InvalidArgumentException().setMessage("Smo Number is incorrect").setCode(12)),
    EMPTY_REGISTRY(new NotFoundException().setMessage("Result is empty. Maybe you chose invalid dates.").setCode(13)),
    ACCOUNT_NOT_PRIMARY(new InvalidArgumentException().setMessage("This is not primary upload").setCode(14)),
    ACCOUNT_NOT_SECONDARY(new InvalidArgumentException().setMessage("This is primary upload").setCode(15)),
    ORGSTRUCTURE_ID_LIST_INCORRECT(new InvalidArgumentException().setMessage("No one of orgStructureId in list is correct").setCode(16));


    private TException exception;

    private TFOMSErrors(final TException exception) {
        this.exception = exception;
    }

    public TException getException() {
        return exception;
    }

    public String getMessageName() {
        return this.name();
    }
}
