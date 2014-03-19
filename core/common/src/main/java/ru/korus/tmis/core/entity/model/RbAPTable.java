package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbAPTable database table.
 * 
 */
@Entity
@Table(name="rbAPTable")
public class RbAPTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=50)
	private String code;

	@Column(nullable=false, length=256)
	private String masterField;

	@Column(nullable=false, length=256)
	private String name;

	@Column(nullable=false, length=256)
	private String tableName;

	//bi-directional many-to-one association to RbAPTableField
	@OneToMany(mappedBy="rbAptable")
	private List<RbAPTableField> rbAptableFields;

	public RbAPTable() {
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

	public String getMasterField() {
		return this.masterField;
	}

	public void setMasterField(String masterField) {
		this.masterField = masterField;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<RbAPTableField> getRbAptableFields() {
		return this.rbAptableFields;
	}

	public void setRbAptableFields(List<RbAPTableField> rbAptableFields) {
		this.rbAptableFields = rbAptableFields;
	}

}