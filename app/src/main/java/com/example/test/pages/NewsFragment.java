package com.example.test.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.models.MyData;

import java.util.ArrayList;


/**
 * Фрагмент для отображения списка новостей с помощью RecyclerView.
 */
public class NewsFragment extends Fragment {
    RecyclerView rv;
    ArrayList<MyData> news_list;
    LinearLayoutManager linearLayoutManager;

    MyRvAdapter news;

    /**
     * Создает и возвращает иерархию представлений, связанную с данным фрагментом.
     *
     * @param inflater           объект {@link LayoutInflater} для раздувания представления фрагмента
     * @param container          родительский контейнер, к которому будет присоединен фрагмент
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     * @return корневое представление для фрагмента
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    /**
     * Вызывается после создания представления фрагмента. Инициализирует RecyclerView, адаптер
     * и заполняет список новостей.
     *
     * @param view               представление фрагмента
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = view.findViewById(R.id.recycler_news);


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
    }

    /**
     * Адаптер для RecyclerView, отображающий список новостных данных.
     */
    static class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final ArrayList<MyData> data;

        /**
         * Конструктор для создания адаптера с переданными данными.
         *
         * @param data список объектов {@link MyData} для отображения
         */
        public MyRvAdapter(ArrayList<MyData> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());


            View view = inflater.inflate(R.layout.list_item_main_menu_card, parent, false);
            return new CardViewHolder(view);

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

        /**
         * ViewHolder для отображения карточки новости.
         */
        static class CardViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;

            /**
             * Конструктор для создания ViewHolder.
             *
             * @param itemView представление элемента
             */
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.image_view);
            }

            /**
             * Привязывает данные к ImageView.
             *
             * @param item объект {@link MyData}, представляющий новостные данные
             */
            public void bind(MyData item) {
                ivImage.setImageResource(item.getImageResId());
            }
        }


    }
}