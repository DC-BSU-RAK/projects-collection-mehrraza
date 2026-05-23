package com.example.gymapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.card.MaterialCardView;

public class WorkoutsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workouts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] workoutNames = {
                "Upper Body Power", "Lower Body Strength", "Full Body HIIT",
                "Core & Abs", "Back & Biceps", "Chest & Triceps"
        };

        int[] cardIds = {
                R.id.card_w1, R.id.card_w2, R.id.card_w3,
                R.id.card_w4, R.id.card_w5, R.id.card_w6
        };

        for (int i = 0; i < cardIds.length; i++) {
            final String name = workoutNames[i];
            MaterialCardView card = view.findViewById(cardIds[i]);
            if (card != null) {
                card.setOnClickListener(v -> {
                    WorkoutDetailDialogFragment dialog = WorkoutDetailDialogFragment.newInstance(name);
                    dialog.show(getParentFragmentManager(), "WorkoutDetail");
                });
            }
        }
    }
}
