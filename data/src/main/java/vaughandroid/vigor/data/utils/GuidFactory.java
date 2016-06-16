package vaughandroid.vigor.data.utils;

import javax.inject.Inject;

import vaughandroid.vigor.domain.exercise.ExerciseId;

/**
 * TODO: javadoc
 *
 * @author Chris
 */
public class GuidFactory {

    @Inject
    public GuidFactory() {}

    public ExerciseId newGuid() {
        return ExerciseId.create("" + Math.random()); // TODO: 15/06/2016 implement proper GUID creation
    }
}
