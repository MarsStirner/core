package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the rbFinance1C database table.
 */
@Entity
@Table(name = "rbFinance1C")
@NamedQueries(
        {
                @NamedQuery(name = "RbFinance1C.getByFinanceId", query = "SELECT f FROM RbFinance1C f WHERE f.rbFinance.id = :financeId")
        }
)

public class RbFinance1C implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false, length = 127)
    private String code1C;

    // bi-directional many-to-one association to RbFinance
    @ManyToOne
    @JoinColumn(name = "finance_id")
    private RbFinance rbFinance;

    public RbFinance1C() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode1C() {
        return this.code1C;
    }

    public void setCode1C(String code1C) {
        this.code1C = code1C;
    }

    public RbFinance getRbFinance() {
        return this.rbFinance;
    }

    public void setRbFinance(RbFinance rbFinance) {
        this.rbFinance = rbFinance;
    }

}