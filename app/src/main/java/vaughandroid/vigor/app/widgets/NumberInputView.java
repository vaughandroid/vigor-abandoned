/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */

package vaughandroid.vigor.app.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import com.google.common.base.Objects;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vaughandroid.vigor.R;

/**
 * @author Chris
 */
public class NumberInputView extends LinearLayout {
  // TODO: 19/06/2016 There's likely a way to simplify the EditText interactions

  @BindView(R.id.view_number_input_TextInputLayout_value) TextInputLayout valueTextInputLayout;
  @BindView(R.id.view_number_input_EditText_value) EditText valueEditText;
  @BindView(R.id.view_number_input_TextView_units) TextView unitsTextView;
  @BindView(R.id.view_number_input_Button_less) Button lessButton;
  @BindView(R.id.view_number_input_Button_more) Button moreButton;
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Nullable private BigDecimal value;
  @Nullable private BigDecimal lastNotifiedValue;
  @Nullable private ValueChangedListener valueChangedListener;

  public NumberInputView(Context context) {
    super(context);
    init(context, null, 0, 0);
  }

  public NumberInputView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0, 0);
  }

  public NumberInputView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs, defStyle, 0);
  }

  @TargetApi(21)
  public NumberInputView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
    super(context, attrs, defStyle, defStyleRes);
    init(context, attrs, defStyle, defStyleRes);
  }

  private void init(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
    inflate(context, R.layout.view_number_input, this);
    ButterKnife.bind(this);
    setOrientation(HORIZONTAL);

    if (attrs != null) {
      TypedArray typedArray =
          context.obtainStyledAttributes(attrs, R.styleable.NumberInputView, defStyle, defStyleRes);
      try {
        valueTextInputLayout.setHint(typedArray.getString(R.styleable.NumberInputView_label));
        boolean showUnits = typedArray.getBoolean(R.styleable.NumberInputView_showUnits, false);
        unitsTextView.setVisibility(showUnits ? VISIBLE : GONE);
      } finally {
        typedArray.recycle();
      }
    }
  }

  @Nullable public BigDecimal getValue() {
    return value;
  }

  @Nullable public Integer getIntValue() {
    return value != null ? value.intValue() : null;
  }

  public void setValue(@Nullable Integer valueInt) {
    BigDecimal value = valueInt != null ? BigDecimal.valueOf(valueInt) : null;
    setValueInternal(value, true);
  }

  @NonNull public BigDecimal getValueOrZero() {
    return value != null ? value : BigDecimal.ZERO;
  }

  public void setValue(@Nullable BigDecimal value) {
    setValueInternal(value, true);
  }

  private void setValueInternal(@Nullable BigDecimal value, boolean notifyIfChanged) {
    if (!Objects.equal(this.value, value)) {
      this.value = value;
      updateValueEditText(value);
      if (notifyIfChanged) {
        notifyValueChangedListenerIfChanged();
      }
    }
  }

  private void updateValueEditText(@Nullable BigDecimal value) {
    String text = value != null ? value.toPlainString() : "";
    if (!Objects.equal(text, valueEditText.getText().toString())) {
      valueEditText.setText(text);
      valueEditText.setSelection(text.length());
    }
  }

  private void notifyValueChangedListenerIfChanged() {
    if (valueChangedListener != null && !Objects.equal(value, lastNotifiedValue)) {
      lastNotifiedValue = value;
      valueChangedListener.onValueChanged(value);
    }
  }

  public void setUnits(@NonNull String units) {
    unitsTextView.setText(units);
  }

  public void setUnitsShown(boolean shown) {
    unitsTextView.setVisibility(shown ? GONE : VISIBLE);
  }

  public void setValueChangedListener(@Nullable ValueChangedListener valueChangedListener) {
    this.valueChangedListener = valueChangedListener;
  }

  @OnClick(R.id.view_number_input_Button_less) void onClickLess() {
    setValueInternal(getValueOrZero().subtract(BigDecimal.ONE), true);
  }

  @OnClick(R.id.view_number_input_Button_more) void onClickMore() {
    setValueInternal(getValueOrZero().add(BigDecimal.ONE), true);
  }

  @OnTextChanged(R.id.view_number_input_EditText_value) void onValueTextChanged() {
    String valueString = valueEditText.getText().toString();
    try {
      BigDecimal value = new BigDecimal(valueString);
      setValueInternal(value, false);
      notifyValueChangedListenerIfChanged();
    } catch (NumberFormatException e) {
      logger.warn("Failed to parse value '{}'", valueString);
    }
  }

  @OnFocusChange(R.id.view_number_input_EditText_value) void onValueFocusChanged(boolean hasFocus) {
    if (!hasFocus) {
      // If the value in the field is invalid, then revert to the last valid value.
      updateValueEditText(value);
    }
  }

  public interface ValueChangedListener {
    void onValueChanged(BigDecimal newValue);
  }
}
