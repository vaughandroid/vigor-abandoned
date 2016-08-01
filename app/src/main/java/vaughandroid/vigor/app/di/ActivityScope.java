package vaughandroid.vigor.app.di;

import android.app.Activity;
import javax.inject.Scope;

/**
 * Scope for objects which share the same lifetime as a single {@link Activity}
 *
 * @author Chris
 */
@Scope public @interface ActivityScope {
}
