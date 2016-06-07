package vaughandroid.vigor.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vaughandroid.vigor.app.di.ActivityComponent;
import vaughandroid.vigor.app.di.ActivityComponentSource;
import vaughandroid.vigor.app.di.ApplicationComponentSource;

/**
 * Custom {@link Activity} base class.
 *
 * @author Chris
 */
public abstract class VigorActivity extends AppCompatActivity implements ActivityComponentSource {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = ((ApplicationComponentSource) getApplication()).getApplicationComponent()
                .activityComponent();
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
