package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rlsNomen database table.
 * 
 */
@Embeddable
public class RlsNomenPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(unique=true, nullable=false)
	private Integer version;

    public RlsNomenPK(Integer id) {
       this.id = id;
       version = 0;
    }

    public RlsNomenPK(RlsNomenPK prev) {
        this.id = prev.id;
        this.version = ++prev.version;
    }

    public RlsNomenPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return this.version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RlsNomenPK)) {
			return false;
		}
		RlsNomenPK castOther = (RlsNomenPK)other;
		return 
			(this.id == castOther.id)
			&& (this.version == castOther.version);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.version;
		
		return hash;
	}
}