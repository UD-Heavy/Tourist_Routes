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


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    // Ссылки на фрагменты
    private Fragment mainMenuPage;
    private Fragment newsPage;
    private Fragment createTripPage;
    private Fragment favouritePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());


        // Инициализация фрагментов
        mainMenuPage = new MainMenuPage();
        newsPage = new ProfilePage();
        createTripPage = new CreateTripPage();

        // Добавление фрагментов в FragmentManager
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mainMenuPage, "home")
                .add(R.id.frame_layout, newsPage, "profile")
                .add(R.id.frame_layout, createTripPage, "settings")
                .hide(newsPage)
                .hide(createTripPage)
                .commit();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_navbar) {
                showFragment(mainMenuPage);
            } else if (itemId == R.id.news_navbar) {
                showFragment(newsPage);
            } else if (itemId == R.id.map_navbar) {
                showFragment(createTripPage);
            } else if (itemId == R.id.favorite_navbar) {
                showFragment(createTripPage);
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                if (currentFragment != null && currentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                    currentFragment.getChildFragmentManager().popBackStack();
                } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    showExitConfirmationDialog();
                }
            }
        });
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Выйти из приложения?")
                .setMessage("Вы уверены, что хотите выйти?")
                .setPositiveButton("Да", (dialog, which) -> finish())
                .setNegativeButton("Нет", null)
                .show();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mainMenuPage)
                .hide(newsPage)
                .hide(createTripPage)
                .show(fragment)
                .commit();
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}
