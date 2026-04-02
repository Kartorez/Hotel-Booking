package com.example.hotelbooking.practical4;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.ItemHotelBinding;

import com.example.hotelbooking.model.Hotel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    public interface OnHotelClick {
        void onClick(Hotel hotel);
    }

    public interface OnSaveClick {
        void onSave(Hotel hotel, int position);
    }

    private final List<Hotel> hotels;
    private final OnHotelClick listener;
    private final OnSaveClick saveListener;

    public HotelAdapter(List<Hotel> hotels, OnHotelClick listener, OnSaveClick saveListener) {
        this.hotels = hotels;
        this.listener = listener;
        this.saveListener = saveListener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotelViewHolder(
                ItemHotelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int pos) {
        Hotel hotel = hotels.get(pos);
        holder.setHotel(hotel);
        holder.itemView.setOnClickListener(v -> listener.onClick(hotel));
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        private final ItemHotelBinding binding;

        public HotelViewHolder(@NonNull ItemHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setHotel(Hotel h) {
            binding.setHotel(h);
            binding.setRoom(h.getRooms().get(0));
            binding.executePendingBindings();
        }
    }
}