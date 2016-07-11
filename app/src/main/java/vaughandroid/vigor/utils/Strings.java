package vaughandroid.vigor.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Utilities for working with {@link String}s.
 *
 * @author Chris
 */
public class Strings {

    /**
     * Format a string with optional arguments. The arguments replace any <em>{}</em> tokens in the String, in order.
     * If the number of arguments exceeds the number of tokens, any extra arguments are appended to the end of the
     * output String.
     *
     * @param string
     * @param args optional arguments
     * @return formatted string
     */
    public static String format(@NonNull String string, @Nullable Object... args) {
        StringBuilder sb = new StringBuilder(string.length());
        int stringIdx = 0;
        int i = 0;
        while (i < args.length) {
            int tokenIdx = string.indexOf("{}", stringIdx);
            if (tokenIdx == -1) {
                break;
            }

            sb.append(string, stringIdx, tokenIdx - stringIdx);
            sb.append(args[i++]);
            stringIdx = tokenIdx + 2;
        }

        sb.append(string, stringIdx, string.length() - 1);

        if (i < args.length) {
            sb.append('[');
            for (; i < args.length; i++) {
                sb.append(args[i]);
                sb.append(',');
            }
            sb.setCharAt(sb.length() - 1, ']');
        }

        return sb.toString();
    }

    private Strings() {
        throw new AssertionError("No instances.");
    }
}
