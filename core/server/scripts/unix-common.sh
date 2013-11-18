#!/usr/bin/env bash
#
# Данный скрипт содержит функции, используемые в скриптах создания домена, развертывания и удаления приложения
#

# Запуск данного скрипта требует bash и если он вызывается чем-то другим, то пытаемся запустить его с помощью bash
if [ -z "$BASH_VERSION" ]
then
    exec bash "$0" "$@"
fi

# Адрес хоста, например, localhost
HOST=${glassfish.host}
# Путь к директории bin Glassfish
ASADMIN=${glassfish.bin}
# Имя домена
DOMAIN=${glassfish.domain}
# Путь к директории, содержащей домены Glassfish
DOMAIN_DIR=${glassfish.domain.dir}
# Логин доступа к Glassfish
ADMIN_LOGIN=${glassfish.admin.login}
# Пароль доступа к Glassfish
ADMIN_PASSWORD=${glassfish.admin.password}
# Порт на котором будут доступны приложения
PORT_INSTANCE=${glassfish.port.instance}
# Порт подключения к GlassFish Server Administration Console
ADMIN_PORT=${glassfish.port.admin}
# Мастер-пароль
MASTER_PASSWORD=${glassfish.master.password}
# Имя приложения при деплое
APP_NAME=${glassfish.application.name}

# Параметры подключения к БД
MYSQL_LOGIN=${mysql.login}
MYSQL_PASSWORD=${mysql.password}
MYSQL_SERVER_NAME=${mysql.db.host}
MYSQL_PORT=${mysql.db.port}

MYSQL_DB_MIS=${mysql.db.mis}
MYSQL_DB_MIS_POOL=${mysql.db.mis.pool}
MYSQL_DB_JNDI_MIS=${mysql.db.jndi.mis}

MYSQL_DB_TMIS_CORE=${mysql.db.tmis_core}
MYSQL_DB_TMIS_CORE_POOL=${mysql.db.tmis_core.pool}
MYSQL_DB_JNDI_TMIS_CORE=${mysql.db.jndi.tmis_core}


LIST_DOMAINS_CMD="${ASADMIN}/asadmin list-domains"

STOP_DOMAIN_CMD="${ASADMIN}/asadmin stop-domain ${DOMAIN}"

COPY_CONFIG_CMD="cp ${DIR}/logback.xml ${DOMAIN_DIR}/${DOMAIN}/config"

START_DOMAIN_CMD="${ASADMIN}/asadmin start-domain
        --passwordfile $GF_PASSWD_FILE \
        --domaindir ${DOMAIN_DIR} \
         ${DOMAIN}"

function list_domains {
    print_header "List domains"

    echo $LIST_DOMAINS_CMD
    if ! $LIST_DOMAINS_CMD; then
        # Если получение списка доменов завершается ошибкой, то продолжать смысла нет, так как это говорит о серьезной
        # проблеме, например неправильно заданной директории GlassFish
        exit 1
    fi
}

function stop_domain {
    print_header "Stop domain ${DOMAIN}"

    echo $STOP_DOMAIN_CMD
    if ! $STOP_DOMAIN_CMD; then
        # Остановка домена возвращает код ошибки в случае если домен не существует, поэтому ошибка на этом шаге не
        # должна приводить к остановке работы скрипта, т.к. домен просто может отсутствовать
        echo $?
    fi
}

function start_domain {
    print_header "Start domain ${DOMAIN}"

    echo $START_DOMAIN_CMD
    if ! $START_DOMAIN_CMD; then
        # Ошибка запуска домена критична
        exit 4
    fi
}

function print_separator {
    columns=$(tput cols)
    for ((x = 0; x < columns; x++)); do
        printf %s -
    done
}

function print_header {
    print_separator
    echo "$(tput setaf 2)$1$(tput sgr0)"
    echo ""
}
