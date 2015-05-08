package ru.korus.tmis.core.ext.entities.s11r64;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;


/**
 * The persistent class for the ActionTemplate database table.
 * 
 */
@Entity
public class ActionTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @ManyToOne
	@JoinColumn(name="action_id", nullable=true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Action action;

	private String age = "";

	@Column(name="age_bc")
	private Integer ageBc;

	@Column(name="age_bu")
	private Integer ageBu;

	@Column(name="age_ec")
	private Integer ageEc;

	@Column(name="age_eu")
	private Integer ageEu;

	private String code = "";

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	private byte deleted;

	@ManyToOne
    @JoinColumn(name="group_id")
	private ActionTemplate group;

    @OneToMany(mappedBy = "group")
    private List<ActionTemplate> actionTemplateList = new ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	private String name;

	@Column(name="owner_id")
	private Integer ownerId;

	private Byte sex = 0;

	@Column(name="speciality_id")
	private Integer specialityId;

	public ActionTemplate() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action= action;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Integer getAgeBc() {
		return this.ageBc;
	}

	public void setAgeBc(Integer ageBc) {
		this.ageBc = ageBc;
	}

	public Integer getAgeBu() {
		return this.ageBu;
	}

	public void setAgeBu(Integer ageBu) {
		this.ageBu = ageBu;
	}

	public Integer getAgeEc() {
		return this.ageEc;
	}

	public void setAgeEc(Integer ageEc) {
		this.ageEc = ageEc;
	}

	public Integer getAgeEu() {
		return this.ageEu;
	}

	public void setAgeEu(Integer ageEu) {
		this.ageEu = ageEu;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Integer getCreatePerson_id() {
		return this.createPerson_id;
	}

	public void setCreatePerson_id(Integer createPerson_id) {
		this.createPerson_id = createPerson_id;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public ActionTemplate getGroup() {
		return this.group;
	}

	public void setGroup(ActionTemplate group) {
		this.group = group;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public Integer getModifyPerson_id() {
		return this.modifyPerson_id;
	}

	public void setModifyPerson_id(Integer modifyPerson_id) {
		this.modifyPerson_id = modifyPerson_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Integer getSpecialityId() {
		return this.specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}

    public List<ActionTemplate> getActionTemplateList() {
        return actionTemplateList;
    }

    public void setActionTemplateList(List<ActionTemplate> actionTemplateList) {
        this.actionTemplateList = actionTemplateList;
    }
}