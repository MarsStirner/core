#!/bin/bash
#
# Скрипт установки ядра МИС
#
# Требования:
#       - установленный сервер БД MySQL >= 5.1.X
#       - установленный консольный клиент MySQL
#       - установленный сервер приложений Glassfish 3.1.1
#       - в переменной окружения $PATH должен содержаться путь к директории, 
#         в которой расположен исполняемый файл asadmin
#
# Параметры запуска скрипта: deploy.sh <имя ear файла> <имя проекта (обычно имя еar без расширения)> <название домена>

# Настроечные параметры
GF_ADMIN_USER=admin
GF_ADMIN_PASSWD=admin1234
GF_MASTER_PASSWD=admin1234
GF_DOMAIN_NAME=$3
GF_DOMAIN_DIR=/usr/local/opt/glassfish/libexec/glassfish/domains
GF_ADMIN_PORT=4848
GF_APP_NAME=$2
APP=$1
GF_PASSWD_FILE=./password

# Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="$GF_ADMIN_PASSWD > $GF_PASSWD_FILE                                                                                                     
echo "AS_ADMIN_MASTERPASSWORD="$GF_MASTER_PASSWD >> $GF_PASSWD_FILE                                                                                             

export PATH=/usr/local/opt/glassfish/bin/:$PATH
asadmin stop-domain --domaindir /usr/local/opt/glassfish/libexec/glassfish/domains domain1
asadmin start-domain --domaindir /usr/local/opt/glassfish/libexec/glassfish/domains domain1

# Установка приложения
asadmin --host localhost \
        --port $GF_ADMIN_PORT \
        --user $GF_ADMIN_USER \
        --passwordfile $GF_PASSWD_FILE \
    --interactive=false \
    --echo=true \
    --terse=true \
    deploy --name $GF_APP_NAME \
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
    $APP

rm -f $GF_PASSWD_FILE
