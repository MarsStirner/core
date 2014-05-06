package ru.korus.tmis.tariff;

import ru.korus.tmis.tariff.thriftgen.*;
import ru.korus.tmis.tariff.thriftgen.Error;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 14:22 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public enum TariffError {
    C_TAR_LENGTH(new Error().setCode((short)1).setMessage("C_TAR length is not 18 symbols")),
    MEDICAL_KIND_CODE_INCORRECT(new Error().setCode((short)2).setMessage("rbMedicalKind is not found by code")),
    SERVICE_FINANCE_CODE_INCORRECT(new Error().setCode((short)3).setMessage("rbServiceFinance is not found by code")),
    MEDICAL_AID_UNIT_CODE_INCORRECT(new Error().setCode((short)4).setMessage("rbMedicalAidUnit is not found by code")),
    SEX_AND_AGE_LIST_EMPTY(new Error().setCode((short)5).setMessage("No one pair of sex and age founded")),
    SERVICES_EMPTY(new Error().setCode((short)6).setMessage("No one rbService founded")),
    MKU_EMPTY(new Error().setCode((short)7).setMessage("No one pair of this MedicalKind and MedicalAidUnit and internal EventType found")),
    CONTRACT_SPECIFICATION_NOT_FOUND(new Error().setCode((short)7).setMessage("Contract hasnt required specification")),
    NO_SERVICE(new Error().setCode((short) 8).setMessage("No one rbService found"));

    private Error error;

    private TariffError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
