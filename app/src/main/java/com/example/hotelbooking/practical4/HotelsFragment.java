package com.example.hotelbooking.practical4;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentHotelsBinding;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelsViewModel;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class HotelsFragment extends Fragment {

    private P3FragmentHotelsBinding binding;
    private HotelsViewModel viewModel;
    private HotelAdapter adapter;
    private final List<Hotel> filteredHotels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentHotelsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HotelsViewModel.class);

        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.getController().setZoom(6.0);
        binding.mapView.getController().setCenter(new GeoPoint(49.0, 31.0));

        filteredHotels.addAll(viewModel.getHotels());

        adapter = new HotelAdapter(filteredHotels, hotel -> {
        }, (hotel, position) -> {});

        binding.recyclerHotels.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerHotels.setAdapter(adapter);

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.search.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (binding.search.getCompoundDrawables()[2] != null) {
                    int drawableEnd = binding.search.getRight()
                            - binding.search.getCompoundDrawables()[2].getBounds().width()
                            - binding.search.getPaddingEnd();
                    if (event.getRawX() >= drawableEnd) {
                        binding.search.setText("");
                        return true;
                    }
                }
            }
            return false;
        });

        binding.toggleGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.buttonShowList) {
                binding.recyclerHotels.setVisibility(View.VISIBLE);
                binding.mapView.setVisibility(View.GONE);

                TextViewCompat.setCompoundDrawableTintList(binding.buttonShowList,
                        ColorStateList.valueOf(Color.parseColor("#008AC9")));
                binding.buttonShowList.setTextColor(Color.parseColor("#008AC9"));

                TextViewCompat.setCompoundDrawableTintList(binding.buttonShowMap,
                        ColorStateList.valueOf(Color.parseColor("#1C1B1F")));
                binding.buttonShowMap.setTextColor(Color.parseColor("#1C1B1F"));
            } else {
                binding.recyclerHotels.setVisibility(View.GONE);
                binding.mapView.setVisibility(View.VISIBLE);

                TextViewCompat.setCompoundDrawableTintList(binding.buttonShowMap,
                        ColorStateList.valueOf(Color.parseColor("#008AC9")));
                binding.buttonShowMap.setTextColor(Color.parseColor("#008AC9"));

                TextViewCompat.setCompoundDrawableTintList(binding.buttonShowList,
                        ColorStateList.valueOf(Color.parseColor("#1C1B1F")));
                binding.buttonShowList.setTextColor(Color.parseColor("#1C1B1F"));
            }
        });

        binding.toggleGroup.check(R.id.buttonShowList);
        TextViewCompat.setCompoundDrawableTintList(binding.buttonShowList,
                ColorStateList.valueOf(Color.parseColor("#008AC9")));
        binding.buttonShowList.setTextColor(Color.parseColor("#008AC9"));

        updateMarkers(filteredHotels);
    }

    private void updateMarkers(List<Hotel> hotels) {
        binding.mapView.getOverlays().clear();

        for (Hotel hotel : hotels) {
            Marker marker = new Marker(binding.mapView);
            marker.setPosition(new GeoPoint(hotel.getLatitude(), hotel.getLongitude()));
            marker.setTitle(hotel.getName());
            marker.setSnippet("₴" + (int) hotel.getPricePerNight());
            binding.mapView.getOverlays().add(marker);
        }

        binding.mapView.invalidate();
    }

    private void filter(String query) {
        filteredHotels.clear();

        if (query.isEmpty()) {
            filteredHotels.addAll(viewModel.getHotels());
        } else {
            String lower = query.toLowerCase();

            for (Hotel hotel : viewModel.getHotels()) {
                if (hotel.getName().toLowerCase().contains(lower)
                        || hotel.getCity().toLowerCase().contains(lower)
                        || hotel.getAddress().toLowerCase().contains(lower)) {
                    filteredHotels.add(hotel);
                }
            }
        }

        adapter.notifyDataSetChanged();
        updateMarkers(filteredHotels);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}