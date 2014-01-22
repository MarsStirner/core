package ru.korus.tmis.util;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.01.14, 15:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DbDescriptorCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        System.out.println("**************************** DbDescriptorCustomizer.customize");
        for(org.eclipse.persistence.mappings.DatabaseMapping mapping : classDescriptor.getMappings() ){
            final DatabaseField field = mapping.getField();
            if (field != null) {
                final String name = field.getName();
                field.setName("`" + name + "`");
            }
        }
    }
}
