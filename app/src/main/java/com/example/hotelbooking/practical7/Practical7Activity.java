package com.example.hotelbooking.practical7;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.ActivityPractical7Binding;

public class Practical7Activity extends AppCompatActivity {

    private ActivityPractical7Binding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPractical7Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.navHost);

        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.toolbar, navController);


        binding.fabProfile.setOnClickListener(v ->
                navController.navigate(R.id.profileFragment));
    }
}