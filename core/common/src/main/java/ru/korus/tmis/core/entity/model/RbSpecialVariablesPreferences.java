package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 03.02.14, 18:10 <br>
 * Company: Korus Consulting IT <br>
 * Description: Специальные переменные <br>
 */
@Entity
@Table(name = "rbSpecialVariablesPreferences")
@NamedQueries(
        {
                @NamedQuery(name = "rbSpecialVariablesPreferences.getByName",
                        query = "SELECT svp FROM RbSpecialVariablesPreferences svp WHERE svp.name = :name")
        }
)
public class RbSpecialVariablesPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name", unique = true)
    private String name;

    @Basic(optional = false)
    @Column(name = "query")
    private String query;

    public RbSpecialVariablesPreferences() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbSpecialVariablesPreferences that = (RbSpecialVariablesPreferences) o;

        return !(id != null ? !id.equals(that.id) : that.id != null) && name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
