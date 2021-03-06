package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 22.10.13
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "rbLaboratory_Test")
@NamedQueries(
        {
                @NamedQuery(name = "RbLaboratoryTest.findAll", query = "SELECT j FROM RbLaboratoryTest j")
        })
@XmlType(name = "rbLaboratoryTest")
@XmlRootElement(name = "rbLaboratoryTest")
public class RbLaboratoryTest implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "master_id")
    private RbLaboratory rbLaboratory;

    @Basic(optional = false)
    @Column(name = "test_id")
    private Integer testId;

    @Basic(optional = false)
    @Column(name = "book")
    private String book = "";

    @Basic(optional = false)
    @Column(name = "code")
    private String code = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RbLaboratory getRbLaboratory() {
        return rbLaboratory;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbLaboratoryTest that = (RbLaboratoryTest) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbLaboratoryTest[id=" + id + "]";
    }
}
