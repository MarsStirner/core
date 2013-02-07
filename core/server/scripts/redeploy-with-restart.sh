#!/bin/bash
#
# Скрипт редеплоя приложения c рестартом
#

bash ./undeploy-ear.sh \
&&  asadmin stop-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain} \
&&  asadmin start-domain --domaindir ${glassfish.domain.dir} ${glassfish.domain} \\
&& bash ./deploy-ear.sh

# Показать последние строки лога
tail -n 5000 ${glassfish.domain.dir}/${glassfish.domain}/logs/server.log