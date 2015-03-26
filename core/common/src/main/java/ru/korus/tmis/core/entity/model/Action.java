package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.*;

import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;

@Entity
@Table(name = "Action")
@NamedQueries(
        {
                @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a"),
                @NamedQuery(name = "Action.findById", query = "SELECT a FROM Action a WHERE a.id = :id"),
                @NamedQuery(name = "Action.findNewByFlatCode", query = "SELECT a FROM Action a WHERE a.status = 0 AND a.actionType.flatCode = :flatCode AND a.event IS NOT NULL AND a.event.patient IS NOT NULL AND a.deleted = 0 "),
                @NamedQuery(name = "Action.ActionsByFlatCode", query = "SELECT a FROM Action a WHERE a.actionType.flatCode IN :codes AND a.event.id = :id AND a.deleted = 0"),
                @NamedQuery(name = "Action.ServiceList", query = "SELECT a FROM Action a WHERE a.actionType.service IS NOT NULL AND a.event.id = :eventId AND a.deleted = 0"),
                @NamedQuery(name = "Action.findByEventId", query = "SELECT a FROM Action a WHERE a.event.id = :eventId AND a.deleted = 0"),
                @NamedQuery(name = "Action.findByFlatCodesAndEventId", query = "SELECT a FROM Action a WHERE a.actionType.flatCode IN :flatCodes AND a.event.id = :eventId AND a.deleted = 0"),
                @NamedQuery(name = "Action.findLastByFlatCodesAndEventId", query = "SELECT a FROM Action a WHERE a.actionType.flatCode IN :flatCodes AND a.event.id = :eventId AND a.deleted = 0  ORDER BY a.createDatetime DESC"),
                @NamedQuery(name = "Action.findLastByActionTypesAndClientId", query = "SELECT a FROM Action a WHERE a.actionType.code IN :codes AND a.deleted = 0 AND a.event.patient.id = :clientId ORDER BY a.createDatetime DESC"),
                @NamedQuery(name = "Action.findLatestMove", query = "SELECT a FROM Action a WHERE (a.actionType.flatCode = 'moving' OR a.actionType.flatCode = 'received')" +
                        " AND a.event.id = :eventId AND a.deleted = 0 ORDER BY a.createDatetime DESC"),
                @NamedQuery(name = "Action.AllActionsOfPatientThatHasActionProperty",
                        query = "SELECT a FROM Action a, ActionProperty ap, ActionPropertyType apt WHERE a.deleted = 0 AND " +
                                "a.event.patient.id = :patientId " +
                                "AND ap.action.id = a.id AND apt.id = ap.actionPropertyType.id AND apt.code = :code")

        })
@XmlType(name = "action")
@XmlRootElement(name = "action")
public class Action
        implements Serializable, Cloneable, DbEnumerable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "actionType_id")
    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Basic(optional = false)
    @Column(name = "idx")
    private int idx = 0;

    @Column(name = "directionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date directionDate = new Date();

    @Basic(optional = false)
    @Column(name = "status")
    private short status;

    @ManyToOne
    @JoinColumn(name = "setPerson_id")
    private Staff assigner;

    @Basic(optional = false)
    @Column(name = "isUrgent")
    private boolean isUrgent = false;

    @Column(name = "begDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Basic(optional = false)
    @Column(name = "plannedEndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedEndDate = new Date();

    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Basic(optional = false)
    @Lob
    @Column(name = "note")
    private String note = "";

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Staff executor;

    @Basic(optional = false)
    @Column(name = "office")
    private String office = "";

    @Basic(optional = false)
    @Column(name = "amount")
    private double amount = 0.0;

    @Column(name = "uet")
    private Double uet;

    @Basic(optional = false)
    @Column(name = "expose")
    private int expose = 0;

    @Basic(optional = false)
    @Column(name = "payStatus")
    private int payStatus = 0;

    @Basic(optional = false)
    @Column(name = "account")
    private boolean account = false;

    @Column(name = "finance_id")
    private Integer financeId;

    @Column(name = "prescription_id")
    private Integer prescriptionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "takenTissueJournal_id")
    private TakenTissue takenTissue;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "coordDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date coordDate;

    @Basic(optional = false)
    @Column(name = "coordAgent")
    private String coordAgent = "";

    @Basic(optional = false)
    @Column(name = "coordInspector")
    private String coordInspector = "";

    @Basic(optional = false)
    @Column(name = "coordText")
    private String coordText = "";

    @Basic(optional = false)
    @Column(name = "hospitalUidFrom")
    private String hospitalUidFrom = "";

    @Basic(optional = true)
    @Column(name = "pacientInQueueType")
    private Short pacientInQueueType = 0;

    @Basic(optional = true)
    @Column(name = "appointmentType")
    private String appointmentType = AppointmentType.NONE.getName();

    @Version
    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ActionTissue",
            joinColumns = {@JoinColumn(name = "action_id")},
            inverseJoinColumns = {@JoinColumn(name = "tissue_id")})
    private Set<Tissue> tissue = new LinkedHashSet<Tissue>();

    //@Basic(optional = false)
    //@Column(name = "toOrder")
    //private boolean toOrder = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid_id")
    private UUID uuid;

    @Basic(optional = false)
    @Column(name = "parentAction_id")
    private Integer parentActionId = null;
    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
    private List<ActionProperty> actionProperties = new LinkedList<ActionProperty>();

    public List<ActionProperty> getActionProperties() {
        if (actionProperties == null) actionProperties = new LinkedList<ActionProperty>();
        return actionProperties;
    }

    public void addProperty(final ActionProperty actionProperty) {
        this.actionProperties.add(actionProperty);
        if (actionProperty.getAction() != this) {
            actionProperty.setAction(this);
        }
    }

    public Map<ActionPropertyType, ActionProperty> getActionPropertiesByTypes(
            final Set<ActionPropertyType> types) {
        final Map<ActionPropertyType, ActionProperty> result =
                new LinkedHashMap<ActionPropertyType, ActionProperty>();

        for (ActionProperty ap : actionProperties) {
            if (types.contains(ap.getType())) {
                result.put(ap.getType(), ap);
            }
        }

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "action")
    private Set<AssignmentHour> assignmentHours = new HashSet<AssignmentHour>();

    public void addAssignmentHour(final AssignmentHour ah) {
        this.assignmentHours.add(ah);
        if (ah.getId().getActionId() != this.getId()) {
            ah.getId().setActionId(this.getId());
        }
    }

    public Set<AssignmentHour> getAssignmentHours() {
        if (assignmentHours == null) {
            assignmentHours = new HashSet<AssignmentHour>();
        }
        return assignmentHours;
    }

    public void resetAssignmentHours() {
        assignmentHours.clear();
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////

    public Action() {
    }

    public Action(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ActionType getActionType() {
        return actionType != null
                ? actionType
                : ActionType.getEmptyActionType();
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Date getDirectionDate() {
        return directionDate;
    }

    public void setDirectionDate(Date directionDate) {
        this.directionDate = directionDate;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Staff getAssigner() {
        return assigner;
    }

    public void setAssigner(Staff assigner) {
        this.assigner = assigner;
    }

    public boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

/*    public boolean getToOrder() {
        return toOrder;
    }

    public void setToOrder(boolean toOrder) {
        this.toOrder = toOrder;
    }*/

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(Date plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Staff getExecutor() {
        return executor;
    }

    public void setExecutor(Staff executor) {
        this.executor = executor;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Double getUet() {
        return uet;
    }

    public void setUet(Double uet) {
        this.uet = uet;
    }

    public int getExpose() {
        return expose;
    }

    public void setExpose(int expose) {
        this.expose = expose;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public boolean getAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Integer financeId) {
        this.financeId = financeId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public TakenTissue getTakenTissue() {
        return takenTissue;
    }

    public void setTakenTissue(TakenTissue takenTissue) {
        this.takenTissue = takenTissue;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Date getCoordDate() {
        return coordDate;
    }

    public void setCoordDate(Date coordDate) {
        this.coordDate = coordDate;
    }

    public String getCoordAgent() {
        return coordAgent;
    }

    public void setCoordAgent(String coordAgent) {
        this.coordAgent = coordAgent;
    }

    public String getCoordInspector() {
        return coordInspector;
    }

    public void setCoordInspector(String coordInspector) {
        this.coordInspector = coordInspector;
    }

    public String getCoordText() {
        return coordText;
    }

    public void setCoordText(String coordText) {
        this.coordText = coordText;
    }

    public String getHospitalUidFrom() {
        return hospitalUidFrom;
    }

    public void setHospitalUidFrom(String hospitalUidFrom) {
        this.hospitalUidFrom = hospitalUidFrom;
    }

    public Short getPacientInQueueType() {
        return pacientInQueueType;
    }

    public void setPacientInQueueType(Short pacientInQueueType) {
        this.pacientInQueueType = pacientInQueueType;
    }

    public AppointmentType getAppointmentType() {
        return AppointmentType.getByValue(appointmentType);
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<Tissue> getTissue() {
        if (tissue == null) tissue = new HashSet<Tissue>();
        return tissue;
    }

    public void setTissue(Set<Tissue> tissue) {
        this.tissue = tissue;
    }

    public Integer getParentActionId() {
        return parentActionId;
    }

    public void setParentActionId(int parentActionId) {
        this.parentActionId = parentActionId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Action)) {
            return false;
        }

        Action other = (Action) object;

        if (this.id == null && other.id == null && this != other) {
            return false;
        }

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Action[id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Action clone(Action self) throws CloneNotSupportedException {
        Action newAction = (Action) self.clone();

        newAction.actionProperties = new LinkedList<ActionProperty>();
        for (ActionProperty ap : self.getActionProperties()) {
            newAction.addProperty((ActionProperty) ap.clone());
        }

        newAction.assignmentHours = new HashSet<AssignmentHour>();
        for (AssignmentHour ah : self.getAssignmentHours()) {
            newAction.addAssignmentHour((AssignmentHour) ah.clone());
        }

        return newAction;
    }

    private static Map<Integer, Action> dbEnumsById =
            new HashMap<Integer, Action>();

    public static Action getById(final Integer id) {
        return dbEnumsById.get(id);
    }

    @Override
    public void loadEnums(final Collection<Object> enums) {
        Map<Integer, Action> newDbEnumsById =
                new HashMap<Integer, Action>();
        Map<String, Action> newDbEnumsByName =
                new HashMap<String, Action>();
        for (Object e : enums) {
            if (e instanceof Action) {
                Action ras = (Action) e;
                newDbEnumsById.put(ras.id, ras);
            }
        }
        dbEnumsById = newDbEnumsById;
    }
}
