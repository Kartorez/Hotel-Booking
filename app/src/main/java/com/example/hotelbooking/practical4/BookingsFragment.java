package com.example.hotelbooking.practical4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentBookingsBinding;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.BookingsViewModel;
import com.example.hotelbooking.model.Hotel;

public class BookingsFragment extends Fragment {

    private P3FragmentBookingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BookingsViewModel bookingViewModel = new ViewModelProvider(requireActivity())
                .get(BookingsViewModel.class);


        binding.recyclerBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerBookings.setAdapter(new BookingAdapter(
                bookingViewModel.getBookings(),
                bookingViewModel.getBookedHotels(),
                new BookingAdapter.OnBookingClick() {
                    @Override
                    public void onCancel(int position) {
                        bookingViewModel.cancelBooking(position);
                        binding.recyclerBookings.getAdapter().notifyItemChanged(position);
                    }

                    @Override
                    public void onDetails(Booking booking, Hotel hotel) {

                    }
                }
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}