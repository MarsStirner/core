package ru.korus.tmis.core.ext.entities.s11r64.vmp;

import ru.korus.tmis.core.ext.entities.s11r64.RbFinance;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the QuotaCatalog database table.
 * 
 */
@Entity
public class QuotaCatalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date begDate;

	private String catalogNumber;

	@Lob
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	private String documentCorresp;

	@Temporal(TemporalType.DATE)
	private Date documentDate;

	private String documentNumber;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDatetime;

	//bi-directional many-to-one association to RbFinance
	@ManyToOne
	@JoinColumn(name="finance_id")
	private RbFinance rbFinance;

	//bi-directional many-to-one association to QuotaType
	@OneToMany(mappedBy="quotaCatalog")
	private List<QuotaType> quotaTypes;

	public QuotaCatalog() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBegDate() {
		return this.begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public String getCatalogNumber() {
		return this.catalogNumber;
	}

	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getDocumentCorresp() {
		return this.documentCorresp;
	}

	public void setDocumentCorresp(String documentCorresp) {
		this.documentCorresp = documentCorresp;
	}

	public Date getDocumentDate() {
		return this.documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public RbFinance getRbFinance() {
		return this.rbFinance;
	}

	public void setRbFinance(RbFinance rbFinance) {
		this.rbFinance = rbFinance;
	}

	public List<QuotaType> getQuotaTypes() {
		return this.quotaTypes;
	}

	public void setQuotaTypes(List<QuotaType> quotaTypes) {
		this.quotaTypes = quotaTypes;
	}

}