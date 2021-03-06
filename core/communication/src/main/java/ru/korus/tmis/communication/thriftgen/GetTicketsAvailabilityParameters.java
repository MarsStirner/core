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

public class GetTicketsAvailabilityParameters implements org.apache.thrift.TBase<GetTicketsAvailabilityParameters, GetTicketsAvailabilityParameters._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetTicketsAvailabilityParameters");

  private static final org.apache.thrift.protocol.TField ORG_STRUCTURE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orgStructureId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField RECURSIVE_FIELD_DESC = new org.apache.thrift.protocol.TField("recursive", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField SPECIALITY_NOTATION_FIELD_DESC = new org.apache.thrift.protocol.TField("specialityNotation", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SPECIALITY_FIELD_DESC = new org.apache.thrift.protocol.TField("speciality", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PERSON_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("personId", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField BEG_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("begDate", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField END_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("endDate", org.apache.thrift.protocol.TType.I64, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GetTicketsAvailabilityParametersStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GetTicketsAvailabilityParametersTupleSchemeFactory());
  }

  public int orgStructureId; // required
  public boolean recursive; // optional
  public String specialityNotation; // optional
  public String speciality; // optional
  public int personId; // required
  public long begDate; // optional
  public long endDate; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ORG_STRUCTURE_ID((short)1, "orgStructureId"),
    RECURSIVE((short)2, "recursive"),
    SPECIALITY_NOTATION((short)3, "specialityNotation"),
    SPECIALITY((short)4, "speciality"),
    PERSON_ID((short)5, "personId"),
    BEG_DATE((short)6, "begDate"),
    END_DATE((short)7, "endDate");

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
        case 1: // ORG_STRUCTURE_ID
          return ORG_STRUCTURE_ID;
        case 2: // RECURSIVE
          return RECURSIVE;
        case 3: // SPECIALITY_NOTATION
          return SPECIALITY_NOTATION;
        case 4: // SPECIALITY
          return SPECIALITY;
        case 5: // PERSON_ID
          return PERSON_ID;
        case 6: // BEG_DATE
          return BEG_DATE;
        case 7: // END_DATE
          return END_DATE;
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
  private static final int __ORGSTRUCTUREID_ISSET_ID = 0;
  private static final int __RECURSIVE_ISSET_ID = 1;
  private static final int __PERSONID_ISSET_ID = 2;
  private static final int __BEGDATE_ISSET_ID = 3;
  private static final int __ENDDATE_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.RECURSIVE,_Fields.SPECIALITY_NOTATION,_Fields.SPECIALITY,_Fields.BEG_DATE,_Fields.END_DATE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ORG_STRUCTURE_ID, new org.apache.thrift.meta_data.FieldMetaData("orgStructureId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECURSIVE, new org.apache.thrift.meta_data.FieldMetaData("recursive", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.SPECIALITY_NOTATION, new org.apache.thrift.meta_data.FieldMetaData("specialityNotation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SPECIALITY, new org.apache.thrift.meta_data.FieldMetaData("speciality", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PERSON_ID, new org.apache.thrift.meta_data.FieldMetaData("personId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BEG_DATE, new org.apache.thrift.meta_data.FieldMetaData("begDate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "timestamp")));
    tmpMap.put(_Fields.END_DATE, new org.apache.thrift.meta_data.FieldMetaData("endDate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "timestamp")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetTicketsAvailabilityParameters.class, metaDataMap);
  }

  public GetTicketsAvailabilityParameters() {
  }

  public GetTicketsAvailabilityParameters(
    int orgStructureId,
    int personId)
  {
    this();
    this.orgStructureId = orgStructureId;
    setOrgStructureIdIsSet(true);
    this.personId = personId;
    setPersonIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetTicketsAvailabilityParameters(GetTicketsAvailabilityParameters other) {
    __isset_bitfield = other.__isset_bitfield;
    this.orgStructureId = other.orgStructureId;
    this.recursive = other.recursive;
    if (other.isSetSpecialityNotation()) {
      this.specialityNotation = other.specialityNotation;
    }
    if (other.isSetSpeciality()) {
      this.speciality = other.speciality;
    }
    this.personId = other.personId;
    this.begDate = other.begDate;
    this.endDate = other.endDate;
  }

  public GetTicketsAvailabilityParameters deepCopy() {
    return new GetTicketsAvailabilityParameters(this);
  }

  @Override
  public void clear() {
    setOrgStructureIdIsSet(false);
    this.orgStructureId = 0;
    setRecursiveIsSet(false);
    this.recursive = false;
    this.specialityNotation = null;
    this.speciality = null;
    setPersonIdIsSet(false);
    this.personId = 0;
    setBegDateIsSet(false);
    this.begDate = 0;
    setEndDateIsSet(false);
    this.endDate = 0;
  }

  public int getOrgStructureId() {
    return this.orgStructureId;
  }

  public GetTicketsAvailabilityParameters setOrgStructureId(int orgStructureId) {
    this.orgStructureId = orgStructureId;
    setOrgStructureIdIsSet(true);
    return this;
  }

  public void unsetOrgStructureId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORGSTRUCTUREID_ISSET_ID);
  }

  /** Returns true if field orgStructureId is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgStructureId() {
    return EncodingUtils.testBit(__isset_bitfield, __ORGSTRUCTUREID_ISSET_ID);
  }

  public void setOrgStructureIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORGSTRUCTUREID_ISSET_ID, value);
  }

  public boolean isRecursive() {
    return this.recursive;
  }

  public GetTicketsAvailabilityParameters setRecursive(boolean recursive) {
    this.recursive = recursive;
    setRecursiveIsSet(true);
    return this;
  }

  public void unsetRecursive() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECURSIVE_ISSET_ID);
  }

  /** Returns true if field recursive is set (has been assigned a value) and false otherwise */
  public boolean isSetRecursive() {
    return EncodingUtils.testBit(__isset_bitfield, __RECURSIVE_ISSET_ID);
  }

  public void setRecursiveIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECURSIVE_ISSET_ID, value);
  }

  public String getSpecialityNotation() {
    return this.specialityNotation;
  }

  public GetTicketsAvailabilityParameters setSpecialityNotation(String specialityNotation) {
    this.specialityNotation = specialityNotation;
    return this;
  }

  public void unsetSpecialityNotation() {
    this.specialityNotation = null;
  }

  /** Returns true if field specialityNotation is set (has been assigned a value) and false otherwise */
  public boolean isSetSpecialityNotation() {
    return this.specialityNotation != null;
  }

  public void setSpecialityNotationIsSet(boolean value) {
    if (!value) {
      this.specialityNotation = null;
    }
  }

  public String getSpeciality() {
    return this.speciality;
  }

  public GetTicketsAvailabilityParameters setSpeciality(String speciality) {
    this.speciality = speciality;
    return this;
  }

  public void unsetSpeciality() {
    this.speciality = null;
  }

  /** Returns true if field speciality is set (has been assigned a value) and false otherwise */
  public boolean isSetSpeciality() {
    return this.speciality != null;
  }

  public void setSpecialityIsSet(boolean value) {
    if (!value) {
      this.speciality = null;
    }
  }

  public int getPersonId() {
    return this.personId;
  }

  public GetTicketsAvailabilityParameters setPersonId(int personId) {
    this.personId = personId;
    setPersonIdIsSet(true);
    return this;
  }

  public void unsetPersonId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PERSONID_ISSET_ID);
  }

  /** Returns true if field personId is set (has been assigned a value) and false otherwise */
  public boolean isSetPersonId() {
    return EncodingUtils.testBit(__isset_bitfield, __PERSONID_ISSET_ID);
  }

  public void setPersonIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PERSONID_ISSET_ID, value);
  }

  public long getBegDate() {
    return this.begDate;
  }

  public GetTicketsAvailabilityParameters setBegDate(long begDate) {
    this.begDate = begDate;
    setBegDateIsSet(true);
    return this;
  }

  public void unsetBegDate() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BEGDATE_ISSET_ID);
  }

  /** Returns true if field begDate is set (has been assigned a value) and false otherwise */
  public boolean isSetBegDate() {
    return EncodingUtils.testBit(__isset_bitfield, __BEGDATE_ISSET_ID);
  }

  public void setBegDateIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BEGDATE_ISSET_ID, value);
  }

  public long getEndDate() {
    return this.endDate;
  }

  public GetTicketsAvailabilityParameters setEndDate(long endDate) {
    this.endDate = endDate;
    setEndDateIsSet(true);
    return this;
  }

  public void unsetEndDate() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDDATE_ISSET_ID);
  }

  /** Returns true if field endDate is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDate() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDDATE_ISSET_ID);
  }

  public void setEndDateIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDDATE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORG_STRUCTURE_ID:
      if (value == null) {
        unsetOrgStructureId();
      } else {
        setOrgStructureId((Integer)value);
      }
      break;

    case RECURSIVE:
      if (value == null) {
        unsetRecursive();
      } else {
        setRecursive((Boolean)value);
      }
      break;

    case SPECIALITY_NOTATION:
      if (value == null) {
        unsetSpecialityNotation();
      } else {
        setSpecialityNotation((String)value);
      }
      break;

    case SPECIALITY:
      if (value == null) {
        unsetSpeciality();
      } else {
        setSpeciality((String)value);
      }
      break;

    case PERSON_ID:
      if (value == null) {
        unsetPersonId();
      } else {
        setPersonId((Integer)value);
      }
      break;

    case BEG_DATE:
      if (value == null) {
        unsetBegDate();
      } else {
        setBegDate((Long)value);
      }
      break;

    case END_DATE:
      if (value == null) {
        unsetEndDate();
      } else {
        setEndDate((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORG_STRUCTURE_ID:
      return Integer.valueOf(getOrgStructureId());

    case RECURSIVE:
      return Boolean.valueOf(isRecursive());

    case SPECIALITY_NOTATION:
      return getSpecialityNotation();

    case SPECIALITY:
      return getSpeciality();

    case PERSON_ID:
      return Integer.valueOf(getPersonId());

    case BEG_DATE:
      return Long.valueOf(getBegDate());

    case END_DATE:
      return Long.valueOf(getEndDate());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORG_STRUCTURE_ID:
      return isSetOrgStructureId();
    case RECURSIVE:
      return isSetRecursive();
    case SPECIALITY_NOTATION:
      return isSetSpecialityNotation();
    case SPECIALITY:
      return isSetSpeciality();
    case PERSON_ID:
      return isSetPersonId();
    case BEG_DATE:
      return isSetBegDate();
    case END_DATE:
      return isSetEndDate();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GetTicketsAvailabilityParameters)
      return this.equals((GetTicketsAvailabilityParameters)that);
    return false;
  }

  public boolean equals(GetTicketsAvailabilityParameters that) {
    if (that == null)
      return false;

    boolean this_present_orgStructureId = true;
    boolean that_present_orgStructureId = true;
    if (this_present_orgStructureId || that_present_orgStructureId) {
      if (!(this_present_orgStructureId && that_present_orgStructureId))
        return false;
      if (this.orgStructureId != that.orgStructureId)
        return false;
    }

    boolean this_present_recursive = true && this.isSetRecursive();
    boolean that_present_recursive = true && that.isSetRecursive();
    if (this_present_recursive || that_present_recursive) {
      if (!(this_present_recursive && that_present_recursive))
        return false;
      if (this.recursive != that.recursive)
        return false;
    }

    boolean this_present_specialityNotation = true && this.isSetSpecialityNotation();
    boolean that_present_specialityNotation = true && that.isSetSpecialityNotation();
    if (this_present_specialityNotation || that_present_specialityNotation) {
      if (!(this_present_specialityNotation && that_present_specialityNotation))
        return false;
      if (!this.specialityNotation.equals(that.specialityNotation))
        return false;
    }

    boolean this_present_speciality = true && this.isSetSpeciality();
    boolean that_present_speciality = true && that.isSetSpeciality();
    if (this_present_speciality || that_present_speciality) {
      if (!(this_present_speciality && that_present_speciality))
        return false;
      if (!this.speciality.equals(that.speciality))
        return false;
    }

    boolean this_present_personId = true;
    boolean that_present_personId = true;
    if (this_present_personId || that_present_personId) {
      if (!(this_present_personId && that_present_personId))
        return false;
      if (this.personId != that.personId)
        return false;
    }

    boolean this_present_begDate = true && this.isSetBegDate();
    boolean that_present_begDate = true && that.isSetBegDate();
    if (this_present_begDate || that_present_begDate) {
      if (!(this_present_begDate && that_present_begDate))
        return false;
      if (this.begDate != that.begDate)
        return false;
    }

    boolean this_present_endDate = true && this.isSetEndDate();
    boolean that_present_endDate = true && that.isSetEndDate();
    if (this_present_endDate || that_present_endDate) {
      if (!(this_present_endDate && that_present_endDate))
        return false;
      if (this.endDate != that.endDate)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(GetTicketsAvailabilityParameters other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    GetTicketsAvailabilityParameters typedOther = (GetTicketsAvailabilityParameters)other;

    lastComparison = Boolean.valueOf(isSetOrgStructureId()).compareTo(typedOther.isSetOrgStructureId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgStructureId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgStructureId, typedOther.orgStructureId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecursive()).compareTo(typedOther.isSetRecursive());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecursive()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recursive, typedOther.recursive);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSpecialityNotation()).compareTo(typedOther.isSetSpecialityNotation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSpecialityNotation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.specialityNotation, typedOther.specialityNotation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSpeciality()).compareTo(typedOther.isSetSpeciality());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSpeciality()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.speciality, typedOther.speciality);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPersonId()).compareTo(typedOther.isSetPersonId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPersonId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.personId, typedOther.personId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBegDate()).compareTo(typedOther.isSetBegDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBegDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.begDate, typedOther.begDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndDate()).compareTo(typedOther.isSetEndDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDate, typedOther.endDate);
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
    StringBuilder sb = new StringBuilder("GetTicketsAvailabilityParameters(");
    boolean first = true;

    sb.append("orgStructureId:");
    sb.append(this.orgStructureId);
    first = false;
    if (isSetRecursive()) {
      if (!first) sb.append(", ");
      sb.append("recursive:");
      sb.append(this.recursive);
      first = false;
    }
    if (isSetSpecialityNotation()) {
      if (!first) sb.append(", ");
      sb.append("specialityNotation:");
      if (this.specialityNotation == null) {
        sb.append("null");
      } else {
        sb.append(this.specialityNotation);
      }
      first = false;
    }
    if (isSetSpeciality()) {
      if (!first) sb.append(", ");
      sb.append("speciality:");
      if (this.speciality == null) {
        sb.append("null");
      } else {
        sb.append(this.speciality);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("personId:");
    sb.append(this.personId);
    first = false;
    if (isSetBegDate()) {
      if (!first) sb.append(", ");
      sb.append("begDate:");
      sb.append(this.begDate);
      first = false;
    }
    if (isSetEndDate()) {
      if (!first) sb.append(", ");
      sb.append("endDate:");
      sb.append(this.endDate);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'orgStructureId' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'personId' because it's a primitive and you chose the non-beans generator.
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

  private static class GetTicketsAvailabilityParametersStandardSchemeFactory implements SchemeFactory {
    public GetTicketsAvailabilityParametersStandardScheme getScheme() {
      return new GetTicketsAvailabilityParametersStandardScheme();
    }
  }

  private static class GetTicketsAvailabilityParametersStandardScheme extends StandardScheme<GetTicketsAvailabilityParameters> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetTicketsAvailabilityParameters struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORG_STRUCTURE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.orgStructureId = iprot.readI32();
              struct.setOrgStructureIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RECURSIVE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.recursive = iprot.readBool();
              struct.setRecursiveIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SPECIALITY_NOTATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.specialityNotation = iprot.readString();
              struct.setSpecialityNotationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SPECIALITY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.speciality = iprot.readString();
              struct.setSpecialityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PERSON_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.personId = iprot.readI32();
              struct.setPersonIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // BEG_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.begDate = iprot.readI64();
              struct.setBegDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // END_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endDate = iprot.readI64();
              struct.setEndDateIsSet(true);
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
      if (!struct.isSetOrgStructureId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'orgStructureId' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetPersonId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'personId' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetTicketsAvailabilityParameters struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ORG_STRUCTURE_ID_FIELD_DESC);
      oprot.writeI32(struct.orgStructureId);
      oprot.writeFieldEnd();
      if (struct.isSetRecursive()) {
        oprot.writeFieldBegin(RECURSIVE_FIELD_DESC);
        oprot.writeBool(struct.recursive);
        oprot.writeFieldEnd();
      }
      if (struct.specialityNotation != null) {
        if (struct.isSetSpecialityNotation()) {
          oprot.writeFieldBegin(SPECIALITY_NOTATION_FIELD_DESC);
          oprot.writeString(struct.specialityNotation);
          oprot.writeFieldEnd();
        }
      }
      if (struct.speciality != null) {
        if (struct.isSetSpeciality()) {
          oprot.writeFieldBegin(SPECIALITY_FIELD_DESC);
          oprot.writeString(struct.speciality);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(PERSON_ID_FIELD_DESC);
      oprot.writeI32(struct.personId);
      oprot.writeFieldEnd();
      if (struct.isSetBegDate()) {
        oprot.writeFieldBegin(BEG_DATE_FIELD_DESC);
        oprot.writeI64(struct.begDate);
        oprot.writeFieldEnd();
      }
      if (struct.isSetEndDate()) {
        oprot.writeFieldBegin(END_DATE_FIELD_DESC);
        oprot.writeI64(struct.endDate);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetTicketsAvailabilityParametersTupleSchemeFactory implements SchemeFactory {
    public GetTicketsAvailabilityParametersTupleScheme getScheme() {
      return new GetTicketsAvailabilityParametersTupleScheme();
    }
  }

  private static class GetTicketsAvailabilityParametersTupleScheme extends TupleScheme<GetTicketsAvailabilityParameters> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetTicketsAvailabilityParameters struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.orgStructureId);
      oprot.writeI32(struct.personId);
      BitSet optionals = new BitSet();
      if (struct.isSetRecursive()) {
        optionals.set(0);
      }
      if (struct.isSetSpecialityNotation()) {
        optionals.set(1);
      }
      if (struct.isSetSpeciality()) {
        optionals.set(2);
      }
      if (struct.isSetBegDate()) {
        optionals.set(3);
      }
      if (struct.isSetEndDate()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetRecursive()) {
        oprot.writeBool(struct.recursive);
      }
      if (struct.isSetSpecialityNotation()) {
        oprot.writeString(struct.specialityNotation);
      }
      if (struct.isSetSpeciality()) {
        oprot.writeString(struct.speciality);
      }
      if (struct.isSetBegDate()) {
        oprot.writeI64(struct.begDate);
      }
      if (struct.isSetEndDate()) {
        oprot.writeI64(struct.endDate);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetTicketsAvailabilityParameters struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.orgStructureId = iprot.readI32();
      struct.setOrgStructureIdIsSet(true);
      struct.personId = iprot.readI32();
      struct.setPersonIdIsSet(true);
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.recursive = iprot.readBool();
        struct.setRecursiveIsSet(true);
      }
      if (incoming.get(1)) {
        struct.specialityNotation = iprot.readString();
        struct.setSpecialityNotationIsSet(true);
      }
      if (incoming.get(2)) {
        struct.speciality = iprot.readString();
        struct.setSpecialityIsSet(true);
      }
      if (incoming.get(3)) {
        struct.begDate = iprot.readI64();
        struct.setBegDateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.endDate = iprot.readI64();
        struct.setEndDateIsSet(true);
      }
    }
  }

}

