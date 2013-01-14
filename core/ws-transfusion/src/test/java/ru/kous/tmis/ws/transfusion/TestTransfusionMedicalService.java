package ru.kous.tmis.ws.transfusion;

import java.util.List;

import org.junit.Test;

import ru.korus.tmis.ws.transfusion.ProcedureType;
import ru.korus.tmis.ws.transfusion.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.TransfusionMedicalService_Service;

import static org.junit.Assert.assertTrue;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.12.12, 12:23 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestTransfusionMedicalService {
    @Test
    public void test() {
        TransfusionMedicalService_Service service = new TransfusionMedicalService_Service();
        
        final TransfusionMedicalService transfusionMedicalService = service.getTransfusionMedicalService();
        List<ProcedureType> res =
                transfusionMedicalService.getProcedureTypes();
        if(res != null) {
            for(ProcedureType pt : res)
                System.out.println("id: " + pt.getId() + " value: " + pt.getValue());
        }
        assertTrue(true);
            
    }

}
