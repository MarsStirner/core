package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 11.02.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "rbTestTubeType")
@NamedQueries(
        {
                @NamedQuery(name = "RbTestTubeType.findAll", query = "SELECT t FROM RbTestTubeType t")
        })
@XmlType(name = "RbTestTubeType")
@XmlRootElement(name = "RbTestTubeType")
public class RbTestTubeType implements Serializable {

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
    @Column(name = "volume")
    private double volume = 0.0;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private RbUnit unit;

    @Column(name = "covCol")
    private String covCol;

    @Column(name = "image")
    private String image;

    @Column(name = "color")
    private String color;

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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public RbUnit getUnit() {
        return unit;
    }

    public void setUnit(RbUnit unit) {
        this.unit = unit;
    }

    public String getCovCol() {
        return covCol;
    }

    public void setCovCol(String covCol) {
        this.covCol = covCol;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbTestTubeType that = (RbTestTubeType) o;

        if (Double.compare(that.volume, volume) != 0) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (covCol != null ? !covCol.equals(that.covCol) : that.covCol != null) return false;
        if (!id.equals(that.id)) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbTestTubeType[id=" + id + "]";
    }
}