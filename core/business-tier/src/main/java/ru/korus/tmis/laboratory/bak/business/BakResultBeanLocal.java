package ru.korus.tmis.laboratory.bak.business;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.lis.data.model.hl7.complex.POLBIN224100UV01;
import ru.korus.tmis.util.logs.ToLog;

import javax.ejb.Local;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/18/14
 * Time: 6:33 PM
 */
@Local
public interface BakResultBeanLocal {

    public void processRequest(final POLBIN224100UV01 request, final ToLog toLog) throws CoreException;

}
