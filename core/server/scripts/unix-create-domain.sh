#!/usr/bin/env bash
#
# Скрипт (пере)создания домена
#
# Коды ошибок:
# 1 - ошибка выполнения asadmin list-domains
# 2 - ошибка создание домена
# 3 - ошибка копирования файла конфигурации или внешних библиотек
# 4 - ошибка запуска домена
# 5 - ошибка включения secure admin
# 6 - ошибка создания пола подключений к базе данных


# Запуск данного скрипта требует bash и если он вызывается чем-то другим, то пытаемся запустить его с помощью bash
if [ -z "$BASH_VERSION" ]
then
    exec bash "$0" "$@"
fi

DELETE_JVM_OPTIONS=('-Xmx512m' '-XX\:MaxPermSize=192m')
CREATE_JVM_OPTIONS=(
#        '-Dlogback.configurationFile=${com.sun.aas.instanceRoot}/config/logback.xml'
        '-Dfile.encoding=UTF8'
        '-agentlib\:jdwp=transport=dt_socket,server=y,suspend=n,address=5005'
        '-XX\:+UnlockExperimentalVMOptions'
        '-Xmx1024m'
        '-XX\:MaxPermSize=256m'
)

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

COPY_LIBRARIES_CMD="cp ${DIR}/lib/* ${DOMAIN_DIR}/${DOMAIN}/lib/ext/"

DELETE_DOMAIN_CMD="${ASADMIN}/asadmin delete-domain ${DOMAIN}"

CREATE_DOMAIN_CMD="${ASADMIN}/asadmin --user ${ADMIN_LOGIN} --passwordfile $GF_PASSWD_FILE \
        create-domain \
        --usemasterpassword \
        --instanceport ${PORT_INSTANCE} \
        --adminport ${ADMIN_PORT} \
        --savemasterpassword=true \
        --domaindir ${DOMAIN_DIR} \
        ${DOMAIN}"

ENABLE_SECURE_ADMIN_CMD="${ASADMIN}/asadmin --user ${ADMIN_LOGIN} \
        --passwordfile $GF_PASSWD_FILE \
        --port ${ADMIN_PORT} \
        enable-secure-admin"



function stop_domain {
    print_header "Stop domain ${DOMAIN}"

    echo $STOP_DOMAIN_CMD
    if ! $STOP_DOMAIN_CMD; then
        # Остановка домена возвращает код ошибки в случае если домен не существует, поэтому ошибка на этом шаге не
        # должна приводить к остановке работы скрипта, т.к. домен просто может отсутствовать
        echo $?
    fi
}

function delete_domain {
    print_header "Delete domain ${DOMAIN}"

    echo $DELETE_DOMAIN_CMD
    if ! $DELETE_DOMAIN_CMD; then
        # Удаление домена возвращает код ошибки в случае если домен не существует, поэтому ошибка на этом шаге не
        # должна приводить к остановке работы скрипта, т.к. домен просто может отсутствовать
        echo $?
    fi
}

function create_domain {
    print_header "Create domain ${DOMAIN}"
    echo $CREATE_DOMAIN_CMD
    if ! $CREATE_DOMAIN_CMD; then
        # Ошибка создания домена должна приводить к остановке скрипта, т.к. последующее выполнения команд будет
        # невозможно
        exit 2
    fi
}

function copy_config_files {
    print_header "Copy config file and libraries"

    echo $COPY_CONFIG_CMD
    if ! $COPY_CONFIG_CMD; then
        exit 3
    fi

    echo $COPY_LIBRARIES_CMD
    if ! $COPY_LIBRARIES_CMD; then
        exit 3
    fi
}


function enable_secure_admin {
    print_header "Enable secure admin"

    echo $ENABLE_SECURE_ADMIN_CMD
    if ! $ENABLE_SECURE_ADMIN_CMD; then
        # Ошибка включения secure admin
        exit 5
    fi
}

# Данная функция принимает 2 аргумета:
# $1 - массив опций
# $2 - название конфигурации
function delete_jvm_opts {
    print_header "Delete jvm options in ${2}"
    declare -a OPTS=("${!1}")

    DELETE_JVM_OPT_CMD="${ASADMIN}/asadmin --user ${ADMIN_LOGIN}
               --passwordfile ${GF_PASSWD_FILE}
               --port ${ADMIN_PORT}
               delete-jvm-options --target"

    for i in "${OPTS[@]}"
    do
        echo "$DELETE_JVM_OPT_CMD ${2} ${i}"
        $DELETE_JVM_OPT_CMD ${2} ${i}
    done
}

# Данная функция принимает 2 аргумета:
# $1 - массив опций
# $2 - название конфигурации
function create_jvm_opts {
    print_header "Create jvm options in ${2}"
    declare -a OPTS=("${!1}")

    CREATE_JVM_OPT_CMD="${ASADMIN}/asadmin --user ${ADMIN_LOGIN}
               --passwordfile ${GF_PASSWD_FILE}
               --port ${ADMIN_PORT}
               create-jvm-options --target"
    for i in "${OPTS[@]}"
    do
        echo "$CREATE_JVM_OPT_CMD  ${2} ${i}"
        $CREATE_JVM_OPT_CMD ${2} ${i}
    done
}

# Создает пул подключений к базе данных, принимает 3 параметра:
# $1 - имя базы данных
# $2 - пул подключений
# $3 - JNDI
function create_db_pool {
    MYSQL_DB=$1
    MYSQL_DB_POOL=$2
    MYSQL_DB_JNDI=$3

    print_header "Create DB pool ${MYSQL_DB}"
    CREATE_CONNECTION_POOL_CMD="${ASADMIN}/asadmin --user ${ADMIN_LOGIN}
        --passwordfile ${GF_PASSWD_FILE}
        --port ${ADMIN_PORT}
        create-jdbc-connection-pool
        --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource
        --property user=${MYSQL_LOGIN}:password=${MYSQL_PASSWORD}:DatabaseName=${MYSQL_DB}:ServerName=${MYSQL_SERVER_NAME}:port=${MYSQL_PORT}:useUnicode=true:characterEncoding=UTF-8:characterSetResults=UTF-8:datasourceName=${MYSQL_DB}:zeroDateTimeBehavior=convertToNull
        --validateatmostonceperiod=60 --creationretryattempts=3 --creationretryinterval=10 --isconnectvalidatereq=true --validationmethod=auto-commit --ping=true
        ${MYSQL_DB_POOL}"

    CREATE_RESOURCE="${ASADMIN}/asadmin --user ${ADMIN_LOGIN}
        --passwordfile ${GF_PASSWD_FILE}
        --port ${ADMIN_PORT}
        create-jdbc-resource
        --connectionpoolid ${MYSQL_DB_POOL} ${MYSQL_DB_JNDI}"

    echo "$CREATE_CONNECTION_POOL_CMD"
    if ! $CREATE_CONNECTION_POOL_CMD; then
        exit 6
    fi

    echo "$CREATE_RESOURCE"
    if ! $CREATE_RESOURCE; then
        exit 7
    fi

}

function create_jms_connection_factory {
    print_header "Create JMS ConnectionFactory - ${CONNECTION_FACTORY}"

    CREATE_CONNECTION_FACTORY="${ASADMIN}/asadmin
        --user ${ADMIN_LOGIN}
        --passwordfile ${GF_PASSWD_FILE}
        --port ${ADMIN_PORT}
        create-jms-resource
        --restype javax.jms.ConnectionFactory
        ${CONNECTION_FACTORY}"

        echo "$CREATE_CONNECTION_FACTORY"
        if ! $CREATE_CONNECTION_FACTORY; then
            exit 8
        fi
}

function create_jms_laboratory_topic {
    print_header "Create JMS Topic - ${LABORATORY_TOPIC}"

    CREATE_JMS_TOPIC="${ASADMIN}/asadmin
        --user ${ADMIN_LOGIN}
        --passwordfile ${GF_PASSWD_FILE}
        --port ${ADMIN_PORT}
        create-jms-resource
        --restype javax.jms.Topic
        ${LABORATORY_TOPIC}"

        echo "$CREATE_JMS_TOPIC"
        if ! $CREATE_JMS_TOPIC; then
            exit 9
        fi
}

function start_stop_domain {
    print_header "Stop&Start domain ${DOMAIN}"
    STOP_CMD="${ASADMIN}/asadmin stop-domain --passwordfile ${GF_PASSWD_FILE} --domaindir ${DOMAIN_DIR} ${DOMAIN}"
    START_CMD="${ASADMIN}/asadmin start-domain --passwordfile ${GF_PASSWD_FILE} --domaindir ${DOMAIN_DIR} ${DOMAIN}"

    echo $STOP_DOMAIN_CMD
    $STOP_DOMAIN_CMD

    echo $START_DOMAIN_CMD
    $START_DOMAIN_CMD
    print_separator

}

# Удаление файла с паролями по завершению скрипта (в том числе при возникновении ошибки)
function cleanup {
    echo "rm -f ${GF_PASSWD_FILE}"
    rm -f $GF_PASSWD_FILE
}
trap cleanup EXIT

# Создание файла с паролями
echo "AS_ADMIN_MASTERPASSWORD="${MASTER_PASSWORD} > $GF_PASSWD_FILE
echo "AS_ADMIN_USERPASSWORD="${ADMIN_PASSWORD} >> $GF_PASSWD_FILE
echo "AS_ADMIN_ALIASPASSWORD="${ADMIN_PASSWORD} >> $GF_PASSWD_FILE
echo "AS_ADMIN_PASSWORD="${ADMIN_PASSWORD} >> $GF_PASSWD_FILE

# Запуск функций
list_domains
stop_domain
delete_domain
create_domain
copy_config_files
start_domain
enable_secure_admin
delete_jvm_opts DELETE_JVM_OPTIONS[@] "server-config"
delete_jvm_opts DELETE_JVM_OPTIONS[@] "default-config"
create_jvm_opts CREATE_JVM_OPTIONS[@] "server-config"
create_jvm_opts CREATE_JVM_OPTIONS[@] "default-config"
create_db_pool $MYSQL_DB_MIS $MYSQL_DB_MIS_POOL $MYSQL_DB_JNDI_MIS
create_db_pool $MYSQL_DB_TMIS_CORE $MYSQL_DB_TMIS_CORE_POOL $MYSQL_DB_JNDI_TMIS_CORE
create_jms_connection_factory
create_jms_laboratory_topic
start_stop_domain
