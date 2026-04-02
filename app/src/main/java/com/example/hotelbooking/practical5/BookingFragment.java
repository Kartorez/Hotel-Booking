package com.example.hotelbooking.practical5;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentBookingBinding;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.BookingsViewModel;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelsViewModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingFragment extends Fragment {

    private P3FragmentBookingBinding binding;
    private Booking booking;
    private int roomPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int hotelId = getArguments() != null ? getArguments().getInt("hotelId", 0) : 0;
        String roomType = getArguments() != null ? getArguments().getString("roomType", "") : "";
        roomPrice = getArguments() != null ? getArguments().getInt("roomPrice", 0) : 0;

        booking = new Booking();
        booking.setHotelId(hotelId);
        booking.setRoomType(roomType);
        booking.setRoomPrice(roomPrice);

        binding.setGuest(booking);
        binding.executePendingBindings();

        TextWatcher dateWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                recalculate();
            }
        };

        binding.checkInInput.addTextChangedListener(dateWatcher);
        binding.checkOutInput.addTextChangedListener(dateWatcher);

        binding.buttonConfirm.setOnClickListener(v -> {
            if (!validateAndSave()) return;

            BookingsViewModel bookingsViewModel = new ViewModelProvider(requireActivity())
                    .get(BookingsViewModel.class);

            HotelsViewModel hotelsViewModel = new ViewModelProvider(requireActivity())
                    .get(HotelsViewModel.class);

            Hotel hotel = hotelsViewModel.getHotels().get(booking.getHotelId());

            bookingsViewModel.addBooking(booking, hotel);

            Navigation.findNavController(requireView())
                    .navigate(R.id.action_booking_to_bookings);
        });
    }

    private void recalculate() {
        String checkIn = binding.checkInInput.getText().toString().trim();
        String checkOut = binding.checkOutInput.getText().toString().trim();

        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("uk", "UA"));
        binding.textViewPricePerNight.setText("₴" + fmt.format(roomPrice) + "/ніч");

        if (checkIn.isEmpty() || checkOut.isEmpty()) {
            binding.textViewNights.setText("Вкажіть дати");
            binding.textViewNightsSummary.setText("0 ночей × ₴" + fmt.format(roomPrice));
            binding.textViewTotalPrice.setText("₴0");
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            sdf.setLenient(false);

            Date dateIn = sdf.parse(checkIn);
            Date dateOut = sdf.parse(checkOut);

            if (dateIn == null || dateOut == null || !dateOut.after(dateIn)) {
                binding.textViewNights.setText("Виїзд має бути після заїзду");
                binding.textViewTotalPrice.setText("₴0");
                return;
            }

            long diffMs = dateOut.getTime() - dateIn.getTime();
            int nights = (int) (diffMs / (1000 * 60 * 60 * 24));
            int total = nights * roomPrice;
            booking.setTotalPrice(total);

            binding.textViewNights.setText(nights + " " + getNightsLabel(nights));
            binding.textViewNightsSummary.setText(nights + " ніч × ₴" + fmt.format(roomPrice));
            binding.textViewTotalPrice.setText("₴" + fmt.format(total));

        } catch (ParseException e) {
            binding.textViewNights.setText("Формат: дд.мм.рррр");
            binding.textViewTotalPrice.setText("₴0");
        }
    }

    private String getNightsLabel(int nights) {
        if (nights % 100 >= 11 && nights % 100 <= 14) return "ночей";
        switch (nights % 10) {
            case 1: return "ніч";
            case 2: case 3: case 4: return "ночі";
            default: return "ночей";
        }
    }

    private boolean validateAndSave() {
        boolean valid = true;

        if (binding.checkInInput.getText().toString().trim().isEmpty()) {
            binding.checkInInput.setError("Вкажіть дату заїзду");
            valid = false;
        }
        if (binding.checkOutInput.getText().toString().trim().isEmpty()) {
            binding.checkOutInput.setError("Вкажіть дату виїзду");
            valid = false;
        }
        if (binding.nameInput.getText().toString().trim().isEmpty()) {
            binding.nameInput.setError("Вкажіть ім'я");
            valid = false;
        }
        if (binding.emailInput.getText().toString().trim().isEmpty()) {
            binding.emailInput.setError("Вкажіть email");
            valid = false;
        }
        if (binding.phoneInput.getText().toString().trim().isEmpty()) {
            binding.phoneInput.setError("Вкажіть телефон");
            valid = false;
        }
        if (booking.getTotalPrice() <= 0) {
            binding.checkInInput.setError("Перевірте дати");
            valid = false;
        }

        if (!valid) return false;

        booking.setCheckInDate(binding.checkInInput.getText().toString().trim());
        booking.setCheckOutDate(binding.checkOutInput.getText().toString().trim());
        booking.setFirstGuestName(binding.nameInput.getText().toString().trim());
        booking.setEmail(binding.emailInput.getText().toString().trim());
        booking.setPhone(binding.phoneInput.getText().toString().trim());
        booking.setStatus("Активне");

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}