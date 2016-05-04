/*
 * Copyright (c) 2016 Christopher John Vaughan. All rights reserved.
 */

package vaughandroid.vigor.widgets;

import java.math.BigDecimal;

/**
 * @author Chris
 */
public interface NumberInputContract {

    interface View {
        void setPresenter(Presenter presenter);

        void setValue(BigDecimal value);
        void setUnits(String units);
        void setUnitsShown(boolean shown);
        void setMoreEnabled(boolean enabled);
        void setLessEnabled(boolean enabled);
    }

    interface Presenter {
        void setView(View view);

        void onValueEntered(String valueString);
        void onDecrementClicked();
        void onIncrementClicked();
    }
}
