#!/bin/bash
#
# Скрипт редеплоя приложения c рестартом
#

# Получение расположения данного скрипта. Таким образом мы получаем возможность запускать его из любой директории.
SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
    DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
    SOURCE="$(readlink "$SOURCE")"
    [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

GF_PASSWD_FILE="${DIR}/password"

source ${DIR}/unix-common.sh

LIST_APPLICATION_CMD="$ASADMIN/asadmin --user ${ADMIN_LOGIN} \
        --passwordfile ${GF_PASSWD_FILE} \
        --port ${ADMIN_PORT} \
        list-applications"

UNDEPLOY_CMD="$ASADMIN/asadmin --host ${HOST} \
        --port ${ADMIN_PORT} \
        --user  ${ADMIN_LOGIN} \
        --passwordfile ${GF_PASSWD_FILE} \
        --interactive=false \
        undeploy \
        ${APP_NAME}"

DEPLOY_CMD="${ASADMIN}/asadmin --host ${HOST} \
        --port ${ADMIN_PORT} \
        --user ${ADMIN_LOGIN} \
        --passwordfile ${GF_PASSWD_FILE} \
        --interactive=false \
        --echo=true \
        --terse=true \
        deploy \
        --name ${APP_NAME} \
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
        ${APP_NAME}.ear"

# Создание файла с паролями
echo "AS_ADMIN_PASSWORD="${ADMIN_PASSWORD} > $GF_PASSWD_FILE
echo "AS_ADMIN_MASTERPASSWORD="${MASTER_PASSWORD} >> $GF_PASSWD_FILE

# Необходимость данной конструкции под сомнением
export PATH=${glassfish.home}/bin/:$PATH

function list_applications {
    print_header "List applications"

    echo $LIST_APPLICATION_CMD
    if ! $LIST_APPLICATION_CMD; then
        exit 1
    fi
}

function undeploy {
    print_header "Undeploy ${glassfish.application.name}"

    echo $UNDEPLOY_CMD
    if ! $UNDEPLOY_CMD; then
       # exit 3
       echo !!
    fi
}

function copy_config {
    print_header "Copy config file"

    echo $COPY_CONFIG_CMD
    if ! $COPY_CONFIG_CMD; then
        exit 2
    fi
}

function deploy_application {
    print_header "Deploy ${glassfish.application.name}"

    if ! $DEPLOY_CMD; then
        # Выходим с ошибкой в случае ошибки развертывания приложения
        exit 3
    fi
}

function print_tail {
    print_separator
    echo ""
    # Показать лог
    echo "tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/tmis-core/core.log"
    echo "tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/pharmacy/pharmacy.log"
    echo "tail -f -n 5000 ${com.sun.aas.instanceRoot}/logs/server.log"
}


# Удаление файла с паролями по завершению скрипта (в том числе при возникновении ошибки)
function cleanup {
    echo "rm -f ${GF_PASSWD_FILE}"
    rm -f $GF_PASSWD_FILE
}
trap cleanup EXIT

# Запуск функций
list_domains
list_applications
undeploy
stop_domain
copy_config
start_domain
deploy_application
print_tail