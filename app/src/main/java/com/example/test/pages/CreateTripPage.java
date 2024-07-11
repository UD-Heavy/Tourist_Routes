package com.example.test.pages;

import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;

public class CreateTripPage extends Fragment {

    RecyclerView rv, rv1;
    ArrayList<MyData> popularDataList, categoriesDataList, ownTripDataList;

    MyRvAdapter adapterRV_popular, adapterRV_categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_trip_page, container, false);
        super.onCreate(savedInstanceState);

        rv = view.findViewById(R.id.popular_view);
        rv1 = view.findViewById(R.id.categories_view);

        // соединение с бд, получаем список объектов
        // ArrayList<Document> DBList = dbRepositories.getAll();
        // for (MyData data : DBList){
        //      dataSourse.add(data.get("title"), convertBase64ToImage(data.get("image")))
        // }
        popularDataList = new ArrayList<>();
        popularDataList.add(new MyData(getStringResourceByName("chas"), R.drawable.kurgan_ts));
        popularDataList.add(new MyData(getStringResourceByName("aleksandra_nevskogo"), R.drawable.kurgan_ts));

        categoriesDataList = new ArrayList<>();
        categoriesDataList.add(new MyData("Природа", R.drawable.priroda));
        categoriesDataList.add(new MyData("Музеи", R.drawable.muzei));
        categoriesDataList.add(new MyData("Культура", R.drawable.kultura));
        categoriesDataList.add(new MyData("Еда", R.drawable.eda));
        categoriesDataList.add(new MyData("Спорт", R.drawable.sport));
        categoriesDataList.add(new MyData("Развлечения", R.drawable.entertainment));
        categoriesDataList.add(new MyData("Объекты\nкультуры", R.drawable.obj_cult));
        categoriesDataList.add(new MyData("Ночные", R.drawable.night_club));


        // Настройка разметки для карточек (влияет только на визуал)
        adapterRV_popular = new MyRvAdapter(popularDataList, R.layout.item_popular);
        adapterRV_categories = new MyRvAdapter(categoriesDataList, R.layout.item_categories);

        rv.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 колонки в сетке
        rv1.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        rv.setAdapter(adapterRV_popular);
        rv1.setAdapter(adapterRV_categories);
        // Добавление отступов (values/dimens.xml), только с ним регало нормальные отступы (только визуал)
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_between_items);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = spacingInPixels;
                outRect.right = spacingInPixels;
            }
        };
        rv.addItemDecoration(itemDecoration);

        return view;
    }

    private String getStringResourceByName(String aString) {
        String packageName = requireContext().getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
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
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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





            // Отдельный обработчик для сердечка
            if (holder.favoriteButton != null) {
                // Устанавливаем начальное изображение в зависимости от состояния
                holder.favoriteButton.setImageResource(holder.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                // Слушаем нажатие и меняем иконку
                holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.isFavorite = !holder.isFavorite;
                        holder.favoriteButton.setImageResource(holder.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                        Toast.makeText(v.getContext(), holder.isFavorite ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                });
            }
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
