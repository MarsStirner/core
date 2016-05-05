package ru.korus.tmis.hsct;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 19:12 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctRequestActionContainer {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "action")
    private String action;

    public HsctRequestActionContainer() {
        //Default empty constructor
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public boolean isEnqueueAction() {
        return "enqueue".equalsIgnoreCase(action);
    }


    public boolean isDequeueAction() {
        return "dequeue".equalsIgnoreCase(action);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctRequestActionContainer{");
        sb.append("id=").append(id);
        sb.append(", action='").append(action).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
