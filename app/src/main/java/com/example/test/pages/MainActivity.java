package com.example.test.pages;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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
    private Fragment profilePage;
    private Fragment settingsPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        // Инициализация фрагментов
        mainMenuPage = new MainMenuPage();
        profilePage = new ProfilePage();
        settingsPage = new ProfilePage();

        // Добавление фрагментов в FragmentManager
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mainMenuPage, "home")
                .add(R.id.frame_layout, profilePage, "profile")
                .add(R.id.frame_layout, settingsPage, "settings")
                .hide(profilePage)
                .hide(settingsPage)
                .commit();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                showFragment(mainMenuPage);
            } else if (itemId == R.id.profile) {
                showFragment(profilePage);
            } else if (itemId == R.id.settings) {
                showFragment(settingsPage);
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mainMenuPage)
                .hide(profilePage)
                .hide(settingsPage)
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
