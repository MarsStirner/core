/**
 * IAcrossIntf_FNKCserviceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ru.korus.tmis.laboratory.across.ws;

public class IAcrossIntf_FNKCserviceLocator extends org.apache.axis.client.Service implements IAcrossIntf_FNKCservice {

    public IAcrossIntf_FNKCserviceLocator() {
    }


    public IAcrossIntf_FNKCserviceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IAcrossIntf_FNKCserviceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IAcrossIntf_FNKCPort
    private String IAcrossIntf_FNKCPort_address = "http://node1-lis.fccho-moscow.ru/ws_acl/Acrosswebserv.exe/soap/IAcrossIntf_FNKC";

    public String getIAcrossIntf_FNKCPortAddress() {
        return IAcrossIntf_FNKCPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String IAcrossIntf_FNKCPortWSDDServiceName = "IAcrossIntf_FNKCPort";

    public String getIAcrossIntf_FNKCPortWSDDServiceName() {
        return IAcrossIntf_FNKCPortWSDDServiceName;
    }

    public void setIAcrossIntf_FNKCPortWSDDServiceName(String name) {
        IAcrossIntf_FNKCPortWSDDServiceName = name;
    }

    public IAcrossIntf_FNKC getIAcrossIntf_FNKCPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IAcrossIntf_FNKCPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIAcrossIntf_FNKCPort(endpoint);
    }

    public IAcrossIntf_FNKC getIAcrossIntf_FNKCPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            IAcrossIntf_FNKCbindingStub _stub = new IAcrossIntf_FNKCbindingStub(portAddress, this);
            _stub.setPortName(getIAcrossIntf_FNKCPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIAcrossIntf_FNKCPortEndpointAddress(String address) {
        IAcrossIntf_FNKCPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IAcrossIntf_FNKC.class.isAssignableFrom(serviceEndpointInterface)) {
                IAcrossIntf_FNKCbindingStub _stub = new IAcrossIntf_FNKCbindingStub(new java.net.URL(IAcrossIntf_FNKCPort_address), this);
                _stub.setPortName(getIAcrossIntf_FNKCPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("IAcrossIntf_FNKCPort".equals(inputPortName)) {
            return getIAcrossIntf_FNKCPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.korusconsulting.ru", "IAcrossIntf_FNKCservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.korusconsulting.ru", "IAcrossIntf_FNKCPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("IAcrossIntf_FNKCPort".equals(portName)) {
            setIAcrossIntf_FNKCPortEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
