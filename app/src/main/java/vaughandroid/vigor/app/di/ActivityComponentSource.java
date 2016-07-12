package vaughandroid.vigor.app.di;

/**
 * Interface for objects which can supply a {@link ActivityComponent} instance.
 *
 * @author Chris
 */
public interface ActivityComponentSource {

    ActivityComponent getActivityComponent();
}
