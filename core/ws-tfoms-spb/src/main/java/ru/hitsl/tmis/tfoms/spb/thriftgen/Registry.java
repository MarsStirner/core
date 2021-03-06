/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.hitsl.tmis.tfoms.spb.thriftgen;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-7-30")
public class Registry implements org.apache.thrift.TBase<Registry, Registry._Fields>, java.io.Serializable, Cloneable, Comparable<Registry> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Registry");

  private static final org.apache.thrift.protocol.TField SERVICE_REGISTRY_FIELD_DESC = new org.apache.thrift.protocol.TField("serviceRegistry", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField PATIENT_REGISTRY_FIELD_DESC = new org.apache.thrift.protocol.TField("patientRegistry", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RegistryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RegistryTupleSchemeFactory());
  }

  public List<ServiceEntry> serviceRegistry; // required
  public List<PatientEntry> patientRegistry; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SERVICE_REGISTRY((short)1, "serviceRegistry"),
    PATIENT_REGISTRY((short)2, "patientRegistry");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SERVICE_REGISTRY
          return SERVICE_REGISTRY;
        case 2: // PATIENT_REGISTRY
          return PATIENT_REGISTRY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SERVICE_REGISTRY, new org.apache.thrift.meta_data.FieldMetaData("serviceRegistry", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ServiceEntry.class))));
    tmpMap.put(_Fields.PATIENT_REGISTRY, new org.apache.thrift.meta_data.FieldMetaData("patientRegistry", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PatientEntry.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Registry.class, metaDataMap);
  }

  public Registry() {
  }

  public Registry(
    List<ServiceEntry> serviceRegistry,
    List<PatientEntry> patientRegistry)
  {
    this();
    this.serviceRegistry = serviceRegistry;
    this.patientRegistry = patientRegistry;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Registry(Registry other) {
    if (other.isSetServiceRegistry()) {
      List<ServiceEntry> __this__serviceRegistry = new ArrayList<ServiceEntry>(other.serviceRegistry.size());
      for (ServiceEntry other_element : other.serviceRegistry) {
        __this__serviceRegistry.add(new ServiceEntry(other_element));
      }
      this.serviceRegistry = __this__serviceRegistry;
    }
    if (other.isSetPatientRegistry()) {
      List<PatientEntry> __this__patientRegistry = new ArrayList<PatientEntry>(other.patientRegistry.size());
      for (PatientEntry other_element : other.patientRegistry) {
        __this__patientRegistry.add(new PatientEntry(other_element));
      }
      this.patientRegistry = __this__patientRegistry;
    }
  }

  public Registry deepCopy() {
    return new Registry(this);
  }

  @Override
  public void clear() {
    this.serviceRegistry = null;
    this.patientRegistry = null;
  }

  public int getServiceRegistrySize() {
    return (this.serviceRegistry == null) ? 0 : this.serviceRegistry.size();
  }

  public java.util.Iterator<ServiceEntry> getServiceRegistryIterator() {
    return (this.serviceRegistry == null) ? null : this.serviceRegistry.iterator();
  }

  public void addToServiceRegistry(ServiceEntry elem) {
    if (this.serviceRegistry == null) {
      this.serviceRegistry = new ArrayList<ServiceEntry>();
    }
    this.serviceRegistry.add(elem);
  }

  public List<ServiceEntry> getServiceRegistry() {
    return this.serviceRegistry;
  }

  public Registry setServiceRegistry(List<ServiceEntry> serviceRegistry) {
    this.serviceRegistry = serviceRegistry;
    return this;
  }

  public void unsetServiceRegistry() {
    this.serviceRegistry = null;
  }

  /** Returns true if field serviceRegistry is set (has been assigned a value) and false otherwise */
  public boolean isSetServiceRegistry() {
    return this.serviceRegistry != null;
  }

  public void setServiceRegistryIsSet(boolean value) {
    if (!value) {
      this.serviceRegistry = null;
    }
  }

  public int getPatientRegistrySize() {
    return (this.patientRegistry == null) ? 0 : this.patientRegistry.size();
  }

  public java.util.Iterator<PatientEntry> getPatientRegistryIterator() {
    return (this.patientRegistry == null) ? null : this.patientRegistry.iterator();
  }

  public void addToPatientRegistry(PatientEntry elem) {
    if (this.patientRegistry == null) {
      this.patientRegistry = new ArrayList<PatientEntry>();
    }
    this.patientRegistry.add(elem);
  }

  public List<PatientEntry> getPatientRegistry() {
    return this.patientRegistry;
  }

  public Registry setPatientRegistry(List<PatientEntry> patientRegistry) {
    this.patientRegistry = patientRegistry;
    return this;
  }

  public void unsetPatientRegistry() {
    this.patientRegistry = null;
  }

  /** Returns true if field patientRegistry is set (has been assigned a value) and false otherwise */
  public boolean isSetPatientRegistry() {
    return this.patientRegistry != null;
  }

  public void setPatientRegistryIsSet(boolean value) {
    if (!value) {
      this.patientRegistry = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SERVICE_REGISTRY:
      if (value == null) {
        unsetServiceRegistry();
      } else {
        setServiceRegistry((List<ServiceEntry>)value);
      }
      break;

    case PATIENT_REGISTRY:
      if (value == null) {
        unsetPatientRegistry();
      } else {
        setPatientRegistry((List<PatientEntry>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SERVICE_REGISTRY:
      return getServiceRegistry();

    case PATIENT_REGISTRY:
      return getPatientRegistry();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SERVICE_REGISTRY:
      return isSetServiceRegistry();
    case PATIENT_REGISTRY:
      return isSetPatientRegistry();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Registry)
      return this.equals((Registry)that);
    return false;
  }

  public boolean equals(Registry that) {
    if (that == null)
      return false;

    boolean this_present_serviceRegistry = true && this.isSetServiceRegistry();
    boolean that_present_serviceRegistry = true && that.isSetServiceRegistry();
    if (this_present_serviceRegistry || that_present_serviceRegistry) {
      if (!(this_present_serviceRegistry && that_present_serviceRegistry))
        return false;
      if (!this.serviceRegistry.equals(that.serviceRegistry))
        return false;
    }

    boolean this_present_patientRegistry = true && this.isSetPatientRegistry();
    boolean that_present_patientRegistry = true && that.isSetPatientRegistry();
    if (this_present_patientRegistry || that_present_patientRegistry) {
      if (!(this_present_patientRegistry && that_present_patientRegistry))
        return false;
      if (!this.patientRegistry.equals(that.patientRegistry))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_serviceRegistry = true && (isSetServiceRegistry());
    list.add(present_serviceRegistry);
    if (present_serviceRegistry)
      list.add(serviceRegistry);

    boolean present_patientRegistry = true && (isSetPatientRegistry());
    list.add(present_patientRegistry);
    if (present_patientRegistry)
      list.add(patientRegistry);

    return list.hashCode();
  }

  @Override
  public int compareTo(Registry other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetServiceRegistry()).compareTo(other.isSetServiceRegistry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServiceRegistry()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serviceRegistry, other.serviceRegistry);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPatientRegistry()).compareTo(other.isSetPatientRegistry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPatientRegistry()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.patientRegistry, other.patientRegistry);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Registry(");
    boolean first = true;

    sb.append("serviceRegistry:");
    if (this.serviceRegistry == null) {
      sb.append("null");
    } else {
      sb.append(this.serviceRegistry);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("patientRegistry:");
    if (this.patientRegistry == null) {
      sb.append("null");
    } else {
      sb.append(this.patientRegistry);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RegistryStandardSchemeFactory implements SchemeFactory {
    public RegistryStandardScheme getScheme() {
      return new RegistryStandardScheme();
    }
  }

  private static class RegistryStandardScheme extends StandardScheme<Registry> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Registry struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SERVICE_REGISTRY
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.serviceRegistry = new ArrayList<ServiceEntry>(_list0.size);
                ServiceEntry _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ServiceEntry();
                  _elem1.read(iprot);
                  struct.serviceRegistry.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setServiceRegistryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PATIENT_REGISTRY
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list3 = iprot.readListBegin();
                struct.patientRegistry = new ArrayList<PatientEntry>(_list3.size);
                PatientEntry _elem4;
                for (int _i5 = 0; _i5 < _list3.size; ++_i5)
                {
                  _elem4 = new PatientEntry();
                  _elem4.read(iprot);
                  struct.patientRegistry.add(_elem4);
                }
                iprot.readListEnd();
              }
              struct.setPatientRegistryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Registry struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.serviceRegistry != null) {
        oprot.writeFieldBegin(SERVICE_REGISTRY_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.serviceRegistry.size()));
          for (ServiceEntry _iter6 : struct.serviceRegistry)
          {
            _iter6.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.patientRegistry != null) {
        oprot.writeFieldBegin(PATIENT_REGISTRY_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.patientRegistry.size()));
          for (PatientEntry _iter7 : struct.patientRegistry)
          {
            _iter7.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RegistryTupleSchemeFactory implements SchemeFactory {
    public RegistryTupleScheme getScheme() {
      return new RegistryTupleScheme();
    }
  }

  private static class RegistryTupleScheme extends TupleScheme<Registry> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Registry struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetServiceRegistry()) {
        optionals.set(0);
      }
      if (struct.isSetPatientRegistry()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetServiceRegistry()) {
        {
          oprot.writeI32(struct.serviceRegistry.size());
          for (ServiceEntry _iter8 : struct.serviceRegistry)
          {
            _iter8.write(oprot);
          }
        }
      }
      if (struct.isSetPatientRegistry()) {
        {
          oprot.writeI32(struct.patientRegistry.size());
          for (PatientEntry _iter9 : struct.patientRegistry)
          {
            _iter9.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Registry struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list10 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.serviceRegistry = new ArrayList<ServiceEntry>(_list10.size);
          ServiceEntry _elem11;
          for (int _i12 = 0; _i12 < _list10.size; ++_i12)
          {
            _elem11 = new ServiceEntry();
            _elem11.read(iprot);
            struct.serviceRegistry.add(_elem11);
          }
        }
        struct.setServiceRegistryIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.patientRegistry = new ArrayList<PatientEntry>(_list13.size);
          PatientEntry _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new PatientEntry();
            _elem14.read(iprot);
            struct.patientRegistry.add(_elem14);
          }
        }
        struct.setPatientRegistryIsSet(true);
      }
    }
  }

}

