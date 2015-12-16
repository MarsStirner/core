package ru.korus.tmis.lis.data.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        25.10.13, 12:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  Результаты ИФА теста<br>
 */
public class IFA {
    private String text;

    private String value;

    private long actionId;

    private String comment;

    /**
     * Признак завершенности исследования
     */
    private boolean complete;
    private String code;

    public IFA() {

    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public int getActionId() {
        return (int)actionId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    /**
     * Вывод результата в формате {значение}/{текст}
     * @return
     */
    public String getFullResult() {
        return value + (!text.isEmpty() ? "/" + text : "");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IFA{");
        sb.append("result='").append(text).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", actionId=").append(actionId);
        sb.append(", code='").append(code)
                .append("'}");
        return sb.toString();
    }


}
