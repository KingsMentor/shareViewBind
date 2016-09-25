package xyz.belvi.sharedview.Sharedpref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zone2 on 9/21/16.
 */

public class BindHandler {
    private Field targetField;
    private OperationType operationType;
    private SharedObj sharedObj;
    private Method method;
    private Object target;
    private int priority;


    public BindHandler(Field targetField, Object target, SharedField sharedView) {
        this.targetField = targetField;
        this.target = target;
        sharedObj = sharedView.dataType();
        operationType = sharedView.operationType();
        priority = sharedView.priority();
    }


    public BindHandler(Method method, Object target, SharedMethod sharedMethod) {
        this.method = method;
        this.target = target;
        sharedObj = sharedMethod.dataType();
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

    public SharedObj getSharedObj() {
        return sharedObj;
    }

    public void setSharedObj(SharedObj sharedObj) {
        this.sharedObj = sharedObj;
    }
}
