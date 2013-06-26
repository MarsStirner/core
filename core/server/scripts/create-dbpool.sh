#!/bin/bash
#
# Скрипт создания пула к БД
#
# Создание файла с паролями
echo "AS_ADMIN_PASSWORD="admin1234 > ./password
echo "AS_ADMIN_MASTERPASSWORD="admin1234 >> ./password

asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-connection-pool \
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.mis}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.mis}:zeroDateTimeBehavior=convertToNull \
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true \
        ${mysql.db.mis.pool}

asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-resource \
        --connectionpoolid ${mysql.db.mis.pool} ${mysql.db.jndi.mis}


rm -f ./password