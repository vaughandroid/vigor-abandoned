/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */

package vaughandroid.vigor.app.widgets;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import vaughandroid.vigor.R;

/**
 * @author Chris
 */
public class NumberInputView extends LinearLayout {

    public interface InputListener {
        void onIncrementClicked();
        void onDecrementClicked();
        void onValueEntered(String s);
    }

    @BindView(R.id.view_number_input_EditText_value) EditText valueEditText;
    @BindView(R.id.view_number_input_TextView_units) TextView unitsTextView;
    @BindView(R.id.view_number_input_Button_less) Button lessButton;
    @BindView(R.id.view_number_input_Button_more) Button moreButton;

    @Nullable
    private InputListener inputListener;

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
                valueEditText.setHint(typedArray.getString(R.styleable.NumberInputView_label));
            } finally {
                typedArray.recycle();
            }
        }
    }

    public void setValue(@NonNull BigDecimal value) {
        valueEditText.setText(value.toPlainString());
    }

    @SuppressLint("SetTextI18n")
    public void setValue(int value) {
        valueEditText.setText(Integer.toString(value));
    }

    public void setUnits(@NonNull String units) {
        unitsTextView.setText(units);
    }

    public void setUnitsShown(boolean shown) {
        unitsTextView.setVisibility(shown ? GONE : VISIBLE);
    }

    public void setMoreEnabled(boolean enabled) {
        moreButton.setEnabled(enabled);
    }

    public void setLessEnabled(boolean enabled) {
        lessButton.setEnabled(enabled);
    }

    public void setInputListener(@Nullable InputListener inputListener) {
        this.inputListener = inputListener;
    }

    @OnClick(R.id.view_number_input_Button_less)
    void onClickLess() {
        if (inputListener != null) {
            inputListener.onDecrementClicked();
        }
    }

    @OnClick(R.id.view_number_input_Button_more)
    void onClickMore() {
        if (inputListener != null) {
            inputListener.onIncrementClicked();
        }
    }

    @OnTextChanged(R.id.view_number_input_EditText_value)
    void onValueTextChanged() {
        if (inputListener != null) {
            inputListener.onValueEntered(valueEditText.getText().toString());
        }
    }
}
