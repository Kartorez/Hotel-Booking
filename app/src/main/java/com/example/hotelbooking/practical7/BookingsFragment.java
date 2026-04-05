package com.example.hotelbooking.practical7;

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
import com.example.hotelbooking.model.practical7.BookingsViewModel;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.practical4.BookingAdapter;

public class BookingsFragment extends Fragment {

    private P3FragmentBookingsBinding binding;
    private BookingAdapter adapter;
    private BookingsViewModel bookingViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookingViewModel = new ViewModelProvider(requireActivity())
                .get(BookingsViewModel.class);

        NavController navController = NavHostFragment.findNavController(this);

        adapter = new BookingAdapter(
                bookingViewModel.getBookings(),
                bookingViewModel.getBookedHotels(),
                new BookingAdapter.OnBookingClick() {
                    @Override
                    public void onCancel(int position) {
                        bookingViewModel.cancelBooking(position);
                    }

                    @Override
                    public void onDetails(Booking booking, Hotel hotel) {
                        int index = bookingViewModel.getBookings().indexOf(booking);
                        Bundle args = new Bundle();
                        args.putInt("bookingIndex", index);
                        navController.navigate(
                                R.id.action_bookingsFragment_to_bookingFragment, args);
                    }
                }
        );

        binding.recyclerBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerBookings.setAdapter(adapter);

        bookingViewModel.getBookingsLive().observe(getViewLifecycleOwner(), bookings -> {
            adapter.setBookings(bookings);
        });

        bookingViewModel.getBookedHotelsLive().observe(getViewLifecycleOwner(), hotels -> {
            adapter.setHotels(hotels);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        bookingViewModel.reload();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}