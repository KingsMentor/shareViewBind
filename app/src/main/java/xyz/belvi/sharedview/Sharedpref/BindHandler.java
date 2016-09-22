package xyz.belvi.sharedview.Sharedpref;

import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by zone2 on 9/21/16.
 */

public class BindHandler {
    private View view;
    private SharedObj sharedObj;
    private boolean isMethod, hasViewBinding;
    private Method method;
    private Class target;


    public BindHandler(boolean hasViewBinding, View view, SharedObj sharedObj) {
        this.view = view;
        this.hasViewBinding = hasViewBinding;
        this.sharedObj = sharedObj;
    }


    public BindHandler(boolean isMethod, Method method, Class target, SharedObj sharedObj) {
        this.isMethod = isMethod;
        this.method = method;
        this.target = target;
        this.sharedObj = sharedObj;
    }

    public boolean hasViewBinding() {
        return this.hasViewBinding;
    }

    public void setHasViewBinding(boolean hasViewBinding) {
        this.hasViewBinding = hasViewBinding;
    }

    public boolean isMethod() {
        return this.isMethod;
    }

    public void setMethod(boolean method) {
        this.isMethod = method;
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
