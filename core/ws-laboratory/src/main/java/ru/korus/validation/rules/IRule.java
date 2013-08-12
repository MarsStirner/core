package ru.korus.validation.rules;

/**
 * Интерфейс для правила валидрования
 *
 * @author anosov@outlook.com
 *         date: 5/29/13
 */
public interface IRule<T> {

    void apply(final T value);
}
