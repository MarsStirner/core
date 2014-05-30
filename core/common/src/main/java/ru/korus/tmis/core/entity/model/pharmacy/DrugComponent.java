package ru.korus.tmis.core.entity.model.pharmacy;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.RlsNomen;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 15:17 <br>
 * Company: Korus Consulting IT <br>
 * Description: Компоненты Лекарства для Листов Назначений <br>
 */
@Entity
@Table(name = "DrugComponent", schema = "", catalog = "")
@NamedQueries(
        {
                @NamedQuery(name = "DrugComponent.getByActionId", query = "SELECT dc FROM DrugComponent dc WHERE dc.action.id = :actionId AND dc.cancelDateTime IS NULL"),
        }
)
public class DrugComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;

    @ManyToOne
    @JoinColumn(name = "nomen", nullable = true)
    private RlsNomen nomen;

    @Basic(optional = true)
    @Column(name = "name", nullable = true)
    private String name;

    @Basic(optional = true)
    @Column(name = "dose", nullable = true)
    private Double dose;

    @ManyToOne
    @JoinColumn(name = "unit", nullable = true)
    private RbUnit unit;

    @Basic(optional = false)
    @Column(name = "createDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;

    @Basic(optional = true)
    @Column(name = "cancelDateTime", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDateTime;

    public DrugComponent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public RlsNomen getNomen() {
        return nomen;
    }

    public void setNomen(RlsNomen nomen) {
        this.nomen = nomen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public RbUnit getUnit() {
        return unit;
    }

    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getCancelDateTime() {
        return cancelDateTime;
    }

    public void setCancelDateTime(Date cancelDateTime) {
        this.cancelDateTime = cancelDateTime;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrugComponent that = (DrugComponent) o;

        if (!action.equals(that.action)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }
}
