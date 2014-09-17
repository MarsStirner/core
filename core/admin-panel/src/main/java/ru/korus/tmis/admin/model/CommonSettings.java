package ru.korus.tmis.admin.model;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.09.14, 16:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class CommonSettings {

    private Integer orgId;

    private String orgName = "???";

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
