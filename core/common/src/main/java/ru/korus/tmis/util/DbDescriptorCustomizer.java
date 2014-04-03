package ru.korus.tmis.util;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import org.eclipse.persistence.mappings.DatabaseMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.01.14, 15:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DbDescriptorCustomizer implements DescriptorCustomizer {

    DatabaseField tmpF;
    org.eclipse.persistence.mappings.DatabaseMapping tmpM;

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        System.out.println("**************************** DbDescriptorCustomizer.customize: " + classDescriptor.getAlias());
       /*for ( DatabaseField primaryKeyFields : classDescriptor.getPrimaryKeyFields() ) {
            if ( !primaryKeyFields.getName().startsWith("`") && "index".equals(primaryKeyFields.getName())) {
                primaryKeyFields.setName("`" + primaryKeyFields.getName() + "`");
                String[] qualifiedNameSplit = primaryKeyFields.getQualifiedName().split(".");
                primaryKeyFields.resetQualifiedName(qualifiedNameSplit[0] + "." + "`" + qualifiedNameSplit[1]  + "`");
                primaryKeyFields.setUseDelimiters(true);
            }
        }*/
        for(org.eclipse.persistence.mappings.DatabaseMapping mapping : classDescriptor.getMappings() ){
            tmpM = mapping;
/*
            if (mapping instanceof AggregateObjectMapping) {
                final AggregateObjectMapping a = (AggregateObjectMapping)mapping;
            }
*/
            final DatabaseField field = mapping.getField();
            tmpF = field;
            if (field != null && !field.getName().startsWith("`")) {
                final String name = field.getName();
                field.setName("`" + name + "`");
            }
        }
    }
}
