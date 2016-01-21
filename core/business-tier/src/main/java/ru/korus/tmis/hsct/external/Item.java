package ru.korus.tmis.hsct.external;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Author: Upatov Egor <br>
 * Date: 15.01.2016, 18:58 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlElement(name = "descript")
    private String description;


    @XmlElement(name = "icd_code")
    private String code;

    public Item() {
    }

    public Item(final String description, final String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Item item = (Item) o;

        if (description != null ? !description.equals(item.description) : item.description != null) {
            return false;
        }
        return !(code != null ? !code.equals(item.code) : item.code != null);

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
