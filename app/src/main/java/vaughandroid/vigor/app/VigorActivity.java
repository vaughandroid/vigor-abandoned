package vaughandroid.vigor.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vaughandroid.vigor.app.di.ActivityComponent;
import vaughandroid.vigor.app.di.ActivityComponentSource;
import vaughandroid.vigor.app.di.ApplicationComponent;
import vaughandroid.vigor.app.di.ApplicationComponentSource;

/**
 * Custom {@link Activity} base class.
 *
 * @author Chris
 */
public abstract class VigorActivity extends AppCompatActivity implements ApplicationComponentSource , ActivityComponentSource {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = getApplicationComponent()
                .activityComponent();
    }

    @Override
    public ApplicationComponent getApplicationComponent() {
        return ((ApplicationComponentSource) getApplication()).getApplicationComponent();
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
