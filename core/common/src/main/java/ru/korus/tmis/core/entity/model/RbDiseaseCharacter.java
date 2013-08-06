package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 05.08.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbDiseaseCharacter", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "RbDiseaseCharacter.findAll", query = "SELECT res FROM RbDiseaseCharacter res")
        })
@XmlType(name = "rbDiseaseCharacter")
@XmlRootElement(name = "rbDiseaseCharacter")
public class RbDiseaseCharacter implements Serializable {

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
    @Column(name = "replaceInDiagnosis")
    private String replaceInDiagnosis;

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

    public String getReplaceInDiagnosis() {
        return replaceInDiagnosis;
    }

    public void setReplaceInDiagnosis(String replaceInDiagnosis) {
        this.replaceInDiagnosis = replaceInDiagnosis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbDiseaseCharacter that = (RbDiseaseCharacter) o;

        if (!code.equals(that.code)) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (replaceInDiagnosis != null ? !replaceInDiagnosis.equals(that.replaceInDiagnosis) : that.replaceInDiagnosis != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (replaceInDiagnosis != null ? replaceInDiagnosis.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbDiseaseCharacter[id=" + id + "]";
    }
}
