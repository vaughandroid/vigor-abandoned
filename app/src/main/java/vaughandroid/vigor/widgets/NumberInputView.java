/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */

package vaughandroid.vigor.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
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
public class NumberInputView extends LinearLayout implements NumberInputContract.View {

    @BindView(R.id.view_number_input_EditText_value) EditText mValueEditText;
    @BindView(R.id.view_number_input_TextView_units) TextView mUnitsTextView;
    @BindView(R.id.view_number_input_Button_less) Button mLessButton;
    @BindView(R.id.view_number_input_Button_more) Button mMoreButton;

    @Nullable private NumberInputContract.Presenter mPresenter;

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
                mValueEditText.setHint(typedArray.getString(R.styleable.NumberInputView_label));
            } finally {
                typedArray.recycle();
            }
        }
    }

    @Override
    public void setPresenter(NumberInputContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setView(this);
    }

    @Override
    public void setValue(BigDecimal value) {
        mValueEditText.setText(value.toPlainString());
    }

    @Override
    public void setUnits(String units) {
        mUnitsTextView.setText(units);
    }

    @Override
    public void setUnitsShown(boolean shown) {
        mUnitsTextView.setVisibility(shown ? GONE : VISIBLE);
    }

    @Override
    public void setMoreEnabled(boolean enabled) {
        mMoreButton.setEnabled(enabled);
    }

    @Override
    public void setLessEnabled(boolean enabled) {
        mLessButton.setEnabled(enabled);
    }

    @OnClick(R.id.view_number_input_Button_less)
    void onClickLess() {
        if (mPresenter != null) {
            mPresenter.onDecrementClicked();
        }
    }

    @OnClick(R.id.view_number_input_Button_more)
    void onClickMore() {
        if (mPresenter != null) {
            mPresenter.onIncrementClicked();
        }
    }

    @OnTextChanged(R.id.view_number_input_EditText_value)
    void onValueTextChanged() {
        if (mPresenter != null) {
            mPresenter.onValueEntered(mValueEditText.getText().toString());
        }
    }
}
