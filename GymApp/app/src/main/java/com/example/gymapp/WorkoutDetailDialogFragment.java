package com.example.gymapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.button.MaterialButton;

public class WorkoutDetailDialogFragment extends DialogFragment {

    private static final String ARG_WORKOUT = "workout_name";

    public static WorkoutDetailDialogFragment newInstance(String workoutName) {
        WorkoutDetailDialogFragment f = new WorkoutDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORKOUT, workoutName);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_workout_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String workoutName = getArguments() != null
                ? getArguments().getString(ARG_WORKOUT, "Workout")
                : "Workout";

        TextView title = view.findViewById(R.id.tv_workout_title);
        TextView description = view.findViewById(R.id.tv_workout_description);
        MaterialButton closeBtn = view.findViewById(R.id.btn_close_workout);

        title.setText(workoutName);
        description.setText(getWorkoutDescription(workoutName));
        closeBtn.setOnClickListener(v -> dismiss());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private String getWorkoutDescription(String name) {
        switch (name) {
            case "Strength Training":
                return "Build muscle and increase strength with compound lifts.\n\n" +
                        "• Squats: 4 sets × 8 reps\n" +
                        "• Bench Press: 4 sets × 8 reps\n" +
                        "• Deadlift: 3 sets × 6 reps\n" +
                        "• Pull-ups: 3 sets × max reps\n" +
                        "• Overhead Press: 3 sets × 10 reps\n\n" +
                        "Rest 90 seconds between sets. Stay hydrated!";
            case "Cardio Blast":
                return "Elevate your heart rate and burn calories fast.\n\n" +
                        "• Warm-up jog: 5 minutes\n" +
                        "• Sprint intervals: 8 × 30 sec on / 30 sec off\n" +
                        "• Jump rope: 3 × 2 minutes\n" +
                        "• Box jumps: 3 × 10 reps\n" +
                        "• Cool-down walk: 5 minutes\n\n" +
                        "Target heart rate: 70–85% of max HR.";
            case "Flexibility & Stretch":
                return "Improve mobility and reduce injury risk.\n\n" +
                        "• Hip flexor stretch: 60 sec each side\n" +
                        "• Hamstring stretch: 60 sec each side\n" +
                        "• Chest opener: 45 sec\n" +
                        "• Cat-cow spine: 10 reps\n" +
                        "• Pigeon pose: 90 sec each side\n\n" +
                        "Breathe deeply and never bounce a stretch.";
            default:
                return "No details available.";
        }
    }
}
