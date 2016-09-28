package xyz.belvi.sharedview.Sharedpref;

import android.content.SharedPreferences;
import android.view.View;

import java.util.Set;

/**
 * Created by zone2 on 9/22/16.
 */

public class Validator {

    public boolean isView(Object target) {
        return target instanceof View;
    }

    private boolean isSharedCandidate(Class objectClass) {
        return objectClass == String.class || objectClass == Integer.class || objectClass == Long.class
                || objectClass == Float.class || objectClass == Boolean.class || objectClass == Byte.class
                || objectClass == Set.class;
    }


    public Object getValue(Class objectClass, SharedPreferences sharedPreferences, String s) {

        if()
        return null;
    }
}
