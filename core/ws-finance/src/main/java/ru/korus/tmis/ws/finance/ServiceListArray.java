package ru.korus.tmis.ws.finance;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.04.14, 17:02 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ServiceListArray {

    private List<ServiceInfo> listService = new LinkedList<ServiceInfo>();

    public  List<ServiceInfo>  getListService() {
        return listService;
    }

    public void setListService( List<ServiceInfo>  listService) {
        this.listService = listService;
    }
}
