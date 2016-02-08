package ru.korus.tmis.hsct;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 04.02.2016, 20:15 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueContainer {

    @XmlElement(name = "entries")
    private List<QueueEntry> entries;

    public QueueContainer() {
    }

    public List<QueueEntry> getEntries() {
        return entries;
    }

    public void setEntries(final List<QueueEntry> entries) {
        this.entries = entries;
    }

    public boolean addToEntries(final QueueEntry entry) {
        if(this.entries == null){
            entries = new ArrayList<>();
        }
        return entries.add(entry);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueueContainer{");
        sb.append("entries=").append(entries);
        sb.append('}');
        return sb.toString();
    }
}
