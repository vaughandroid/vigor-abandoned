package vaughandroid.vigor.app.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

/**
 * Base class for MVP Presenters.
 *
 * @author Chris
 */
public abstract class BasePresenter<View> implements Presenter<View> {

  @NonNull protected final ActivityLifecycleProvider activityLifecycleProvider;

  @Nullable private View view;

  protected BasePresenter(@NonNull ActivityLifecycleProvider activityLifecycleProvider) {
    this.activityLifecycleProvider = activityLifecycleProvider;

    activityLifecycleProvider.lifecycle()
        .filter(event -> event == ActivityEvent.DESTROY)
        .subscribe(event -> {
          BasePresenter.this.view = null;
        });
  }

  @Override @Nullable public View getView() {
    return view;
  }

  @Override public void setView(@NonNull View view) {
    this.view = view;
    initView(view);
  }

  protected abstract void initView(@NonNull View view);
}
