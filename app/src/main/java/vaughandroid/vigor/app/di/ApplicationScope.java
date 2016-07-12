package vaughandroid.vigor.app.di;

import android.app.Application;

import javax.inject.Scope;

/**
 * Scope for objects which share the same lifetime as the {@link Application}
 *
 * @author Chris
 */
@Scope
public @interface ApplicationScope {
}
