package ru.korus.tmis.util;


public interface PublicClonable<T extends PublicClonable<T>> {
    public T clone() throws CloneNotSupportedException;
}
