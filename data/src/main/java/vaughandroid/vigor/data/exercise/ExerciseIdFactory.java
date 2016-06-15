package vaughandroid.vigor.data.exercise;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.ExerciseId;

/**
 * TODO: javadoc
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseIdFactory {

    @Inject
    public ExerciseIdFactory() {}

    public ExerciseId newId() {
        return ExerciseId.create("" + Math.random()); // TODO: 15/06/2016 implement proper GUID creation
    }
}
