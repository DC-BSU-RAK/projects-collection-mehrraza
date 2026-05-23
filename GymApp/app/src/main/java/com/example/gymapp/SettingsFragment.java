package com.example.gymapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsFragment extends Fragment {

    private SharedPreferences prefs;

    // Preference keys
    private static final String KEY_USERNAME = "pref_username";
    private static final String KEY_FITNESS_LEVEL = "pref_fitness_level";
    private static final String KEY_WEEKLY_GOAL = "pref_weekly_goal";
    private static final String KEY_NOTIFICATIONS = "pref_notifications";
    private static final String KEY_UNIT = "pref_unit";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());

        TextInputEditText etUsername = view.findViewById(R.id.et_username);
        RadioGroup rgFitnessLevel = view.findViewById(R.id.rg_fitness_level);
        RadioButton rbBeginner = view.findViewById(R.id.rb_beginner);
        RadioButton rbIntermediate = view.findViewById(R.id.rb_intermediate);
        RadioButton rbAdvanced = view.findViewById(R.id.rb_advanced);
        Slider sliderWeeklyGoal = view.findViewById(R.id.slider_weekly_goal);
        TextView tvWeeklyGoalValue = view.findViewById(R.id.tv_weekly_goal_value);
        Switch switchNotifications = view.findViewById(R.id.switch_notifications);
        RadioGroup rgUnit = view.findViewById(R.id.rg_unit);
        RadioButton rbKg = view.findViewById(R.id.rb_kg);
        RadioButton rbLbs = view.findViewById(R.id.rb_lbs);
        MaterialButton btnSave = view.findViewById(R.id.btn_save_settings);
        TextView tvSavedMsg = view.findViewById(R.id.tv_saved_msg);

        // --- Load saved preferences ---
        etUsername.setText(prefs.getString(KEY_USERNAME, ""));

        String level = prefs.getString(KEY_FITNESS_LEVEL, "beginner");
        switch (level) {
            case "intermediate": rbIntermediate.setChecked(true); break;
            case "advanced": rbAdvanced.setChecked(true); break;
            default: rbBeginner.setChecked(true); break;
        }

        int weeklyGoal = prefs.getInt(KEY_WEEKLY_GOAL, 3);
        sliderWeeklyGoal.setValue(weeklyGoal);
        tvWeeklyGoalValue.setText(weeklyGoal + " days/week");

        switchNotifications.setChecked(prefs.getBoolean(KEY_NOTIFICATIONS, true));

        String unit = prefs.getString(KEY_UNIT, "kg");
        if ("lbs".equals(unit)) rbLbs.setChecked(true);
        else rbKg.setChecked(true);

        // Live update slider label
        sliderWeeklyGoal.addOnChangeListener((slider, value, fromUser) ->
                tvWeeklyGoalValue.setText((int) value + " days/week"));

        // --- Save button ---
        btnSave.setOnClickListener(v -> {
            String username = etUsername.getText() != null
                    ? etUsername.getText().toString().trim() : "";

            int selectedLevel = rgFitnessLevel.getCheckedRadioButtonId();
            String fitnessLevel = "beginner";
            if (selectedLevel == R.id.rb_intermediate) fitnessLevel = "intermediate";
            else if (selectedLevel == R.id.rb_advanced) fitnessLevel = "advanced";

            int goal = (int) sliderWeeklyGoal.getValue();
            boolean notifications = switchNotifications.isChecked();

            String unitPref = rgUnit.getCheckedRadioButtonId() == R.id.rb_lbs ? "lbs" : "kg";

            prefs.edit()
                    .putString(KEY_USERNAME, username)
                    .putString(KEY_FITNESS_LEVEL, fitnessLevel)
                    .putInt(KEY_WEEKLY_GOAL, goal)
                    .putBoolean(KEY_NOTIFICATIONS, notifications)
                    .putString(KEY_UNIT, unitPref)
                    .apply();

            tvSavedMsg.setVisibility(View.VISIBLE);
            view.postDelayed(() -> tvSavedMsg.setVisibility(View.GONE), 2000);
        });
    }
}
