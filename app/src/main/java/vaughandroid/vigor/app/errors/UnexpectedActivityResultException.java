package vaughandroid.vigor.app.errors;

import android.content.Intent;

import vaughandroid.vigor.utils.Strings;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class UnexpectedActivityResultException extends RuntimeException {

    public UnexpectedActivityResultException(int requestCode, int resultCode, Intent data) {
        super(Strings.format("Unexpected activity result, request code: {}, result code: {}, data: {}",
                requestCode, resultCode, data));
    }
}
