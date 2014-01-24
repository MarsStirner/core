package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rlsTradeName database table.
 * 
 */
@Entity
@Table(name="rlsTradeName")
public class RlsTradeName implements Serializable, UniqueName, UniqueLocalName {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String localName;

	@Column(length=255)
	private String name;

	//bi-directional many-to-one association to RlsNomen
	@OneToMany(mappedBy="rlsTradeName")
	private List<RlsNomen> rlsNomens;

	public RlsTradeName() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocalName() {
		return this.localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RlsNomen> getRlsNomens() {
		return this.rlsNomens;
	}

	public void setRlsNomens(List<RlsNomen> rlsNomens) {
		this.rlsNomens = rlsNomens;
	}

}