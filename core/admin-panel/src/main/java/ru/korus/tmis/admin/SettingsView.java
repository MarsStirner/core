package ru.korus.tmis.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.07.14, 17:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SettingsView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setSizeFull();
        //addStyleName("transactions");
        Table table = new Table();

        for( String colName : new String[] {"Параметр", "Значение", "Краткое описание"} ) {
            table.addContainerProperty(colName, String.class, null);
        }
        //table.setVisibleColumns(new Object[] {"Параметр", "Значение", "Краткое описание"});
        //table.setValidationVisible(true);
        addComponent(table);
    }
}
