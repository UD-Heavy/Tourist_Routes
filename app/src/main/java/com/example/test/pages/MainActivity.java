package com.example.test.pages;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.test.R;
import com.example.test.databinding.ActivityMainBinding;


/**
 * Главная активность приложения, отображающая разные фрагменты для навигации по вкладкам.
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    // Ссылки на фрагменты
    private Fragment mainMenuPage;
    private Fragment newsPage;
    private Fragment createTripPage;
    private Fragment favouritePage;
    private int currentTabIndex = 0;

    /**
     * Метод вызывается при создании активности. Устанавливает контент, инициализирует фрагменты
     * и настраивает навигацию между ними.
     *
     * @param savedInstanceState объект {@link Bundle} с сохраненным состоянием активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        mainMenuPage = new MainMenuPage();
        newsPage = new NewsPage();
        createTripPage = new CreateTripPage();
        favouritePage = new FavouritePage();

        preloadFragments();
        showFragment(mainMenuPage, 0);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int newTabIndex = 0;
            int itemId = item.getItemId();
            if (itemId == R.id.home_navbar) {
                newTabIndex = 0;
                showFragment(mainMenuPage, newTabIndex);
            } else if (itemId == R.id.news_navbar) {
                newTabIndex = 1;
                showFragment(newsPage, newTabIndex);
            } else if (itemId == R.id.map_navbar) {
                newTabIndex = 2;
                showFragment(createTripPage, newTabIndex);
            } else if (itemId == R.id.favorite_navbar) {
                newTabIndex = 3;
                showFragment(favouritePage, newTabIndex);
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
        // обработчик кнопки назад
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
//                if (currentFragment != null && currentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
//                    currentFragment.getChildFragmentManager().popBackStack();
//                } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    getSupportFragmentManager().popBackStack();
//                } else {
//                    showExitConfirmationDialog();
//                }
//            }
//        });
    }

//    private void showExitConfirmationDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Выйти из приложения?")
//                .setMessage("Вы уверены, что хотите выйти?")
//                .setPositiveButton("Да", (dialog, which) -> {
//                    finishAffinity(); // Закрывает все активности приложения
//                })
//                .setNegativeButton("Нет", null)
//                .show();
//    }

    /**
     * Предварительно загружает фрагменты, добавляя их в стек и скрывая,
     * чтобы обеспечить быструю смену вкладок.
     */
    private void preloadFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_layout, mainMenuPage, "home")
                .add(R.id.frame_layout, newsPage, "news")
                .add(R.id.frame_layout, createTripPage, "create_trip")
                .add(R.id.frame_layout, favouritePage, "favourite")
                .hide(newsPage)
                .hide(createTripPage)
                .hide(favouritePage);

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * Показать фрагмент, соответствующий выбранной вкладке, с анимацией перехода.
     *
     * @param fragment    фрагмент для отображения
     * @param newTabIndex индекс выбранной вкладки
     */
    private void showFragment(Fragment fragment, int newTabIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (newTabIndex > currentTabIndex){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right_next, R.anim.slide_in_left_next);
        }
        else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left_previous, R.anim.slide_in_right_previous);
        }

        currentTabIndex = newTabIndex;

        // Скрыть все фрагменты и показать выбранный
        fragmentTransaction.hide(mainMenuPage)
                .hide(newsPage)
                .hide(createTripPage)
                .hide(favouritePage)
                .show(fragment)
                .commit();
    }

    /**
     * Заменяет текущий фрагмент на новый, заменяя содержимое frame_layout.
     *
     * @param fragment фрагмент для замены
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}
