package ru.risar.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anosov
 *         Date: 29.07.13 11:35
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Addresses {
    
    @XmlElement(name = "address")
    private List<Address> addresses = new ArrayList<Address>();

    public Addresses() {
    }

    public Addresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
