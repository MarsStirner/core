package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbtrfuproceduretypes database table.
 * 
 */
@Entity
@Table(name="rbTrfuProcedureTypes")
public class RbTrfuProcedureTypes implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String name;

	@Column(name="trfu_id")
	private int trfuId;

	@Column(nullable=false)
	private boolean unused;

	public RbTrfuProcedureTypes() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTrfuId() {
		return this.trfuId;
	}

	public void setTrfuId(int trfuId) {
		this.trfuId = trfuId;
	}

	public boolean getUnused() {
		return this.unused;
	}

	public void setUnused(boolean unused) {
		this.unused = unused;
	}
	
	 /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + trfuId;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RbTrfuProcedureTypes other = (RbTrfuProcedureTypes) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (trfuId != other.trfuId) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RbTrfuProcedureTypes [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", trfuId=");
        builder.append(trfuId);
        builder.append("]");
        return builder.toString();
    }
    
    

}