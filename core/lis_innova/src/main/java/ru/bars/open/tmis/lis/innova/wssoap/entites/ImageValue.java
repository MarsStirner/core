package ru.bars.open.tmis.lis.innova.wssoap.entites;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Класс для передачи изображений
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {
        "description",
        "imageData"
})
public class ImageValue {
    /**
     * строка описания
     */
    private String description;

    /**
     * картинка, закодированная в Base64
     */
    private String imageData;

    @XmlElement(name = "imageString")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "imageData")
    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
