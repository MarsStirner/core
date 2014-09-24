package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AppLockDetail database table.
 * 
 */
@Embeddable
public class AppLockDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="master_id", unique=true, nullable=false, updatable = false)
	private Integer masterId;

	@Column(unique=true, nullable=false, length=64, updatable = false)
	private String tableName;

	@Column(unique=true, nullable=false, updatable = false)
	private int recordId;

	public AppLockDetailPK() {
	}

    public AppLockDetailPK(String tableName, Integer recordId) {
        this.tableName = tableName;
        this.recordId = recordId;
    }

    public Integer getMasterId() {
		return this.masterId;
	}
	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}
	public String getTableName() {
		return this.tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getRecordId() {
		return this.recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AppLockDetailPK))  {
            return false;
        }

        AppLockDetailPK that = (AppLockDetailPK) o;

        if (recordId != that.recordId || tableName == null || !tableName.equals(that.tableName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableName.hashCode();
        result = 31 * result + recordId;

        return result;
    }
}