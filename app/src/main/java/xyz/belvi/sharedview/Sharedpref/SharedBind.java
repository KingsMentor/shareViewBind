package xyz.belvi.sharedview.Sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zone2 on 9/21/16.
 */

public class SharedBind implements SharedPreferences.OnSharedPreferenceChangeListener {

    HashMap<String, BindHandler> bindHandlerHashMap = new HashMap();

    public void shareView(Object target, Context context) {

        Class<?> obj = target.getClass();
        for (Method method : obj.getDeclaredMethods()) {

            // if method is annotated with @Test
            if (method.isAnnotationPresent(SharedMethod.class)) {

                Annotation annotation = method.getAnnotation(SharedMethod.class);
                SharedMethod sharedMethod = (SharedMethod) annotation;

                BindHandler bindHandler = new BindHandler(true, method, obj, sharedMethod.dataType());
                // if enabled = true (default)
                bindHandlerHashMap.put(sharedMethod.key(), bindHandler);
            }

        }
        for (Field field : obj.getDeclaredFields()) {
            if (field.isAnnotationPresent(SharedView.class)) {
                Annotation annotation = field.getAnnotation(SharedView.class);
                SharedView sharedView = (SharedView) annotation;

                try {
                    BindHandler bindHandler = bindHandlerHashMap.get(sharedView.key());
                    if (bindHandler == null)
                        bindHandler = new BindHandler(true, (View) field.get(target), sharedView.dataType());
                    else {
                        bindHandler.setHasViewBinding(true);
                        bindHandler.setView((View) field.get(target));
                        bindHandler.setSharedObj(sharedView.dataType());
                    }
                    bindHandlerHashMap.put(sharedView.key(), bindHandler);
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
        BindHandler viewBind = bindHandlerHashMap.get(s);
        if (viewBind != null) {
            if (viewBind.isMethod()) {
                try {

                    viewBind.getMethod().invoke(viewBind.getTarget().newInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            if (viewBind.hasViewBinding()) {
                if (viewBind.getSharedObj() == SharedObj.STRING) {
                    ((AppCompatTextView) (bindHandlerHashMap.get(s).getView())).setText(sharedPreferences.getString(s, viewBind.getSharedObj().getDefValue().toString()));
                }
            }

        }
    }
}
