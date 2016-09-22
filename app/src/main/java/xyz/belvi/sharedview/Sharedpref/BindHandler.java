package xyz.belvi.sharedview.Sharedpref;

import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by zone2 on 9/21/16.
 */

public class BindHandler {
    private View view;
    private SharedObj sharedObj;
    private Method method;
    private Class target;


    public BindHandler(View view, SharedObj sharedObj) {
        this.view = view;
        this.sharedObj = sharedObj;
    }


    public BindHandler(Method method, Class target, SharedObj sharedObj) {
        this.method = method;
        this.target = target;
        this.sharedObj = sharedObj;
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

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public SharedObj getSharedObj() {
        return this.sharedObj;
    }

    public void setSharedObj(SharedObj sharedObj) {
        this.sharedObj = sharedObj;
    }
}
