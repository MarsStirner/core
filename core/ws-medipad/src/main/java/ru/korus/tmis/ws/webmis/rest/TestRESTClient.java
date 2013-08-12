package ru.korus.tmis.ws.webmis.rest;

import java.io.Serializable;

/**
 * Методы для тестирования REST-сервисов
 * Author: idmitriev Systema-Soft
 * Date: 5/13/13 12:56 PM
 * Since: 1.0.1.10
 */
public interface TestRESTClient extends Serializable {

    String makeTestRESTData();
}
