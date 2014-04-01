package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IndexedId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private int id;

    @Column(name = "index")
    private int index;

    public IndexedId() {
    }

    public IndexedId(final int id,
                     final int index) {
        this.id = id;
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndexedId)) {
            return false;
        }

        final IndexedId indexedId = (IndexedId) o;

        if (id != indexedId.id) {
            return false;
        }
        if (index != indexedId.index) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + index;
        return result;
    }

    @Override
    public String toString() {
        return "(" + id + "," + index + ")";
    }
}
