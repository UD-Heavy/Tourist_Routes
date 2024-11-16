package com.example.test.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.test.R;
import com.example.test.models.MyData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * Фрагмент для отображения страницы новостей с табами и ViewPager для переключения между вкладками.
 */
public class NewsPage extends Fragment {

    ArrayList<MyData> news_list;
    private ViewPager2 viewPager;

    //MyRvAdapter news;

    /**
     * Создает и возвращает иерархию представлений, связанную с данным фрагментом.
     *
     * @param inflater           объект {@link LayoutInflater} для раздувания представления фрагмента
     * @param container          родительский контейнер, к которому будет присоединен фрагмент
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     * @return возвращает корневое представление для фрагмента
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_page, container, false);
    }

    /**
     * Вызывается после того, как представление было создано. Устанавливает адаптер для ViewPager
     * и настраивает TabLayout для переключения между страницами.
     *
     * @param view               представление фрагмента
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием фрагмента
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //rv = view.findViewById(R.id.recycler_view);
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        PageAdapter sa = new PageAdapter(fm, getLifecycle());
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sa);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        // соединение с бд, получаем список объектов
        // ArrayList<Document> DBList = dbRepositories.getAll();
        // for (MyData data : DBList){
        //      dataSourse.add(data.get("title"), convertBase64ToImage(data.get("image")))
        // }
        news_list = new ArrayList<>();
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.LoginPageForgotPass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.LoginPageForgotPass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.LoginPageForgotPass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.LoginPageForgotPass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.LoginPageForgotPass), R.drawable.aleksandra_nevskogo));
        news_list.add(new MyData(getResources().getString(R.string.first_card), R.drawable.chas));
        news_list.add(new MyData(getResources().getString(R.string.LoginPageForgotPass), R.drawable.aleksandra_nevskogo));

        //linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        //news = new MyRvAdapter(news_list);
        //linearLayoutManager.isAutoMeasureEnabled();
        //rv.setLayoutManager(linearLayoutManager);
        //rv.setNestedScrollingEnabled(false);
        
        //rv.setAdapter(news);
    }
//    static class MyRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//        private final ArrayList<MyData> data;
//
//        public MyRvAdapter(ArrayList<MyData> data) {
//            this.data = data;
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//
//            View view = inflater.inflate(R.layout.list_item_main_menu_card, parent, false);
//            return new CardViewHolder(view);
//
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            if (position < data.size()) {
//                MyData currentItem1 = data.get(position);
//                if (holder instanceof MyRvAdapter.CardViewHolder) {
//                    ((MyRvAdapter.CardViewHolder) holder).bind(currentItem1);
//                }
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        // ViewHolder для карточки без кнопки
//        static class CardViewHolder extends RecyclerView.ViewHolder {
//            ImageView ivImage;
//
//            public CardViewHolder(@NonNull View itemView) {
//                super(itemView);
//                ivImage = itemView.findViewById(R.id.image_view);
//            }
//
//            public void bind(MyData item) {
//                ivImage.setImageResource(item.getImageResId());
//            }
//        }
//
//
//    }
}