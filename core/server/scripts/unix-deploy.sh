#!/usr/bin/env bash
#
# Скрипт редеплоя приложения c рестартом
#
if [ -z "$BASH_VERSION" ]
then
    exec bash "$0" "$@"
fi

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

UNDEPLOY_CMD_WITHOUT_APPNAME="$ASADMIN/asadmin --host ${HOST} \
        --port ${ADMIN_PORT} \
        --user  ${ADMIN_LOGIN} \
        --passwordfile ${GF_PASSWD_FILE} \
        --interactive=false \
        undeploy \
        "

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


EQUALS_APPLICATIONS=""
function list_applications {
    print_header "List applications"

    echo $LIST_APPLICATION_CMD
    local APPLICATIONS_PLAIN=`$LIST_APPLICATION_CMD`
    
    local NEWAPP=$(echo "$APP_NAME" | sed 's/-[0-9].*//')

    # Проходим по списку установленных приложений
    while IFS= read -r line
    do
        echo "$line"
        # Получаем первое слово (это имя приложения и его версия)
        APP=`echo "$line" | awk '{ print $1 }'` 
        # Получаем имя приложения без версии
        PROGRAM_NAME=$(echo "$APP" | sed 's/-[0-9].*//')
        # Если оно совпадает с именем устанавливаемого приложения, то добавляем его в список на удаление
        if [ $NEWAPP == $PROGRAM_NAME ]; then
            EQUALS_APPLICATIONS+=$APP
            EQUALS_APPLICATIONS+=$'\n'
        fi
    done <<< "$APPLICATIONS_PLAIN"  
}

function undeploy {
    print_header "Undeploy $(echo "$APP_NAME" | sed 's/-[0-9].*//')"    
    
    NUM_OF_APPS=`echo "EQUALS_APPLICATIONS" | wc -l`
     
    if [ $NUM_OF_APPS == 1 ] && [ "$EQUALS_APPLICATIONS" != "" ]; then 
        UNDEPLOY_CMD="${UNDEPLOY_CMD_WITHOUT_APPNAME} $EQUALS_APPLICATIONS"
        echo $UNDEPLOY_CMD    
        if ! $UNDEPLOY_CMD; then
            # Может завершиться ошибкой, но это не критично
            echo $?
        fi

    elif [ $NUM_OF_APPS == 0 ] || [ "$EQUALS_APPLICATIONS" == "" ]; then
        echo "Cannot find similar applications. If there are any of them undeploy them manually."
    else
        echo "Founded more than 1 similar applications. Undeploy them manually."
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
    print_header "Deploy ${APP_NAME}"

    if ! $DEPLOY_CMD; then
        # Выходим с ошибкой в случае ошибки развертывания приложения
        exit 3
    fi
}

function print_tail {
    print_separator
    echo ""
    # Показать лог
    echo "tail -f -n 5000 ${DOMAIN_DIR}/${DOMAIN}/logs/tmis-core/core.$(date +%Y-%-m-%-d).log"
    echo "tail -f -n 5000 ${DOMAIN_DIR}/${DOMAIN}/logs/pharmacy/pharmacy.$(date +%Y-%-m-%-d).log"
    echo "tail -f -n 5000 ${DOMAIN_DIR}/${DOMAIN}/logs/server.log"
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
