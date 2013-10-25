package ru.korus.tmis.laboratory.bak.ws.server.model;

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

    public IFA(String text, String value, long actionId) {
        this.text = text;
        this.value = value;
        this.actionId = actionId;
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
        sb.append('}');
        return sb.toString();
    }
}
