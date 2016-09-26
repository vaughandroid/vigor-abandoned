package vaughandroid.vigor.domain.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import vaughandroid.vigor.domain.rx.SchedulingPolicy;

/**
 * Base interface for all use cases.
 *
 * @author Chris
 */
public abstract class UseCase {

  protected final SchedulingPolicy schedulingPolicy;

  protected UseCase(SchedulingPolicy schedulingPolicy) {
    this.schedulingPolicy = schedulingPolicy;
  }
}
