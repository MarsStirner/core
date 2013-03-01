package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Job;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Методы для работы с Job
 * Author: mmakankov Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.69
 */
@Local
public interface DbJobBeanLocal {
    Job getJobById(int id) throws CoreException;

}
