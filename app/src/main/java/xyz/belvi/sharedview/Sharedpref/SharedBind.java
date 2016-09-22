package xyz.belvi.sharedview.Sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zone2 on 9/21/16.
 */

public class SharedBind implements SharedPreferences.OnSharedPreferenceChangeListener {

    HashMap<String, ArrayList<BindHandler>> viewBindHandlerHashMap = new HashMap();
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

                methodBindHandlers.add(new BindHandler(method, obj, sharedMethod.dataType()));

                methodBindHandlerHashMap.put(sharedMethod.key(), methodBindHandlers);
            }

        }
        for (Field field : obj.getDeclaredFields()) {
            if (field.isAnnotationPresent(SharedView.class)) {
                Annotation annotation = field.getAnnotation(SharedView.class);
                SharedView sharedView = (SharedView) annotation;

                try {
                    ArrayList<BindHandler> viewBindHandlers = methodBindHandlerHashMap.get(sharedView.key());
                    if (viewBindHandlers == null)
                        viewBindHandlers = new ArrayList<>();
                    viewBindHandlers.add(new BindHandler((View) field.get(target), sharedView.dataType()));
                    viewBindHandlerHashMap.put(sharedView.key(), viewBindHandlers);
                    Log.e("done", "mdf");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e("error", "mdf");
                }
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
        ArrayList<BindHandler> methodBinds = methodBindHandlerHashMap.get(s);
        if (methodBinds != null) {
            for (BindHandler bindHandler : methodBinds) {
                try {
                    bindHandler.getMethod().invoke(bindHandler.getTarget().newInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList<BindHandler> viewBinds = methodBindHandlerHashMap.get(s);
        if (viewBinds != null) {
            for (BindHandler bindHandler : viewBinds) {
                try {
                    bindHandler.getMethod().invoke(bindHandler.getTarget().newInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
