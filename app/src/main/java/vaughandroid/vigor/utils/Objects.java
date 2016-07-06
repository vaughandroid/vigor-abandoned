package vaughandroid.vigor.utils;

import android.support.annotation.Nullable;

/**
 * Utility methods for working with {@link Object}s
 *
 * @author chris.vaughan@laterooms.com
 */
public class Objects {

    /**
     * Check two objects are equal. They are considered equal if they are both null.
     *
     * @param a first object, may be null
     * @param b second object, may be null
     * @return true if the objects are equal, or are both null
     */
    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    private Objects() {
        throw new AssertionError("No instances.");
    }
}
