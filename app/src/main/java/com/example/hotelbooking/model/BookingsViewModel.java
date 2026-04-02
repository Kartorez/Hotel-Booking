package com.example.hotelbooking.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookingsViewModel extends ViewModel {
    private List<Booking> bookings = new ArrayList<>();
    private List<Hotel> bookedHotels = new ArrayList<>();

    public BookingsViewModel() {}

    public List<Booking> getBookings() { return bookings; }
    public List<Hotel> getBookedHotels() { return bookedHotels; }

    public void addBooking(Booking booking, Hotel hotel) {
        bookings.add(booking);
        bookedHotels.add(hotel);
    }

    public void cancelBooking(int position) {
        bookings.get(position).setStatus("Скасовано");
    }
}