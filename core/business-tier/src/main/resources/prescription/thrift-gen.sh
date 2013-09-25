#/bin/bash

# Сгенеренные классы попадают сразу в папку src
./thrift.exe -out ../../java/ -v -gen java prescr.thrift