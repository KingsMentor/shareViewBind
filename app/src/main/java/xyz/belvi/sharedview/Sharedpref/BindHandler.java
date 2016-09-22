package xyz.belvi.sharedview.Sharedpref;

import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by zone2 on 9/21/16.
 */

public class BindHandler {
    private Object targetObj;
    private OperationType operationType;
    private SharedObj sharedObj;
    private Method method;
    private Class target;


    public BindHandler(Object targetObj, SharedView sharedView) {
        this.targetObj = targetObj;
        this.sharedObj = sharedView.dataType();
        this.operationType = sharedView.operationType();
    }


    public BindHandler(Method method, Class target, SharedMethod sharedView) {
        this.method = method;
        this.target = target;
        this.sharedObj = sharedView.dataType();
        this.operationType = sharedView.operationType();
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getTarget() {
        return this.target;
    }

    public void setTarget(Class target) {
        this.target = target;
    }

    public Object getTargetObj() {
        return this.targetObj;
    }

    public void setTargetObj(View view) {
        this.targetObj = view;
    }

    public SharedObj getSharedObj() {
        return this.sharedObj;
    }

    public void setSharedObj(SharedObj sharedObj) {
        this.sharedObj = sharedObj;
    }
}
