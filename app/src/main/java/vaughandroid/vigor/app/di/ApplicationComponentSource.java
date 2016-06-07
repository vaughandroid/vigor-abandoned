package vaughandroid.vigor.app.di;

/**
 * Interface for objects which can supply the {@link ApplicationComponent} instance.
 *
 * @author Chris
 */
public interface ApplicationComponentSource {

    ApplicationComponent getApplicationComponent();
}
