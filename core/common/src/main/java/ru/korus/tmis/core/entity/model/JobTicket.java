package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 11.02.13
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Job_Ticket", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "JobTicket.findAll", query = "SELECT jt FROM JobTicket jt")
        })
@XmlType(name = "JobTicket")
@XmlRootElement(name = "JobTicket")
public class JobTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    /*
    @ManyToOne
    @JoinColumn(name = "master_id")
    private Job job;    //TODO узнать верно ли?
    */
    @Column(name = "master_id")
    private int master_id;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx;

    @Basic(optional = false)
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Basic(optional = false)
    @Column(name = "resTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resTimestamp;             //   TIMESTAMP????

    @Basic(optional = false)
    @Column(name = "resConnectionId")
    private int resConnectionId;

    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    @Column(name = "begDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDateTime;

    @Column(name = "endDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    @Column(name = "label")
    private String label;

    @Column(name = "note")
    private String note;


}
