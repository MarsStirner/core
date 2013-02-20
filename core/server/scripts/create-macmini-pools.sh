#!/bin/bash
#
# Скрипт установки ядра МИС
#
# Создание файла с паролями
echo "AS_ADMIN_PASSWORD="admin1234 > ./password
echo "AS_ADMIN_MASTERPASSWORD="admin1234 >> ./password


asadmin --user admin --passwordfile ./password create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=tmis:password=q1w2e3r4t5:DatabaseName=s11r64_test4:ServerName=10.2.1.58:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=s11r64_test4:zeroDateTimeBehavior=convertToNull \
        macmini-s11r64-pool

asadmin --user admin --passwordfile ./password create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=tmis:password=q1w2e3r4t5:DatabaseName=rls:ServerName=10.2.1.58:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=rls:zeroDateTimeBehavior=convertToNull \
        macmini-rls-pool

asadmin --user admin --passwordfile ./password create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=tmis:password=q1w2e3r4t5:DatabaseName=tmis_core:ServerName=10.2.1.58:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=tmis_core:zeroDateTimeBehavior=convertToNull \
        macmini-tmis_core-pool


rm -f ./password