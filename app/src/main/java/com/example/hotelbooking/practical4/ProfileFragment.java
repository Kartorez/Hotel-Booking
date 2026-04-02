package com.example.hotelbooking.practical4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbooking.databinding.P3FragmentProfileBinding;
import com.example.hotelbooking.practical6.Practical6Activity;
import com.example.hotelbooking.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private P3FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            binding.textProfileName.setText(user.getDisplayName() != null ? user.getDisplayName() : "Користувач");
            binding.textProfileEmail.setText(user.getEmail());
        } else {
            binding.textProfileName.setText("Не залогінено");
            binding.textProfileEmail.setText("");
        }

        binding.buttonBookings.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.bookingsFragment);
        });

        binding.buttonAuth.setOnClickListener(v -> {
            AuthUI.getInstance().signOut(requireActivity()).addOnCompleteListener(task -> {
                startActivity(new Intent(requireActivity(), Practical6Activity.class));
                requireActivity().finish();
            });
        });
    }
}