package com.example.hotelbooking.practical6;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String error = !Patterns.EMAIL_ADDRESS.matcher(s.toString().trim()).matches()
                        ? "Невірний email" : null;
                binding.emailLayout.setError(error);
                binding.emailLayout.setErrorEnabled(error != null);
            }
        });

        binding.editPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String error = s.toString().trim().length() < 6
                        ? "Мінімум 6 символів" : null;
                binding.passwordLayout.setError(error);
                binding.passwordLayout.setErrorEnabled(error != null);
            }
        });

        binding.buttonLogin.setOnClickListener(v -> login());

        binding.buttonGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }

    private void login() {
        String email = binding.editEmail.getText().toString().trim();
        String pass = binding.editPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Заповніть усі поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (binding.emailLayout.getError() != null || binding.passwordLayout.getError() != null) {
            Toast.makeText(this, "Виправте помилки", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(r -> {
                    Intent intent = new Intent(this, com.example.hotelbooking.practical7.Practical7Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Помилка: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }
}