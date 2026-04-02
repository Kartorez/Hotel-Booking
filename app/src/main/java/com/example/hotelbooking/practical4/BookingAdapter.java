package com.example.hotelbooking.practical4;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.databinding.ItemBookingBinding;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Hotel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public interface OnBookingClick {
        void onCancel(int position);
        void onDetails(Booking booking, Hotel hotel);
    }

    private final List<Booking> bookings;
    private final List<Hotel> hotels;
    private final OnBookingClick listener;

    public BookingAdapter(List<Booking> bookings, List<Hotel> hotels, OnBookingClick listener) {
        this.bookings = bookings;
        this.hotels = hotels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingViewHolder(
                ItemBookingBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int pos) {
        Booking booking = bookings.get(pos);
        Hotel hotel = hotels.get(pos);

        holder.binding.setBooking(booking);
        holder.binding.setHotel(hotel);
        holder.binding.executePendingBindings();

        holder.binding.buttonCancel.setOnClickListener(v ->
                listener.onCancel(pos)
        );
        holder.binding.buttonDetails.setOnClickListener(v ->
                listener.onDetails(booking, hotel)
        );

        if ("Скасовано".equals(booking.getStatus())) {
            holder.binding.textViewStatus.setTextColor(
                    android.graphics.Color.parseColor("#B00020"));
        } else {
            holder.binding.textViewStatus.setTextColor(
                    android.graphics.Color.parseColor("#1E8E3E"));
        }
    }

    @Override
    public int getItemCount() { return bookings.size(); }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        final ItemBookingBinding binding;

        public BookingViewHolder(@NonNull ItemBookingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}