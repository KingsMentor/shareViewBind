package xyz.belvi.sharedview.Sharedpref;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zone2 on 9/21/16.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SharedField {

    int priority() default 0;

    OperationType operationType() default OperationType.CHANGE_TEXT;

    String key() default "default";


}

