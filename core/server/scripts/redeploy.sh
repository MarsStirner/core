#!/bin/bash
#
# Скрипт редеплоя приложения
#

GF_PASSWD_FILE=./password

# Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="${glassfish.admin.password} > $GF_PASSWD_FILE
echo "AS_ADMIN_MASTERPASSWORD="${glassfish.admin.password} >> $GF_PASSWD_FILE

# Копирование конфига logback.xml
cp ./logback.xml ${glassfish.domain.dir}/${glassfish.domain}/config

export PATH=${glassfish.home}/bin/:$PATH

# Список доменов
asadmin list-domains

# Рестарт
#asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
#asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}

# Установка приложения
asadmin --host ${glassfish.host} \
        --port ${glassfish.port.admin} \
        --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        --interactive=false \
        --echo=true \
        --terse=true \
        redeploy \
        --name ${glassfish.application.name} \
        --force=true \
        --precompilejsp=false \
        --verify=false \
        --generatermistubs=false \
        --availabilityenabled=false \
        --asyncreplication=true \
        --keepreposdir=false \
        --keepfailedstubs=false \
        --isredeploy=true \
        --logreportederrors=true \
        --upload=true \
        ${glassfish.application.name}.ear

rm -f $GF_PASSWD_FILE

echo "--------------------------------------------------------------------"
echo "Create alias 'tt' -> 'tail TMIS-CORE'"
echo "--------------------------------------------------------------------"
alias tt="tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/tmis-core/core.log"

echo "--------------------------------------------------------------------"
echo "Create alias 'ss' -> 'tail server.log'"
echo "--------------------------------------------------------------------"
alias ss="tail -f -n 5000 ${glassfish.domain.dir}/${glassfish.domain}/logs/server.log"

# Показать лог
#tail -f -n 5000 ${glassfish.domain.dir}/${glassfish.domain}/logs/server.log