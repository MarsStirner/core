package ru.korus.tmis.core.entity.model.layout;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Entity для работы с таблицей LayoutAttribute
 * Author: idmitriev Systema-Soft
 * Date: 5/14/13 1:13 PM
 * Since: 1.0.1.10
 */
@Entity
@Table(name = "LayoutAttribute", catalog = "", schema = "")
@NamedQueries({
        @NamedQuery(name = "LayoutAttribute.findAll", query = "SELECT lo FROM LayoutAttribute lo"),
        @NamedQuery(name = "LayoutAttribute.findById", query = "SELECT lo FROM LayoutAttribute lo WHERE lo.id = :id")})
@XmlType(name = "layoutAttribute")
@XmlRootElement(name = "layoutAttribute")
public class LayoutAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

   /* @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "name")
    private String name;*/

    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "title")
    private String title;

    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 1023)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Size(min = 0, max = 255)
    @Column(name = "typeName")
    private String typeName = null;

    @Basic(optional = false)
    @Size(min = 0, max = 255)
    @Column(name = "measure")
    private String measure = null;

    @Basic(optional = false)
    @Size(min = 0, max = 1023)
    @Column(name = "defaultValue")
    private String defaultValue = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LayoutAttribute that = (LayoutAttribute) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.layout.LayoutAttribute[ id=" + id + " ]";
    }
}

