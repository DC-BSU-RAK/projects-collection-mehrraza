package com.example.gymapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Personalised greeting based on time of day
        TextView greetingText = view.findViewById(R.id.tv_greeting);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 12) greeting = "Good morning, Athlete!";
        else if (hour < 17) greeting = "Good afternoon, Athlete!";
        else greeting = "Good evening, Athlete!";
        greetingText.setText(greeting);

        // Info / About button → shows modal dialog
        MaterialButton infoBtn = view.findViewById(R.id.btn_info);
        infoBtn.setOnClickListener(v -> {
            InfoDialogFragment dialog = new InfoDialogFragment();
            dialog.show(getParentFragmentManager(), "InfoDialog");
        });

        // Quick-start workout cards
        MaterialCardView cardStrength = view.findViewById(R.id.card_strength);
        MaterialCardView cardCardio = view.findViewById(R.id.card_cardio);
        MaterialCardView cardFlexibility = view.findViewById(R.id.card_flexibility);

        cardStrength.setOnClickListener(v -> openWorkoutDetail("Strength Training"));
        cardCardio.setOnClickListener(v -> openWorkoutDetail("Cardio Blast"));
        cardFlexibility.setOnClickListener(v -> openWorkoutDetail("Flexibility & Stretch"));
    }

    private void openWorkoutDetail(String workoutName) {
        WorkoutDetailDialogFragment dialog = WorkoutDetailDialogFragment.newInstance(workoutName);
        dialog.show(getParentFragmentManager(), "WorkoutDetail");
    }
}
