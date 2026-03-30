package com.example.hotelbooking.practical4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.databinding.P3FragmentHotelDetailBinding;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelsViewModel;
import com.example.hotelbooking.model.Room;

public class HotelDetailFragment extends Fragment {

    private P3FragmentHotelDetailBinding binding;
    private HotelsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentHotelDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(HotelsViewModel.class);

        Hotel hotel = viewModel.getHotels().get(0);
        binding.setHotel(hotel);
        binding.executePendingBindings();

        Room[] selectedRoom = {hotel.getRooms().get(0)};

        binding.recyclerRooms.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerRooms.setAdapter(new RoomAdapter(hotel.getRooms(), room -> {
            selectedRoom[0] = room;
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}