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
# Параметры запуска скрипта: recreatedomain.sh <имя ear файла> <название домена>

# Настроечные параметры
MYSQL_ROOT_PASSWD=root

MIS_DB_NAME=s11r64
RLS_DB_NAME=rls

GF_ADMIN_USER=admin                                                                                                                                             
GF_ADMIN_PASSWD=admin                                                                                                                                           
GF_MASTER_PASSWD=admin                                                                                                                                          
GF_DOMAIN_NAME=$2
                                                                                                                                     
GF_DOMAIN_DIR=/usr/local/glassfish3/glassfish/domains
GF_INSTANCE_PORT=8080                                                                                                           
GF_ADMIN_PORT=4848                                                                                                                                              
APP=$1
GF_PASSWD_FILE=./password                                                                                                                                       

# Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="$GF_ADMIN_PASSWD > $GF_PASSWD_FILE                                                                                                     
echo "AS_ADMIN_MASTERPASSWORD="$GF_MASTER_PASSWD >> $GF_PASSWD_FILE                                                                                             

export PATH=/usr/local/glassfish3/bin/:$PATH
asadmin stop-domain

# Удаление домена GF
asadmin delete-domain $GF_DOMAIN_NAME

# Создание домена GF
asadmin create-domain --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE --instanceport $GF_INSTANCE_PORT --adminport $GF_ADMIN_PORT --profile developer --savemasterpassword --domaindir $GF_DOMAIN_DIR $GF_DOMAIN_NAME

# Копирование библиотек
cp lib/* $GF_DOMAIN_DIR/$GF_DOMAIN_NAME/lib/ext/

# Перезапуск домена
asadmin stop-domain --domaindir $GF_DOMAIN_DIR $GF_DOMAIN_NAME
asadmin start-domain --domaindir $GF_DOMAIN_DIR $GF_DOMAIN_NAME

# Создание пулов соединений и ресурсов для необходимых ядру БД
asadmin --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=root:password=$MYSQL_ROOT_PASSWD:DatabaseName=$MIS_DB_NAME:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=s11r64:zeroDateTimeBehavior=convertToNull \
        $MIS_DB_NAME-pool
asadmin --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE create-jdbc-resource --connectionpoolid $MIS_DB_NAME-pool s11r64

asadmin --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=root:password=$MYSQL_ROOT_PASSWD:DatabaseName=$RLS_DB_NAME:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=rls:zeroDateTimeBehavior=convertToNull \
        $RLS_DB_NAME-pool
asadmin --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE create-jdbc-resource --connectionpoolid $RLS_DB_NAME-pool rls

asadmin --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=root:password=$MYSQL_ROOT_PASSWD:DatabaseName=tmis_core:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=tmis_core:zeroDateTimeBehavior=convertToNull \
        tmis_core-pool
asadmin --user $GF_ADMIN_USER --passwordfile $GF_PASSWD_FILE create-jdbc-resource --connectionpoolid tmis_core-pool tmis_core

rm -f $GF_PASSWD_FILE
