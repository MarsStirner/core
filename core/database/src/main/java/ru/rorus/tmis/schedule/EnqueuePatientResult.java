package ru.rorus.tmis.schedule;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.12.13, 18:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */


public class EnqueuePatientResult {

    private boolean success;
    private String message;
    private int index;
    private int queueId;

    public boolean isSuccess() {
        return success;
    }

    public EnqueuePatientResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EnqueuePatientResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public EnqueuePatientResult setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getQueueId() {
        return queueId;
    }

    public EnqueuePatientResult setQueueId(int queueId) {
        this.queueId = queueId;
        return this;
    }
}
