package vaughandroid.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Helper methods for asserting conditions.
 *
 * @author Chris
 */
public class Assertions {

  private Assertions() {
    throw new AssertionError("No instances!");
  }

  public static void checkNotNull(@Nullable Object object) {
    if (object == null) {
      throw new NullPointerException();
    }
  }

  public static void checkNotNull(@Nullable Object object, @NonNull String message, @Nullable Object... args) {
    if (object == null) {
      throw new NullPointerException(String.format(message, args));
    }
  }
}
