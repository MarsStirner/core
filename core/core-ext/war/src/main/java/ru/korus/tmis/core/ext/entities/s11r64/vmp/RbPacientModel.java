package ru.korus.tmis.core.ext.entities.s11r64.vmp;

import ru.korus.tmis.core.ext.entities.s11r64.ReferenceBook;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rbPacientModel database table.
 * 
 */
@Entity
@Table(name = "rbPacientModel")
public class RbPacientModel implements Serializable, ReferenceBook {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	@Lob
	private String name;


	public RbPacientModel() {
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