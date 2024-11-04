package com.example.test.pages;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


/**
 * Адаптер страниц для управления фрагментами в ViewPager2.
 * Используется для переключения между различными страницами (фрагментами),
 * такими как {@link EventFragment} и {@link NewsFragment}.
 */
public class PageAdapter extends FragmentStateAdapter {

    /**
     * Конструктор для создания экземпляра {@link PageAdapter}.
     *
     * @param fragmentManager объект {@link FragmentManager} для управления фрагментами
     * @param lifecycle       объект {@link Lifecycle}, описывающий жизненный цикл фрагментов
     */
    public PageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    /**
     * Создает и возвращает фрагмент для указанной позиции.
     *
     * @param position индекс позиции страницы, для которой создается фрагмент
     * @return объект {@link Fragment} для указанной позиции
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new EventFragment();
        }
        return new NewsFragment();
        }

    /**
     * Возвращает общее количество страниц (фрагментов).
     *
     * @return количество фрагментов, управляемых адаптером
     */
    @Override
    public int getItemCount() {
        return 2;
    }
}
