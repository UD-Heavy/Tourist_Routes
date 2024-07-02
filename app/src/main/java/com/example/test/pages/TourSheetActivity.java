package com.example.test.pages;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TourSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // объявление объектов
        setContentView(R.layout.tour_window);
        View bottomSheet = findViewById(R.id.sheet);
        LinearLayout layout = findViewById(R.id.baseinfo);

        // Настройка ViewTreeObserver для получения высоты макета после его рендеринга
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Удаление слушателя, чтобы он не вызывался снова
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Получение высоты макета
                int height = layout.getMeasuredHeight();

                // Получение BottomSheetBehavior из View
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

                // Установка состояния и высоты
                bottomSheetBehavior.setPeekHeight(height);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setHideable(true); // Позволяет скрывать BottomSheet полностью
            }
        });
    }
}
