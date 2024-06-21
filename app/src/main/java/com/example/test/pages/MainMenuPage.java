package com.example.test.pages;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;

public class MainMenuPage extends AppCompatActivity {
    RecyclerView rv, rv1;
    ArrayList<MyData> dataSource;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    MainMenuPage.MyRvAdapter adapterRV_tour, adapterRV_news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu_page);

        rv = findViewById(R.id.tours_view);
        rv1 = findViewById(R.id.news_view);

        dataSource = new ArrayList<>();
        dataSource.add(new MyData(getResources().getString(R.string.first_card), R.drawable.kurgan_ts));
        dataSource.add(new MyData(getResources().getString(R.string.second_card), R.drawable.dk_mashin));

        linearLayoutManager = new LinearLayoutManager(MainMenuPage.this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager1 = new LinearLayoutManager(MainMenuPage.this, LinearLayoutManager.HORIZONTAL, false);

        adapterRV_tour = new MyRvAdapter(dataSource);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapterRV_tour);

        adapterRV_news = new MyRvAdapter(dataSource);
        rv1.setLayoutManager(linearLayoutManager1);
        rv1.setAdapter(adapterRV_news);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_CARD = 0;
        private static final int TYPE_CARD_WITH_BUTTON = 1;
        private final ArrayList<MyData> data;

        public MyRvAdapter(ArrayList<MyData> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (viewType == TYPE_CARD) {
                View view = inflater.inflate(R.layout.list_item_main_menu_card, parent, false);
                return new CardViewHolder(view);
            } else {
                View view = inflater.inflate(R.layout.list_item_main_menu_button, parent, false);
                return new CardWithButtonViewHolder(view);

            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position < data.size()) {
                MyData currentItem1 = data.get(position);
                if (holder instanceof CardViewHolder) {
                    ((CardViewHolder) holder).bind(currentItem1);
                }
            }
            /*MyData currentItem1 = data.get(position);
            if (holder instanceof CardViewHolder) {
                ((CardViewHolder) holder).bind(currentItem1);
            }*/
        }

        @Override
        public int getItemCount() {
            return data.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            // Определите, является ли элемент последним
            if (position != data.size()) {
                return TYPE_CARD;
            } else {
                return TYPE_CARD_WITH_BUTTON;
            }
            //return position == data.size() - 1 ? TYPE_CARD_WITH_BUTTON : TYPE_CARD;
        }

        // ViewHolder для карточки без кнопки
        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            ImageView ivImage;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.text_view);
                ivImage = itemView.findViewById(R.id.image_view);
            }

            public void bind(MyData item) {
                tvTitle.setText(item.getTitle());
                ivImage.setImageResource(item.getImageResource());
            }
        }

        // ViewHolder для карточки с кнопкой
        class CardWithButtonViewHolder extends RecyclerView.ViewHolder {

            Button button;

            public CardWithButtonViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button_more_news);
                // Слушатель для кнопки
                button.setOnClickListener(v -> {
                    // Обработка нажатия кнопки
                });
            }
        }
    }

//    public static class MyData {
//        private final String title;
//        private final int imageResource;
//
//        public MyData(String title, int imageResource) {
//            this.title = title;
//            this.imageResource = imageResource;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public int getImageResource() {
//            return imageResource;
//        }
//    }
}