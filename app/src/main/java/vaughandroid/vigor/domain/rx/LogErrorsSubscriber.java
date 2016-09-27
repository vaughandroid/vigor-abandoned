package vaughandroid.vigor.domain.rx;

import java.util.concurrent.CancellationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Subscriber;

/**
 * Simple {@link Subscriber} which just logs errors.
 *
 * @author Chris
 */
public class LogErrorsSubscriber<T> extends Subscriber<T> {

  public static <T> LogErrorsSubscriber<T> create() {
    return new LogErrorsSubscriber<>();
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private LogErrorsSubscriber() {}

  @Override public void onCompleted() {
    // No op
  }

  @Override public void onError(Throwable t) {
    if (!(t instanceof CancellationException)) {
      logger.error("Error", t);
    }
  }

  @Override public void onNext(T o) {
    // No op
  }
}
