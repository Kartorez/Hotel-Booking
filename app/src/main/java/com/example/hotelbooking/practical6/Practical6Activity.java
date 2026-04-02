package com.example.hotelbooking.practical6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.MainActivity;
import com.example.hotelbooking.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class Practical6Activity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    result -> {
                        IdpResponse response = result.getIdpResponse();
                        if (result.getResultCode() == RESULT_OK) {
                            Log.i("LoginActivity", "User logged in");
                            gotoMainScreen();
                        } else {
                            if (response != null && response.getError() != null) {
                                Log.w("LoginActivity", "Sign in failed: " +
                                        response.getError().getErrorCode());
                            }
                        }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical6);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            gotoMainScreen();
            return;
        }

        findViewById(R.id.buttonLoginWithEmail).setOnClickListener(v ->
                startSignIn(new AuthUI.IdpConfig.EmailBuilder().build())
        );

        findViewById(R.id.buttonLoginGoogle).setOnClickListener(v ->
                startSignIn(new AuthUI.IdpConfig.GoogleBuilder().build())
        );
    }

    private void startSignIn(AuthUI.IdpConfig provider) {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(provider))
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void gotoMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}