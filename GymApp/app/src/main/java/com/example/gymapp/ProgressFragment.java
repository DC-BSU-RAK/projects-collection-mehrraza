package com.example.gymapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProgressFragment extends Fragment {

    private SharedPreferences prefs;

    private static final String KEY_WORKOUTS_DONE = "workouts_done";
    private static final String KEY_WEIGHT = "current_weight";
    private static final String KEY_GOAL_WEIGHT = "goal_weight";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());

        TextView tvWorkoutCount = view.findViewById(R.id.tv_workout_count);
        TextInputEditText etWeight = view.findViewById(R.id.et_current_weight);
        TextInputEditText etGoalWeight = view.findViewById(R.id.et_goal_weight);
        TextView tvWeightDiff = view.findViewById(R.id.tv_weight_diff);
        MaterialButton btnLogWorkout = view.findViewById(R.id.btn_log_workout);
        MaterialButton btnSaveWeight = view.findViewById(R.id.btn_save_weight);
        MaterialButton btnReset = view.findViewById(R.id.btn_reset_progress);

        // Load saved values
        int workoutsDone = prefs.getInt(KEY_WORKOUTS_DONE, 0);
        tvWorkoutCount.setText(String.valueOf(workoutsDone));

        String savedWeight = prefs.getString(KEY_WEIGHT, "");
        String savedGoal = prefs.getString(KEY_GOAL_WEIGHT, "");
        etWeight.setText(savedWeight);
        etGoalWeight.setText(savedGoal);
        updateWeightDiff(savedWeight, savedGoal, tvWeightDiff);

        // Log a workout session
        btnLogWorkout.setOnClickListener(v -> {
            int newCount = prefs.getInt(KEY_WORKOUTS_DONE, 0) + 1;
            prefs.edit().putInt(KEY_WORKOUTS_DONE, newCount).apply();
            tvWorkoutCount.setText(String.valueOf(newCount));
        });

        // Save weight data
        btnSaveWeight.setOnClickListener(v -> {
            String weight = etWeight.getText() != null ? etWeight.getText().toString().trim() : "";
            String goal = etGoalWeight.getText() != null ? etGoalWeight.getText().toString().trim() : "";
            prefs.edit()
                    .putString(KEY_WEIGHT, weight)
                    .putString(KEY_GOAL_WEIGHT, goal)
                    .apply();
            updateWeightDiff(weight, goal, tvWeightDiff);
        });

        // Reset progress
        btnReset.setOnClickListener(v -> {
            prefs.edit()
                    .putInt(KEY_WORKOUTS_DONE, 0)
                    .putString(KEY_WEIGHT, "")
                    .putString(KEY_GOAL_WEIGHT, "")
                    .apply();
            tvWorkoutCount.setText("0");
            etWeight.setText("");
            etGoalWeight.setText("");
            tvWeightDiff.setText("—");
        });
    }

    private void updateWeightDiff(String current, String goal, TextView tvDiff) {
        try {
            if (!current.isEmpty() && !goal.isEmpty()) {
                double c = Double.parseDouble(current);
                double g = Double.parseDouble(goal);
                double diff = g - c;
                if (diff > 0) {
                    tvDiff.setText(String.format("+%.1f kg to goal", diff));
                    tvDiff.setTextColor(getResources().getColor(R.color.accent_orange, null));
                } else if (diff < 0) {
                    tvDiff.setText(String.format("%.1f kg above goal", Math.abs(diff)));
                    tvDiff.setTextColor(getResources().getColor(R.color.accent_red, null));
                } else {
                    tvDiff.setText("Goal reached! 🎉");
                    tvDiff.setTextColor(getResources().getColor(R.color.accent_green, null));
                }
            } else {
                tvDiff.setText("—");
            }
        } catch (NumberFormatException e) {
            tvDiff.setText("—");
        }
    }
}
