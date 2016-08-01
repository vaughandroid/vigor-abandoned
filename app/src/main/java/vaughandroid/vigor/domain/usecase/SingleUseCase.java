package vaughandroid.vigor.domain.usecase;

import rx.Single;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public abstract class SingleUseCase<T> extends UseCase {

    public SingleUseCase(SchedulingPolicy schedulingPolicy) {
        super(schedulingPolicy);
    }

    public abstract Single<T> createSingle();
}
