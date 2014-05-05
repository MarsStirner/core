package ru.korus.tmis.tariff;

import org.apache.thrift.TException;
import ru.korus.tmis.tariff.thriftgen.InvalidArgumentException;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 12:31 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public enum TariffServiceErrors {

    NO_SUCH_CONTRACT(new InvalidArgumentException().setCode(1).setMessage("Invalid contract_id: No such contract."));

    private TException exception;

    private TariffServiceErrors(final TException exception) {
        this.exception = exception;
    }

    public TException getException() {
        return exception;
    }

    public String getMessageName() {
        return this.name();
    }
}
