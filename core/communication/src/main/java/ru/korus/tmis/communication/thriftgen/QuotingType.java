/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.korus.tmis.communication.thriftgen;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * QuotingType
 * Перечисление типов квотирования
 */
public enum QuotingType implements org.apache.thrift.TEnum {
  FROM_REGISTRY(1),
  SECOND_VISIT(2),
  BETWEEN_CABINET(3),
  FROM_OTHER_LPU(4),
  FROM_PORTAL(5);

  private final int value;

  private QuotingType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static QuotingType findByValue(int value) { 
    switch (value) {
      case 1:
        return FROM_REGISTRY;
      case 2:
        return SECOND_VISIT;
      case 3:
        return BETWEEN_CABINET;
      case 4:
        return FROM_OTHER_LPU;
      case 5:
        return FROM_PORTAL;
      default:
        return null;
    }
  }
}