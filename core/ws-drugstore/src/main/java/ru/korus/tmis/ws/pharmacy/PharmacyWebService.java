package ru.korus.tmis.ws.pharmacy;

import ru.korus.tmis.core.entity.model.Medicament;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        targetNamespace = "http://korus.ru/tmis/pharmacy",
        name = "pharmacy"
)
public interface PharmacyWebService {

    /**
     * Получить список лекарств, имеющихся в наличии.
     *
     * @param medicament наименование лекарства.
     *
     * @return cписок имеющихся лекарств-аналогов
     */
    @WebMethod
    List<Medicament> getAvailableMedicaments(String medicament);

    /**
     * Проверить доступность лекарства.
     *
     * @param code код лекарства из РЛС (rls.vNomen.code)
     *
     * @return true, если лекарство есть в наличии в аптеке
     */
    @WebMethod
    boolean getIsMedicamentAvailable(int code);

    /**
     * Передать назначение в 1С Аптеку.
     *
     * @param prescriptionId ID назначения (ключ таблицы Action)
     */
    @WebMethod
    void setPrescription(int prescriptionId);
}
