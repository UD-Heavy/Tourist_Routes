package com.example.test.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * Класс, представляющий главный экран меню приложения. Отображает список туров и новостей
 * с помощью RecyclerView и настраивает интерфейс в режиме Edge-to-Edge.
 */
public class MainMenuPage extends AppCompatActivity {
    RecyclerView rv, rv1;
    ArrayList<MyData> dataSource;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    MainMenuPage.MyRvAdapter adapterRV_tour, adapterRV_news;


    /**
     * Инициализирует главный экран при его создании. Устанавливает макет,
     * включает поддержку Edge-to-Edge, инициализирует списки для туров и новостей,
     * а также адаптеры для отображения данных в виде карточек.
     *
     * @param savedInstanceState объект {@link Bundle},
     * содержащий данные о предыдущем состоянии активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu_page);

        rv = findViewById(R.id.tours_view);
        rv1 = findViewById(R.id.news_view);

        // соединение с бд, получаем список объектов
        // ArrayList<Document> DBList = dbRepositories.getAll();
        // for (MyData data : DBList){
        //      dataSourse.add(data.get("title"), convertBase64ToImage(data.get("image")))
        // }
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


    /**
     * Внутренний адаптер RecyclerView для отображения элементов меню на главном экране.
     * Адаптер поддерживает два типа элементов: карточка и карточка с кнопкой.
     */
    class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_CARD = 0;
        private static final int TYPE_CARD_WITH_BUTTON = 1;
        private final ArrayList<MyData> data;

        public MyRvAdapter(ArrayList<MyData> data) {
            this.data = data;
        }


        /**
         * Создаёт соответствующий ViewHolder в зависимости от типа элемента:
         * карточка или карточка с кнопкой.
         *
         * @param parent ViewGroup, к которому будет прикреплён новый View
         * @param viewType тип элемента, определяемый методом {@link #getItemViewType(int)}
         * @return созданный объект {@link RecyclerView.ViewHolder}
         */
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

        /**
         * Привязывает данные к ViewHolder в заданной позиции.
         *
         * @param holder объект {@link RecyclerView.ViewHolder}, к которому привязываются данные
         * @param position позиция элемента в адаптере
         */
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


        /**
         * Возвращает количество элементов в адаптере. Включает один дополнительный элемент
         * для отображения карточки с кнопкой в конце списка.
         *
         * @return количество элементов в адаптере
         */
        @Override
        public int getItemCount() {
            return data.size() + 1;
        }


        /**
         * Определяет тип элемента в указанной позиции. Если элемент является последним,
         * он отображается как карточка с кнопкой.
         *
         * @param position позиция элемента в адаптере
         * @return тип элемента, либо {@code TYPE_CARD} для обычной карточки, либо {@code TYPE_CARD_WITH_BUTTON} для карточки с кнопкой
         */
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

        /**
         * ViewHolder для отображения карточки без кнопки, содержащей текст и изображение.
         */
        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            ImageView ivImage;

            /**
             * Конструктор, инициализирующий компоненты карточки.
             *
             * @param itemView View, представляющий элемент списка
             */
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.text_view);
                ivImage = itemView.findViewById(R.id.image_view);
            }

            /**
             * Связывает данные объекта {@link MyData} с компонентами ViewHolder.
             *
             * @param item объект {@link MyData}, содержащий данные для отображения
             */
            public void bind(MyData item) {
                tvTitle.setText(item.getTitle());
                ivImage.setImageResource(item.getImageResource());
            }
        }

        /**
         * ViewHolder для отображения карточки с кнопкой. Включает слушатель для обработки нажатия на кнопку.
         */
        class CardWithButtonViewHolder extends RecyclerView.ViewHolder {

            Button button;

            /**
             * Конструктор, инициализирующий компонент кнопки и устанавливающий слушатель для нажатия.
             *
             * @param itemView View, представляющий элемент списка
             */
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