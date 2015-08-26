package ru.korus.tmis.core.ext.entities.s11r64.vmp;

import ru.korus.tmis.core.ext.entities.s11r64.Mkb;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MKB_VMPQuotaFilter database table.
 * 
 */
@Entity
public class MKB_VMPQuotaFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Mkb
	@ManyToOne
	private Mkb mkb;

	//bi-directional many-to-one association to VMPQuotaDetail
	@ManyToOne
	@JoinColumn(name="quotaDetails_id")
	private VMPQuotaDetail vmpquotaDetail;

	public MKB_VMPQuotaFilter() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Mkb getMkb() {
		return this.mkb;
	}

	public void setMkb(Mkb mkb) {
		this.mkb = mkb;
	}

	public VMPQuotaDetail getVmpquotaDetail() {
		return this.vmpquotaDetail;
	}

	public void setVmpquotaDetail(VMPQuotaDetail vmpquotaDetail) {
		this.vmpquotaDetail = vmpquotaDetail;
	}

}