package ru.korus.tmis.core.ext.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbEventTypePurpose database table.
 * 
 */
@Entity
@Table(name = "rbEventTypePurpose")
public class RbEventTypePurpose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private String codePlace;

	private String name;

	//bi-directional many-to-one association to RbAcheResult
	@OneToMany(mappedBy="rbEventTypePurpose")
	private List<RbAcheResult> rbAcheResults;

	public RbEventTypePurpose() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodePlace() {
		return this.codePlace;
	}

	public void setCodePlace(String codePlace) {
		this.codePlace = codePlace;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RbAcheResult> getRbAcheResults() {
		return this.rbAcheResults;
	}

	public void setRbAcheResults(List<RbAcheResult> rbAcheResults) {
		this.rbAcheResults = rbAcheResults;
	}

}