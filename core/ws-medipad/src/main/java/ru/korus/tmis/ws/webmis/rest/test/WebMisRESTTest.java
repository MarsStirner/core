package ru.korus.tmis.ws.webmis.rest.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 7/11/13
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class WebMisRESTTest {

    @InjectMocks
    WebMisRESTImpl wsImpl = new WebMisRESTImpl();

    @Mock
    private DbOrgStructureBeanLocal dbOrgStructureBean;

    @Before
    public void init() {
    }

    @Test
    public void testGetAllDepartments() throws CoreException {

        //request
        final DepartmentsDataFilter filter = new DepartmentsDataFilter(false);
        final ListDataRequest request = new ListDataRequest("id", "asc", 10, 1, filter);
        //test response
        //(считаем что бд вернул список из 3-х отделений)
        List<OrgStructure> list = new ArrayList<OrgStructure>();
        list.add(new OrgStructure(17));
        list.add(new OrgStructure(18));
        list.add(new OrgStructure(20));

        //AllDepartmentsListData data = new AllDepartmentsListData();
        //data.setRequestData(request);

        when(dbOrgStructureBean.getCountAllOrgStructuresWithFilter(request.filter())).thenReturn(3L);
        when(dbOrgStructureBean.getAllOrgStructuresByRequest(request.limit(),
                request.page()-1,
                request.sortingFieldInternal(),
                request.filter().unwrap())).thenReturn(list);

        AllDepartmentsListData result = wsImpl.getAllDepartments(request);

        verify(dbOrgStructureBean).getCountAllOrgStructuresWithFilter(request.filter());
        verify(dbOrgStructureBean).getAllOrgStructuresByRequest(request.limit(),
                request.page()-1,
                request.sortingFieldInternal(),
                request.filter().unwrap());

        Assert.assertNotNull(result);
        //проверка количества
        Assert.assertEquals(3L, result.requestData().recordsCount());
        Assert.assertEquals(3, result.data().size());
        //проверка сохранности сортировки
        Assert.assertEquals(list.get(0).getId().intValue(), result.data().get(0).id());
        Assert.assertEquals(list.get(1).getId().intValue(), result.data().get(1).id());
        Assert.assertEquals(list.get(2).getId().intValue(), result.data().get(2).id());
        validateMockitoUsage();
    }

}

