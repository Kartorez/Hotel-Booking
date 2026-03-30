package com.example.hotelbooking.practical3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hotelbooking.R;

import com.example.hotelbooking.databinding.P3FragmentHotelDetailBinding;

public class HotelDetailFragment extends Fragment {

    private P3FragmentHotelDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentHotelDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonBook.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_hotelDetailFragment_to_bookingFragment);
        });

        binding.buttonOpenMap.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_hotelDetailFragment_to_hotelsFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}