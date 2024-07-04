package com.example.test.pages;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TourSheetActivity extends AppCompatActivity {
    private boolean isFavorite = false; // Статус избранного
    private ImageButton favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // объявление объектов
        setContentView(R.layout.tour_window);
        View bottomSheet = findViewById(R.id.sheet);
        LinearLayout layout = findViewById(R.id.baseinfo);
        favButton = findViewById(R.id.fav_button);
        final Animation favAnim = AnimationUtils.loadAnimation(this, R.anim.fav_anim);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite; // Переключение статуса избранного
                updateFavoriteIcon();
                favButton.startAnimation(favAnim);

            }
        });



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
    private void updateFavoriteIcon() {
        if (isFavorite) {
            favButton.setColorFilter(Color.RED); // Заливка красным цветом
        } else {
            favButton.clearColorFilter(); // Удаление заливки
        }
    }
}
