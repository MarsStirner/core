package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rlsActMatters_Nomen database table.
 * 
 */
@Entity
@Table(name="rlsActMatters_Nomen")
@NamedQueries(
        {
                @NamedQuery(name = "RlsActMatters_Nomen.findNomenByCode", query = "  SELECT a FROM RlsActMatters_Nomen a WHERE a.id.nomenId = :code")
        })
public class RlsActMatters_Nomen implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RlsActMatters_NomenPK id;

	public RlsActMatters_Nomen() {
	}

	public RlsActMatters_NomenPK getId() {
		return this.id;
	}

	public void setId(RlsActMatters_NomenPK id) {
		this.id = id;
	}

}