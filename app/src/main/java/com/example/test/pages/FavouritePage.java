package com.example.test.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;

public class FavouritePage extends Fragment {

    RecyclerView rv;
    ArrayList<MyData> news_list;
    LinearLayoutManager linearLayoutManager;

    MyRvAdapter news;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = view.findViewById(R.id.recycler_view);

        // соединение с бд, получаем список объектов
        // ArrayList<Document> DBList = dbRepositories.getAll();
        // for (MyData data : DBList){
        //      dataSourse.add(data.get("title"), convertBase64ToImage(data.get("image")))
        // }
        news_list = new ArrayList<>();
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.forgot_pass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.forgot_pass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.forgot_pass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.forgot_pass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.forgot_pass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.forgot_pass), R.drawable.aleksandra_nevskogo));

        linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        news = new MyRvAdapter(news_list);
        linearLayoutManager.isAutoMeasureEnabled();
        rv.setLayoutManager(linearLayoutManager);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(news);

        view.findViewById(R.id.kurgan_logo).setOnClickListener(this::onClickProfile);
    }

    public void onClickProfile(View v) {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_favorite_navbar_to_profilePage);    }
    class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final ArrayList<MyData> data;

        public MyRvAdapter(ArrayList<MyData> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());


            View view = inflater.inflate(R.layout.list_item_main_menu_card, parent, false);
            return new MyRvAdapter.CardViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position < data.size()) {
                MyData currentItem1 = data.get(position);
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


    }
}