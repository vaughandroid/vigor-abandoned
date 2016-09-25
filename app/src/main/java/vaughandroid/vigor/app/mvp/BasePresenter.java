package vaughandroid.vigor.app.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for MVP Presenters.
 *
 * @author Chris
 */
public abstract class BasePresenter<View> {

  @NonNull protected final ActivityLifecycleProvider activityLifecycleProvider;
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private View view;

  protected BasePresenter(@NonNull ActivityLifecycleProvider activityLifecycleProvider) {
    this.activityLifecycleProvider = activityLifecycleProvider;
  }

  @NonNull protected View getView() {
    if (view == null) {
      throw new IllegalStateException("view not initialised");
    }
    return view;
  }

  protected void setView(@NonNull View view) {
    this.view = view;
  }
}
