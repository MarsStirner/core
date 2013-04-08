@echo off
SET GF_PASSWD_FILE=.\password

echo AS_ADMIN_PASSWORD=${glassfish.admin.password}>%GF_PASSWD_FILE%
echo AS_ADMIN_MASTERPASSWORD=${glassfish.admin.password}>>%GF_PASSWD_FILE%

SET PATH=${glassfish.home}\bin\;$PATH
echo.
echo.
echo.
echo.
echo --------------------------------------------------------------------
echo List domains
echo.
call asadmin list-domains
echo --------------------------------------------------------------------
echo Stop domains
echo.
call asadmin stop-domain
echo --------------------------------------------------------------------
echo Delete domain ${glassfish.domain}
echo.
call asadmin delete-domain ${glassfish.domain}
echo --------------------------------------------------------------------
echo Create domain ${glassfish.domain}
echo.
call asadmin --user ${glassfish.admin.login} --passwordfile %GF_PASSWD_FILE% ^
 create-domain ^
 --instanceport ${glassfish.port.instance} ^
 --adminport ${glassfish.port.admin} --savemasterpassword --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo.
echo --------------------------------------------------------------------
echo.
echo Copy config file to ${glassfish.domain.dir}\${glassfish.domain}\config\logback.xml
copy /y logback.xml ${glassfish.domain.dir}\${glassfish.domain}\config\
echo Copy library to ${glassfish.domain.dir}\${glassfish.domain}\lib\ext\
copy /y ..\scripts\lib\*.* ${glassfish.domain.dir}\${glassfish.domain}\lib\ext\
echo.
echo --------------------------------------------------------------------
echo Start domain ${glassfish.domain}
call asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo --------------------------------------------------------------------
echo Enable secure admin
echo.
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        enable-secure-admin
echo --------------------------------------------------------------------
echo Add JVM options
echo.
echo for logback.xml
echo.
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target server-config '-Dlogback.configurationFile=${com.sun.aas.instanceRoot}/config/logback.xml'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target default-config '-Dlogback.configurationFile=${com.sun.aas.instanceRoot}/config/logback.xml'
echo.
echo for remote debug on 5005 port
echo.
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target server-config '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target default-config '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005'
echo.
echo for JVM
echo.
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target server-config '-XX:+UnlockExperimentalVMOptions'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        delete-jvm-options --target server-config '-Xmx512m'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target server-config '-Xmx1024m'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        delete-jvm-options --target server-config '-XX:MaxPermSize=192m'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target server-config '-XX:MaxPermSize=256m'


call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target default-config '-XX:+UnlockExperimentalVMOptions'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        delete-jvm-options --target default-config '-Xmx512m'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target default-config '-Xmx1024m'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        delete-jvm-options --target default-config '-XX:MaxPermSize=192m'
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jvm-options --target default-config '-XX:MaxPermSize=256m'

echo --------------------------------------------------------------------
echo Create DB pools
echo.
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jdbc-connection-pool ^
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource ^
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.mis}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.mis}:zeroDateTimeBehavior=convertToNull ^
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true ^
        ${mysql.db.mis.pool}
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jdbc-resource ^
        --connectionpoolid ${mysql.db.mis.pool} ${mysql.db.jndi.mis}


call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jdbc-connection-pool ^
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource ^
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.rls}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.rls}:zeroDateTimeBehavior=convertToNull ^
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true ^
        ${mysql.db.rls.pool}
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jdbc-resource ^
        --connectionpoolid ${mysql.db.rls.pool} ${mysql.db.jndi.rls}


call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jdbc-connection-pool ^
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource ^
        --property user=${mysql.login}:password=${mysql.password}:DatabaseName=${mysql.db.tmis_core}:ServerName=${mysql.db.host}:port=${mysql.db.port}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${mysql.db.tmis_core}:zeroDateTimeBehavior=convertToNull ^
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true ^
        ${mysql.db.tmis_core.pool}
call asadmin --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        create-jdbc-resource ^
        --connectionpoolid ${mysql.db.tmis_core.pool} ${mysql.db.jndi.tmis_core}

del -f %GF_PASSWD_FILE%

echo --------------------------------------------------------------------
echo Stop-Start domain ${glassfish.domain}
echo.
call asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
call asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo --------------------------------------------------------------------


