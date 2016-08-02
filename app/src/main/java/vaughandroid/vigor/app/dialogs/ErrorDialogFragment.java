package vaughandroid.vigor.app.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import vaughandroid.vigor.R;

/**
 * Dialog for showing errors.
 * <p/>
 * The hosting {@link Activity} must implement the {@link Listener} interface.
 *
 * @author Chris
 */
public class ErrorDialogFragment extends AppCompatDialogFragment {

  public interface Listener {
    void onErrorDialogDismissed(@NonNull ErrorDialogFragment dialog);
  }

  private static final String KEY_TITLE = "title";
  private static final String KEY_MESSAGE = "message";

  public static ErrorDialogFragment create() {
    return new ErrorDialogFragment();
  }

  public static ErrorDialogFragment create(String title, String message) {
    ErrorDialogFragment fragment = create();

    Bundle args = new Bundle();
    args.putString(KEY_TITLE, title);
    args.putString(KEY_MESSAGE, message);
    fragment.setArguments(args);

    return fragment;
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(getTitle());
    builder.setMessage(getMessage());
    builder.setCancelable(false);
    builder.setPositiveButton(R.string.ok, (dialog, buttonId) -> {
      dialog.dismiss();
      ((Listener) getActivity()).onErrorDialogDismissed(this);
    });
    return builder.create();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof Listener)) {
      throw new ClassCastException("Host Activity must implement the Listener interface");
    }
  }

  private String getTitle() {
    Bundle args = getArguments();
    return args.containsKey(KEY_TITLE) ? args.getString(KEY_TITLE) : getString(R.string.error_general_title);
  }

  private String getMessage() {
    Bundle args = getArguments();
    return args.containsKey(KEY_MESSAGE) ? args.getString(KEY_MESSAGE) : getString(R.string.error_general_message);
  }
}
