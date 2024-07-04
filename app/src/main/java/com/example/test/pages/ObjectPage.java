package com.example.test.pages;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class ObjectPage extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Integer> photo_list;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter photos;
    private TextView phoneNumberText;

    private boolean isFavorite = false; // Статус избранного
    private ImageButton favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        photo_list = new ArrayList<>();
        photo_list.add(R.drawable.image1);
        photo_list.add(R.drawable.image2);
        photo_list.add(R.drawable.image3);
        photo_list.add(R.drawable.image1);
        photo_list.add(R.drawable.image2);

        // объявление объектов
        setContentView(R.layout.object_page);
        View bottomSheet = findViewById(R.id.sheet);
        LinearLayout layout = findViewById(R.id.baseinfo);

        rv = findViewById(R.id.images_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        photos = new MyRvAdapter(photo_list);

        phoneNumberText = findViewById(R.id.contacts);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(photos);

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

        String phoneNumber = "+7 999 999 99 99"; // Replace with actual database retrieval
        phoneNumberText.setText(phoneNumber);

        // Set click listener to copy phone number on click
        phoneNumberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyPhoneNumberToClipboard(phoneNumber);
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

    private void copyPhoneNumberToClipboard(String phoneNumber) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Phone Number", phoneNumber);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Phone number copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            favButton.setColorFilter(Color.RED); // Заливка красным цветом
        } else {
            favButton.clearColorFilter(); // Удаление заливки
        }
    }

    class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final ArrayList<Integer> data;

        public MyRvAdapter(ArrayList<Integer> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());


            View view = inflater.inflate(R.layout.item_image, parent, false);
            return new ObjectPage.MyRvAdapter.CardViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position < data.size()) {
                Integer currentItem1 = data.get(position);
                if (holder instanceof MyRvAdapter.CardViewHolder) {
                    ((MyRvAdapter.CardViewHolder) holder).bind(currentItem1);
                }
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }



        // ViewHolder для карточки без кнопки
        class CardViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.imageView);
            }

            public void bind(Integer item) {
                ivImage.setImageResource(item);
            }
        }


    }


}
