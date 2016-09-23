package xyz.belvi.sharedview.Sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zone2 on 9/21/16.
 */

public class SharedBind extends Validator implements SharedPreferences.OnSharedPreferenceChangeListener {

    HashMap<String, ArrayList<BindHandler>> fieldBindHandlerHashMap = new HashMap();
    HashMap<String, ArrayList<BindHandler>> methodBindHandlerHashMap = new HashMap();

    public void shareView(Object target, Context context) {

        Class<?> obj = target.getClass();
        for (Method method : obj.getDeclaredMethods()) {

            // if method is annotated with @Test
            if (method.isAnnotationPresent(SharedMethod.class)) {


                Annotation annotation = method.getAnnotation(SharedMethod.class);
                SharedMethod sharedMethod = (SharedMethod) annotation;
                ArrayList<BindHandler> methodBindHandlers = methodBindHandlerHashMap.get(sharedMethod.key());
                if (methodBindHandlers == null)
                    methodBindHandlers = new ArrayList<>();

                methodBindHandlers.add(new BindHandler(method, target, sharedMethod));

                methodBindHandlerHashMap.put(sharedMethod.key(), methodBindHandlers);
            }

        }
        for (Field field : obj.getDeclaredFields()) {
            if (field.isAnnotationPresent(SharedField.class)) {
                Annotation annotation = field.getAnnotation(SharedField.class);
                SharedField sharedView = (SharedField) annotation;
                ArrayList<BindHandler> fieldBindHandlers = fieldBindHandlerHashMap.get(sharedView.key());
                if (fieldBindHandlers == null)
                    fieldBindHandlers = new ArrayList<>();
                fieldBindHandlers.add(new BindHandler(field, target, sharedView));
                fieldBindHandlerHashMap.put(sharedView.key(), fieldBindHandlers);
                Log.e("done", "mdf");

                Log.e("result", field.getGenericType().toString());

            }
        }
    }

    public void putString(SharedPreferences preferences, String key, String value) {
        preferences.registerOnSharedPreferenceChangeListener(this);
        preferences.edit().putString(key, value).commit();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.e("load", s);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);


        ArrayList<BindHandler> viewBinds = fieldBindHandlerHashMap.get(s);
        if (viewBinds != null) {
            for (BindHandler bindHandler : viewBinds) {
                bindHandler.getTargetField().setAccessible(true);
                if (bindHandler.getSharedObj() == SharedObj.STRING) {
                    try {
                        if (isView(bindHandler.getTargetField().get(bindHandler.getTarget()))) {

                            ((AppCompatTextView) (bindHandler.getTargetField().get(bindHandler.getTarget()))).setText(sharedPreferences.getString(s, bindHandler.getSharedObj().getDefValue().toString()));
                        } else {
                            Log.e("value", "" + bindHandler.getTargetField().getInt(bindHandler.getTarget()));
                            bindHandler.getTargetField().setInt(bindHandler.getTarget(), 234);
                            Log.e("jksj","K");
//                            bindHandler.getTargetField().set(bindHandler.getTarget(), sharedPreferences.getString(s, bindHandler.getSharedObj().getDefValue().toString()));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        ArrayList<BindHandler> methodBinds = methodBindHandlerHashMap.get(s);
        if (methodBinds != null) {
            for (BindHandler bindHandler : methodBinds) {
                try {
                    bindHandler.getMethod().invoke(bindHandler.getTarget());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
