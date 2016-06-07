package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

/**
 * Data for an exercise.
 *
 * @author Chris
 */
@AutoValue
public abstract class Exercise {

    public static Builder builder() {
        return new AutoValue_Exercise.Builder()
                .reps(0)
                .weight(BigDecimal.ZERO);
    }

    public abstract int reps();
    public abstract Exercise withReps(int reps);

    public abstract BigDecimal weight();
    public abstract Exercise withWeight(BigDecimal weight);

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder reps(int foo);
        public abstract Builder weight(BigDecimal weight);
        public abstract Exercise build();
    }
}
