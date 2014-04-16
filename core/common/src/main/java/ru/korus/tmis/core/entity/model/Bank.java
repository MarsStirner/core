package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Bank database table.
 * 
 */
@Entity
@Table(name="Bank")
public class Bank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=10)
	private String bik;

	@Column(nullable=false, length=100)
	private String branchName;

	@Column(nullable=false, length=20)
	private String corrAccount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date createDatetime;

	private int createPerson_id;

	@Column(nullable=false)
	private byte deleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date modifyDatetime;

	private int modifyPerson_id;

	@Column(nullable=false, length=100)
	private String name;

	@Column(nullable=false, length=20)
	private String subAccount;

	public Bank() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBik() {
		return this.bik;
	}

	public void setBik(String bik) {
		this.bik = bik;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCorrAccount() {
		return this.corrAccount;
	}

	public void setCorrAccount(String corrAccount) {
		this.corrAccount = corrAccount;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public int getCreatePerson_id() {
		return this.createPerson_id;
	}

	public void setCreatePerson_id(int createPerson_id) {
		this.createPerson_id = createPerson_id;
	}

	public byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public int getModifyPerson_id() {
		return this.modifyPerson_id;
	}

	public void setModifyPerson_id(int modifyPerson_id) {
		this.modifyPerson_id = modifyPerson_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubAccount() {
		return this.subAccount;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

}