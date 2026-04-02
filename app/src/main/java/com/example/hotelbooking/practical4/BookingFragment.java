package com.example.hotelbooking.practical4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelbooking.databinding.P3FragmentBookingBinding;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.BookingsViewModel;

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

        BookingsViewModel bookingsViewModel = new ViewModelProvider(requireActivity())
                .get(BookingsViewModel.class);

        int bookingIndex = -1;
        Bundle args = getArguments();
        if (args != null) {
            bookingIndex = args.getInt("bookingIndex", -1);
        }

        if (bookingIndex >= 0) {
            Booking booking = bookingsViewModel.getBookings().get(bookingIndex);
            binding.setGuest(booking);
        } else {
            binding.setGuest(new Booking());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}