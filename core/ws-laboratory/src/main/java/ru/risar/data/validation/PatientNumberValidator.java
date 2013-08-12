package ru.risar.data.validation;

import ru.korus.tmis.core.entity.model.RbDocumentType;
import ru.korus.tmis.core.entity.model.RbPolicyType;
import ru.korus.validation.Validator;
import ru.risar.data.PatientNumber;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author anosov
 *         Date: 28.07.13 14:05
 */
public class PatientNumberValidator {

    Validator validator;
    List<RbDocumentType> documentDictionary;
    List<RbPolicyType> policyTypeDictionary;

    public PatientNumberValidator(Validator validator) {
        this.validator = validator;
    }

    public void initializeDocumentDictionary(final List<RbDocumentType> documentDictionary) {
        this.documentDictionary = documentDictionary;
    }

    public void initializePolicyTypeDictionary(final List<RbPolicyType> policyTypeDictionary) {
        this.policyTypeDictionary = policyTypeDictionary;
    }

    public void validateNumbers(final List<PatientNumber> patientNumbers) {
        for (final PatientNumber patientNumber : patientNumbers) {
            validateNumber(patientNumber);
        }
    }

    public void validateNumber(final PatientNumber patientNumber) {
        validateNumberType(patientNumber);
        validateNumberValue(patientNumber);
    }

    private void validateNumberType(final PatientNumber patientNumber) {
        final String numberType = patientNumber.getNumberType();
        boolean isExist = false;
        for (RbDocumentType documentType : documentDictionary) {
            isExist = numberType.equalsIgnoreCase(documentType.getName());

            if (numberType.equalsIgnoreCase("СНИЛС") || numberType.equalsIgnoreCase("ОМС")) {
                isExist = true;
            }
            if (isExist) {
                break;
            }
        }
        if (!isExist) {
            final StringBuilder errorText = new StringBuilder("Неизвестный тип документа [")
                    .append(numberType)
                    .append("]. Возможные варианты: \n ");
            for (RbDocumentType documentType : documentDictionary) {
                errorText.append(" \t").append(documentType.getName()).append("\n");
            }
            validator.addError(errorText.toString());
        }
    }

    private void validateNumberValue(final PatientNumber patientNumber) {
        final String numberType = patientNumber.getNumberType();
        final String number = patientNumber.getNumber();

        if (numberType.equalsIgnoreCase("СНИЛС")) {
            validateSNILS(number);
        } else if (numberType.equalsIgnoreCase("ОМС")) {
            validateOMS(patientNumber, number);
        } else if (numberType.equalsIgnoreCase("ПАСПОРТ РФ")) {
            validatePass(number);
        }
    }

    private void validatePass(String number) {
        final String pattern = "^\\d\\d\\d\\d[ ]\\d+$";
        final Matcher matcher = Pattern.compile(pattern).matcher(number);
        if (!matcher.find()) {
            validator.addError("Значение документа ПАСПОРТ РФ не соответсвует стандарту (" + number + ")");
        }
    }

    private void validateSNILS(String number) {
        final String pattern = "^\\d+[-]\\d+[-]\\d+[ ]\\d\\d$";
        final Matcher matcher = Pattern.compile(pattern).matcher(number);
        if (!matcher.find()) {
            validator.addError("Значение документа СНИЛС не соответсвует стандарту (" + number + ")");
        }
    }

    private void validateOMS(PatientNumber patientNumber, String number) {
        if (number.length() != 16) {
            validator.addError("Значение документа ОМС не соответсвует стандарту (" + number + ")");
        }
        final String policyType = patientNumber.getPolicyType();
        boolean isExist = false;
        for (RbPolicyType poliRbDocumentType : policyTypeDictionary) {
            if (policyType.equalsIgnoreCase(poliRbDocumentType.getName())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            final StringBuilder errorText = new StringBuilder("Неизвестный тип полиса ОМС [")
                    .append(policyType)
                    .append("]. Возможные варианты: \n ");
            for (RbPolicyType poliRbDocumentType : policyTypeDictionary) {
                errorText.append(" \t").append(poliRbDocumentType.getName()).append("\n");
            }
            validator.addError(errorText.toString());
        }
    }
}
