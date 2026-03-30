package com.example.hotelbooking.practical5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.hotelbooking.R;
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String roomType = getArguments().getString("roomType", "");
        double roomPrice = getArguments().getDouble("roomPrice", 0);

        binding.setGuest(new Booking());
        binding.textViewTotalPrice.setText("₴" + (int) roomPrice);

        binding.buttonConfirm.setOnClickListener(v ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_bookingFragment_to_hotelsFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}