package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Mkb;
import ru.korus.tmis.core.entity.model.Thesaurus;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DbThesaurusBeanLocal {

    /**
     * Получить весь тезаурус.
     *
     * @return список элементов тезауруса
     */
    List<Thesaurus> getThesaurus()
            throws CoreException;

    /**
     * Получить дерево тезауруса, начиная с корневой вершины, имеющей код code.
     *
     * @param code код корневой вершины
     * @return список элементов тезауруса
     */
    List<Thesaurus> getThesaurusByCode(int code)
            throws CoreException;

    /**
     * Получить весь список МКБ.
     *
     * @return список элементов МКБ
     */
    List<Mkb> getMkb()
            throws CoreException;
}
