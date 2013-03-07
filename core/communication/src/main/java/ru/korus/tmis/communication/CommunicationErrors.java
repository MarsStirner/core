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
    msgNoSuchPatient(200, "no such patient."),

    /**
     * пациента искали - и нашли более 1
     */
    msgTooManySuchPatients(201, "too many such patients."),

    /**
     * указанного типа идентификатора пациента не существует
     */
    msgNoSuchIdentifierType(202, "no such identifier type."),

    /**
     * передали id пациента - а такой записи нет (или она удалена)
     */
    msgWrongPatientId(302, "wrong patient id."),

    /**
     * пациент отмечен как умерший
     */
    msgPatientMarkedAsDead(303, "patient marked as dead."),

    /**
     * пациент не подходит по полу или возрасту
     */
    msgEnqueueNotApplicable(304, "enqueue not applicable."),

    /**
     * очереди (записи) у указанного врача на указанную дату нет
     */
    msgQueueNotFound(305, "queue not found."),

    /**
     * в очереди нет приёма на это время
     */
    msgTicketNotFound(306, "ticket not found."),

    /**
     * пациент уже записан
     */
    msgPatientAlreadyQueued(307, "patient already queued."),

    /**
     * талон уже занят
     */
    msgTicketIsBusy(308, "ticket is busy."),

    /**
     * указанная запись на приём к врачу не найдена
     */
    msgPatientQueueNotFound(309, "patient queue not found."),

    /**
     * пациент не имеет прикрепления
     */
    msgPatientNotAttached(310, "patient not attached."),

    /**
     * постановка в очередь запрещена
     */
    msgQueueingProhibited(400, "queueing prohibited."),

    /**
     * квота исчерпана
     */
    msgNoTicketsAvailable(401, "queueing prohibited."),

    //////////////////////////////////////////////////////////////////////
    // New error messages
    //////////////////////////////////////////////////////////////////////

    //При поиске обязательно нужно указывать {полис\документ\Id пациета}
    /**
     * Не  прикреплено ни одного документа
     */
    msgNoDocumentsAttached(701, "no one document is attached."),

    /**
     * Нет такого врача или он удален
     */
    msgWrongDoctorId(702, "wrong doctor Id."),

    /**
     * Запрошенный талончик не принадлежит этому пациенту
     */
    msgTicketIsNotBelongToPatient(703, "this ticket has another owner."),

    /**
     * Ничего не найдено
     */
    msgItemNotFound(704, "no one item was found."),


    /**
     * Метод не реализован (заглушка)
     */
    msgNotImplemented(501, "this method is not implemented now."),

    /**
     * Неизвестная ошибка (смотреть логи сервера)
     */
    msgUnknownError(666, "UnknownError, please watch server logs.");


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

