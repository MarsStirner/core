package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbAPTableField database table.
 * 
 */
@Entity
@Table(name="rbAPTableField")
@NamedQueries(
        {
                @NamedQuery(name = "RbAPTableField.findByCode", query = "SELECT tf FROM RbAPTableField tf WHERE tf.rbAptable.code = :code")
        })
public class RbAPTableField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=256)
	private String fieldName;

	@Column(nullable=false)
	private int idx;

	@Column(nullable=false, length=256)
	private String name;

	@Column(length=256)
	private String referenceTable;

	//bi-directional many-to-one association to RbAPTable
	@ManyToOne
	@JoinColumn(name="master_id", nullable=false)
	private RbAPTable rbAptable;

	public RbAPTableField() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferenceTable() {
		return this.referenceTable;
	}

	public void setReferenceTable(String referenceTable) {
		this.referenceTable = referenceTable;
	}

	public RbAPTable getRbAptable() {
		return this.rbAptable;
	}

	public void setRbAptable(RbAPTable rbAptable) {
		this.rbAptable = rbAptable;
	}

}