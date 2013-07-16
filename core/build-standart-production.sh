#!/bin/bash
#
# Скрипт для сборки проекта со стандартным профилем (/usr/local/glassfish3)
#

echo ----------------------------------------------
echo Build ear for STANDART CONFIG
echo ----------------------------------------------
mvn  -DskipTests=true -P production-standart clean install
