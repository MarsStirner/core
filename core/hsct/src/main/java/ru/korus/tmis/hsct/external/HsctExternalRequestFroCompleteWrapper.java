package ru.korus.tmis.hsct.external;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HsctExternalRequestFroCompleteWrapper {
    @JsonProperty("mis_id")
    private String misId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("is_completed")
    private boolean completed;

    public HsctExternalRequestFroCompleteWrapper() {
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(final String misId) {
        this.misId = misId;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctExternalRequestFroCompleteWrapper{");
        sb.append("misId='").append(misId).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", completed=").append(completed);
        sb.append('}');
        return sb.toString();
    }
}
