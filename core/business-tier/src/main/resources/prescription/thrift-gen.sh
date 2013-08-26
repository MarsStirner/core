#/bin/bash

# Сгенеренные классы попадают сразу в папку src
thrift -out ../../java/ -v -gen java prescr.thrift