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
 * Данные экшена назначения и значения его свойств
 */
public class ActionData implements org.apache.thrift.TBase<ActionData, ActionData._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ActionData");

  private static final org.apache.thrift.protocol.TField ACTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("action_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NOTE_FIELD_DESC = new org.apache.thrift.protocol.TField("note", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SET_PERSON_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("setPerson_id", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField MOA_FIELD_DESC = new org.apache.thrift.protocol.TField("moa", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField VOA_FIELD_DESC = new org.apache.thrift.protocol.TField("voa", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ActionDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ActionDataTupleSchemeFactory());
  }

  public int action_id; // optional
  public String note; // optional
  public int setPerson_id; // required
  public String moa; // optional
  public String voa; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ACTION_ID((short)1, "action_id"),
    NOTE((short)2, "note"),
    SET_PERSON_ID((short)3, "setPerson_id"),
    MOA((short)4, "moa"),
    VOA((short)5, "voa");

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
        case 1: // ACTION_ID
          return ACTION_ID;
        case 2: // NOTE
          return NOTE;
        case 3: // SET_PERSON_ID
          return SET_PERSON_ID;
        case 4: // MOA
          return MOA;
        case 5: // VOA
          return VOA;
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
  private static final int __ACTION_ID_ISSET_ID = 0;
  private static final int __SETPERSON_ID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ACTION_ID,_Fields.NOTE,_Fields.MOA,_Fields.VOA};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ACTION_ID, new org.apache.thrift.meta_data.FieldMetaData("action_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NOTE, new org.apache.thrift.meta_data.FieldMetaData("note", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SET_PERSON_ID, new org.apache.thrift.meta_data.FieldMetaData("setPerson_id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MOA, new org.apache.thrift.meta_data.FieldMetaData("moa", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VOA, new org.apache.thrift.meta_data.FieldMetaData("voa", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ActionData.class, metaDataMap);
  }

  public ActionData() {
  }

  public ActionData(
    int setPerson_id)
  {
    this();
    this.setPerson_id = setPerson_id;
    setSetPerson_idIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ActionData(ActionData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.action_id = other.action_id;
    if (other.isSetNote()) {
      this.note = other.note;
    }
    this.setPerson_id = other.setPerson_id;
    if (other.isSetMoa()) {
      this.moa = other.moa;
    }
    if (other.isSetVoa()) {
      this.voa = other.voa;
    }
  }

  public ActionData deepCopy() {
    return new ActionData(this);
  }

  @Override
  public void clear() {
    setAction_idIsSet(false);
    this.action_id = 0;
    this.note = null;
    setSetPerson_idIsSet(false);
    this.setPerson_id = 0;
    this.moa = null;
    this.voa = null;
  }

  public int getAction_id() {
    return this.action_id;
  }

  public ActionData setAction_id(int action_id) {
    this.action_id = action_id;
    setAction_idIsSet(true);
    return this;
  }

  public void unsetAction_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ACTION_ID_ISSET_ID);
  }

  /** Returns true if field action_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAction_id() {
    return EncodingUtils.testBit(__isset_bitfield, __ACTION_ID_ISSET_ID);
  }

  public void setAction_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ACTION_ID_ISSET_ID, value);
  }

  public String getNote() {
    return this.note;
  }

  public ActionData setNote(String note) {
    this.note = note;
    return this;
  }

  public void unsetNote() {
    this.note = null;
  }

  /** Returns true if field note is set (has been assigned a value) and false otherwise */
  public boolean isSetNote() {
    return this.note != null;
  }

  public void setNoteIsSet(boolean value) {
    if (!value) {
      this.note = null;
    }
  }

  public int getSetPerson_id() {
    return this.setPerson_id;
  }

  public ActionData setSetPerson_id(int setPerson_id) {
    this.setPerson_id = setPerson_id;
    setSetPerson_idIsSet(true);
    return this;
  }

  public void unsetSetPerson_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SETPERSON_ID_ISSET_ID);
  }

  /** Returns true if field setPerson_id is set (has been assigned a value) and false otherwise */
  public boolean isSetSetPerson_id() {
    return EncodingUtils.testBit(__isset_bitfield, __SETPERSON_ID_ISSET_ID);
  }

  public void setSetPerson_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SETPERSON_ID_ISSET_ID, value);
  }

  public String getMoa() {
    return this.moa;
  }

  public ActionData setMoa(String moa) {
    this.moa = moa;
    return this;
  }

  public void unsetMoa() {
    this.moa = null;
  }

  /** Returns true if field moa is set (has been assigned a value) and false otherwise */
  public boolean isSetMoa() {
    return this.moa != null;
  }

  public void setMoaIsSet(boolean value) {
    if (!value) {
      this.moa = null;
    }
  }

  public String getVoa() {
    return this.voa;
  }

  public ActionData setVoa(String voa) {
    this.voa = voa;
    return this;
  }

  public void unsetVoa() {
    this.voa = null;
  }

  /** Returns true if field voa is set (has been assigned a value) and false otherwise */
  public boolean isSetVoa() {
    return this.voa != null;
  }

  public void setVoaIsSet(boolean value) {
    if (!value) {
      this.voa = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ACTION_ID:
      if (value == null) {
        unsetAction_id();
      } else {
        setAction_id((Integer)value);
      }
      break;

    case NOTE:
      if (value == null) {
        unsetNote();
      } else {
        setNote((String)value);
      }
      break;

    case SET_PERSON_ID:
      if (value == null) {
        unsetSetPerson_id();
      } else {
        setSetPerson_id((Integer)value);
      }
      break;

    case MOA:
      if (value == null) {
        unsetMoa();
      } else {
        setMoa((String)value);
      }
      break;

    case VOA:
      if (value == null) {
        unsetVoa();
      } else {
        setVoa((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ACTION_ID:
      return Integer.valueOf(getAction_id());

    case NOTE:
      return getNote();

    case SET_PERSON_ID:
      return Integer.valueOf(getSetPerson_id());

    case MOA:
      return getMoa();

    case VOA:
      return getVoa();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ACTION_ID:
      return isSetAction_id();
    case NOTE:
      return isSetNote();
    case SET_PERSON_ID:
      return isSetSetPerson_id();
    case MOA:
      return isSetMoa();
    case VOA:
      return isSetVoa();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ActionData)
      return this.equals((ActionData)that);
    return false;
  }

  public boolean equals(ActionData that) {
    if (that == null)
      return false;

    boolean this_present_action_id = true && this.isSetAction_id();
    boolean that_present_action_id = true && that.isSetAction_id();
    if (this_present_action_id || that_present_action_id) {
      if (!(this_present_action_id && that_present_action_id))
        return false;
      if (this.action_id != that.action_id)
        return false;
    }

    boolean this_present_note = true && this.isSetNote();
    boolean that_present_note = true && that.isSetNote();
    if (this_present_note || that_present_note) {
      if (!(this_present_note && that_present_note))
        return false;
      if (!this.note.equals(that.note))
        return false;
    }

    boolean this_present_setPerson_id = true;
    boolean that_present_setPerson_id = true;
    if (this_present_setPerson_id || that_present_setPerson_id) {
      if (!(this_present_setPerson_id && that_present_setPerson_id))
        return false;
      if (this.setPerson_id != that.setPerson_id)
        return false;
    }

    boolean this_present_moa = true && this.isSetMoa();
    boolean that_present_moa = true && that.isSetMoa();
    if (this_present_moa || that_present_moa) {
      if (!(this_present_moa && that_present_moa))
        return false;
      if (!this.moa.equals(that.moa))
        return false;
    }

    boolean this_present_voa = true && this.isSetVoa();
    boolean that_present_voa = true && that.isSetVoa();
    if (this_present_voa || that_present_voa) {
      if (!(this_present_voa && that_present_voa))
        return false;
      if (!this.voa.equals(that.voa))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(ActionData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ActionData typedOther = (ActionData)other;

    lastComparison = Boolean.valueOf(isSetAction_id()).compareTo(typedOther.isSetAction_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAction_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.action_id, typedOther.action_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNote()).compareTo(typedOther.isSetNote());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNote()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.note, typedOther.note);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSetPerson_id()).compareTo(typedOther.isSetSetPerson_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSetPerson_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.setPerson_id, typedOther.setPerson_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMoa()).compareTo(typedOther.isSetMoa());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMoa()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moa, typedOther.moa);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVoa()).compareTo(typedOther.isSetVoa());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVoa()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.voa, typedOther.voa);
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
    StringBuilder sb = new StringBuilder("ActionData(");
    boolean first = true;

    if (isSetAction_id()) {
      sb.append("action_id:");
      sb.append(this.action_id);
      first = false;
    }
    if (isSetNote()) {
      if (!first) sb.append(", ");
      sb.append("note:");
      if (this.note == null) {
        sb.append("null");
      } else {
        sb.append(this.note);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("setPerson_id:");
    sb.append(this.setPerson_id);
    first = false;
    if (isSetMoa()) {
      if (!first) sb.append(", ");
      sb.append("moa:");
      if (this.moa == null) {
        sb.append("null");
      } else {
        sb.append(this.moa);
      }
      first = false;
    }
    if (isSetVoa()) {
      if (!first) sb.append(", ");
      sb.append("voa:");
      if (this.voa == null) {
        sb.append("null");
      } else {
        sb.append(this.voa);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'setPerson_id' because it's a primitive and you chose the non-beans generator.
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

  private static class ActionDataStandardSchemeFactory implements SchemeFactory {
    public ActionDataStandardScheme getScheme() {
      return new ActionDataStandardScheme();
    }
  }

  private static class ActionDataStandardScheme extends StandardScheme<ActionData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ActionData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ACTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.action_id = iprot.readI32();
              struct.setAction_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NOTE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.note = iprot.readString();
              struct.setNoteIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SET_PERSON_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.setPerson_id = iprot.readI32();
              struct.setSetPerson_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MOA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.moa = iprot.readString();
              struct.setMoaIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // VOA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.voa = iprot.readString();
              struct.setVoaIsSet(true);
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
      if (!struct.isSetSetPerson_id()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'setPerson_id' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ActionData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetAction_id()) {
        oprot.writeFieldBegin(ACTION_ID_FIELD_DESC);
        oprot.writeI32(struct.action_id);
        oprot.writeFieldEnd();
      }
      if (struct.note != null) {
        if (struct.isSetNote()) {
          oprot.writeFieldBegin(NOTE_FIELD_DESC);
          oprot.writeString(struct.note);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(SET_PERSON_ID_FIELD_DESC);
      oprot.writeI32(struct.setPerson_id);
      oprot.writeFieldEnd();
      if (struct.moa != null) {
        if (struct.isSetMoa()) {
          oprot.writeFieldBegin(MOA_FIELD_DESC);
          oprot.writeString(struct.moa);
          oprot.writeFieldEnd();
        }
      }
      if (struct.voa != null) {
        if (struct.isSetVoa()) {
          oprot.writeFieldBegin(VOA_FIELD_DESC);
          oprot.writeString(struct.voa);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ActionDataTupleSchemeFactory implements SchemeFactory {
    public ActionDataTupleScheme getScheme() {
      return new ActionDataTupleScheme();
    }
  }

  private static class ActionDataTupleScheme extends TupleScheme<ActionData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ActionData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.setPerson_id);
      BitSet optionals = new BitSet();
      if (struct.isSetAction_id()) {
        optionals.set(0);
      }
      if (struct.isSetNote()) {
        optionals.set(1);
      }
      if (struct.isSetMoa()) {
        optionals.set(2);
      }
      if (struct.isSetVoa()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetAction_id()) {
        oprot.writeI32(struct.action_id);
      }
      if (struct.isSetNote()) {
        oprot.writeString(struct.note);
      }
      if (struct.isSetMoa()) {
        oprot.writeString(struct.moa);
      }
      if (struct.isSetVoa()) {
        oprot.writeString(struct.voa);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ActionData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.setPerson_id = iprot.readI32();
      struct.setSetPerson_idIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.action_id = iprot.readI32();
        struct.setAction_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.note = iprot.readString();
        struct.setNoteIsSet(true);
      }
      if (incoming.get(2)) {
        struct.moa = iprot.readString();
        struct.setMoaIsSet(true);
      }
      if (incoming.get(3)) {
        struct.voa = iprot.readString();
        struct.setVoaIsSet(true);
      }
    }
  }

}

