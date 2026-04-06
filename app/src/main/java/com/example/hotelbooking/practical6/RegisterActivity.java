package com.example.hotelbooking.practical6;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.editFullName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String[] parts = s.toString().trim().split("\\s+");
                String error = parts.length < 2 ? "Введіть ім'я та прізвище" : null;
                binding.fullNameLayout.setError(error);
                binding.fullNameLayout.setErrorEnabled(error != null);
            }
        });

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

        binding.editConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String pass = binding.editPassword.getText().toString().trim();
                String error = !s.toString().trim().equals(pass)
                        ? "Паролі не співпадають" : null;
                binding.confirmPasswordLayout.setError(error);
                binding.confirmPasswordLayout.setErrorEnabled(error != null);
            }
        });

        binding.buttonRegister.setOnClickListener(v -> register());
        binding.buttonGoLogin.setOnClickListener(v -> finish());
    }

    private void register() {
        String fullName = binding.editFullName.getText().toString().trim();
        String email    = binding.editEmail.getText().toString().trim();
        String pass     = binding.editPassword.getText().toString().trim();
        String confirm  = binding.editConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Заповніть усі поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (binding.fullNameLayout.getError() != null ||
                binding.emailLayout.getError() != null ||
                binding.passwordLayout.getError() != null ||
                binding.confirmPasswordLayout.getError() != null) {
            Toast.makeText(this, "Виправте помилки", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(r -> {
                    Toast.makeText(this, "Акаунт створено!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Помилка: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }
}