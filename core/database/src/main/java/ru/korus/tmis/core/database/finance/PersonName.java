package ru.korus.tmis.core.database.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 12:43 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PersonName {

    private String family;

    private String given;

    private String partName;

    public String getPartName() {
        return partName;
    }

    public String getGiven() {
        return given;
    }

    public String getFamily() {
        return family;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("PersonName");
        sb.append("{family='").append(family).append('\'');
        sb.append(", given='").append(given).append('\'');
        sb.append(", partName='").append(partName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
