package com.example.hotelbooking.practical5;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import com.example.hotelbooking.R;
import com.example.hotelbooking.databinding.P3FragmentHotelsBinding;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelsViewModel;
import com.example.hotelbooking.practical4.HotelAdapter;

public class HotelsFragment extends Fragment {

    private P3FragmentHotelsBinding binding;
    private HotelsViewModel viewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = P3FragmentHotelsBinding.inflate(inflater, container, false);

        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());

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

        for (Hotel hotel : viewModel.getHotels()) {
            Marker marker = new Marker(binding.mapView);
            marker.setPosition(new GeoPoint(hotel.getLatitude(), hotel.getLongitude()));
            marker.setTitle(hotel.getName());
            marker.setSnippet("₴" + (int) hotel.getPricePerNight());
            binding.mapView.getOverlays().add(marker);
        }

        binding.recyclerHotels.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerHotels.setAdapter(new HotelAdapter(viewModel.getHotels(), hotel -> {
            Bundle args = new Bundle();
            args.putInt("hotelId", hotel.id);
            Navigation.findNavController(view)
                    .navigate(R.id.action_hotelsFragment_to_hotelDetailFragment, args);
        }));

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}