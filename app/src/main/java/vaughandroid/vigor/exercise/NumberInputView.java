/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */

package vaughandroid.vigor.exercise;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import vaughandroid.vigor.R;

/**
 * @author Chris
 */
public class NumberInputView extends LinearLayout {

    @BindView(R.id.view_number_input_Button_less) Button mLessButton;
    @BindView(R.id.view_number_input_EditText) EditText mEditText;
    @BindView(R.id.view_number_input_Button_more) Button mMoreButton;

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
                mEditText.setHint(typedArray.getString(R.styleable.NumberInputView_label));
            } finally {
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
