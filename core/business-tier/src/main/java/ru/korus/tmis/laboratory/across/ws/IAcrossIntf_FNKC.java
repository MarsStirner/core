/**
 * IAcrossIntf_FNKC.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ru.korus.tmis.laboratory.across.ws;

public interface IAcrossIntf_FNKC extends java.rmi.Remote {
    public int queryAnalysis(
            PatientInfo patientInfo,
            DiagnosticRequestInfo diagnosticRequestInfo,
            BiomaterialInfo biomaterialInfo,
            OrderInfo orderInfo) throws java.rmi.RemoteException;
}
