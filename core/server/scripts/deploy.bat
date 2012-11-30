@echo off
rem  Скрипт установки ядра МИС
rem 
rem  Требования:
rem        - установленный сервер БД MySQL >= 5.1.X
rem        - установленный консольный клиент MySQL
rem        - установленный сервер приложений Glassfish 3.1.1
rem        - в переменной окружения $PATH должен содержаться путь к директории, 
rem          в которой расположен исполняемый файл asadmin
rem 
rem  Параметры запуска скрипта: deploy.sh <имя ear файла> <имя проекта (обычно имя еar без расширения)> <название домена>

rem  Настроечные параметры
SET GF_ADMIN_USER=admin1234
SET GF_ADMIN_PASSWD=admin1234
SET GF_MASTER_PASSWD=admin1234
SET GF_DOMAIN_NAME=fccho
SET GF_DOMAIN_DIR=C:/Winprog/glassfish3/glassfish/domains
SET GF_ADMIN_PORT=4848
SET GF_APP_NAME=fccho
SET APP=fccho
SET GF_PASSWD_FILE=./password

rem  Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="$GF_ADMIN_PASSWD > $GF_PASSWD_FILE                                                                                                     
echo "AS_ADMIN_MASTERPASSWORD="$GF_MASTER_PASSWD >> $GF_PASSWD_FILE                                                                                             

rem asadmin stop-domain --domaindir ~/glassfish3/glassfish/domains korus-domain
rem asadmin start-domain --domaindir ~/glassfish3/glassfish/domains korus-domain

rem  Установка приложения
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
