#!/bin/bash
#
# Данный скрипт содержит функции, используемые в скриптах создания домена, развертывания и удаления приложения
#


HOST=${glassfish.host}
ASADMIN=${glassfish.bin}
DOMAIN=${glassfish.domain}
DOMAIN_DIR=${glassfish.domain.dir}
ADMIN_LOGIN=${glassfish.admin.login}
ADMIN_PASSWORD=${glassfish.admin.password}
PORT_INSTANCE=${glassfish.port.instance}
ADMIN_PORT=${glassfish.port.admin}
MASTER_PASSWORD=${glassfish.master.password}
APP_NAME=${glassfish.application.name}

MYSQL_LOGIN=${mysql.login}
MYSQL_PASSWORD=${mysql.password}
MYSQL_SERVER_NAME=${mysql.db.host}
MYSQL_PORT=${mysql.db.port}

MYSQL_DB_MIS=${mysql.db.mis}
MYSQL_DB_MIS_POOL=${mysql.db.mis.pool}
MYSQL_DB_JNDI_MIS=${mysql.db.jndi.mis}

MYSQL_DB_RLS=${mysql.db.rls}
MYSQL_DB_RLS_POOL=${mysql.db.rls.pool}
MYSQL_DB_JNDI_RLS=${mysql.db.jndi.rls}

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
