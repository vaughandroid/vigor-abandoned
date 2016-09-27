package vaughandroid.vigor.domain.usecase;

import rx.Single;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * Base class for {@link UseCase}s which return a {@link Single}
 *
 * @author Chris
 */
public abstract class SingleUseCase<T> extends UseCase {

  public SingleUseCase(SchedulingPolicy schedulingPolicy) {
    super(schedulingPolicy);
  }

  public Single<T> getSingle() {
    return create().compose(schedulingPolicy.singleTransformer());
  }

  protected abstract Single<T> create();
}
