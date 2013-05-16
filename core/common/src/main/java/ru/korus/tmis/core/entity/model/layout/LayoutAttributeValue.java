package ru.korus.tmis.core.entity.model.layout;

import ru.korus.tmis.core.entity.model.ActionPropertyType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Entity для работы с таблицей LayoutAttributeValue
 * Author: idmitriev Systema-Soft
 * Date: 5/14/13 2:20 PM
 * Since: 1.0.1.10
 */
@Entity
@Table(name = "LayoutAttributeValue", catalog = "", schema = "")
@NamedQueries({
        @NamedQuery(name = "LayoutAttributeValue.findByActionPropertyTypeId", query = "SELECT lov FROM LayoutAttributeValue lov WHERE lov.actionPropertyType.id = :id")})
@XmlType(name = "layoutAttributeValue")
@XmlRootElement(name = "layoutAttributeValue")
public class LayoutAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "actionPropertyType_id")
    private ActionPropertyType actionPropertyType;

    @ManyToOne
    @JoinColumn(name = "layoutAttribute_id")
    private LayoutAttribute layoutAttribute;

    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 1023)
    @Column(name = "value")
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ActionPropertyType getActionPropertyType() {
        return actionPropertyType;
    }

    public void setActionPropertyType(ActionPropertyType actionPropertyType) {
        this.actionPropertyType = actionPropertyType;
    }

    public LayoutAttribute getLayoutAttribute() {
        return layoutAttribute;
    }

    public void setLayoutAttribute(LayoutAttribute layoutAttribute) {
        this.layoutAttribute = layoutAttribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LayoutAttributeValue that = (LayoutAttributeValue) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue[ id=" + id + " ]";
    }
}
