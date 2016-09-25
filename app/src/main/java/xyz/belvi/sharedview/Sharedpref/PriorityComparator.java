package xyz.belvi.sharedview.Sharedpref;

import java.util.Comparator;

/**
 * Created by zone2 on 9/25/16.
 */

public class PriorityComparator implements Comparator<BindHandler> {
    @Override
    public int compare(BindHandler bindHandler, BindHandler t1) {
        if (bindHandler.getPriority() > t1.getPriority()) {
            return -1;
        } else if (bindHandler.getPriority() == t1.getPriority()) {
            return 0;
        }
        return 1;
    }
}
