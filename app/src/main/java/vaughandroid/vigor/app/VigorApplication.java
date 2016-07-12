package vaughandroid.vigor.app;

import android.app.Application;

import vaughandroid.vigor.app.di.ApplicationComponent;
import vaughandroid.vigor.app.di.ApplicationComponentSource;
import vaughandroid.vigor.app.di.DaggerApplicationComponent;

/**
 * Custom {@link Application} class.
 *
 * @author Chris
 */
public class VigorApplication extends Application implements ApplicationComponentSource {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();
    }

    @Override
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
