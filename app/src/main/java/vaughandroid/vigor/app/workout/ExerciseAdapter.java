package vaughandroid.vigor.app.workout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vaughandroid.vigor.R;
import vaughandroid.vigor.domain.exercise.Exercise;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} for displaying {@link Exercise}s.
 *
 * @author chris.vaughan@laterooms.com
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    public interface UserInteractionListener {

        void onItemClicked(@NonNull Exercise exercise);
    }

    @NonNull
    private final List<Exercise> exercises = new ArrayList<>();

    @Nullable
    private UserInteractionListener userInteractionListener;

    public void setExercises(@NonNull Collection<Exercise> exercises) {
        this.exercises.clear();
        this.exercises.addAll(exercises);
    }

    /**
     * @param userInteractionListener to be notified of user interactions
     */
    public void setUserInteractionListener(@Nullable UserInteractionListener userInteractionListener) {
        this.userInteractionListener = userInteractionListener;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        holder.setExercise(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_exercise_TextView_name) TextView nameTextView;
        @BindView(R.id.item_exercise_TextView_values) TextView valuesTextView;

        @NonNull private Exercise exercise;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setExercise(@NonNull Exercise exercise) {
            this.exercise = exercise;
            nameTextView.setText(exercise.id().toString());
            Context context = valuesTextView.getContext();
            String valuesText = context.getString(R.string.exercise_list_item_values_weight_and_reps, exercise.weight(),
                    "Kg", exercise.reps()); // XXX weight units
            valuesTextView.setText(valuesText);
        }

        @OnClick(R.id.item_exercise_ViewGroup_container)
        void onClickItem() {
            if (userInteractionListener != null) {
                userInteractionListener.onItemClicked(exercise);
            }
        }
    }
}
