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


    public BindHandler(Field targetField, Object target, SharedField sharedView) {
        this.targetField = targetField;
        this.target = target;
        this.sharedObj = sharedView.dataType();
        this.operationType = sharedView.operationType();
    }


    public BindHandler(Method method, Object target, SharedMethod sharedView) {
        this.method = method;
        this.target = target;
        this.sharedObj = sharedView.dataType();
        this.operationType = sharedView.operationType();
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Field getTargetField() {
        return this.targetField;
    }

    public void setTargetField(Field targetField) {
        this.targetField = targetField;
    }

    public SharedObj getSharedObj() {
        return this.sharedObj;
    }

    public void setSharedObj(SharedObj sharedObj) {
        this.sharedObj = sharedObj;
    }
}
