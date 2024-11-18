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
import android.widget.RatingBar;
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

/**
 * Класс, представляющий страницу объекта. Отображает информацию об объекте,
 * включая фотографии и контактные данные, а также позволяет добавлять в избранное.
 */
public class ObjectPage extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Integer> photo_list;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter photos;
    private TextView phoneNumberText;

    private boolean isFavorite = false; // Статус избранного
    private ImageButton favButton;

    /**
     * Вызывается при создании активности. Устанавливает макет, инициализирует список фотографий,
     * контактные данные и обработчики событий для избранного и копирования номера телефона.
     *
     * @param savedInstanceState объект {@link Bundle}, содержащий данные о предыдущем состоянии активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.object_page);
        View bottomSheet = findViewById(R.id.sheet);
        LinearLayout layout = findViewById(R.id.baseinfo);

        // Получаем данные из Intent
        MyData placeData = (MyData) getIntent().getSerializableExtra("place_data");

        if (placeData != null) {
            // Название объекта
            TextView titleView = findViewById(R.id.tour_label);
            titleView.setText(placeData.getTitle());

            // Адрес
            TextView addressView = findViewById(R.id.tour_label2);
            addressView.setText(placeData.getAddress());

            // Категория
            TextView categoryView = findViewById(R.id.tour_label3);
            categoryView.setText(placeData.getCategory());

            // Рейтинг в звездочках
            RatingBar ratingBar = findViewById(R.id.score);
            ratingBar.setRating((float) placeData.getRating());

            // Рейтинг в баллах
            TextView ratingValue = findViewById(R.id.ratingNumber);
            ratingValue.setText(getString(R.string.rating_format, placeData.getRating()));

            // Количество отзывов
            TextView reviewCount = findViewById(R.id.reviewCount);
            reviewCount.setText(getString(R.string.review_count_format, placeData.getReviewCount()));

            // Часы работы
            TextView workingHoursView = findViewById(R.id.tour_label4);
            workingHoursView.setText(placeData.getWorkingHours());

            // Статус открытия
            TextView openUntilView = findViewById(R.id.tour_label5);
            openUntilView.setText(placeData.getOpenUntil());

            // Телефон
            TextView contactsView = findViewById(R.id.contacts);
            contactsView.setText(placeData.getPhoneNumber());
            contactsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copyPhoneNumberToClipboard(placeData.getPhoneNumber());
                }
            });

            // Установка фонового изображения
            findViewById(android.R.id.content).setBackgroundResource(placeData.getBackgroundImageResId());

            // Дополнительные изображения
            rv = findViewById(R.id.images_view);
            if (placeData.getAdditionalImages() != null && !placeData.getAdditionalImages().isEmpty()) {
                photo_list = placeData.getAdditionalImages();
            } else {
                // Если изображений нет в JSON, используем дефолтный список
                photo_list = new ArrayList<>();
                photo_list.add(R.drawable.image1);
                photo_list.add(R.drawable.image2);
                photo_list.add(R.drawable.image3);
            }

            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            photos = new MyRvAdapter(photo_list);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(photos);

        }

        // Настройка кнопки избранного
        favButton = findViewById(R.id.fav_button);
        final Animation favAnim = AnimationUtils.loadAnimation(this, R.anim.fav_anim);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                updateFavoriteIcon();
                favButton.startAnimation(favAnim);
            }
        });

        // Настройка ViewTreeObserver для получения высоты макета после его рендеринга
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = layout.getMeasuredHeight();
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(height);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setHideable(true);
            }
        });
    }


    /**
     * Копирует номер телефона в буфер обмена и отображает уведомление.
     *
     * @param phoneNumber номер телефона для копирования
     */
    private void copyPhoneNumberToClipboard(String phoneNumber) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Phone Number", phoneNumber);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Phone number copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    /**
     * Обновляет цвет иконки избранного в зависимости от статуса.
     */
    private void updateFavoriteIcon() {
        if (isFavorite) {
            favButton.setColorFilter(Color.RED); // Заливка красным цветом
        } else {
            favButton.clearColorFilter(); // Удаление заливки
        }
    }

    /**
     * Адаптер для RecyclerView, отображающий список изображений.
     */
    class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final ArrayList<Integer> data;

        /**
         * Конструктор для создания адаптера.
         *
         * @param data список ресурсов изображений для отображения
         */
        public MyRvAdapter(ArrayList<Integer> data) {
            this.data = data;
        }

        /**
         * Создает новый ViewHolder для элемента списка изображений.
         *
         * @param parent   родительский контейнер
         * @param viewType тип представления
         * @return новый экземпляр ViewHolder
         */
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());


            View view = inflater.inflate(R.layout.item_image, parent, false);
            return new ObjectPage.MyRvAdapter.CardViewHolder(view);

        }

        /**
         * Связывает данные изображения с ViewHolder.
         * Если позиция действительна, устанавливает изображение в ImageView.
         *
         * @param holder   ViewHolder для элемента списка
         * @param position позиция элемента в списке
         */
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position < data.size()) {
                Integer currentItem1 = data.get(position);
                if (holder instanceof MyRvAdapter.CardViewHolder) {
                    ((MyRvAdapter.CardViewHolder) holder).bind(currentItem1);
                }
            }
        }

        /**
         * Возвращает количество элементов в списке изображений.
         *
         * @return размер списка данных
         */
        @Override
        public int getItemCount() {
            return data.size();
        }



        /**
         * ViewHolder для отображения отдельного изображения.
         */
        class CardViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;

            /**
             * Конструктор для создания ViewHolder.
             *
             * @param itemView объект {@link View}, представляющий элемент изображения
             */
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.imageView);
            }

            /**
             * Привязывает изображение к ImageView.
             *
             * @param item ресурс изображения для отображения
             */
            public void bind(Integer item) {
                ivImage.setImageResource(item);
            }
        }


    }


}
