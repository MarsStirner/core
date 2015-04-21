package ru.korus.tmis.vmp.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the VMPQuotaDetails database table.
 * 
 */
@Entity
@Table(name="VMPQuotaDetails")
public class VMPQuotaDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal price;

	//bi-directional many-to-one association to Client_Quoting
	@OneToMany(mappedBy="vmpquotaDetail")
	private List<Client_Quoting> clientQuotings;

	//bi-directional many-to-one association to MKB_VMPQuotaFilter
	@OneToMany(mappedBy="vmpquotaDetail")
	private List<MKB_VMPQuotaFilter> mkbVmpquotaFilters;

	//bi-directional many-to-one association to RbPacientModel
	@ManyToOne
	@JoinColumn(name="pacientModel_id")
	private RbPacientModel rbPacientModel;

	//bi-directional many-to-one association to RbTreatment
	@ManyToOne
	@JoinColumn(name="treatment_id")
	private RbTreatment rbTreatment;

	//bi-directional many-to-one association to QuotaType
	@ManyToOne
	private QuotaType quotaType;

	public VMPQuotaDetail() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Client_Quoting> getClientQuotings() {
		return this.clientQuotings;
	}

	public void setClientQuotings(List<Client_Quoting> clientQuotings) {
		this.clientQuotings = clientQuotings;
	}

	public List<MKB_VMPQuotaFilter> getMkbVmpquotaFilters() {
		return this.mkbVmpquotaFilters;
	}

	public void setMkbVmpquotaFilters(List<MKB_VMPQuotaFilter> mkbVmpquotaFilters) {
		this.mkbVmpquotaFilters = mkbVmpquotaFilters;
	}

	public RbPacientModel getRbPacientModel() {
		return this.rbPacientModel;
	}

	public void setRbPacientModel(RbPacientModel rbPacientModel) {
		this.rbPacientModel = rbPacientModel;
	}

	public RbTreatment getRbTreatment() {
		return this.rbTreatment;
	}

	public void setRbTreatment(RbTreatment rbTreatment) {
		this.rbTreatment = rbTreatment;
	}

	public QuotaType getQuotaType() {
		return this.quotaType;
	}

	public void setQuotaType(QuotaType quotaType) {
		this.quotaType = quotaType;
	}

}