package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 03.02.14, 18:19 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Entity
@Table(name = "VariablesforSQL")
@NamedQueries({
        @NamedQuery(name = "VariablesforSQL.findSpecialVar",
                query = "SELECT v FROM VariablesforSQL v WHERE v.specialVar = :specialVar")
})
public class VariablesforSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "specialVarName_id")
    private RbSpecialVariablesPreferences specialVar;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "var_type")
    private String type;

    @Basic(optional = false)
    @Column(name = "label")
    private String label;

    public String getInfo(){
        final StringBuilder result = new StringBuilder("VariableForSQL [");
        result.append(" name=").append(name);
        result.append(" type=").append(type);
        result.append(" label=").append(label);
        result.append("]");
        return result.toString();
    }

    public VariablesforSQL() {
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariablesforSQL that = (VariablesforSQL) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!specialVar.equals(that.specialVar)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + specialVar.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    //Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RbSpecialVariablesPreferences getSpecialVar() {
        return specialVar;
    }

    public void setSpecialVar(RbSpecialVariablesPreferences specialVar) {
        this.specialVar = specialVar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
