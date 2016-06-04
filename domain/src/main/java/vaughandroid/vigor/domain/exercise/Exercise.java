package vaughandroid.vigor.domain.exercise;

import com.google.auto.value.AutoValue;

/**
 * Data for an exercise.
 *
 * @author chris.vaughan@laterooms.com
 */
@AutoValue
public abstract class Exercise {

    public static Builder builder() {
        // TODO: can specify default properties here
        return new AutoValue_Exercise.Builder();
    }

    public abstract int foo();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder foo(int foo);
        public abstract Exercise build();
    }
}
