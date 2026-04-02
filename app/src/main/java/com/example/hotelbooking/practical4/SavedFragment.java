package com.example.hotelbooking.practical4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.databinding.P3FragmentSavedBinding;
import com.example.hotelbooking.model.SavedViewModel;

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
        binding.recyclerSaved.setAdapter(new HotelAdapter(
                savedViewModel.getSavedHotels(),
                hotel -> {
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