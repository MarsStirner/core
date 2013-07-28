package ru.sda.data.validation;

import ru.korus.tmis.core.entity.model.RbDocumentType;
import ru.korus.validation.Validator;
import ru.sda.data.PatientNumber;

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

    public PatientNumberValidator(Validator validator) {
        this.validator = validator;
    }

    public void initializeDocumentDictionary(final List<RbDocumentType> documentDictionary) {
        this.documentDictionary = documentDictionary;
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
        }
        if (!isExist) {
            final StringBuilder errorText = new StringBuilder("Неизвестный тип документа. Возможные варианты: \n ");
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
            final String pattern = "^\\d+[-]\\d+[-]\\d+[ ]\\d\\d$";
            final Matcher matcher = Pattern.compile(pattern).matcher(number);
            if (!matcher.find()) {
                validator.addError("Значение документа СНИЛС не соответсвует стандарту (" + number + ")");
            }
        } else if (numberType.equalsIgnoreCase("ОМС")) {
            if (number.length() != 16) {
                validator.addError("Значение документа ОМС не соответсвует стандарту (" + number + ")");
            }
        } else if (numberType.equalsIgnoreCase("ПАСПОРТ РФ")) {
            final String pattern = "^\\d\\d\\d\\d[ ]\\d+$";
            final Matcher matcher = Pattern.compile(pattern).matcher(number);
            if (!matcher.find()) {
                validator.addError("Значение документа ПАСПОРТ РФ не соответсвует стандарту (" + number + ")");
            }
        }
    }
}
