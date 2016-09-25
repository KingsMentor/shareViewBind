package xyz.belvi.sharedview.Sharedpref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zone2 on 9/21/16.
 */

public class BindHandler {
    private Field targetField;
    private OperationType operationType;
    private Method method;
    private Object target;
    private int priority;
    private Class classType;
    private String defaultValue;


    public BindHandler(Field targetField, Object target, SharedField sharedView) {
        this.targetField = targetField;
        this.target = target;
        operationType = sharedView.operationType();
        priority = sharedView.priority();
        classType = sharedView.classType();
        defaultValue = sharedView.defaultValue();
    }


    public BindHandler(Method method, Object target, SharedMethod sharedMethod) {
        this.method = method;
        this.target = target;
        operationType = sharedMethod.operationType();
        priority = sharedMethod.priority();

    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Field getTargetField() {
        return targetField;
    }

    public void setTargetField(Field targetField) {
        this.targetField = targetField;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class getClassType() {
        return this.classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
}
