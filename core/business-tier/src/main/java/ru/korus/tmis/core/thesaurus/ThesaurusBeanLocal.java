package ru.korus.tmis.core.thesaurus;

import ru.korus.tmis.core.data.ThesaurusData;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface ThesaurusBeanLocal {

    ThesaurusData getThesaurus()
            throws CoreException;

    ThesaurusData getThesaurusByCode(int code)
            throws CoreException;

    ThesaurusData getMkb()
            throws CoreException;
}
