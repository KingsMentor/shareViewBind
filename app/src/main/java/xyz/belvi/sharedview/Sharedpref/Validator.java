package xyz.belvi.sharedview.Sharedpref;

import android.content.SharedPreferences;
import android.util.Base64;
import android.view.View;

/**
 * Created by zone2 on 9/22/16.
 */

public class Validator {

    public boolean isView(Object target) {
        return target instanceof View;
    }

    public boolean isSharedCandidate(Class objectClass) {
        return objectClass == String.class || objectClass == Integer.class || objectClass == Long.class
                || objectClass == Float.class || objectClass == Boolean.class || objectClass == Byte.class;
    }


    public void putByteArray(SharedPreferences sharedPreferences, String key, byte[] b) {
        sharedPreferences.edit().putString(key, Base64.encodeToString(b, Base64.DEFAULT)).commit();
    }

    public byte[] getByteArray(SharedPreferences sharedPreferences, String key, byte b[]) {
        String value = sharedPreferences.getString(key, Base64.encodeToString(b, Base64.DEFAULT));
        return Base64.decode(value, Base64.DEFAULT);
    }

    public Object getValue(Class objectClass, SharedPreferences sharedPreferences, String s, String defaultValue) {

        if (isSharedCandidate(objectClass)) {
            if (objectClass == String.class) {
                return sharedPreferences.getString(s, defaultValue);
            } else if (objectClass == Byte.class) {
                return getByteArray(sharedPreferences, s, defaultValue.getBytes());
            } else if (objectClass == Float.class) {
                return sharedPreferences.getFloat(s, Float.parseFloat(defaultValue));
            } else if (objectClass == Boolean.class) {
                return sharedPreferences.getBoolean(s, Boolean.valueOf(defaultValue));
            } else if (objectClass == Integer.class) {
                return sharedPreferences.getInt(s, Integer.valueOf(defaultValue));
            } else if (objectClass == Long.class) {
                return sharedPreferences.getLong(s, Long.valueOf(defaultValue));
            }
        }
        try {
            throw new ShareBindException(String.valueOf(objectClass) + " is not a candidate of a sharedPreference Object");
        } catch (ShareBindException e) {
            e.printStackTrace();
        }

        return null;
    }
}
