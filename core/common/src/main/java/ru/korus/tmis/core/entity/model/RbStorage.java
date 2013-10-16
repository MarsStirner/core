package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rbStorage database table.
 * 
 */
@Entity
@Table(name="rbStorage")
@NamedQueries(
        { @NamedQuery(name = "rbStorage.findByUUID",
                query = "SELECT s FROM RbStorage s WHERE s.uuid = :uuid"),
          @NamedQuery(name = "rbStorage.findAll",
                query = "SELECT s FROM RbStorage s")
        })
public class RbStorage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=256)
	private String name;

	@Column(nullable=false, length=50)
	private String uuid;

	//bi-directional many-to-one association to RlsBalanceOfGood
	@OneToMany(mappedBy="rbStorage")
	private List<RlsBalanceOfGood> rlsBalanceOfGoods;

	//bi-directional many-to-one association to OrgStructure
	@ManyToOne
	@JoinColumn(name="orgStructure_id")
	private OrgStructure orgStructure;

	public RbStorage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<RlsBalanceOfGood> getRlsBalanceOfGoods() {
		return this.rlsBalanceOfGoods;
	}

	public void setRlsBalanceOfGoods(List<RlsBalanceOfGood> rlsBalanceOfGoods) {
		this.rlsBalanceOfGoods = rlsBalanceOfGoods;
	}

	public OrgStructure getOrgStructure() {
		return this.orgStructure;
	}

	public void setOrgStructure(OrgStructure orgStructure) {
		this.orgStructure = orgStructure;
	}

}