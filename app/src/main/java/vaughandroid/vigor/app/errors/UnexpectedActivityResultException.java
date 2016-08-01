package vaughandroid.vigor.app.errors;

import android.content.Intent;

/**
 * Thrown when an Activity receives an unexpected or invalid result {@link Intent}
 *
 * @author Chris
 */
public class UnexpectedActivityResultException extends RuntimeException {

  public UnexpectedActivityResultException(int requestCode, int resultCode, Intent data) {
    super(String.format("Unexpected activity result, request code: %s, result code: %s, data: %s",
        requestCode, resultCode, data));
  }
}
