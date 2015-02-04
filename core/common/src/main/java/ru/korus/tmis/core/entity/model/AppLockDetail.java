package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the AppLockDetail database table.
 * 
 */

@NamedQueries(
        {
                @NamedQuery(name = "AppLockDetail.findLock", query = "SELECT al FROM AppLockDetail al " +
                        "WHERE al.id.tableName = :tableName AND al.id.recordId = :id AND al.appLock IS NOT NULL")
        })
@Entity
@Table(name="AppLock_Detail")
public class AppLockDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AppLockDetailPK id;

	@Column(nullable=false, updatable = false)
	private int recordIndex;

    @ManyToOne
    @JoinColumn(name="master_id", nullable=false, insertable=false, updatable=false)
    private AppLock appLock;

    public AppLockDetail() {
	}

    public AppLockDetail(String tableName, Integer recordId) {
        id = new AppLockDetailPK(tableName, recordId);
    }

    public AppLock getAppLock() {
        return appLock;
    }

    public void setAppLock(AppLock appLock) {
        this.appLock = appLock;
    }


    public AppLockDetailPK getId() {
		return this.id;
	}

	public void setId(AppLockDetailPK id) {
		this.id = id;
	}

	public int getRecordIndex() {
		return this.recordIndex;
	}

	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppLockDetail)) return false;

        AppLockDetail that = (AppLockDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}