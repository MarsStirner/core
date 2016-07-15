package ru.bars.open.tmis.lis.innova.wssoap.entites;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Изображение
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "imageString",
        "imageData"
})
public class ImageValue {
    /**
     * строка описания
     */
    @XmlElement(name = "imageString")
    private String imageString;

    /**
     * картинка, закодированная в Base64
     */
    @XmlElement(name = "imageData")
    private String imageData;

    public String getImageString() {
        return imageString;
    }

    public void setImageString(final String imageString) {
        this.imageString = imageString;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(final String imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImageValue{");
        sb.append("imageString='").append(imageString).append('\'');
        sb.append(", imageData='").append(imageData).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
