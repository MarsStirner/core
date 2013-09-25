#!/bin/bash
#
# Скрипт редеплоя приложения c рестартом
#

GF_PASSWD_FILE=./password
ASADMIN=${glassfish.bin}


# Создание файла с паролями
echo "AS_ADMIN_PASSWORD="${glassfish.admin.password} > $GF_PASSWD_FILE
echo "AS_ADMIN_MASTERPASSWORD="${glassfish.master.password} >> $GF_PASSWD_FILE

export PATH=${glassfish.home}/bin/:$PATH
echo ""
echo ""
echo ""
echo ""
echo "--------------------------------------------------------------------"
echo "List domains"
echo ""
$ASADMIN/asadmin list-domains
echo ""
echo "--------------------------------------------------------------------"
echo "List applications"
echo ""
$ASADMIN/asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        --port ${glassfish.port.admin} \
       list-applications
echo ""
echo "--------------------------------------------------------------------"
echo "Undeploy ${glassfish.application.name}"
echo ""
$ASADMIN/asadmin --host ${glassfish.host} \
        --port ${glassfish.port.admin} \
        --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        --interactive=false \
        undeploy \
        ${glassfish.application.name}
echo "--------------------------------------------------------------------"
echo "Stop Glassfish"
echo ""
$ASADMIN/asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo "--------------------------------------------------------------------"
echo "Copy config file to ${glassfish.domain.dir}/${glassfish.domain}/config/logback.xml"
echo ""
cp ./logback.xml ${glassfish.domain.dir}/${glassfish.domain}/config
echo "--------------------------------------------------------------------"
echo "Start Glassfish"
echo ""
$ASADMIN/asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo "--------------------------------------------------------------------"
echo "Deploy ${glassfish.application.name}"
echo ""
$ASADMIN/asadmin --host ${glassfish.host} \
        --port ${glassfish.port.admin} \
        --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        --interactive=false \
        --echo=true \
        --terse=true \
        deploy \
        --name ${glassfish.application.name} \
        --force=true \
        --precompilejsp=false \
        --verify=false \
        --generatermistubs=false \
        --availabilityenabled=false \
        --asyncreplication=true \
        --keepreposdir=false \
        --keepfailedstubs=false \
        --isredeploy=false \
        --logreportederrors=true \
        --upload=true \
        ${glassfish.application.name}.ear
echo "--------------------------------------------------------------------"
echo ""
rm -f $GF_PASSWD_FILE

# Показать лог
echo "tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/tmis-core/core.log"
echo "tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/pharmacy/pharmacy.log"
echo "tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/server.log"
echo ""
echo ""
tail -n 1000 ${com.sun.aas.instanceRoot}/logs/server.log | grep "${glassfish.application.name} was successfully deployed in"
echo ""
echo ""