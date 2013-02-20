rem Скрипт установки ядра МИС
rem Требования:
rem       - установленный сервер БД MySQL >= 5.1.X
rem       - установленный консольный клиент MySQL
rem       - установленный сервер приложений Glassfish 3.1.1
rem       - в переменной окружения $PATH должен содержаться путь к директории, 
rem         в которой расположен исполняемый файл asadmin


rem asadmin stop-domain

rem Удаление домена GF
call asadmin delete-domain fccho

rem Создание домена GF
call asadmin create-domain --user admin --passwordfile PASSWD_FILE --instanceport 8080 --adminport 4848 --savemasterpassword --domaindir C:\Winprog\glassfish3\glassfish\domains\ fccho

rem Копирование библиотек
call copy lib\*.* C:\Winprog\glassfish3\glassfish\domains\fccho\lib\ext\

rem Перезапуск домена
call asadmin stop-domain --domaindir C:\Winprog\glassfish3\glassfish\domains\ fccho
call asadmin start-domain --domaindir C:\Winprog\glassfish3\glassfish\domains\ fccho

rem Создание пулов соединений и ресурсов для необходимых ядру БД
call asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=s11r64:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=s11r64:zeroDateTimeBehavior=convertToNull --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true  s11r64-pool

call asadmin --user admin --passwordfile PASSWD_FILE create-jdbc-resource --connectionpoolid s11r64-pool s11r64

call asadmin --user admin --passwordfile PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=rls:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=rls:zeroDateTimeBehavior=convertToNull rls-pool

call asadmin --user admin --passwordfile PASSWD_FILE create-jdbc-resource --connectionpoolid rls-pool rls

call asadmin --user admin --passwordfile PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=tmis_core:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=tmis_core:zeroDateTimeBehavior=convertToNull  tmis_core-pool

call asadmin --user admin --passwordfile PASSWD_FILE create-jdbc-resource --connectionpoolid tmis_core-pool tmis_core


