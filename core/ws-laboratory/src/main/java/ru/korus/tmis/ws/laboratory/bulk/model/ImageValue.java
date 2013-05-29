package ru.korus.tmis.ws.laboratory.bulk.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author anosov@outlook.com
 *         date: 5/28/13
 */
@XmlRootElement
public class ImageValue {

    /**
     * строка описания
     */
    @XmlElement
    private String imageString;

    /**
     * картинка, закодированная в Base64
     */
    @XmlElement
    private String imageData;

    String getImageString() {
        return imageString;
    }

    void setImageString(String imageString) {
        this.imageString = imageString;
    }

    String getImageData() {
        return imageData;
    }

    void setImageData(String imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "ImageValue{" +
                "imageString='" + imageString + '\'' +
                ", imageData='" + imageData + '\'' +
                '}';
    }
}

