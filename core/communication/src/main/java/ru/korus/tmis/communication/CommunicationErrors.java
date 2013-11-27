package ru.korus.tmis.communication;

/**
 * User: EUpatov<br>
 * Date: 27.02.13 at 20:34<br>
 * Company Korus Consulting IT<br>
 */
public enum CommunicationErrors {
    /**
     * Все ок
     */
    msgOk(100, "ok."),

    /**
     * пациента искали - и не нашли
     */
    msgNoSuchPatient(200, "Пациент не зарегистрирован в выбранном ЛПУ"),

    /**
     * пациента искали - и нашли более 1
     */
    msgTooManySuchPatients(201, "Слишком много похожих пациентов"),

    /**
     * указанного типа идентификатора пациента не существует
     */
    msgNoSuchIdentifierType(202, "указанного типа идентификатора пациента не существует"),

    /**
     * передали id пациента - а такой записи нет (или она удалена)
     */
    msgWrongPatientId(302, "Некорректный ID пациента"),

    /**
     * пациент отмечен как умерший
     */
    msgPatientMarkedAsDead(303, "пациент отмечен как умерший"),

    /**
     * пациент не подходит по полу или возрасту
     */
    msgEnqueueNotApplicable(304, "пациент не подходит по полу или возрасту"),

    /**
     * очереди (записи) у указанного врача на указанную дату нет
     */
    msgQueueNotFound(305, "очереди (записи) у указанного врача на указанную дату нет"),

    /**
     * в очереди нет приёма на это время
     */
    msgTicketNotFound(306, "в очереди нет приёма на это время"),

    /**
     * пациент уже записан
     */
    msgPatientAlreadyQueued(307, "Пациент уже записан"),

    /**
     * талон уже занят
     */
    msgTicketIsBusy(308, "Талончик занят"),

    /**
     * указанная запись на приём к врачу не найдена
     */
    msgPatientQueueNotFound(309, "указанная запись на приём к врачу не найдена"),

    /**
     * пациент не имеет прикрепления
     */
    msgPatientNotAttached(310, "пациент не имеет прикрепления"),

    /**
     * постановка в очередь запрещена
     */
    msgQueueingProhibited(400, "постановка в очередь запрещена"),

    /**
     * квота исчерпана
     */
    msgNoTicketsAvailable(401, "квота исчерпана"),

    //////////////////////////////////////////////////////////////////////
    // New error messages
    //////////////////////////////////////////////////////////////////////

    //При поиске обязательно нужно указывать {полис\документ\Id пациета}
    /**
     * Не  прикреплено ни одного документа
     */
    msgNoDocumentsAttached(701, "Не  прикреплено ни одного документа"),

    /**
     * Нет такого врача или он удален
     */
    msgWrongDoctorId(702, "Нет такого врача или он удален"),

    /**
     * Запрошенный талончик не принадлежит этому пациенту
     */
    msgTicketIsNotBelongToPatient(703, "Запрошенный талончик не принадлежит этому пациенту"),

    /**
     * Ничего не найдено
     */
    msgItemNotFound(704, "Ничего не найдено"),


    /**
     * Метод не реализован (заглушка)
     */
    msgNotImplemented(501, "Метод не реализован (заглушка)"),

    /**
     * Неизвестная ошибка (смотреть логи сервера)
     */
    msgUnknownError(666, "Неизвестная ошибка (смотреть логи сервера)"),

    msgInvalidPersonalInfo(1, "Некорректные личные данные"),

    msgPatientAlreadyQueuedToTime(311, "За выбранный промежуток времени у пациента есть запись к другому врачу");


    private final int id;
    private final String message;

    private CommunicationErrors(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getId() + " " + getMessage();
    }

    public String getMessageName() {
        return this.name();
    }
}

