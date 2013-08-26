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
 * Интервал времени исполнения назначенного диапазона времени.
 * Для этих записей установлено поле master_id, ссылающееся на запись
 * назначенного интервала
 */
public class DrugIntervalInner implements org.apache.thrift.TBase<DrugIntervalInner, DrugIntervalInner._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DrugIntervalInner");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ACTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("action_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField MASTER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("master_id", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField BEG_DATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("begDateTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField END_DATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("endDateTime", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField STATUS_DATETIME_FIELD_DESC = new org.apache.thrift.protocol.TField("statusDatetime", org.apache.thrift.protocol.TType.I64, (short)7);
  private static final org.apache.thrift.protocol.TField NOTE_FIELD_DESC = new org.apache.thrift.protocol.TField("note", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DrugIntervalInnerStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DrugIntervalInnerTupleSchemeFactory());
  }

  public int id; // optional
  public int action_id; // optional
  public int master_id; // optional
  public long begDateTime; // required
  public long endDateTime; // optional
  public int status; // required
  public long statusDatetime; // optional
  public String note; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    ACTION_ID((short)2, "action_id"),
    MASTER_ID((short)3, "master_id"),
    BEG_DATE_TIME((short)4, "begDateTime"),
    END_DATE_TIME((short)5, "endDateTime"),
    STATUS((short)6, "status"),
    STATUS_DATETIME((short)7, "statusDatetime"),
    NOTE((short)8, "note");

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
        case 2: // ACTION_ID
          return ACTION_ID;
        case 3: // MASTER_ID
          return MASTER_ID;
        case 4: // BEG_DATE_TIME
          return BEG_DATE_TIME;
        case 5: // END_DATE_TIME
          return END_DATE_TIME;
        case 6: // STATUS
          return STATUS;
        case 7: // STATUS_DATETIME
          return STATUS_DATETIME;
        case 8: // NOTE
          return NOTE;
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
  private static final int __ACTION_ID_ISSET_ID = 1;
  private static final int __MASTER_ID_ISSET_ID = 2;
  private static final int __BEGDATETIME_ISSET_ID = 3;
  private static final int __ENDDATETIME_ISSET_ID = 4;
  private static final int __STATUS_ISSET_ID = 5;
  private static final int __STATUSDATETIME_ISSET_ID = 6;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ID,_Fields.ACTION_ID,_Fields.MASTER_ID,_Fields.END_DATE_TIME,_Fields.STATUS_DATETIME,_Fields.NOTE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ACTION_ID, new org.apache.thrift.meta_data.FieldMetaData("action_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MASTER_ID, new org.apache.thrift.meta_data.FieldMetaData("master_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BEG_DATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("begDateTime", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "timestamp")));
    tmpMap.put(_Fields.END_DATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("endDateTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "timestamp")));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS_DATETIME, new org.apache.thrift.meta_data.FieldMetaData("statusDatetime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "timestamp")));
    tmpMap.put(_Fields.NOTE, new org.apache.thrift.meta_data.FieldMetaData("note", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DrugIntervalInner.class, metaDataMap);
  }

  public DrugIntervalInner() {
  }

  public DrugIntervalInner(
    long begDateTime,
    int status)
  {
    this();
    this.begDateTime = begDateTime;
    setBegDateTimeIsSet(true);
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DrugIntervalInner(DrugIntervalInner other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.action_id = other.action_id;
    this.master_id = other.master_id;
    this.begDateTime = other.begDateTime;
    this.endDateTime = other.endDateTime;
    this.status = other.status;
    this.statusDatetime = other.statusDatetime;
    if (other.isSetNote()) {
      this.note = other.note;
    }
  }

  public DrugIntervalInner deepCopy() {
    return new DrugIntervalInner(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setAction_idIsSet(false);
    this.action_id = 0;
    setMaster_idIsSet(false);
    this.master_id = 0;
    setBegDateTimeIsSet(false);
    this.begDateTime = 0;
    setEndDateTimeIsSet(false);
    this.endDateTime = 0;
    setStatusIsSet(false);
    this.status = 0;
    setStatusDatetimeIsSet(false);
    this.statusDatetime = 0;
    this.note = null;
  }

  public int getId() {
    return this.id;
  }

  public DrugIntervalInner setId(int id) {
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

  public int getAction_id() {
    return this.action_id;
  }

  public DrugIntervalInner setAction_id(int action_id) {
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

  public int getMaster_id() {
    return this.master_id;
  }

  public DrugIntervalInner setMaster_id(int master_id) {
    this.master_id = master_id;
    setMaster_idIsSet(true);
    return this;
  }

  public void unsetMaster_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MASTER_ID_ISSET_ID);
  }

  /** Returns true if field master_id is set (has been assigned a value) and false otherwise */
  public boolean isSetMaster_id() {
    return EncodingUtils.testBit(__isset_bitfield, __MASTER_ID_ISSET_ID);
  }

  public void setMaster_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MASTER_ID_ISSET_ID, value);
  }

  public long getBegDateTime() {
    return this.begDateTime;
  }

  public DrugIntervalInner setBegDateTime(long begDateTime) {
    this.begDateTime = begDateTime;
    setBegDateTimeIsSet(true);
    return this;
  }

  public void unsetBegDateTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BEGDATETIME_ISSET_ID);
  }

  /** Returns true if field begDateTime is set (has been assigned a value) and false otherwise */
  public boolean isSetBegDateTime() {
    return EncodingUtils.testBit(__isset_bitfield, __BEGDATETIME_ISSET_ID);
  }

  public void setBegDateTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BEGDATETIME_ISSET_ID, value);
  }

  public long getEndDateTime() {
    return this.endDateTime;
  }

  public DrugIntervalInner setEndDateTime(long endDateTime) {
    this.endDateTime = endDateTime;
    setEndDateTimeIsSet(true);
    return this;
  }

  public void unsetEndDateTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENDDATETIME_ISSET_ID);
  }

  /** Returns true if field endDateTime is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDateTime() {
    return EncodingUtils.testBit(__isset_bitfield, __ENDDATETIME_ISSET_ID);
  }

  public void setEndDateTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENDDATETIME_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public DrugIntervalInner setStatus(int status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
  }

  public long getStatusDatetime() {
    return this.statusDatetime;
  }

  public DrugIntervalInner setStatusDatetime(long statusDatetime) {
    this.statusDatetime = statusDatetime;
    setStatusDatetimeIsSet(true);
    return this;
  }

  public void unsetStatusDatetime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STATUSDATETIME_ISSET_ID);
  }

  /** Returns true if field statusDatetime is set (has been assigned a value) and false otherwise */
  public boolean isSetStatusDatetime() {
    return EncodingUtils.testBit(__isset_bitfield, __STATUSDATETIME_ISSET_ID);
  }

  public void setStatusDatetimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STATUSDATETIME_ISSET_ID, value);
  }

  public String getNote() {
    return this.note;
  }

  public DrugIntervalInner setNote(String note) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case ACTION_ID:
      if (value == null) {
        unsetAction_id();
      } else {
        setAction_id((Integer)value);
      }
      break;

    case MASTER_ID:
      if (value == null) {
        unsetMaster_id();
      } else {
        setMaster_id((Integer)value);
      }
      break;

    case BEG_DATE_TIME:
      if (value == null) {
        unsetBegDateTime();
      } else {
        setBegDateTime((Long)value);
      }
      break;

    case END_DATE_TIME:
      if (value == null) {
        unsetEndDateTime();
      } else {
        setEndDateTime((Long)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    case STATUS_DATETIME:
      if (value == null) {
        unsetStatusDatetime();
      } else {
        setStatusDatetime((Long)value);
      }
      break;

    case NOTE:
      if (value == null) {
        unsetNote();
      } else {
        setNote((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case ACTION_ID:
      return Integer.valueOf(getAction_id());

    case MASTER_ID:
      return Integer.valueOf(getMaster_id());

    case BEG_DATE_TIME:
      return Long.valueOf(getBegDateTime());

    case END_DATE_TIME:
      return Long.valueOf(getEndDateTime());

    case STATUS:
      return Integer.valueOf(getStatus());

    case STATUS_DATETIME:
      return Long.valueOf(getStatusDatetime());

    case NOTE:
      return getNote();

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
    case ACTION_ID:
      return isSetAction_id();
    case MASTER_ID:
      return isSetMaster_id();
    case BEG_DATE_TIME:
      return isSetBegDateTime();
    case END_DATE_TIME:
      return isSetEndDateTime();
    case STATUS:
      return isSetStatus();
    case STATUS_DATETIME:
      return isSetStatusDatetime();
    case NOTE:
      return isSetNote();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DrugIntervalInner)
      return this.equals((DrugIntervalInner)that);
    return false;
  }

  public boolean equals(DrugIntervalInner that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_action_id = true && this.isSetAction_id();
    boolean that_present_action_id = true && that.isSetAction_id();
    if (this_present_action_id || that_present_action_id) {
      if (!(this_present_action_id && that_present_action_id))
        return false;
      if (this.action_id != that.action_id)
        return false;
    }

    boolean this_present_master_id = true && this.isSetMaster_id();
    boolean that_present_master_id = true && that.isSetMaster_id();
    if (this_present_master_id || that_present_master_id) {
      if (!(this_present_master_id && that_present_master_id))
        return false;
      if (this.master_id != that.master_id)
        return false;
    }

    boolean this_present_begDateTime = true;
    boolean that_present_begDateTime = true;
    if (this_present_begDateTime || that_present_begDateTime) {
      if (!(this_present_begDateTime && that_present_begDateTime))
        return false;
      if (this.begDateTime != that.begDateTime)
        return false;
    }

    boolean this_present_endDateTime = true && this.isSetEndDateTime();
    boolean that_present_endDateTime = true && that.isSetEndDateTime();
    if (this_present_endDateTime || that_present_endDateTime) {
      if (!(this_present_endDateTime && that_present_endDateTime))
        return false;
      if (this.endDateTime != that.endDateTime)
        return false;
    }

    boolean this_present_status = true;
    boolean that_present_status = true;
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    boolean this_present_statusDatetime = true && this.isSetStatusDatetime();
    boolean that_present_statusDatetime = true && that.isSetStatusDatetime();
    if (this_present_statusDatetime || that_present_statusDatetime) {
      if (!(this_present_statusDatetime && that_present_statusDatetime))
        return false;
      if (this.statusDatetime != that.statusDatetime)
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

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(DrugIntervalInner other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    DrugIntervalInner typedOther = (DrugIntervalInner)other;

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
    lastComparison = Boolean.valueOf(isSetMaster_id()).compareTo(typedOther.isSetMaster_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaster_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.master_id, typedOther.master_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBegDateTime()).compareTo(typedOther.isSetBegDateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBegDateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.begDateTime, typedOther.begDateTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndDateTime()).compareTo(typedOther.isSetEndDateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDateTime, typedOther.endDateTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(typedOther.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, typedOther.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatusDatetime()).compareTo(typedOther.isSetStatusDatetime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatusDatetime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statusDatetime, typedOther.statusDatetime);
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
    StringBuilder sb = new StringBuilder("DrugIntervalInner(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetAction_id()) {
      if (!first) sb.append(", ");
      sb.append("action_id:");
      sb.append(this.action_id);
      first = false;
    }
    if (isSetMaster_id()) {
      if (!first) sb.append(", ");
      sb.append("master_id:");
      sb.append(this.master_id);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("begDateTime:");
    sb.append(this.begDateTime);
    first = false;
    if (isSetEndDateTime()) {
      if (!first) sb.append(", ");
      sb.append("endDateTime:");
      sb.append(this.endDateTime);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
    first = false;
    if (isSetStatusDatetime()) {
      if (!first) sb.append(", ");
      sb.append("statusDatetime:");
      sb.append(this.statusDatetime);
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
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'begDateTime' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'status' because it's a primitive and you chose the non-beans generator.
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

  private static class DrugIntervalInnerStandardSchemeFactory implements SchemeFactory {
    public DrugIntervalInnerStandardScheme getScheme() {
      return new DrugIntervalInnerStandardScheme();
    }
  }

  private static class DrugIntervalInnerStandardScheme extends StandardScheme<DrugIntervalInner> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DrugIntervalInner struct) throws org.apache.thrift.TException {
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
          case 2: // ACTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.action_id = iprot.readI32();
              struct.setAction_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MASTER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.master_id = iprot.readI32();
              struct.setMaster_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BEG_DATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.begDateTime = iprot.readI64();
              struct.setBegDateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // END_DATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endDateTime = iprot.readI64();
              struct.setEndDateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // STATUS_DATETIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.statusDatetime = iprot.readI64();
              struct.setStatusDatetimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // NOTE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.note = iprot.readString();
              struct.setNoteIsSet(true);
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
      if (!struct.isSetBegDateTime()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'begDateTime' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetStatus()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'status' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, DrugIntervalInner struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAction_id()) {
        oprot.writeFieldBegin(ACTION_ID_FIELD_DESC);
        oprot.writeI32(struct.action_id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetMaster_id()) {
        oprot.writeFieldBegin(MASTER_ID_FIELD_DESC);
        oprot.writeI32(struct.master_id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(BEG_DATE_TIME_FIELD_DESC);
      oprot.writeI64(struct.begDateTime);
      oprot.writeFieldEnd();
      if (struct.isSetEndDateTime()) {
        oprot.writeFieldBegin(END_DATE_TIME_FIELD_DESC);
        oprot.writeI64(struct.endDateTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      if (struct.isSetStatusDatetime()) {
        oprot.writeFieldBegin(STATUS_DATETIME_FIELD_DESC);
        oprot.writeI64(struct.statusDatetime);
        oprot.writeFieldEnd();
      }
      if (struct.note != null) {
        if (struct.isSetNote()) {
          oprot.writeFieldBegin(NOTE_FIELD_DESC);
          oprot.writeString(struct.note);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DrugIntervalInnerTupleSchemeFactory implements SchemeFactory {
    public DrugIntervalInnerTupleScheme getScheme() {
      return new DrugIntervalInnerTupleScheme();
    }
  }

  private static class DrugIntervalInnerTupleScheme extends TupleScheme<DrugIntervalInner> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DrugIntervalInner struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.begDateTime);
      oprot.writeI32(struct.status);
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetAction_id()) {
        optionals.set(1);
      }
      if (struct.isSetMaster_id()) {
        optionals.set(2);
      }
      if (struct.isSetEndDateTime()) {
        optionals.set(3);
      }
      if (struct.isSetStatusDatetime()) {
        optionals.set(4);
      }
      if (struct.isSetNote()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetAction_id()) {
        oprot.writeI32(struct.action_id);
      }
      if (struct.isSetMaster_id()) {
        oprot.writeI32(struct.master_id);
      }
      if (struct.isSetEndDateTime()) {
        oprot.writeI64(struct.endDateTime);
      }
      if (struct.isSetStatusDatetime()) {
        oprot.writeI64(struct.statusDatetime);
      }
      if (struct.isSetNote()) {
        oprot.writeString(struct.note);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DrugIntervalInner struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.begDateTime = iprot.readI64();
      struct.setBegDateTimeIsSet(true);
      struct.status = iprot.readI32();
      struct.setStatusIsSet(true);
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.action_id = iprot.readI32();
        struct.setAction_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.master_id = iprot.readI32();
        struct.setMaster_idIsSet(true);
      }
      if (incoming.get(3)) {
        struct.endDateTime = iprot.readI64();
        struct.setEndDateTimeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.statusDatetime = iprot.readI64();
        struct.setStatusDatetimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.note = iprot.readString();
        struct.setNoteIsSet(true);
      }
    }
  }

}

