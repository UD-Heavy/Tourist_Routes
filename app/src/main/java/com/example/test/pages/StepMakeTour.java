package com.example.test.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;

public class StepMakeTour extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<MyData> categories_list;
    MyRvAdapter categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        categories_list = new ArrayList<>();
        categories_list.add(new MyData("Природа", R.drawable.priroda));
        categories_list.add(new MyData("Музеи", R.drawable.muzei));
        categories_list.add(new MyData("Культура", R.drawable.kultura));
        categories_list.add(new MyData("Еда", R.drawable.eda));
        categories_list.add(new MyData("Спорт", R.drawable.sport));
        categories_list.add(new MyData("Развлечения", R.drawable.entertainment));
        categories_list.add(new MyData("Объекты\nкультуры", R.drawable.obj_cult));
        categories_list.add(new MyData("Ночные", R.drawable.night_club));

        rv = findViewById(R.id.categories);
        categories = new MyRvAdapter(categories_list, R.layout.item_categories);
        rv.setLayoutManager(new GridLayoutManager(this, 4)); // 2 колонки в сетке
        rv.setAdapter(categories);

    }
    // Универсальный адаптер для всех элементов (визуал для эффекта кликов)
    public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
        private ArrayList<MyData> dataList;
        private int layoutId;

        public MyRvAdapter(ArrayList<MyData> dataList, int layoutId) {
            this.dataList = dataList;
            this.layoutId = layoutId;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder holder, int position) {
            MyData data = dataList.get(position);
            holder.itemTitle.setText(data.getTitle());

            // Обработчик клика на изображении всех разделов получаем инфу про изображение, на которое нажали
            //можно будет по ID изображения переключаться между активностями
            holder.itemImage.setImageResource(data.getImageResId());
            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked on image: " + data.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Логика инициализации
        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView itemTitle;
            ImageView itemImage;
            ImageView favoriteButton;
            boolean isFavorite;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                itemTitle = itemView.findViewById(R.id.item_title);
                itemImage = itemView.findViewById(R.id.item_image);
                favoriteButton = itemView.findViewById(R.id.favorite_button);
                isFavorite = false;
            }
        }
    }
}
