package vaughandroid.vigor.app.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Base MVP presenter interface.
 *
 * @author Chris
 */
public interface Presenter<T> {

  @Nullable T getView();

  void setView(@NonNull T view);
}
