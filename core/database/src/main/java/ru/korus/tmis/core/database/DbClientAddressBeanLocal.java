package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.AddressEntryContainer;
import ru.korus.tmis.core.entity.model.ClientAddress;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbClientAddressBeanLocal {
    Iterable<ClientAddress> getAllAddresses(int patientId)
            throws CoreException;

    ClientAddress getClientAddressById(int id)
            throws CoreException;

    ClientAddress getClientAddressByClientIdAddressType(int patientId, int addressType)
            throws CoreException;

    ClientAddress insertOrUpdateClientAddress(int addressTypeId,
                                              AddressEntryContainer address,
                                              Patient patient,
                                              Staff sessionUser)
            throws CoreException;

    void deleteClientAddress(int id,
                             Staff sessionUser)
            throws CoreException;

}

