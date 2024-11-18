package com.example.test.pages;

import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.TypeReference;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Фрагмент, представляющий страницу создания путешествия.
 * Содержит три секции для популярных мест, категорий и пользовательских маршрутов.
 */
public class CreateTripPage extends Fragment {

    RecyclerView rv, rv1, rv2;
    ArrayList<MyData> popularDataList, categoriesDataList, ownTripDataList;

    MyRvAdapter adapterRV_popular, adapterRV_categories, adapterRV_own_trip;

    /**
     * Создает и возвращает иерархию представлений, связанную с этим фрагментом.
     *
     * @param inflater           объект {@link LayoutInflater} для создания представлений фрагмента
     * @param container          родительский контейнер, к которому будет добавлено представление фрагмента
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     * @return корневое представление для данного фрагмента
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_trip_page, container, false);
        super.onCreate(savedInstanceState);

        rv = view.findViewById(R.id.popular_view);
        rv1 = view.findViewById(R.id.categories_view);
        rv2 = view.findViewById(R.id.own_trip_view);

        // Вызываем метод инициализации вместо прямого создания списка
        initializePopularData();

        // соединение с бд, получаем список объектов
        // ArrayList<Document> DBList = dbRepositories.getAll();
        // for (MyData data : DBList){
        //      dataSourse.add(data.get("title"), convertBase64ToImage(data.get("image")))
        // }

        /*popularDataList = new ArrayList<>();
        popularDataList.add(new MyData(getStringResourceByName("chas"), R.drawable.chas));
        popularDataList.add(new MyData(getStringResourceByName("aleksandra_nevskogo"), R.drawable.aleksandra_nevskogo));*/

        initializePopularData();


        categoriesDataList = new ArrayList<>();
        categoriesDataList.add(new MyData("Природа", R.drawable.priroda));
        categoriesDataList.add(new MyData("Музеи", R.drawable.muzei));
        categoriesDataList.add(new MyData("Культура", R.drawable.kultura));
        categoriesDataList.add(new MyData("Еда", R.drawable.eda));

        ownTripDataList = new ArrayList<>();
        ownTripDataList.add(new MyData("Создать маршрут", R.drawable.own_trip));


        // Настройка разметки для карточек (влияет только на визуал)
        // Создание адаптеров для каждого RecyclerView
        adapterRV_popular = new MyRvAdapter(popularDataList, R.layout.item_popular, true); // true для "Популярное"
        adapterRV_categories = new MyRvAdapter(categoriesDataList, R.layout.item_categories, false); // false для остальных
        adapterRV_own_trip = new MyRvAdapter(ownTripDataList, R.layout.item_own_trip, false);


        rv.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 колонки в сетке
        rv1.setLayoutManager(new GridLayoutManager(requireContext(), categoriesDataList.size())); // Колонки равны количеству категорий
        rv2.setLayoutManager(new GridLayoutManager(requireContext(), 1)); // 1 колонка

        rv.setAdapter(adapterRV_popular);
        rv1.setAdapter(adapterRV_categories);
        rv2.setAdapter(adapterRV_own_trip);

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
        rv1.addItemDecoration(itemDecoration);
        rv2.addItemDecoration(itemDecoration);
        return view;
    }

    /**
     * Возвращает строковый ресурс по его имени.
     *
     * @param aString имя ресурса
     * @return строка ресурса
     */
    private String getStringResourceByName(String aString) {
        String packageName = requireContext().getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    /**
     * Адаптер для отображения элементов в RecyclerView с эффектом клика
     * и возможностью добавления в избранное.
     */
    public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
        private ArrayList<MyData> dataList;
        private int layoutId;
        private boolean isPopularSection; // Флаг, указывающий на секцию

        /**
         * Конструктор адаптера, инициализирующий список данных и макет элемента.
         *
         * @param dataList список данных, которые будут отображаться
         * @param layoutId идентификатор макета для отображения элементов
         */
        public MyRvAdapter(ArrayList<MyData> dataList, int layoutId, boolean isPopularSection) {
            this.dataList = dataList;
            this.layoutId = layoutId;
            this.isPopularSection = isPopularSection;

        }

        /**
         * Создает новый ViewHolder для элемента списка.
         * Inflate выполняется с использованием указанного layoutId,
         * который определяется в зависимости от типа секции (популярное, категории или собственный маршрут).
         *
         * @param parent   родительский ViewGroup, в котором будет размещен новый View
         * @param viewType тип представления элемента списка
         * @return новый экземпляр MyViewHolder
         */
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new MyViewHolder(view);
        }

        /**
         * Связывает данные с представлением элемента списка.
         * Устанавливает заголовок, изображение и обработчики событий для элемента.
         * Для секции "Популярное" добавляет обработчик клика для перехода на страницу объекта.
         * Также настраивает кнопку избранного, если она присутствует в макете.
         *
         * @param holder   ViewHolder для элемента списка
         * @param position позиция элемента в списке
         */
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            MyData data = dataList.get(position);
            holder.itemTitle.setText(data.getTitle());
            holder.itemImage.setImageResource(data.getImageResId());


            if (isPopularSection) {
                holder.itemImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyData data = dataList.get(holder.getAdapterPosition());
                        Intent intent = new Intent(v.getContext(), ObjectPage.class);
                        intent.putExtra("place_data", data);
                        v.getContext().startActivity(intent);
                    }
                });
            }

/*
            // Обработчик клика на изображении всех разделов получаем инфу про изображение, на которое нажали
            // можно будет по ID изображения переключаться между активностями
            holder.itemImage.setImageResource(data.getImageResId());
            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked on image: " + data.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });*/





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


        /**
         * Возвращает общее количество элементов в списке данных.
         *
         * @return размер списка элементов
         */
        @Override
        public int getItemCount() {
            return dataList.size();
        }

        /**
         * ViewHolder для элементов списка, отображающий заголовок и изображение.
         */
        public class MyViewHolder extends RecyclerView.ViewHolder {
            final TextView itemTitle;
            final ImageView itemImage;
            final ImageView favoriteButton;
            boolean isFavorite;

            /**
             * Конструктор, инициализирующий представления элемента списка.
             *
             * @param itemView представление элемента списка
             */
            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                itemTitle = itemView.findViewById(R.id.item_title);
                itemImage = itemView.findViewById(R.id.item_image);
                favoriteButton = itemView.findViewById(R.id.favorite_button);
            }
        }
    }

    // Добавляем JSON строку как поле класса
    private static final String placesJson = "{"
            + "\"places\": ["
            + "  {"
            + "    \"title\": \"Часовня в честь Святой Троицы\","
            + "    \"mainImage\": \"chas\","
            + "    \"backgroundImage\": \"authorisation_backgroung_image1\","
            + "    \"address\": \"ул. Комсомольская, 21\","
            + "    \"category\": \"Святые места / Церкви\","
            + "    \"rating\": 1.4,"
            + "    \"reviewCount\": 152,"
            + "    \"workingHours\": \"Ежедневно с 10:00 до 21:00\","
            + "    \"openUntil\": \"Открыто до 21:00\","
            + "    \"phoneNumber\": \"+7 999 999 52 52\","
            + "    \"additionalImages\": ["
            + "      \"R.drawable.image2\","
            + "      \"R.drawable.image1\","
            + "      \"R.drawable.image3\","
            + "      \"R.drawable.image1\","
            + "      \"R.drawable.image1\""
            + "    ]"
            + "  },"

            + "  {"
            + "    \"title\": \"Приход Александра Невского\","
            + "    \"mainImage\": \"aleksandra_nevskogo\","
            + "    \"backgroundImage\": \"authorisation_backgroung_image1\","
            + "    \"address\": \"Название улицы 2\","
            + "    \"category\": \"Категория / подкатегория\","
            + "    \"rating\": 4.8,"
            + "    \"reviewCount\": 452,"
            + "    \"workingHours\": \"Ежедневно с 9:00 до 18:00\","
            + "    \"openUntil\": \"Открыто до 18:00\","
            + "    \"phoneNumber\": \"+7 999 273 52 99\","
            + "    \"additionalImages\": ["
            + "      \"R.drawable.image1\","
            + "      \"R.drawable.image2\","
            + "      \"R.drawable.image3\""
            + "    ]"
            + "  }"
            + "]"
            + "}";

    /**
     * Инициализирует данные карточки объекта из JSON-данных.
     * Парсит JSON-строку, извлекая информацию о местах, включая:
     * название, изображения (превью и бэкграунд), адрес, категорию, рейтинг, кол-во отзывов,
     * часы работы, телефон.
     * В случае ошибки парсинга создает список по-умолчанию.
     */
    private void initializePopularData() {

        if (popularDataList == null) {
            popularDataList = new ArrayList<>();
        } else {
            popularDataList.clear();
        }

        try {
            JSONObject jsonObject = new JSONObject(placesJson);
            JSONArray placesArray = jsonObject.getJSONArray("places");

            //popularDataList = new ArrayList<>();

            // предварительное выделение памяти
            popularDataList.ensureCapacity(placesArray.length());

            for (int i = 0; i < placesArray.length(); i++) {
                JSONObject place = placesArray.getJSONObject(i);

                ArrayList<Integer> additionalImages = new ArrayList<>();
                JSONArray imagesArray = place.getJSONArray("additionalImages");
                for (int j = 0; j < imagesArray.length(); j++) {
                    // Преобразуем строку "R.drawable.image1" в реальный id ресурса
                    String imageName = imagesArray.getString(j)
                            .replace("R.drawable.", ""); // Получаем только имя ресурса
                    int resourceId = getResources().getIdentifier(
                            imageName,
                            "drawable",
                            requireContext().getPackageName()
                    );
                    additionalImages.add(resourceId);
                }

                MyData data = new MyData(
                        place.getString("title"),
                        getResources().getIdentifier(
                                place.getString("mainImage"),
                                "drawable",
                                requireContext().getPackageName()
                        ),
                        place.getString("address"),
                        place.getString("category"),
                        place.getDouble("rating"),
                        place.getInt("reviewCount"),
                        place.getString("workingHours"),
                        place.getString("openUntil"),
                        place.getString("phoneNumber"),
                        additionalImages
                );

                data.setBackgroundImageResId(
                        getResources().getIdentifier(
                                place.getString("backgroundImage"),
                                "drawable",
                                requireContext().getPackageName()
                        )
                );

                popularDataList.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            popularDataList = new ArrayList<>();
            popularDataList.add(new MyData(getStringResourceByName("chas"), R.drawable.chas));
            popularDataList.add(new MyData(getStringResourceByName("aleksandra_nevskogo"), R.drawable.aleksandra_nevskogo));
        }
    }


}
