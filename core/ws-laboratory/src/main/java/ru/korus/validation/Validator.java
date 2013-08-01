package ru.korus.validation;

import ru.korus.validation.rules.IRule;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Валидатор для входных параметров результатов анализов
 *
 * @author anosov@outlook.com
 *         date: 5/29/13
 */
public class Validator {

    private List<String> errors;

    public Validator() {
        this.errors = new ArrayList<String>();
    }

    public static String getRequiredMessageError(final Object... args) {
        return Message.REQUIRED.getMessage(args);
    }

    public static String getTopRangeMessageError(final Object... args) {
        return Message.UNBOUND_TOP_RANGE.getMessage(args);
    }

    public static String getBottomRangeMessageError(final Object... args) {
        return Message.UNBOUND_TOP_RANGE.getMessage(args);
    }

    public <T> void validate(final T field, final IRule<T> rule) {
        rule.apply(field);
    }

    public void addError(final String errorText) {
        errors.add(errorText);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean isValid() {
        return !hasErrors();
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFullMessageError() {
        final StringBuilder sb = new StringBuilder("Validation error. Please see details.\n Error validation list:\n");
        for (String error : errors) {
            sb.append(error).append("\n");
        }
        return sb.toString();
    }

    public enum Message {
        UNBOUND_BOTTOM_RANGE("Длина или значение поля {0} не должно быть меньше {1}"),
        UNBOUND_TOP_RANGE("Длина или значение поля {0} не должно быть больше {1}"),
        REQUIRED("Поле {0} должно быть обязательно для заполнения");
        private String message;

        Message(final String message) {
            this.message = message;
        }

        public String getMessage(final Object... args) {
            return MessageFormat.format(this.message, args);
        }
    }
}
