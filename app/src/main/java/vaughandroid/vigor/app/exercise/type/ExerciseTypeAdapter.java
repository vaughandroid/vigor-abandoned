package vaughandroid.vigor.app.exercise.type;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.internal.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;
import vaughandroid.vigor.R;
import vaughandroid.vigor.domain.exercise.type.ExerciseType;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} for displaying {@link ExerciseType}s.
 *
 * @author Chris
 */
public class ExerciseTypeAdapter extends RecyclerView.Adapter<ExerciseTypeAdapter.ExerciseTypeViewHolder> {

    private final List<ExerciseType> exerciseTypes = new ArrayList<>();
    private final ExerciseTypeOnSubscribe exerciseTypeOnSubscribe = new ExerciseTypeOnSubscribe();
    private final Observable<ExerciseType> exerciseTypeObservable = Observable.create(exerciseTypeOnSubscribe);

    public void setExerciseTypes(@NonNull List<ExerciseType> exerciseTypes) {
        this.exerciseTypes.clear();
        this.exerciseTypes.addAll(exerciseTypes);
        notifyDataSetChanged();
    }

    public Observable<ExerciseType> exerciseTypeClickedObservable() {
        return exerciseTypeObservable;
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
            exerciseTypeOnSubscribe.onClick(exerciseType);
        }
    }

    private class ExerciseTypeOnSubscribe implements Observable.OnSubscribe<ExerciseType> {

        private final List<Subscriber<? super ExerciseType>> subscribers = new ArrayList<>();

        @Override
        public void call(Subscriber<? super ExerciseType> subscriber) {
            Preconditions.checkUiThread();
            subscribers.add(subscriber);

            subscriber.add(new MainThreadSubscription() {
                @Override
                protected void onUnsubscribe() {
                    subscribers.remove(subscriber);
                }
            });
        }

        void onClick(@NonNull ExerciseType exerciseType) {
            for (Subscriber<? super ExerciseType> subscriber : subscribers) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(exerciseType);
                }
            }
        }
    }
}
