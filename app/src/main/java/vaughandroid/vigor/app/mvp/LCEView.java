package vaughandroid.vigor.app.mvp;

/**
 * Base interface for MVP Loading-Content-Error views.
 *
 * @author Chris
 */
public interface LCEView {

    void showLoading();
    void showContent();
    void showError();
}
