package ru.korus.tmis.vmp.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbFinance database table.
 * 
 */
@Entity
public class RbFinance implements Serializable, ReferenceBook {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private String name;

	public RbFinance() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}