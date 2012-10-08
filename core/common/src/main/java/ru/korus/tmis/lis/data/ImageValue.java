package ru.korus.tmis.lis.data;

import javax.xml.bind.annotation.XmlElement;

/**
 * Класс для передачи изображений
 */
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
