#!/bin/bash
java --version
wsimport -d ../java ../webapp/WEB-INF/wsdl/wsPoliclinic.wsdl -p ru.korus.tmis.ws.finance.odvd -keep -extension 