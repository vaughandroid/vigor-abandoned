package vaughandroid.vigor.app.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import vaughandroid.vigor.R;

/**
 * {@link android.support.v7.widget.RecyclerView.ItemDecoration} for drawing a simple divider line
 * between items.
 *
 * @author Chris
 */
public class HorizontalDividerLineItemDecoration extends RecyclerView.ItemDecoration {

  private final Drawable mDivider;

  public HorizontalDividerLineItemDecoration(Context context) {
    mDivider = ContextCompat.getDrawable(context, R.drawable.divider_line);
  }

  @Override public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
    int left = parent.getPaddingLeft();
    int right = parent.getWidth() - parent.getPaddingRight();

    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);

      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      int top = child.getBottom() + params.bottomMargin;
      int bottom = top + mDivider.getIntrinsicHeight();

      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(c);
    }
  }
}
