package com.example.hotelbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hotelbooking.databinding.ActivityMainBinding;
import com.example.hotelbooking.practical3.Practical3Activity;
import com.example.hotelbooking.practical4.Practical4Activity;
import com.example.hotelbooking.practical5.Practical5Activity;
import com.example.hotelbooking.practical6.Practical6Activity;
import com.example.hotelbooking.practical7.Practical7Activity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonPractical3.setOnClickListener(v ->
                startActivity(new Intent(this, Practical3Activity.class)));
        binding.buttonPractical4.setOnClickListener(v ->
                startActivity(new Intent(this, Practical4Activity.class)));
        binding.buttonPractical5.setOnClickListener(v ->
                startActivity(new Intent(this, Practical5Activity.class)));
        binding.buttonPractical6.setOnClickListener(v ->
                startActivity(new Intent(this, Practical6Activity.class)));
        binding.buttonPractical7.setOnClickListener(v ->
                startActivity(new Intent(this, Practical7Activity.class)));
    }
}