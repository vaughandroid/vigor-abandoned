package vaughandroid.vigor.domain.exercise.type;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ComparisonChain;

import java.io.Serializable;
import java.util.Comparator;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
@AutoValue
public abstract class ExerciseType implements Serializable {

    public static final ExerciseType UNSET = create(ExerciseTypeId.UNASSIGNED, "");

    public static ExerciseType create(ExerciseTypeId id, String name) {
        return new AutoValue_ExerciseType(id, name);
    }

    @NonNull
    public abstract ExerciseTypeId id();
    public abstract ExerciseType withId(@NonNull ExerciseTypeId id);

    @NonNull
    public abstract String name();
    public String nameLowercase() {
        return name().toLowerCase();
    }

    /**
     * Orders {@link ExerciseType}s by {@link ExerciseType#name()}
     */
    public static class NameComparator implements Comparator<ExerciseType> {

        @Override
        public int compare(ExerciseType o1, ExerciseType o2) {
            return ComparisonChain.start()
                    .compare(o1.name(), o2.name())
                    .result();
        }
    }
}