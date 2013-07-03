@echo off
SET PATH=${glassfish.bin};%PATH%

SET GF_PASSWD_FILE=.\password
echo AS_ADMIN_PASSWORD=${glassfish.admin.password}>%GF_PASSWD_FILE%
echo AS_ADMIN_MASTERPASSWORD=${glassfish.admin.password}>>%GF_PASSWD_FILE%

echo.
echo.
echo.
echo.
echo --------------------------------------------------------------------
echo List domains
echo.
call ${glassfish.bin}\asadmin list-domains
echo.
echo --------------------------------------------------------------------
echo List applications
echo.
call ${glassfish.bin}\asadmin --user ${glassfish.admin.login} ^
             --passwordfile %GF_PASSWD_FILE% ^
            list-applications
echo.
echo --------------------------------------------------------------------
echo Undeploy ${glassfish.application.name}
echo.
call ${glassfish.bin}\asadmin --host ${glassfish.host} ^
        --port ${glassfish.port.admin} ^
        --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        --interactive=false ^
        undeploy ^
        ${glassfish.application.name}
echo --------------------------------------------------------------------
echo Stop Glassfish
echo.
call ${glassfish.bin}\asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo --------------------------------------------------------------------
echo Copy config file to ${glassfish.domain.dir}\${glassfish.domain}\config\logback.xml
echo.
copy /Y logback.xml ${glassfish.domain.dir}\${glassfish.domain}\config\
echo --------------------------------------------------------------------
echo Start Glassfish
echo.
call ${glassfish.bin}\asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain}
echo --------------------------------------------------------------------
echo Deploy ${glassfish.application.name}
echo.
call ${glassfish.bin}\asadmin --host ${glassfish.host} ^
        --port ${glassfish.port.admin} ^
        --user ${glassfish.admin.login} ^
        --passwordfile %GF_PASSWD_FILE% ^
        --interactive=false ^
        --echo=true ^
        --terse=true ^
        deploy ^
        --name ${glassfish.application.name} ^
        --force=true ^
        --precompilejsp=false ^
        --verify=false ^
        --generatermistubs=false ^
        --availabilityenabled=false ^
        --asyncreplication=true ^
        --keepreposdir=false ^
        --keepfailedstubs=false ^
        --isredeploy=false ^
        --logreportederrors=true ^
        --upload=true ^
        ${glassfish.application.name}.ear
echo --------------------------------------------------------------------
echo.
del -f %GF_PASSWD_FILE%
echo.
echo.