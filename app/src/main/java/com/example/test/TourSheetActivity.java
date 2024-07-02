package com.example.test;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TourSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // объявление объектов
        setContentView(R.layout.tour_window);
        View bottomSheet = findViewById(R.id.sheet);

        // Получение BottomSheetBehavior из View
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Начальное состояние BottomSheet и макс. высота
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setPeekHeight(0);
    }
}