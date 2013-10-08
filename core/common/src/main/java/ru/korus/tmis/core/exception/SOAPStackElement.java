package ru.korus.tmis.core.exception;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.10.13, 21:24 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SOAPStackElement implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "class")
    private String clazz;
    @XmlAttribute(name = "file")
    private String file;
    @XmlAttribute(name = "methodname")
    private String methodName;
    @XmlAttribute(name = "line")
    private Integer line;

}
