package ru.korus.tmis.core.ext.entities.s11r64.vmp;

import ru.korus.tmis.core.ext.entities.s11r64.ReferenceBook;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the QuotaType database table.
 * 
 */
@Entity
public class QuotaType implements Serializable, ReferenceBook {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="class")
	private byte class_;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private Integer createPerson_id;

	private byte deleted;

	@Column(name="group_code")
	private String groupCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	private Integer modifyPerson_id;

	private String name;

	private BigDecimal price;

	@Column(name="profile_code")
	private String profileCode;

	private byte teenOlder;

	@Column(name="type_code")
	private String typeCode;

	//bi-directional many-to-one association to QuotaCatalog
	@ManyToOne
	@JoinColumn(name="catalog_id")
	private QuotaCatalog quotaCatalog;

	//bi-directional many-to-one association to VMPQuotaDetail
	@OneToMany(mappedBy="quotaType")
	private List<VMPQuotaDetail> vmpquotaDetails;

	public QuotaType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte getClass_() {
		return this.class_;
	}

	public void setClass_(byte class_) {
		this.class_ = class_;
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

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProfileCode() {
		return this.profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public byte getTeenOlder() {
		return this.teenOlder;
	}

	public void setTeenOlder(byte teenOlder) {
		this.teenOlder = teenOlder;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public QuotaCatalog getQuotaCatalog() {
		return this.quotaCatalog;
	}

	public void setQuotaCatalog(QuotaCatalog quotaCatalog) {
		this.quotaCatalog = quotaCatalog;
	}

	public List<VMPQuotaDetail> getVmpquotaDetails() {
		return this.vmpquotaDetails;
	}

	public void setVmpquotaDetails(List<VMPQuotaDetail> vmpquotaDetails) {
		this.vmpquotaDetails = vmpquotaDetails;
	}

}