package com.example.hotelbooking.practical6;

import android.os.Bundle;
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

        binding.buttonRegister.setOnClickListener(v -> register());

        binding.buttonGoLogin.setOnClickListener(v -> finish());
    }

    private void register() {
        String email   = binding.editEmail.getText().toString().trim();
        String pass    = binding.editPassword.getText().toString().trim();
        String confirm = binding.editConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Заповніть усі поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Паролі не співпадають", Toast.LENGTH_SHORT).show();
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