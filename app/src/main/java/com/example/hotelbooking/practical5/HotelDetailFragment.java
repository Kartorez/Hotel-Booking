package com.example.hotelbooking.practical5;

import android.content.Intent;
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
import com.example.hotelbooking.model.SavedViewModel;
import com.example.hotelbooking.practical4.RoomAdapter;
import com.google.firebase.auth.FirebaseAuth;

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

        final int hotelId = getArguments() != null
                ? getArguments().getInt("hotelId", 0) : 0;

        Hotel hotel = viewModel.getHotels().get(hotelId);
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

        binding.textViewBarPrice.setText("₴" + selectedRoom[0].getPricePerNight() + "/за ніч");
        binding.textViewGuestCount.setText(selectedRoom[0].getMaxGuests() + " гостей");

        binding.recyclerRooms.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerRooms.setAdapter(new RoomAdapter(hotel.getRooms(), room -> {
            selectedRoom[0] = room;
            binding.textViewBarPrice.setText("₴" + room.getPricePerNight() + "/за ніч");
            binding.textViewGuestCount.setText(room.getMaxGuests() + " гостей");
        }));

        binding.buttonBook.setOnClickListener(v -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Потрібна авторизація")
                        .setMessage("Увійдіть в акаунт щоб забронювати готель")
                        .setPositiveButton("Увійти", (dialog, which) ->
                                startActivity(new Intent(requireActivity(),
                                        com.example.hotelbooking.practical6.LoginActivity.class))
                        )
                        .setNegativeButton("Скасувати", null)
                        .show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("hotelId", hotelId);
            bundle.putString("roomType", selectedRoom[0].getType());
            bundle.putInt("roomPrice", selectedRoom[0].getPricePerNight());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_detail_to_booking, bundle);
        });

        binding.buttonOpenMap.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putBoolean("showMap", true);
            args.putString("searchAddress", hotel.getAddress() + ", " + hotel.getCity());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_hotelDetailFragment_to_hotelsFragment, args);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}