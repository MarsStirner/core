package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventPerson;

/**
 * Перечень событий и условия, приводящие к назначению ответственного врача для пациента в рамках госпитализации
 * Author: idmitriev Systema-Soft
 * Date: 4/16/13 9:49 PM
 * Since: 1.0.1.3
 */
public enum ExecPersonSetType  {

    EP_CREATE_APPEAL("CREATE_APPEAL"){
        public int getEventPersonId(Object data) { return 0;}  //Всегда создаем новое
        public boolean getFlushFlag() { return true;}
        public boolean isFindLast() { return false;}
        public int getVarId(){return 0;}
    },
    EP_CREATE_PRIMARY("CREATE_PRIMARY"){
        public int getEventPersonId(Object data) {
            return (data!=null && (data instanceof EventPerson))
                    ? ((EventPerson)data).getId().intValue()
                    : 0;
        }
        public boolean getFlushFlag() { return false;}  //Не флэшим
        public boolean isFindLast() { return true;}
        public int getVarId(){return 0;}
    },
    EP_MODIFY_PRIMARY("MODIFY_PRIMARY"){
        public int getEventPersonId(Object data) {
            return (data!=null && (data instanceof EventPerson))
                    ? ((EventPerson)data).getId().intValue()
                    : 0;
        }
        public boolean getFlushFlag() { return false;}  //Не флэшим
        public boolean isFindLast() { return true;}
        public int getVarId(){return 1;}
    },
    EP_SET_IN_LPU("SET_IN_LPU"){
        public int getEventPersonId(Object data) {
            return (data!=null && (data instanceof EventPerson))
                    ? ((EventPerson)data).getId().intValue()
                    : 0;
        }
        public boolean getFlushFlag() { return true;}
        public boolean isFindLast() { return true;}
        public int getVarId(){return 0;}
    };

    public abstract int getEventPersonId(Object data); //Получение идентификатора предыдущего ответственного
    public abstract int getVarId();         //Получение типа идентификатора 0 - эвент, 1 - экшн
    public abstract boolean getFlushFlag();            //Получение флага необходимости флэшить
    public abstract boolean isFindLast();              //Получение флага необходимости флэшить

    private String typeValue;

    private ExecPersonSetType(String type) {
        typeValue = type;
    }

    static public ExecPersonSetType getType(String pType) {
        for (ExecPersonSetType type: ExecPersonSetType.values()) {
            if (type.getTypeValue().equals(pType)) {
                return type;
            }
        }
        throw new RuntimeException("Неизвестный тип для enum ExecPersonSetType");
    }

    public String getTypeValue() {
        return typeValue;
    }
}
