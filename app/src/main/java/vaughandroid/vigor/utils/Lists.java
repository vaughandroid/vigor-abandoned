package vaughandroid.vigor.utils;

import android.support.annotation.NonNull;

import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.List;

import vaughandroid.vigor.utils.annotations.NoSideEffects;

/**
 * Utility methods for working with {@link List}s.
 *
 * @author Chris
 */
public class Lists {

    /**
     *
     * @param list
     * @param predicate
     * @param <T>
     * @return
     */
    @NoSideEffects
    public static <T> List<T> filterList(@NonNull List<T> list, @NonNull Predicate<T> predicate) {
        Preconditions.checkNotNull(list, "list == null");
        Preconditions.checkNotNull(predicate, "predicate == null");

        List<T> filteredList = new ArrayList<>();
        for (T item : list) {
            if (predicate.apply(item)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    private Lists() {
        throw new AssertionError("No instances");
    }
}
