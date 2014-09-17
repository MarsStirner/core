package ru.korus.tmis.prescription;

import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.05.14, 11:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
public class AssigmentIntervalDataArray {

    private List<AssigmentIntervalData> data = new LinkedList<AssigmentIntervalData>();

    public List<AssigmentIntervalData> getData() {
        return data;
    }

    public void setData(List<AssigmentIntervalData> data) {
        this.data = data;
    }
}
