package ru.korus.tmis.vmp.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MKB database table.
 * 
 */
@Entity(name = "MKB")
public class Mkb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String age;

	@Column(name="age_bc")
	private Integer ageBc;

	@Column(name="age_bu")
	private byte ageBu;

	@Column(name="age_ec")
	private Integer ageEc;

	@Column(name="age_eu")
	private byte ageEu;

	private String blockID;

	private String blockName;

	private byte characters;

	private String classID;

	private String className;

	private String diagID;

	private String diagName;

	private Integer duration;

	private Integer MKBSubclass_id;

	private String prim;

	@Column(name="service_id")
	private Integer serviceId;

	private byte sex;

	//bi-directional many-to-one association to MKB_VMPQuotaFilter
	@OneToMany(mappedBy="mkb")
	private List<MKB_VMPQuotaFilter> mkbVmpquotaFilters;

	public Mkb() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public byte getAgeBu() {
		return this.ageBu;
	}

	public void setAgeBu(byte ageBu) {
		this.ageBu = ageBu;
	}

	public Integer getAgeEc() {
		return this.ageEc;
	}

	public void setAgeEc(Integer ageEc) {
		this.ageEc = ageEc;
	}

	public byte getAgeEu() {
		return this.ageEu;
	}

	public void setAgeEu(byte ageEu) {
		this.ageEu = ageEu;
	}

	public String getBlockID() {
		return this.blockID;
	}

	public void setBlockID(String blockID) {
		this.blockID = blockID;
	}

	public String getBlockName() {
		return this.blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public byte getCharacters() {
		return this.characters;
	}

	public void setCharacters(byte characters) {
		this.characters = characters;
	}

	public String getClassID() {
		return this.classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDiagID() {
		return this.diagID;
	}

	public void setDiagID(String diagID) {
		this.diagID = diagID;
	}

	public String getDiagName() {
		return this.diagName;
	}

	public void setDiagName(String diagName) {
		this.diagName = diagName;
	}

	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getMKBSubclass_id() {
		return this.MKBSubclass_id;
	}

	public void setMKBSubclass_id(Integer MKBSubclass_id) {
		this.MKBSubclass_id = MKBSubclass_id;
	}

	public String getPrim() {
		return this.prim;
	}

	public void setPrim(String prim) {
		this.prim = prim;
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public byte getSex() {
		return this.sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public List<MKB_VMPQuotaFilter> getMkbVmpquotaFilters() {
		return this.mkbVmpquotaFilters;
	}

	public void setMkbVmpquotaFilters(List<MKB_VMPQuotaFilter> mkbVmpquotaFilters) {
		this.mkbVmpquotaFilters = mkbVmpquotaFilters;
	}

}