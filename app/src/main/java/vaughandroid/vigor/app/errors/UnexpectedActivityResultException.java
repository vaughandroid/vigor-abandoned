package vaughandroid.vigor.app.errors;

import android.content.Intent;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class UnexpectedActivityResultException extends RuntimeException {

    public UnexpectedActivityResultException(int requestCode, int resultCode, Intent data) {
        super(String.format("Unexpected activity result, request code: %s, result code: %s, data: %s",
                requestCode, resultCode, data));
    }
}
