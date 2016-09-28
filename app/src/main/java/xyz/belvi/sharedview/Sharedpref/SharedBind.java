package xyz.belvi.sharedview.Sharedpref;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zone2 on 9/21/16.
 */

public class SharedBind extends Validator implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    HashMap<String, ArrayList<BindHandler>> bindHandlerHashMap = new HashMap();


    ArrayList<SharedPreferences> preferenceTracker = new ArrayList<>();

    public void bind(SharedPreferences... preferences) {
        for (SharedPreferences preference : preferences)
            if (!preferenceTracker.contains(preference)) {
                preference.registerOnSharedPreferenceChangeListener(this);
                preferenceTracker.add(preference);
            }
    }


    public void unBind(SharedPreferences preferences) {
        if (!preferenceTracker.contains(preferences)) {
            preferences.unregisterOnSharedPreferenceChangeListener(this);
            preferenceTracker.remove(preferences);
        }
    }

    public void unBindAll() {
        for (SharedPreferences preferences : preferenceTracker)
            preferences.registerOnSharedPreferenceChangeListener(this);
        preferenceTracker.clear();
    }

    public void shareView(Object target) {
        Class<?> obj = target.getClass();
        addFields(target, obj);
        addMethods(target, obj);

    }


    private void addFields(Object target, Class<?> obj) {

        for (Field field : obj.getDeclaredFields()) {
            if (field.isAnnotationPresent(SharedField.class)) {
                Annotation annotation = field.getAnnotation(SharedField.class);
                SharedField sharedView = (SharedField) annotation;
                ArrayList<BindHandler> fieldBindHandlers = bindHandlerHashMap.get(sharedView.key());
                if (fieldBindHandlers == null)
                    fieldBindHandlers = new ArrayList<>();
                fieldBindHandlers.add(new BindHandler(field, target, sharedView));
                bindHandlerHashMap.put(sharedView.key(), fieldBindHandlers);
                Log.e("done", "mdf");

                Log.e("result", field.getGenericType().toString());

            }
        }
    }

    private void addMethods(Object target, Class<?> obj) {
        for (Method method : obj.getDeclaredMethods()) {

            // if method is annotated with @Test
            if (method.isAnnotationPresent(SharedMethod.class)) {


                Annotation annotation = method.getAnnotation(SharedMethod.class);
                SharedMethod sharedMethod = (SharedMethod) annotation;
                ArrayList<BindHandler> methodBindHandlers = bindHandlerHashMap.get(sharedMethod.key());
                if (methodBindHandlers == null)
                    methodBindHandlers = new ArrayList<>();

                methodBindHandlers.add(new BindHandler(method, target, sharedMethod));

                bindHandlerHashMap.put(sharedMethod.key(), methodBindHandlers);
            }

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.e("load", s);
        ArrayList<BindHandler> viewBinds = bindHandlerHashMap.get(s);
        if (viewBinds != null) {
            Collections.sort(viewBinds, new PriorityComparator());
            for (BindHandler bindHandler : viewBinds) {
                if (bindHandler.getMethod() == null) {
                    processField(bindHandler, sharedPreferences, s);
                } else {
                    processMethods(bindHandler, sharedPreferences, s);
                }
            }
        }


    }


    private void processField(BindHandler bindHandler, SharedPreferences sharedPreferences, String s) {

        try {
            bindHandler.getTargetField().setAccessible(true);
            if (isView(bindHandler.getTargetField().get(bindHandler.getTarget()))) {
                viewOperation(bindHandler, sharedPreferences, s);
            } else {
                bindHandler.getTargetField().set(bindHandler.getTarget(), sharedPreferences.getString(s, bindHandler.getDefaultValue()));
//                bindHandler.getTargetField().set(bindHandler.getTarget(), 3);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void viewOperation(BindHandler bindHandler, SharedPreferences sharedPreferences, String s) throws IllegalAccessException {
        if (bindHandler.getOperationType() == OperationType.CHANGE_TEXT) {
            if (bindHandler.getTargetField().get(bindHandler.getTarget()) instanceof TextView)
                ((TextView) (bindHandler.getTargetField().get(bindHandler.getTarget()))).setText(String.valueOf(getValue(bindHandler.getClassType(), sharedPreferences, s, bindHandler.getDefaultValue())));
        } else if (bindHandler.getOperationType() == OperationType.CHECK) {
            ((CompoundButton) (bindHandler.getTargetField().get(bindHandler.getTarget()))).setChecked(false);
        }

    }

    private void processMethods(BindHandler bindHandler, SharedPreferences sharedPreferences, String s) {
        try {
            bindHandler.getMethod().invoke(bindHandler.getTarget());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}
