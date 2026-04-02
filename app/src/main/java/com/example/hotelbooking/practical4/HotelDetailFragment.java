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
import com.example.hotelbooking.model.SavedViewModel;
import com.example.hotelbooking.R;

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

        viewModel = new ViewModelProvider(requireActivity()).get(HotelsViewModel.class);
        SavedViewModel savedViewModel = new ViewModelProvider(requireActivity())
                .get(SavedViewModel.class);

        Hotel hotel = viewModel.getHotels().get(0);
        binding.setHotel(hotel);
        binding.executePendingBindings();

        binding.floatingButtonLike.setImageResource(
                hotel.isSaved()
                        ? R.drawable.baseline_favorite_24
                        : R.drawable.baseline_favorite_border_24
        );

        binding.floatingButtonLike.setOnClickListener(v -> {
            savedViewModel.toggleSaved(hotel);
            binding.floatingButtonLike.setImageResource(
                    hotel.isSaved()
                            ? R.drawable.baseline_favorite_24
                            : R.drawable.baseline_favorite_border_24
            );
        });

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