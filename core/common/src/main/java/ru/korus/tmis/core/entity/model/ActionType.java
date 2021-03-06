package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "ActionType")
@NamedQueries(
        {
                @NamedQuery(name = "ActionType.findAll", query = "SELECT a FROM ActionType a"),
                @NamedQuery(name = "ActionType.findByFlatCodes", query = "SELECT a FROM ActionType a WHERE a.flatCode IN :flatCodes" ),
                @NamedQuery(name = "ActionType.getQueueActionType",
                        query = "SELECT a FROM ActionType a WHERE a.code = 'queue'"),
                @NamedQuery(name= "ActionType.findRisarActionType",
                        query = "SELECT at FROM ActionType at WHERE " +
                                "at.id IN (SELECT atp.actionType.id FROM ActionPropertyType atp WHERE atp.code LIKE 'recommendations') AND " +
                                "at.id IN (SELECT atpD.actionType.id FROM ActionPropertyType atpD WHERE atp.code LIKE 'diagnosis') AND " +
                                "at.id NOT IN (SELECT n.actionType.id FROM NotificationActionType n WHERE n.baseUrl LIKE '%ws-risar/api/notification/new/exam%')" )
        })
@XmlType(name = "actionType")
@XmlRootElement(name = "actionType")
public class ActionType implements Serializable {

    private static final long serialVersionUID = 1L;

    ////////////////////////////////////////////////////////////////////////////
    // Custom stuff
    ////////////////////////////////////////////////////////////////////////////

    @Transient
    private static final ActionType emptyActionType = new ActionType();

    static {
        emptyActionType.setId(-1);
        emptyActionType.setName("");
    }

    public static ActionType getEmptyActionType() {
        return emptyActionType;
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom stuff
    ////////////////////////////////////////////////////////////////////////////

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
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "class")
    private short clazz;

    @Column(name = "group_id")
    private Integer groupId;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "title")
    private String title;

    @Basic(optional = false)
    @Column(name = "flatCode")
    private String flatCode;

    @Basic(optional = false)
    @Column(name="hidden")
    private boolean hidden;

    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;

    @Basic(optional = false)
    @Column(name = "age")
    private String age;

    @Basic(optional = false)
    @Column(name = "office")
    private String office;

    @Basic(optional = false)
    @Column(name = "showInForm")
    private boolean showInForm;

    @Basic(optional = false)
    @Column(name = "genTimetable")
    private boolean genTimetable;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private RbService service;

    @Column(name = "quotaType_id")
    private Integer quotaTypeId;

    @Basic(optional = false)
    @Column(name = "context")
    private String context;

    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;

    @Basic(optional = false)
    @Column(name = "amountEvaluation")
    private int amountEvaluation;

    @Basic(optional = false)
    @Column(name = "defaultStatus")
    private short defaultStatus;

    @Basic(optional = false)
    @Column(name = "defaultDirectionDate")
    private short defaultDirectionDate;

    @Basic(optional = false)
    @Column(name = "defaultPlannedEndDate")
    private short defaultPlannedEndDate;

    @Basic(optional = false)
    @Column(name = "defaultEndDate")
    private short defaultEndDate;

    @ManyToOne
    @JoinColumn(name = "defaultExecPerson_id")
    private Staff defaultExecutor;

    @Basic(optional = false)
    @Column(name = "defaultPersonInEvent")
    private short defaultPersonInEvent;

    @Basic(optional = false)
    @Column(name = "defaultPersonInEditor")
    private short defaultPersonInEditor;

    @Basic(optional = false)
    @Column(name = "maxOccursInEvent")
    private int maxOccursInEvent;

    @Basic(optional = false)
    @Column(name = "showTime")
    private boolean showTime;

    @Column(name = "isMES")
    private Integer isMES;

    @Column(name = "nomenclativeService_id")
    private Integer nomenclativeServiceId;

    @Basic(optional = false)
    @Column(name = "isPreferable")
    private boolean isPreferable;

    @Column(name = "prescribedType_id")
    private Integer prescribedTypeId;

    @Column(name = "shedule_id")
    private Integer scheduleId;

    @Basic(optional = false)
    @Column(name = "isRequiredCoordination")
    private boolean isRequiredCoordination;

    @Basic(optional = false)
    @Column(name = "isRequiredTissue")
    private boolean isRequiredTissue;

    @Basic(optional = false)
    @Column(name = "mnem")
    private String mnemonic = "";

    @ManyToOne
    @JoinColumn(name = "testTubeType_id")
    private RbTestTubeType testTubeType;

    @ManyToOne
    @JoinColumn(name = "jobType_id")
    private RbJobType jobType;

    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "actionType", cascade = CascadeType.ALL)
    private List<ActionPropertyType> actionPropertyTypes =
            new LinkedList<ActionPropertyType>();


    public List<ActionPropertyType> getActionPropertyTypes() {
        List<ActionPropertyType> nonDeletedActionPropertyTypes =
                new LinkedList<ActionPropertyType>();
        for (ActionPropertyType apt : actionPropertyTypes) {
            if (!apt.getDeleted()) {
                nonDeletedActionPropertyTypes.add(apt);
            }
        }
        return nonDeletedActionPropertyTypes;
    }


    @OneToOne(mappedBy="actionType")
    private ActionTypeTissueType actionTypeTissueType;

    ////////////////////////////////////////////////////////////////////////////

    @PostLoad
    private void onPostLoad() {

    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////

    public ActionType() {
    }

    public ActionType(Integer id) {
        this.id = id;
    }

    public ActionType(Integer id, Integer groupId, String code, String flatCode, String name) {
        this.id = id;
        this.groupId = groupId;
        this.code = code;
        this.flatCode = flatCode;
        this.name = name;
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

    public short getTypeClass() {
        return clazz;
    }

    public void setTypeClass(short clazz) {
        this.clazz = clazz;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlatCode() {
        return flatCode;
    }

    public void setFlatCode(String flatCode) {
        this.flatCode = flatCode;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public boolean getShowInForm() {
        return showInForm;
    }

    public void setShowInForm(boolean showInForm) {
        this.showInForm = showInForm;
    }

    public boolean getGenTimetable() {
        return genTimetable;
    }

    public void setGenTimetable(boolean genTimetable) {
        this.genTimetable = genTimetable;
    }

    public RbService getService() {
        return service;
    }

    public void setService(RbService service) {
        this.service = service;
    }

    public Integer getQuotaTypeId() {
        return quotaTypeId;
    }

    public void setQuotaTypeId(Integer quotaTypeId) {
        this.quotaTypeId = quotaTypeId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAmountEvaluation() {
        return amountEvaluation;
    }

    public void setAmountEvaluation(int amountEvaluation) {
        this.amountEvaluation = amountEvaluation;
    }

    public short getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(short defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public short getDefaultDirectionDate() {
        return defaultDirectionDate;
    }

    public void setDefaultDirectionDate(short defaultDirectionDate) {
        this.defaultDirectionDate = defaultDirectionDate;
    }

    public short getDefaultPlannedEndDate() {
        return defaultPlannedEndDate;
    }

    public void setDefaultPlannedEndDate(short defaultPlannedEndDate) {
        this.defaultPlannedEndDate = defaultPlannedEndDate;
    }

    public short getDefaultEndDate() {
        return defaultEndDate;
    }

    public void setDefaultEndDate(short defaultEndDate) {
        this.defaultEndDate = defaultEndDate;
    }

    public Staff getDefaultExecutor() {
        return defaultExecutor;
    }

    public void setDefaultExecutor(Staff defaultExecutor) {
        this.defaultExecutor = defaultExecutor;
    }

    public short getDefaultPersonInEvent() {
        return defaultPersonInEvent;
    }

    public void setDefaultPersonInEvent(short defaultPersonInEvent) {
        this.defaultPersonInEvent = defaultPersonInEvent;
    }

    public short getDefaultPersonInEditor() {
        return defaultPersonInEditor;
    }

    public void setDefaultPersonInEditor(short defaultPersonInEditor) {
        this.defaultPersonInEditor = defaultPersonInEditor;
    }

    public int getMaxOccursInEvent() {
        return maxOccursInEvent;
    }

    public void setMaxOccursInEvent(int maxOccursInEvent) {
        this.maxOccursInEvent = maxOccursInEvent;
    }

    public boolean getShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public Integer getIsMES() {
        return isMES;
    }

    public void setIsMES(Integer isMES) {
        this.isMES = isMES;
    }

    public Integer getNomenclativeServiceId() {
        return nomenclativeServiceId;
    }

    public void setNomenclativeServiceId(Integer nomenclativeServiceId) {
        this.nomenclativeServiceId = nomenclativeServiceId;
    }

    public boolean getIsPreferable() {
        return isPreferable;
    }

    public void setIsPreferable(boolean isPreferable) {
        this.isPreferable = isPreferable;
    }

    public Integer getPrescribedTypeId() {
        return prescribedTypeId;
    }

    public void setPrescribedTypeId(Integer prescribedTypeId) {
        this.prescribedTypeId = prescribedTypeId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public boolean getIsRequiredCoordination() {
        return isRequiredCoordination;
    }

    public void setIsRequiredCoordination(boolean isRequiredCoordination) {
        this.isRequiredCoordination = isRequiredCoordination;
    }

    public boolean getIsRequiredTissue() {
        return isRequiredTissue;
    }

    public void setIsRequiredTissue(boolean isRequiredTissue) {
        this.isRequiredTissue = isRequiredTissue;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public RbTestTubeType getTestTubeType() {
        return testTubeType;
    }

    public void setTestTubeType(RbTestTubeType testTubeType) {
        this.testTubeType = testTubeType;
    }

    public RbJobType getJobType() {
        return jobType;
    }

    public void setJobType(RbJobType jobType) {
        this.jobType = jobType;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActionType)) {
            return false;
        }
        ActionType other = (ActionType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.ActionType[id=" + id + "]";
    }

    public ActionTypeTissueType getActionTypeTissueType() {
        return actionTypeTissueType;
    }

    public void setActionTypeTissueType(ActionTypeTissueType actionTypeTissueType) {
        this.actionTypeTissueType = actionTypeTissueType;
    }
}
