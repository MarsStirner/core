package ru.korus.tmis.vmp.entities.s11r64;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbBloodPhenotype database table.
 * 
 */
@Entity(name = "rbBloodPhenotype")
public class RbBloodPhenotype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String code;

	private String name;

	//bi-directional many-to-one association to Client
	@OneToMany(mappedBy="rbBloodPhenotype")
	private List<Client> clients;

	public RbBloodPhenotype() {
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

	public List<Client> getClients() {
		return this.clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}