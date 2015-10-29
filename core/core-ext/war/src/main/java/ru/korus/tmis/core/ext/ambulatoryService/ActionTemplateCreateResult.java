package ru.korus.tmis.core.ext.ambulatoryService;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTemplateCreateResult {
    private Integer aid;
    private Condition con;
    private Integer gid;
    private Integer id;
    private String name;

    public ActionTemplateCreateResult() {
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(final Integer aid) {
        this.aid = aid;
    }

    public Condition getCon() {
        return con;
    }

    public void setCon(final Condition con) {
        this.con = con;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(final Integer gid) {
        this.gid = gid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
