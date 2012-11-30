rem Скрипт установки ядра МИС
rem Требования:
rem       - установленный сервер БД MySQL >= 5.1.X
rem       - установленный консольный клиент MySQL
rem       - установленный сервер приложений Glassfish 3.1.1
rem       - в переменной окружения $PATH должен содержаться путь к директории, 
rem         в которой расположен исполняемый файл asadmin


rem asadmin stop-domain

rem Удаление домена GF
asadmin delete-domain fccho

rem Создание домена GF
asadmin create-domain --user admin1234 --passwordfile PASSWD_FILE --instanceport 8080 --adminport 4848 --savemasterpassword --domaindir C:\Winprog\glassfish3\glassfish\domains\ fccho

rem Копирование библиотек
copy lib\*.* C:\Winprog\glassfish3\glassfish\domains\fccho\lib\ext\

rem Перезапуск домена
asadmin stop-domain --domaindir C:\Winprog\glassfish3\glassfish\domains\ fccho
asadmin start-domain --domaindir C:\Winprog\glassfish3\glassfish\domains\ fccho

rem Создание пулов соединений и ресурсов для необходимых ядру БД
asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=s11r64:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=s11r64:zeroDateTimeBehavior=convertToNull s11r64-pool

asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-resource --connectionpoolid s11r64-pool s11r64

asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=rls:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=rls:zeroDateTimeBehavior=convertToNull rls-pool

asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-resource --connectionpoolid rls-pool rls

asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=tmis_core:ServerName=localhost:port=3306:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=tmis_core:zeroDateTimeBehavior=convertToNull tmis_core-pool

asadmin --user admin1234 --passwordfile PASSWD_FILE create-jdbc-resource --connectionpoolid tmis_core-pool tmis_core


