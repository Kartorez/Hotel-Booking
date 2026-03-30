package com.example.hotelbooking.practical3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentBookingBinding;
import com.example.hotelbooking.databinding.P3FragmentHotelDetailBinding;


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
        binding.buttonConfirm.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_bookingFragment_to_hotelsFragment);
        });
    }
}