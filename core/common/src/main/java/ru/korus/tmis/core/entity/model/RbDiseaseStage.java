package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 05.08.13
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbDiseaseStage")
@NamedQueries(
        {
                @NamedQuery(name = "RbDiseaseStage.findAll", query = "SELECT res FROM RbDiseaseStage res")
        })
@XmlType(name = "rbDiseaseStage")
@XmlRootElement(name = "rbDiseaseStage")
public class RbDiseaseStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "characterRelation")
    private int characterRelation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCharacterRelation() {
        return characterRelation;
    }

    public void setCharacterRelation(int characterRelation) {
        this.characterRelation = characterRelation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbDiseaseStage that = (RbDiseaseStage) o;

        if (characterRelation != that.characterRelation) return false;
        if (!code.equals(that.code)) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbDiseaseStage[id=" + id + "]";
    }
}
