package com.example.hotelbooking.practical5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentSavedBinding;
import com.example.hotelbooking.model.SavedViewModel;
import com.example.hotelbooking.practical4.HotelAdapter;

public class SavedFragment extends Fragment {
    private P3FragmentSavedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentSavedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SavedViewModel savedViewModel = new ViewModelProvider(requireActivity())
                .get(SavedViewModel.class);


        binding.recyclerSaved.setLayoutManager(new LinearLayoutManager(requireContext()));
        NavController navController = NavHostFragment.findNavController(this);

        binding.recyclerSaved.setAdapter(new HotelAdapter(
                savedViewModel.getSavedHotels(),
                hotel -> {
                    int hotelId = savedViewModel.getSavedHotels().indexOf(hotel);
                    Bundle args = new Bundle();
                    args.putInt("hotelId", hotelId);
                    navController.navigate(R.id.action_savedFragment_to_hotelDetailFragment, args);
                },
                (hotel, position) -> {
                    savedViewModel.toggleSaved(hotel);
                    binding.recyclerSaved.getAdapter().notifyDataSetChanged();
                    binding.textSavedCount.setText(savedViewModel.getCount() + " збережених готелів");
                }
        ));

        binding.textSavedCount.setText(savedViewModel.getCount() + " збережених готелів");
    }
}