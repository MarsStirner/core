package ru.korus.tmis.core.database.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 12:43 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PersonFIO {

    private final String family;

    private final String given;

    private final String partName;

    public PersonFIO(String family, String given, String partName) {
        this.family = family;
        this.given = given;
        this.partName = partName;
    }

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
        sb.append("PersonFIO");
        sb.append("{family='").append(family).append('\'');
        sb.append(", given='").append(given).append('\'');
        sb.append(", partName='").append(partName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
