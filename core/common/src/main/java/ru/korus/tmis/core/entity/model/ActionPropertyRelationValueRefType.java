package ru.korus.tmis.core.entity.model;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/28/14
 * Time: 5:17 PM
 */

/**
 * Тип значения отношения между свойствами действия, в текущем виде указывает имя таблицы, на которую
 * ссылается идентификатор {@link ActionPropertyRelationValue#valueReference()}
 */
public enum ActionPropertyRelationValueRefType { FlatDirectory, Action, Event }
