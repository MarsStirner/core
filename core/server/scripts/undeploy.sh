#!/bin/bash
#
# Скрипт редеплоя приложения
#

GF_PASSWD_FILE=./password

# Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="${glassfish.admin.password} > $GF_PASSWD_FILE
echo "AS_ADMIN_MASTERPASSWORD="${glassfish.admin.password} >> $GF_PASSWD_FILE

export PATH=${glassfish.home}/bin/:$PATH

# Удаление приложения
asadmin --host ${glassfish.host} \
        --port ${glassfish.port.admin} \
        --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        --interactive=false \
        --echo=true \
        --terse=true \
        undeploy \
        --name ${glassfish.application.name} \
        --force=true

#asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
#asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}

rm -f $GF_PASSWD_FILE

echo "--------------------------------------------------------------------"
echo "Create alias 'tt' -> 'tail TMIS-CORE'"
echo "--------------------------------------------------------------------"
alias tt="tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/tmis-core/core.log"

echo "--------------------------------------------------------------------"
echo "Create alias 'ss' -> 'tail server.log'"
echo "--------------------------------------------------------------------"
alias ss="tail -f -n 5000 ${glassfish.domain.dir}/${glassfish.domain}/logs/server.log"

# Показать последние строки лога
# tail -n 5000 ${glassfish.domain.dir}/${glassfish.domain}/logs/server.log