package vaughandroid.vigor.domain.usecase;

import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public abstract class ObservableUseCase<T> extends UseCase {

  public ObservableUseCase(SchedulingPolicy schedulingPolicy) {
    super(schedulingPolicy);
  }

  public abstract Observable<T> createObservable();
}
