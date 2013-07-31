package ru.risar.data.validation;

import org.apache.commons.lang.StringUtils;
import ru.korus.validation.Validator;
import ru.korus.validation.rules.IRule;
import ru.risar.data.Gender;
import ru.risar.data.Name;
import ru.risar.data.PatientNumber;

import java.util.Date;
import java.util.List;

import static ru.korus.validation.Validator.getRequiredMessageError;

/**
 * @author anosov
 *         Date: 28.07.13 14:00
 */
public class PatientValidator {
    Validator validator;

    public PatientValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Валидация имени
     * <p>
     * <name>
     * <familyName>Петрова</familyName>
     * <givenName>Любовь</givenName>
     * <middleName>Ивановна</middleName>
     * </name>
     * </p>
     *
     * @param name - полное имя пациента
     */
    public void validateName(final Name name) {
        validator.validate(name.getFamilyName(), new IRule<String>() {
            @Override
            public void apply(final String value) {
                if (StringUtils.isEmpty(value)) {
                    validator.addError(getRequiredMessageError("familyName"));
                }
            }
        });
        validator.validate(name.getGivenName(), new IRule<String>() {
            @Override
            public void apply(final String value) {
                if (StringUtils.isEmpty(value)) {
                    validator.addError(getRequiredMessageError("givenName"));
                }
            }
        });
        validator.validate(name.getMiddleName(), new IRule<String>() {
            @Override
            public void apply(final String value) {
                if (StringUtils.isEmpty(value)) {
                    validator.addError(getRequiredMessageError("middleName"));
                }
            }
        });
    }

    /**
     * Валидация пол пациента
     * Доступны только F или M
     * <p>
     * <gender>M</gender>
     * </p>
     *
     * @param gender - пол пациента
     */
    public void validateGender(final Gender gender) {
        validator.validate(gender, new IRule<Gender>() {
            @Override
            public void apply(final Gender value) {
                final String code = value.code();
                if (!code.equalsIgnoreCase("F") &&
                        !code.equalsIgnoreCase("M")) {
                    validator.addError("Неизвестный пол (" + code + "). Доступны только F или M.");
                }
            }
        });
    }

    /**
     * Валидация даты рождения пациента
     * <p>
     * <BirthTime>1989-11-15T00:00:00Z</BirthTime>
     * </p>
     *
     * @param birthTime - дата рождения пациента
     */
    public void validateBirthTime(final Date birthTime) {
        validator.validate(birthTime, new IRule<Date>() {
            @Override
            public void apply(final Date value) {
                if (value == null) {
                    validator.addError(getRequiredMessageError("birthTime"));
                }
            }
        });
    }


    /**
     * Вместе с ФИО и датой рождения всегда должен быть передан как минимум один документ.
     * <p>
     * <PatientNumbers>
     *      <PatientNumber>
     *          <Number>112-277-324 35</Number> <!-- Валидировать регулярным выражением. Выбрасывать исключение, если не провалидировано. -->
     *          <NumberType>СНИЛС</NumberType>
     *      </PatientNumber>
     *      ...
     * </PatientNumbers>
     * </p>
     *
     * @param patientNumbers - список документов пациента
     */
    public void validatePatientNumbers(final List<PatientNumber> patientNumbers) {
        validator.validate(patientNumbers, new IRule<List<PatientNumber>>() {
            @Override
            public void apply(final List<PatientNumber> value) {
                if (value.isEmpty()) {
                    validator.addError(getRequiredMessageError("patientNumbers"));
                }
            }
        });
    }
}