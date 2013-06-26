#!/bin/bash
java --version
wsimport -d ../../java ./wsdl/PIXManager.wsdl -p ru.korus.tmis.pix -keep -extension -b ./wsdl/option.jxb 