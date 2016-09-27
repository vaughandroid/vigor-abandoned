package vaughandroid.vigor.domain.usecase;

import rx.Observable;
import vaughandroid.vigor.domain.rx.LogErrorsSubscriber;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * Base class for {@link UseCase}s which return an {@link Observable}
 *
 * @author Chris
 */
public abstract class ObservableUseCase<T> extends UseCase {

  public ObservableUseCase(SchedulingPolicy schedulingPolicy) {
    super(schedulingPolicy);
  }

  public Observable<T> perform() {
    return createObservable().compose(schedulingPolicy.observableTransformer());
  }

  protected abstract Observable<T> createObservable();
}
