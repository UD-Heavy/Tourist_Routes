package com.example.test.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;

public class MainMenuPage extends Fragment {
    RecyclerView rv, rv1;
    ArrayList<MyData> dataSource;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    MainMenuPage.MyRvAdapter adapterRV_tour, adapterRV_news;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.tours_view);
        rv1 = view.findViewById(R.id.news_view);

        dataSource = new ArrayList<>();
        dataSource.add(new MyData(getResources().getString(R.string.first_card), R.drawable.kurgan_ts));
        dataSource.add(new MyData(getResources().getString(R.string.second_card), R.drawable.dk_mashin));

        linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        adapterRV_tour = new MyRvAdapter(dataSource);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapterRV_tour);

        adapterRV_news = new MyRvAdapter(dataSource);
        rv1.setLayoutManager(linearLayoutManager1);
        rv1.setAdapter(adapterRV_news);

        view.findViewById(R.id.kurgan_logo).setOnClickListener(this::onClickProfile);
    }


    public void onClickProfile(View v) {
        ProfilePage profilePage = new ProfilePage();
        ((MainActivity) getActivity()).replaceFragment(profilePage);
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
                ivImage.setImageResource(item.getImageResId());
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
}