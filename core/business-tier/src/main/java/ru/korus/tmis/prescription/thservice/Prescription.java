/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.korus.tmis.prescription.thservice;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Структура отдельного назначения. Включает в информацию о действии,
 * список компонентов, список интервалов времен назначений
 */
public class Prescription implements org.apache.thrift.TBase<Prescription, Prescription._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Prescription");

  private static final org.apache.thrift.protocol.TField ACT_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("actInfo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField DRUG_COMPONENTS_FIELD_DESC = new org.apache.thrift.protocol.TField("drugComponents", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField DRUG_INTERVALS_FIELD_DESC = new org.apache.thrift.protocol.TField("drugIntervals", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PrescriptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PrescriptionTupleSchemeFactory());
  }

  public ActionData actInfo; // optional
  public List<DrugComponent> drugComponents; // optional
  public List<DrugInterval> drugIntervals; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ACT_INFO((short)1, "actInfo"),
    DRUG_COMPONENTS((short)2, "drugComponents"),
    DRUG_INTERVALS((short)3, "drugIntervals");

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
        case 1: // ACT_INFO
          return ACT_INFO;
        case 2: // DRUG_COMPONENTS
          return DRUG_COMPONENTS;
        case 3: // DRUG_INTERVALS
          return DRUG_INTERVALS;
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
  private _Fields optionals[] = {_Fields.ACT_INFO,_Fields.DRUG_COMPONENTS,_Fields.DRUG_INTERVALS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ACT_INFO, new org.apache.thrift.meta_data.FieldMetaData("actInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ActionData.class)));
    tmpMap.put(_Fields.DRUG_COMPONENTS, new org.apache.thrift.meta_data.FieldMetaData("drugComponents", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, DrugComponent.class))));
    tmpMap.put(_Fields.DRUG_INTERVALS, new org.apache.thrift.meta_data.FieldMetaData("drugIntervals", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, DrugInterval.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Prescription.class, metaDataMap);
  }

  public Prescription() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Prescription(Prescription other) {
    if (other.isSetActInfo()) {
      this.actInfo = new ActionData(other.actInfo);
    }
    if (other.isSetDrugComponents()) {
      List<DrugComponent> __this__drugComponents = new ArrayList<DrugComponent>();
      for (DrugComponent other_element : other.drugComponents) {
        __this__drugComponents.add(new DrugComponent(other_element));
      }
      this.drugComponents = __this__drugComponents;
    }
    if (other.isSetDrugIntervals()) {
      List<DrugInterval> __this__drugIntervals = new ArrayList<DrugInterval>();
      for (DrugInterval other_element : other.drugIntervals) {
        __this__drugIntervals.add(new DrugInterval(other_element));
      }
      this.drugIntervals = __this__drugIntervals;
    }
  }

  public Prescription deepCopy() {
    return new Prescription(this);
  }

  @Override
  public void clear() {
    this.actInfo = null;
    this.drugComponents = null;
    this.drugIntervals = null;
  }

  public ActionData getActInfo() {
    return this.actInfo;
  }

  public Prescription setActInfo(ActionData actInfo) {
    this.actInfo = actInfo;
    return this;
  }

  public void unsetActInfo() {
    this.actInfo = null;
  }

  /** Returns true if field actInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetActInfo() {
    return this.actInfo != null;
  }

  public void setActInfoIsSet(boolean value) {
    if (!value) {
      this.actInfo = null;
    }
  }

  public int getDrugComponentsSize() {
    return (this.drugComponents == null) ? 0 : this.drugComponents.size();
  }

  public java.util.Iterator<DrugComponent> getDrugComponentsIterator() {
    return (this.drugComponents == null) ? null : this.drugComponents.iterator();
  }

  public void addToDrugComponents(DrugComponent elem) {
    if (this.drugComponents == null) {
      this.drugComponents = new ArrayList<DrugComponent>();
    }
    this.drugComponents.add(elem);
  }

  public List<DrugComponent> getDrugComponents() {
    return this.drugComponents;
  }

  public Prescription setDrugComponents(List<DrugComponent> drugComponents) {
    this.drugComponents = drugComponents;
    return this;
  }

  public void unsetDrugComponents() {
    this.drugComponents = null;
  }

  /** Returns true if field drugComponents is set (has been assigned a value) and false otherwise */
  public boolean isSetDrugComponents() {
    return this.drugComponents != null;
  }

  public void setDrugComponentsIsSet(boolean value) {
    if (!value) {
      this.drugComponents = null;
    }
  }

  public int getDrugIntervalsSize() {
    return (this.drugIntervals == null) ? 0 : this.drugIntervals.size();
  }

  public java.util.Iterator<DrugInterval> getDrugIntervalsIterator() {
    return (this.drugIntervals == null) ? null : this.drugIntervals.iterator();
  }

  public void addToDrugIntervals(DrugInterval elem) {
    if (this.drugIntervals == null) {
      this.drugIntervals = new ArrayList<DrugInterval>();
    }
    this.drugIntervals.add(elem);
  }

  public List<DrugInterval> getDrugIntervals() {
    return this.drugIntervals;
  }

  public Prescription setDrugIntervals(List<DrugInterval> drugIntervals) {
    this.drugIntervals = drugIntervals;
    return this;
  }

  public void unsetDrugIntervals() {
    this.drugIntervals = null;
  }

  /** Returns true if field drugIntervals is set (has been assigned a value) and false otherwise */
  public boolean isSetDrugIntervals() {
    return this.drugIntervals != null;
  }

  public void setDrugIntervalsIsSet(boolean value) {
    if (!value) {
      this.drugIntervals = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ACT_INFO:
      if (value == null) {
        unsetActInfo();
      } else {
        setActInfo((ActionData)value);
      }
      break;

    case DRUG_COMPONENTS:
      if (value == null) {
        unsetDrugComponents();
      } else {
        setDrugComponents((List<DrugComponent>)value);
      }
      break;

    case DRUG_INTERVALS:
      if (value == null) {
        unsetDrugIntervals();
      } else {
        setDrugIntervals((List<DrugInterval>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ACT_INFO:
      return getActInfo();

    case DRUG_COMPONENTS:
      return getDrugComponents();

    case DRUG_INTERVALS:
      return getDrugIntervals();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ACT_INFO:
      return isSetActInfo();
    case DRUG_COMPONENTS:
      return isSetDrugComponents();
    case DRUG_INTERVALS:
      return isSetDrugIntervals();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Prescription)
      return this.equals((Prescription)that);
    return false;
  }

  public boolean equals(Prescription that) {
    if (that == null)
      return false;

    boolean this_present_actInfo = true && this.isSetActInfo();
    boolean that_present_actInfo = true && that.isSetActInfo();
    if (this_present_actInfo || that_present_actInfo) {
      if (!(this_present_actInfo && that_present_actInfo))
        return false;
      if (!this.actInfo.equals(that.actInfo))
        return false;
    }

    boolean this_present_drugComponents = true && this.isSetDrugComponents();
    boolean that_present_drugComponents = true && that.isSetDrugComponents();
    if (this_present_drugComponents || that_present_drugComponents) {
      if (!(this_present_drugComponents && that_present_drugComponents))
        return false;
      if (!this.drugComponents.equals(that.drugComponents))
        return false;
    }

    boolean this_present_drugIntervals = true && this.isSetDrugIntervals();
    boolean that_present_drugIntervals = true && that.isSetDrugIntervals();
    if (this_present_drugIntervals || that_present_drugIntervals) {
      if (!(this_present_drugIntervals && that_present_drugIntervals))
        return false;
      if (!this.drugIntervals.equals(that.drugIntervals))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Prescription other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Prescription typedOther = (Prescription)other;

    lastComparison = Boolean.valueOf(isSetActInfo()).compareTo(typedOther.isSetActInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.actInfo, typedOther.actInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDrugComponents()).compareTo(typedOther.isSetDrugComponents());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDrugComponents()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.drugComponents, typedOther.drugComponents);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDrugIntervals()).compareTo(typedOther.isSetDrugIntervals());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDrugIntervals()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.drugIntervals, typedOther.drugIntervals);
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
    StringBuilder sb = new StringBuilder("Prescription(");
    boolean first = true;

    if (isSetActInfo()) {
      sb.append("actInfo:");
      if (this.actInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.actInfo);
      }
      first = false;
    }
    if (isSetDrugComponents()) {
      if (!first) sb.append(", ");
      sb.append("drugComponents:");
      if (this.drugComponents == null) {
        sb.append("null");
      } else {
        sb.append(this.drugComponents);
      }
      first = false;
    }
    if (isSetDrugIntervals()) {
      if (!first) sb.append(", ");
      sb.append("drugIntervals:");
      if (this.drugIntervals == null) {
        sb.append("null");
      } else {
        sb.append(this.drugIntervals);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (actInfo != null) {
      actInfo.validate();
    }
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

  private static class PrescriptionStandardSchemeFactory implements SchemeFactory {
    public PrescriptionStandardScheme getScheme() {
      return new PrescriptionStandardScheme();
    }
  }

  private static class PrescriptionStandardScheme extends StandardScheme<Prescription> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Prescription struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ACT_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.actInfo = new ActionData();
              struct.actInfo.read(iprot);
              struct.setActInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DRUG_COMPONENTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.drugComponents = new ArrayList<DrugComponent>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  DrugComponent _elem10; // required
                  _elem10 = new DrugComponent();
                  _elem10.read(iprot);
                  struct.drugComponents.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setDrugComponentsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DRUG_INTERVALS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list11 = iprot.readListBegin();
                struct.drugIntervals = new ArrayList<DrugInterval>(_list11.size);
                for (int _i12 = 0; _i12 < _list11.size; ++_i12)
                {
                  DrugInterval _elem13; // required
                  _elem13 = new DrugInterval();
                  _elem13.read(iprot);
                  struct.drugIntervals.add(_elem13);
                }
                iprot.readListEnd();
              }
              struct.setDrugIntervalsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Prescription struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.actInfo != null) {
        if (struct.isSetActInfo()) {
          oprot.writeFieldBegin(ACT_INFO_FIELD_DESC);
          struct.actInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.drugComponents != null) {
        if (struct.isSetDrugComponents()) {
          oprot.writeFieldBegin(DRUG_COMPONENTS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.drugComponents.size()));
            for (DrugComponent _iter14 : struct.drugComponents)
            {
              _iter14.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.drugIntervals != null) {
        if (struct.isSetDrugIntervals()) {
          oprot.writeFieldBegin(DRUG_INTERVALS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.drugIntervals.size()));
            for (DrugInterval _iter15 : struct.drugIntervals)
            {
              _iter15.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PrescriptionTupleSchemeFactory implements SchemeFactory {
    public PrescriptionTupleScheme getScheme() {
      return new PrescriptionTupleScheme();
    }
  }

  private static class PrescriptionTupleScheme extends TupleScheme<Prescription> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Prescription struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetActInfo()) {
        optionals.set(0);
      }
      if (struct.isSetDrugComponents()) {
        optionals.set(1);
      }
      if (struct.isSetDrugIntervals()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetActInfo()) {
        struct.actInfo.write(oprot);
      }
      if (struct.isSetDrugComponents()) {
        {
          oprot.writeI32(struct.drugComponents.size());
          for (DrugComponent _iter16 : struct.drugComponents)
          {
            _iter16.write(oprot);
          }
        }
      }
      if (struct.isSetDrugIntervals()) {
        {
          oprot.writeI32(struct.drugIntervals.size());
          for (DrugInterval _iter17 : struct.drugIntervals)
          {
            _iter17.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Prescription struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.actInfo = new ActionData();
        struct.actInfo.read(iprot);
        struct.setActInfoIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list18 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.drugComponents = new ArrayList<DrugComponent>(_list18.size);
          for (int _i19 = 0; _i19 < _list18.size; ++_i19)
          {
            DrugComponent _elem20; // required
            _elem20 = new DrugComponent();
            _elem20.read(iprot);
            struct.drugComponents.add(_elem20);
          }
        }
        struct.setDrugComponentsIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.drugIntervals = new ArrayList<DrugInterval>(_list21.size);
          for (int _i22 = 0; _i22 < _list21.size; ++_i22)
          {
            DrugInterval _elem23; // required
            _elem23 = new DrugInterval();
            _elem23.read(iprot);
            struct.drugIntervals.add(_elem23);
          }
        }
        struct.setDrugIntervalsIsSet(true);
      }
    }
  }

}
