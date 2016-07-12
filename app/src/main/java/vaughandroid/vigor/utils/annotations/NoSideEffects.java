package vaughandroid.vigor.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For methods which are pure functions. i.e. They do not depend on or update any state.
 *
 * @author Chris
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NoSideEffects {
}
