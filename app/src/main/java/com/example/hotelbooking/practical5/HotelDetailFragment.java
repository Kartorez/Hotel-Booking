package com.example.hotelbooking.practical5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentHotelDetailBinding;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelsViewModel;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.practical4.RoomAdapter;

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

        int hotelId = getArguments().getInt("hotelId", 0);
        Hotel hotel = viewModel.getHotels().get(hotelId);
        binding.setHotel(hotel);
        binding.executePendingBindings();

        Room[] selectedRoom = {hotel.getRooms().get(0)};
        binding.textViewBarPrice.setText("₴" + (int) selectedRoom[0].getPricePerNight() + "/за ніч");
        binding.textViewGuestCount.setText(selectedRoom[0].getMaxGuests()+" гостей");


        binding.recyclerRooms.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerRooms.setAdapter(new RoomAdapter(hotel.getRooms(), room -> {
            selectedRoom[0] = room;
            binding.textViewBarPrice.setText("₴" + (int) room.getPricePerNight() + "/за ніч");
            binding.textViewGuestCount.setText(room.getMaxGuests()+" гостей");

        }));

        binding.buttonBook.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("hotelId", hotelId);
            args.putString("roomType", selectedRoom[0].getType());
            args.putDouble("roomPrice", selectedRoom[0].getPricePerNight());
            Navigation.findNavController(view)
                    .navigate(R.id.action_hotelDetailFragment_to_bookingFragment, args);
        });


        binding.buttonOpenMap.setOnClickListener(v ->
                Navigation.findNavController(view)
                        .navigate(R.id.action_hotelDetailFragment_to_hotelsFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}