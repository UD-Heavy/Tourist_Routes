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

/**
 * Фрагмент, представляющий главный экран приложения. Содержит RecyclerView для отображения
 * туров и новостей, а также кнопку для перехода на страницу профиля.
 */
public class MainMenuPage extends Fragment {
    RecyclerView rv, rv1;
    ArrayList<MyData> dataSource;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    MainMenuPage.MyRvAdapter adapterRV_tour, adapterRV_news;

    /**
     * Создает и возвращает вид иерархии фрагмента, используя указанный контейнер.
     *
     * @param inflater           объект {@link LayoutInflater} для расширения XML-разметки
     * @param container          родительский контейнер, к которому добавляется интерфейс
     * @param savedInstanceState сохраненное состояние фрагмента
     * @return возвращает корневой элемент иерархии пользовательского интерфейса фрагмента
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_page, container, false);
    }


    /**
     * Вызывается после создания представления фрагмента. Инициализирует RecyclerView,
     * данные и устанавливает слушатели для элементов интерфейса.
     *
     * @param view               представление, возвращаемое из {@link #onCreateView}
     * @param savedInstanceState сохраненное состояние фрагмента
     */
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

    /**
     * Открывает страницу профиля, заменяя текущий фрагмент на ProfilePage.
     */
    private void openProfilePage() {
        ProfilePage profilePage = new ProfilePage();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).replaceFragment(profilePage);
        }
    }

    /**
     * Обработчик события нажатия на изображение профиля. Открывает страницу профиля.
     *
     * @param v объект {@link View}, представляющий нажатую кнопку
     */
    public void onClickProfile(View v) {
        openProfilePage();
    }


    /**
     * Адаптер для RecyclerView, отображающий элементы данных в виде карточек.
     */
    class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_CARD = 0;
        private static final int TYPE_CARD_WITH_BUTTON = 1;
        private final ArrayList<MyData> data;

        /**
         * Конструктор адаптера, принимающий данные для отображения.
         *
         * @param data список элементов данных для отображения в RecyclerView
         */
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

        /**
         * ViewHolder для отображения карточки без кнопки.
         */
        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            ImageView ivImage;

            /**
             * Конструктор, инициализирующий элементы интерфейса для отображения карточки.
             *
             * @param itemView представление, представляющее элемент в RecyclerView
             */
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.text_view);
                ivImage = itemView.findViewById(R.id.image_view);
            }

            /**
             * Привязывает данные к элементам интерфейса.
             *
             * @param item объект данных, который нужно отобразить
             */
            public void bind(MyData item) {
                tvTitle.setText(item.getTitle());
                ivImage.setImageResource(item.getImageResId());
            }
        }

        /**
         * ViewHolder для отображения карточки с кнопкой.
         */
        class CardWithButtonViewHolder extends RecyclerView.ViewHolder {

            Button button;

            /**
             * Конструктор, инициализирующий элементы интерфейса для отображения карточки с кнопкой.
             *
             * @param itemView представление, представляющее элемент в RecyclerView
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