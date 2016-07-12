package vaughandroid.vigor.utils;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static vaughandroid.vigor.utils.Strings.format;

/**
 * Simple preconditions.
 *
 * @author Chris
 */
public final class Preconditions {

    public static void checkArgument(boolean assertion, @NonNull String message, @Nullable Object... args) {
        if (!assertion) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static void checkState(boolean assertion, @NonNull String message, Object... args) {
        if (!assertion) {
            throw new IllegalStateException(format(message, args));
        }
    }

    public static <T> T checkNotNull(T value, @NonNull String message, Object... args) {
        if (value == null) {
            throw new NullPointerException(format(message, args));
        }
        return value;
    }

    public static void checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    format("Must be called from the UI thread. Was: {}", Thread.currentThread()));
        }
    }

    private Preconditions() {
        throw new AssertionError("No instances.");
    }
}
