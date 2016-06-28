package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vaughandroid.vigor.R;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} for displaying {@link ExerciseType}s.
 *
 * @author Chris
 */
public class ExerciseTypeAdapter extends RecyclerView.Adapter<ExerciseTypeAdapter.ExerciseTypeViewHolder> {

    public interface UserInteractionListener {

        void onItemClicked(@NonNull ExerciseType type);
    }

    @NonNull private final List<ExerciseType> exerciseTypes = new ArrayList<>();

    @Nullable private UserInteractionListener userInteractionListener;

    public void setExerciseTypes(@NonNull List<ExerciseType> exerciseTypes) {
        this.exerciseTypes.clear();
        this.exerciseTypes.addAll(exerciseTypes);
    }

    public void setUserInteractionListener(@Nullable UserInteractionListener userInteractionListener) {
        this.userInteractionListener = userInteractionListener;
    }

    @Override
    public ExerciseTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise_type, parent, false);
        return new ExerciseTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseTypeViewHolder holder, int position) {
        holder.setExerciseType(exerciseTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return exerciseTypes.size();
    }

    public class ExerciseTypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_exercise_type_TextView) TextView textView;

        @SuppressWarnings("NullableProblems")
        @NonNull ExerciseType exerciseType;

        public ExerciseTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setExerciseType(@NonNull ExerciseType type) {
            this.exerciseType = type;
            textView.setText(type.name());
        }

        @OnClick(R.id.item_exercise_type_TextView)
        void onClick() {
            if (userInteractionListener != null) {
                userInteractionListener.onItemClicked(exerciseType);
            }
        }
    }
}
