package com.example.hotelbooking.practical4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.hotelbooking.databinding.P3FragmentBookingBinding;
import com.example.hotelbooking.model.Booking;

public class BookingFragment extends Fragment {

    private P3FragmentBookingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setGuest(new Booking());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}