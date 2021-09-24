package com.example.schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class ScheduleFragment extends Fragment {
    ImageView imageViewDown, imageViewUp;
    Button buttonMon, buttonTue, buttonWed, buttonThu, buttonFri;
    Switch aSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_schedule);

        imageViewDown = view.findViewById(R.id.imageViewUp);
        imageViewUp = view.findViewById(R.id.imageViewDown);
        aSwitch = view.findViewById(R.id.switchUpDown);

        buttonMon = view.findViewById(R.id.buttonMon);
        buttonTue = view.findViewById(R.id.buttonTue);
        buttonWed = view.findViewById(R.id.buttonWed);
        buttonThu = view.findViewById(R.id.buttonThu);
        buttonFri = view.findViewById(R.id.buttonFri);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    imageViewDown.setColorFilter(getContext().getResources().getColor(R.color.white));
                    imageViewUp.setColorFilter(getContext().getResources().getColor(R.color.teal_200));
                } else {
                    imageViewDown.setColorFilter(getContext().getResources().getColor(R.color.teal_200));
                    imageViewUp.setColorFilter(getContext().getResources().getColor(R.color.white));
                }
            }
        });
        return view;
    }
}
