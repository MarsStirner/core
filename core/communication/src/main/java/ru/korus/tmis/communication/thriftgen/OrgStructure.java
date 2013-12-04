/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.korus.tmis.communication.thriftgen;

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
 * OrgStructure
 * Структура с данными о подразделении
 * @param id					1) Внутренний идентификатор подразделения
 * @param parent_id				2) Внутренний идентификатор вышестоящего подразделения, или 0 если вышестоящего отделеия не существует
 * @param code					3) Код подразделения
 * @param name					4) Название подразделения
 * @param address				5) Адрес подразделения
 * @param sexFilter				6) Половой фильтр
 * @param ageFilter				7) Возрастной фильтр
 */
public class OrgStructure implements org.apache.thrift.TBase<OrgStructure, OrgStructure._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OrgStructure");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PARENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("parent_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ADDRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("address", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField SEX_FILTER_FIELD_DESC = new org.apache.thrift.protocol.TField("sexFilter", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField AGE_FILTER_FIELD_DESC = new org.apache.thrift.protocol.TField("ageFilter", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OrgStructureStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OrgStructureTupleSchemeFactory());
  }

  public int id; // required
  public int parent_id; // optional
  public String code; // required
  public String name; // required
  public String address; // optional
  public String sexFilter; // optional
  public String ageFilter; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PARENT_ID((short)2, "parent_id"),
    CODE((short)3, "code"),
    NAME((short)4, "name"),
    ADDRESS((short)5, "address"),
    SEX_FILTER((short)6, "sexFilter"),
    AGE_FILTER((short)7, "ageFilter");

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
        case 1: // ID
          return ID;
        case 2: // PARENT_ID
          return PARENT_ID;
        case 3: // CODE
          return CODE;
        case 4: // NAME
          return NAME;
        case 5: // ADDRESS
          return ADDRESS;
        case 6: // SEX_FILTER
          return SEX_FILTER;
        case 7: // AGE_FILTER
          return AGE_FILTER;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __PARENT_ID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.PARENT_ID,_Fields.ADDRESS,_Fields.SEX_FILTER,_Fields.AGE_FILTER};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PARENT_ID, new org.apache.thrift.meta_data.FieldMetaData("parent_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ADDRESS, new org.apache.thrift.meta_data.FieldMetaData("address", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SEX_FILTER, new org.apache.thrift.meta_data.FieldMetaData("sexFilter", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AGE_FILTER, new org.apache.thrift.meta_data.FieldMetaData("ageFilter", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OrgStructure.class, metaDataMap);
  }

  public OrgStructure() {
    this.parent_id = 0;

    this.name = "";

    this.address = "";

    this.sexFilter = "";

    this.ageFilter = "";

  }

  public OrgStructure(
    int id,
    String code,
    String name)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.code = code;
    this.name = name;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OrgStructure(OrgStructure other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.parent_id = other.parent_id;
    if (other.isSetCode()) {
      this.code = other.code;
    }
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetAddress()) {
      this.address = other.address;
    }
    if (other.isSetSexFilter()) {
      this.sexFilter = other.sexFilter;
    }
    if (other.isSetAgeFilter()) {
      this.ageFilter = other.ageFilter;
    }
  }

  public OrgStructure deepCopy() {
    return new OrgStructure(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.parent_id = 0;

    this.code = null;
    this.name = "";

    this.address = "";

    this.sexFilter = "";

    this.ageFilter = "";

  }

  public int getId() {
    return this.id;
  }

  public OrgStructure setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public int getParent_id() {
    return this.parent_id;
  }

  public OrgStructure setParent_id(int parent_id) {
    this.parent_id = parent_id;
    setParent_idIsSet(true);
    return this;
  }

  public void unsetParent_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PARENT_ID_ISSET_ID);
  }

  /** Returns true if field parent_id is set (has been assigned a value) and false otherwise */
  public boolean isSetParent_id() {
    return EncodingUtils.testBit(__isset_bitfield, __PARENT_ID_ISSET_ID);
  }

  public void setParent_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PARENT_ID_ISSET_ID, value);
  }

  public String getCode() {
    return this.code;
  }

  public OrgStructure setCode(String code) {
    this.code = code;
    return this;
  }

  public void unsetCode() {
    this.code = null;
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return this.code != null;
  }

  public void setCodeIsSet(boolean value) {
    if (!value) {
      this.code = null;
    }
  }

  public String getName() {
    return this.name;
  }

  public OrgStructure setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getAddress() {
    return this.address;
  }

  public OrgStructure setAddress(String address) {
    this.address = address;
    return this;
  }

  public void unsetAddress() {
    this.address = null;
  }

  /** Returns true if field address is set (has been assigned a value) and false otherwise */
  public boolean isSetAddress() {
    return this.address != null;
  }

  public void setAddressIsSet(boolean value) {
    if (!value) {
      this.address = null;
    }
  }

  public String getSexFilter() {
    return this.sexFilter;
  }

  public OrgStructure setSexFilter(String sexFilter) {
    this.sexFilter = sexFilter;
    return this;
  }

  public void unsetSexFilter() {
    this.sexFilter = null;
  }

  /** Returns true if field sexFilter is set (has been assigned a value) and false otherwise */
  public boolean isSetSexFilter() {
    return this.sexFilter != null;
  }

  public void setSexFilterIsSet(boolean value) {
    if (!value) {
      this.sexFilter = null;
    }
  }

  public String getAgeFilter() {
    return this.ageFilter;
  }

  public OrgStructure setAgeFilter(String ageFilter) {
    this.ageFilter = ageFilter;
    return this;
  }

  public void unsetAgeFilter() {
    this.ageFilter = null;
  }

  /** Returns true if field ageFilter is set (has been assigned a value) and false otherwise */
  public boolean isSetAgeFilter() {
    return this.ageFilter != null;
  }

  public void setAgeFilterIsSet(boolean value) {
    if (!value) {
      this.ageFilter = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case PARENT_ID:
      if (value == null) {
        unsetParent_id();
      } else {
        setParent_id((Integer)value);
      }
      break;

    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((String)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case ADDRESS:
      if (value == null) {
        unsetAddress();
      } else {
        setAddress((String)value);
      }
      break;

    case SEX_FILTER:
      if (value == null) {
        unsetSexFilter();
      } else {
        setSexFilter((String)value);
      }
      break;

    case AGE_FILTER:
      if (value == null) {
        unsetAgeFilter();
      } else {
        setAgeFilter((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case PARENT_ID:
      return Integer.valueOf(getParent_id());

    case CODE:
      return getCode();

    case NAME:
      return getName();

    case ADDRESS:
      return getAddress();

    case SEX_FILTER:
      return getSexFilter();

    case AGE_FILTER:
      return getAgeFilter();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case PARENT_ID:
      return isSetParent_id();
    case CODE:
      return isSetCode();
    case NAME:
      return isSetName();
    case ADDRESS:
      return isSetAddress();
    case SEX_FILTER:
      return isSetSexFilter();
    case AGE_FILTER:
      return isSetAgeFilter();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OrgStructure)
      return this.equals((OrgStructure)that);
    return false;
  }

  public boolean equals(OrgStructure that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_parent_id = true && this.isSetParent_id();
    boolean that_present_parent_id = true && that.isSetParent_id();
    if (this_present_parent_id || that_present_parent_id) {
      if (!(this_present_parent_id && that_present_parent_id))
        return false;
      if (this.parent_id != that.parent_id)
        return false;
    }

    boolean this_present_code = true && this.isSetCode();
    boolean that_present_code = true && that.isSetCode();
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (!this.code.equals(that.code))
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_address = true && this.isSetAddress();
    boolean that_present_address = true && that.isSetAddress();
    if (this_present_address || that_present_address) {
      if (!(this_present_address && that_present_address))
        return false;
      if (!this.address.equals(that.address))
        return false;
    }

    boolean this_present_sexFilter = true && this.isSetSexFilter();
    boolean that_present_sexFilter = true && that.isSetSexFilter();
    if (this_present_sexFilter || that_present_sexFilter) {
      if (!(this_present_sexFilter && that_present_sexFilter))
        return false;
      if (!this.sexFilter.equals(that.sexFilter))
        return false;
    }

    boolean this_present_ageFilter = true && this.isSetAgeFilter();
    boolean that_present_ageFilter = true && that.isSetAgeFilter();
    if (this_present_ageFilter || that_present_ageFilter) {
      if (!(this_present_ageFilter && that_present_ageFilter))
        return false;
      if (!this.ageFilter.equals(that.ageFilter))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(OrgStructure other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    OrgStructure typedOther = (OrgStructure)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParent_id()).compareTo(typedOther.isSetParent_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParent_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parent_id, typedOther.parent_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCode()).compareTo(typedOther.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, typedOther.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(typedOther.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAddress()).compareTo(typedOther.isSetAddress());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAddress()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.address, typedOther.address);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSexFilter()).compareTo(typedOther.isSetSexFilter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSexFilter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sexFilter, typedOther.sexFilter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAgeFilter()).compareTo(typedOther.isSetAgeFilter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAgeFilter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ageFilter, typedOther.ageFilter);
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
    StringBuilder sb = new StringBuilder("OrgStructure(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (isSetParent_id()) {
      if (!first) sb.append(", ");
      sb.append("parent_id:");
      sb.append(this.parent_id);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("code:");
    if (this.code == null) {
      sb.append("null");
    } else {
      sb.append(this.code);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (isSetAddress()) {
      if (!first) sb.append(", ");
      sb.append("address:");
      if (this.address == null) {
        sb.append("null");
      } else {
        sb.append(this.address);
      }
      first = false;
    }
    if (isSetSexFilter()) {
      if (!first) sb.append(", ");
      sb.append("sexFilter:");
      if (this.sexFilter == null) {
        sb.append("null");
      } else {
        sb.append(this.sexFilter);
      }
      first = false;
    }
    if (isSetAgeFilter()) {
      if (!first) sb.append(", ");
      sb.append("ageFilter:");
      if (this.ageFilter == null) {
        sb.append("null");
      } else {
        sb.append(this.ageFilter);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
    if (code == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'code' was not present! Struct: " + toString());
    }
    if (name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'name' was not present! Struct: " + toString());
    }
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OrgStructureStandardSchemeFactory implements SchemeFactory {
    public OrgStructureStandardScheme getScheme() {
      return new OrgStructureStandardScheme();
    }
  }

  private static class OrgStructureStandardScheme extends StandardScheme<OrgStructure> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OrgStructure struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.parent_id = iprot.readI32();
              struct.setParent_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.code = iprot.readString();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ADDRESS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.address = iprot.readString();
              struct.setAddressIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SEX_FILTER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sexFilter = iprot.readString();
              struct.setSexFilterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // AGE_FILTER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ageFilter = iprot.readString();
              struct.setAgeFilterIsSet(true);
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
      if (!struct.isSetId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, OrgStructure struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      if (struct.isSetParent_id()) {
        oprot.writeFieldBegin(PARENT_ID_FIELD_DESC);
        oprot.writeI32(struct.parent_id);
        oprot.writeFieldEnd();
      }
      if (struct.code != null) {
        oprot.writeFieldBegin(CODE_FIELD_DESC);
        oprot.writeString(struct.code);
        oprot.writeFieldEnd();
      }
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.address != null) {
        if (struct.isSetAddress()) {
          oprot.writeFieldBegin(ADDRESS_FIELD_DESC);
          oprot.writeString(struct.address);
          oprot.writeFieldEnd();
        }
      }
      if (struct.sexFilter != null) {
        if (struct.isSetSexFilter()) {
          oprot.writeFieldBegin(SEX_FILTER_FIELD_DESC);
          oprot.writeString(struct.sexFilter);
          oprot.writeFieldEnd();
        }
      }
      if (struct.ageFilter != null) {
        if (struct.isSetAgeFilter()) {
          oprot.writeFieldBegin(AGE_FILTER_FIELD_DESC);
          oprot.writeString(struct.ageFilter);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OrgStructureTupleSchemeFactory implements SchemeFactory {
    public OrgStructureTupleScheme getScheme() {
      return new OrgStructureTupleScheme();
    }
  }

  private static class OrgStructureTupleScheme extends TupleScheme<OrgStructure> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OrgStructure struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.id);
      oprot.writeString(struct.code);
      oprot.writeString(struct.name);
      BitSet optionals = new BitSet();
      if (struct.isSetParent_id()) {
        optionals.set(0);
      }
      if (struct.isSetAddress()) {
        optionals.set(1);
      }
      if (struct.isSetSexFilter()) {
        optionals.set(2);
      }
      if (struct.isSetAgeFilter()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetParent_id()) {
        oprot.writeI32(struct.parent_id);
      }
      if (struct.isSetAddress()) {
        oprot.writeString(struct.address);
      }
      if (struct.isSetSexFilter()) {
        oprot.writeString(struct.sexFilter);
      }
      if (struct.isSetAgeFilter()) {
        oprot.writeString(struct.ageFilter);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OrgStructure struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readI32();
      struct.setIdIsSet(true);
      struct.code = iprot.readString();
      struct.setCodeIsSet(true);
      struct.name = iprot.readString();
      struct.setNameIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.parent_id = iprot.readI32();
        struct.setParent_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.address = iprot.readString();
        struct.setAddressIsSet(true);
      }
      if (incoming.get(2)) {
        struct.sexFilter = iprot.readString();
        struct.setSexFilterIsSet(true);
      }
      if (incoming.get(3)) {
        struct.ageFilter = iprot.readString();
        struct.setAgeFilterIsSet(true);
      }
    }
  }

}

