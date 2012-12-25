package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity для работы из ORM с таблицей s11r64.Event_Persons.
 * @author mmakankov
 * @since 1.0.0.54
 */
@Entity
@Table(name = "Event_Persons", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "EventPerson.findAll", query = "SELECT ep FROM EventPerson ep")
        })
@XmlType(name = "eventPerson")
@XmlRootElement(name = "eventPerson")
public class EventPerson {
}
