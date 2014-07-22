package ru.korus.tmis.admin;

import com.vaadin.data.Container;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.springframework.web.client.RestTemplate;
import ru.korus.tmis.ws.webmis.rest.SettingsInfo;


/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.07.14, 17:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SettingsView extends VerticalLayout implements View {

    private RestTemplate restTemplate;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setSizeFull();
        addStyleName("transactions");
        Table table = new Table();

        final String[] columnNames = {"Параметр", "Значение", "Краткое описание"};
        for( String colName : columnNames) {
            table.addContainerProperty(colName, String.class, null);
        }
        table.setSizeFull();
        table.addStyleName("borderless");
      //  table.setVisibleColumns(columnNames);
       // table.setValidationVisible(true);
        table.setColumnAlignment("Параметр", Table.Align.LEFT);
        table.setImmediate(true);
//        t.setColumnAlignment("Price", Table.Align.RIGHT);

      // table.setSelectable(true);
        table.setEditable(true);
        table.setTableFieldFactory(new DefaultFieldFactory  () {
            public Field createField(Container container, Object itemId,
                                     Object propertyId, Component uiContext) {
                Field field = super.createField(container,itemId,propertyId, uiContext);
                field.setReadOnly(!"Значение".equals(propertyId));
                return field;
            }
        });
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        addComponent(toolbar);
        final Label title = new Label("Параметры работы МИС:");
        title.setSizeUndefined();
        title.addStyleName("h1");
        title.setSizeUndefined();
        toolbar.addComponent(title);
        HorizontalLayout toolbar1 = new HorizontalLayout();
        toolbar1.setSizeUndefined();
        final Button buttonSave = new Button("Сохранить");
        final Button buttonApply = new Button("Применить");
        final Button buttonCancel = new Button("Отмена");
        for(Button button : new Button[] {buttonApply, buttonSave, buttonCancel}) {
            toolbar1.addComponent(button);
          //  button.setSizeUndefined();
            toolbar1.setComponentAlignment(button, Alignment.TOP_LEFT);
        }
        addComponent(toolbar1);
        addComponent(table);
        setExpandRatio(table, 1);
        restTemplate = new RestTemplate();
        initData(table);
    }

    private void initData(Table table) {
        final VaadinSession session = getSession();
        Object token = session == null ? null : session.getAttribute("token");
        if (token instanceof String) {
            restTemplate.getForObject("http://127.0.0.1:8080/tmis-ws-medipad/rest/tms-registry/settings/?_={token}", SettingsInfo.class, token);
            //table.addItem(new Object[]{"Common.OrgId", "3479", "ИД организации"}, new Integer(1));
        }
    }
}
