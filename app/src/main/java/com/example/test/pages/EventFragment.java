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
 * Фрагмент, представляющий страницу событий с отображением списка новостей или событий.
 */
public class EventFragment extends Fragment {
    RecyclerView rv;
    ArrayList<MyData> news_list;
    LinearLayoutManager linearLayoutManager;

    MyRvAdapter news;


    /**
     * Создает и возвращает иерархию представлений, связанную с этим фрагментом.
     *
     * @param inflater           объект {@link LayoutInflater} для создания представлений фрагмента
     * @param container          родительский контейнер, к которому будет добавлено представление фрагмента
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     * @return корневое представление для данного фрагмента
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    /**
     * Метод вызывается сразу после создания представления фрагмента.
     * Инициализирует RecyclerView и адаптер для отображения списка новостей или событий.
     *
     * @param view               корневое представление, возвращаемое {@link #onCreateView}
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv = view.findViewById(R.id.recycler_event);


        // соединение с бд, получаем список объектов
        // ArrayList<Document> DBList = dbRepositories.getAll();
        // for (MyData data : DBList){
        //      dataSourse.add(data.get("title"), convertBase64ToImage(data.get("image")))
        // }
        news_list = new ArrayList<>();
        news_list.add(new MyData(getResources().getString(R.string.text), R.drawable.aleksandra_nevskogo));
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
     * Адаптер для отображения элементов в RecyclerView на странице событий.
     */
    static class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final ArrayList<MyData> data;


        /**
         * Конструктор адаптера, инициализирующий список данных.
         *
         * @param data список данных, которые будут отображаться
         */
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

        /**
         * ViewHolder для отображения элемента списка с изображением.
         */
        static class CardViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;

            /**
             * Конструктор для инициализации представлений ViewHolder.
             *
             * @param itemView представление элемента списка
             */
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.image_view);
            }

            /**
             * Привязывает данные к представлениям ViewHolder.
             *
             * @param item объект {@link MyData}, содержащий данные для отображения
             */
            public void bind(MyData item) {
                ivImage.setImageResource(item.getImageResId());
            }
        }


    }
}