package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the RbTrfuBloodComponentType database table.
 * 
 */
@Entity
@Table(name = "rbTrfuBloodComponentType")
public class RbTrfuBloodComponentType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false)
    private int id;

    @Column(length = 32)
    private String code;

    @Column(length = 256)
    private String name;

    @Column(name = "trfu_id")
    private Integer trfuId;

    @Column(nullable = false)
    private boolean unused;

    public RbTrfuBloodComponentType() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RbTrfuBloodComponentType [id=");
        builder.append(id);
        builder.append(", code=");
        builder.append(code);
        builder.append(", name=");
        builder.append(name);
        builder.append(", trfuId=");
        builder.append(trfuId);
        builder.append(", unused=");
        builder.append(unused);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((trfuId == null) ? 0 : trfuId.hashCode());
        return result;
    }

    /**
     * Сравнение типов компонентов крови. Типы компонентов крови считаются равными, если у них совпадают поля trfu_id, code и name. При этом значение полей id и
     * unused игнорируется
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
        RbTrfuBloodComponentType other = (RbTrfuBloodComponentType) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (trfuId == null) {
            if (other.trfuId != null) {
                return false;
            }
        } else if (!trfuId.equals(other.trfuId)) {
            return false;
        }
        return true;
    }  
}