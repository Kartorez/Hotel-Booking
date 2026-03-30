package com.example.hotelbooking.practical4;

import com.google.android.material.card.MaterialCardView;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.databinding.ItemRoomBinding;
import com.example.hotelbooking.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    public interface OnRoomClick {
        void onClick(Room room);
    }

    private final List<Room> rooms;
    private final OnRoomClick listener;
    private int selectedPosition = 0;

    public RoomAdapter(List<Room> rooms, OnRoomClick listener) {
        this.rooms = rooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomViewHolder(
                ItemRoomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int pos) {
        holder.setRoom(rooms.get(pos));
        MaterialCardView card = (MaterialCardView) holder.binding.getRoot();

        if (pos == selectedPosition) {
            card.setStrokeColor(Color.parseColor("#008AC9"));
            card.setStrokeWidth(4);
        } else {
            card.setStrokeColor(Color.TRANSPARENT);
            card.setStrokeWidth(0);
        }

        holder.itemView.setOnClickListener(v -> {
            int prev = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(prev);
            notifyItemChanged(selectedPosition);
            listener.onClick(rooms.get(selectedPosition));
        });
    }

    @Override
    public int getItemCount() { return rooms.size(); }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        final ItemRoomBinding binding;

        public RoomViewHolder(@NonNull ItemRoomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setRoom(Room r) {
            binding.setRoom(r);
            binding.executePendingBindings();
        }
    }
}