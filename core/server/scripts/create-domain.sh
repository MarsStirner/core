#!/bin/bash
#
# Скрипт (пере)создания домена
#

GF_PASSWD_FILE=./password

# Создание файла с паролями                                                                                                                                     
echo "AS_ADMIN_PASSWORD="${glassfish.admin.password} > $GF_PASSWD_FILE
echo "AS_ADMIN_MASTERPASSWORD="${glassfish.admin.password} >> $GF_PASSWD_FILE

export PATH=${glassfish.home}/bin/:$PATH
asadmin stop-domain

# Список доменов
asadmin list-domains

# Удаление домена GF
asadmin delete-domain ${glassfish.domain}

# Создание домена GF
asadmin create-domain --user ${glassfish.admin.login} --passwordfile $GF_PASSWD_FILE --instanceport ${glassfish.port.instance} \
 --adminport ${glassfish.port.admin} --profile developer --savemasterpassword --domaindir ${glassfish.domain.dir} ${glassfish.domain}

# Копирование конфига logback.xml
cp ./logback.xml ${glassfish.domain.dir}/${glassfish.domain}/config

# Копирование библиотек
cp ../scripts/lib/* ${glassfish.domain.dir}/${glassfish.domain}/lib/ext/

# Перезапуск домена
asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}


# Прописываем конфигурацию логера
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target server-config --'-Dlogback.configurationFile=${com.sun.aas.instanceRoot}/config/logback.xml'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target default-config --'-Dlogback.configurationFile=${com.sun.aas.instanceRoot}/config/logback.xml'

#Настройки JVM
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target server-config --'-XX\:+UnlockExperimentalVMOptions'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target server-config --'-XX\:+UseG1GC'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        delete-jvm-options --target server-config --'-Xmx512m'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target server-config --'-Xmx1024m'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        delete-jvm-options --target server-config --'-XX\:MaxPermSize=192m'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target server-config --'-XX\:MaxPermSize=256m'

asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target default-config --'-XX\:+UnlockExperimentalVMOptions'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target default-config --'-XX\:+UseG1GC'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        delete-jvm-options --target default-config --'-Xmx512m'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target default-config --'-Xmx1024m'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        delete-jvm-options --target default-config --'-XX\:MaxPermSize=192m'
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jvm-options --target default-config --'-XX\:MaxPermSize=256m'


# Создание пулов соединений и ресурсов для необходимых ядру БД
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-connection-pool \
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.mis}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.mis}:zeroDateTimeBehavior=convertToNull \
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true \
        ${mysql.db.mis}-pool
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-resource \
        --connectionpoolid ${mysql.db.mis}-pool ${mysql.db.mis}


asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-connection-pool \
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.rls}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.rls}:zeroDateTimeBehavior=convertToNull \
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true \
        ${mysql.db.rls}-pool
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-resource \
        --connectionpoolid ${mysql.db.rls}-pool ${mysql.db.rls}


asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-connection-pool \
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource \
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.tmis_core}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.tmis_core}:zeroDateTimeBehavior=convertToNull \
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true \
        ${mysql.db.tmis_core}-pool
asadmin --user ${glassfish.admin.login} \
        --passwordfile $GF_PASSWD_FILE \
        create-jdbc-resource \
        --connectionpoolid ${mysql.db.tmis_core}-pool ${mysql.db.tmis_core}

rm -f $GF_PASSWD_FILE

# Перезапуск домена
asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
