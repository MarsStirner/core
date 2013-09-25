package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rlsActMatters_Nomen database table.
 * 
 */
@Embeddable
public class RlsActMatters_NomenPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="nomen_id", unique=true, nullable=false)
	private int nomenId;

	@Column(unique=true, nullable=false)
	private int actMatter_id;

	public RlsActMatters_NomenPK() {
	}
	public int getNomenId() {
		return this.nomenId;
	}
	public void setNomenId(int nomenId) {
		this.nomenId = nomenId;
	}
	public int getActMatter_id() {
		return this.actMatter_id;
	}
	public void setActMatter_id(int actMatter_id) {
		this.actMatter_id = actMatter_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RlsActMatters_NomenPK)) {
			return false;
		}
		RlsActMatters_NomenPK castOther = (RlsActMatters_NomenPK)other;
		return 
			(this.nomenId == castOther.nomenId)
			&& (this.actMatter_id == castOther.actMatter_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nomenId;
		hash = hash * prime + this.actMatter_id;
		
		return hash;
	}
}