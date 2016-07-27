package vaughandroid.vigor.app.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;
import vaughandroid.vigor.domain.usecase.UseCase;

/**
 * Base class for MVP Presenters.
 *
 * @author Chris
 */
public abstract class BasePresenter<View> implements Presenter<View> {

    private final Observable.Transformer<Object, Object> domainTransformer;
    private final Observable.Transformer<Object, Object> uiTransformer;

    @Nullable private View view;

    protected BasePresenter(ActivityLifecycleProvider activityLifecycleProvider,
            SchedulingPolicy domainSchedulingPolicy) {
        domainTransformer = objectObservable -> objectObservable
                .compose(domainSchedulingPolicy.apply())
                .compose(activityLifecycleProvider.bindToLifecycle());
        uiTransformer = objectObservable -> objectObservable
                .compose(activityLifecycleProvider.bindToLifecycle());

        activityLifecycleProvider.lifecycle()
                .filter(event -> event == ActivityEvent.DESTROY)
                .subscribe(event -> {
                    BasePresenter.this.view = null;
                });
    }

    @Override
    public void setView(@NonNull View view) {
        this.view = view;
        initView(view);
    }

    @Override @Nullable
    public View getView() {
        return view;
    }

    protected abstract void initView(@NonNull View view);

    /**
     * Apply default policies for working with {@link UseCase} {@link Observable}s.
     * <p/>
     * Subscription is on the UI thread, observation is on an IO thread.
     * The observable will be unsubscribed during the corresponding lifecycle event.
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> Observable.Transformer<T, T> useCaseTransformer() {
        return (Observable.Transformer<T, T>) domainTransformer;
    }

    /**
     * Apply default policies for working with UI {@link Observable}s.
     * <p/>
     * The observable will be unsubscribed during the corresponding lifecycle event.
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> Observable.Transformer<T, T> uiTransformer() {
        return (Observable.Transformer<T, T>) uiTransformer;
    }
}
