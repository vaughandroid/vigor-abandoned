package vaughandroid.vigor.domain.usecase;

import rx.Completable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * Base class for {@link UseCase}s which return a {@link Completable}
 *
 * @author Chris
 */
public abstract class CompletableUseCase extends UseCase {

  public CompletableUseCase(SchedulingPolicy schedulingPolicy) {
    super(schedulingPolicy);
  }

  public Completable perform() {
    return createCompletable().compose(schedulingPolicy.completableTransformer());
  }

  protected abstract Completable createCompletable();
}
