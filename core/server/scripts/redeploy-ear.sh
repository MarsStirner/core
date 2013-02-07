#!/bin/bash
#
# Скрипт редеплоя приложения
#

GF_APP_NAME=$2
APP=$1
GF_PASSWD_FILE=./password

# Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="${glassfish.admin.password} > $GF_PASSWD_FILE
echo "AS_ADMIN_MASTERPASSWORD="${glassfish.admin.password} >> $GF_PASSWD_FILE

export PATH=${glassfish.home}/bin/:$PATH
asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}

# Установка приложения
asadmin --host localhost \
        --port ${glassfish.port.admin} \
        --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        --interactive=false \
        --echo=true \
        --terse=true \
        redeploy --name ${glassfish.application.name} \
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
