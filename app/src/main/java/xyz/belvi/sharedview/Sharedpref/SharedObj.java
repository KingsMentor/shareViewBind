package xyz.belvi.sharedview.Sharedpref;

import java.util.HashSet;

/**
 * Created by zone2 on 9/21/16.
 */

public enum SharedObj {

    STRING(""), INT(0), FLOAT(0.0f), BOOLEAN(false), LONG(0l), SET(new HashSet<>());

    private Object defValue;

    SharedObj(Object defValue) {
        this.defValue = defValue;
    }

    public Object getDefValue() {
        return this.defValue;
    }

    public void setDefValue(Object defValue) {
        this.defValue = defValue;
    }
}
