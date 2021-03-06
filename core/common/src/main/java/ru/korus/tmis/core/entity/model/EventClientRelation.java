package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.03.2015, 16:15 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name="Event_ClientRelation")
@NamedQueries(
        {
                @NamedQuery(name = "Event_ClientRelation.findByEvent",
                        query = "SELECT e FROM EventClientRelation e WHERE e.event.id = :eventId AND e.deleted = 0"),
                @NamedQuery(name = "Event_ClientRelation.findByEventAndClientRealtion",
                        query = "SELECT e FROM EventClientRelation e WHERE " +
                                "e.event.id = :eventId AND e.clientRelation.id = :clientRelationId AND e.deleted = 0")
        }
)
public class EventClientRelation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientRelation_id", referencedColumnName = "id")
    private ClientRelation clientRelation;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @Basic(optional = false)
    @Lob
    @Column(name = "note")
    private String note = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ClientRelation getClientRelation() {
        return clientRelation;
    }

    public void setClientRelation(ClientRelation clientRelation) {
        this.clientRelation = clientRelation;
    }
}
