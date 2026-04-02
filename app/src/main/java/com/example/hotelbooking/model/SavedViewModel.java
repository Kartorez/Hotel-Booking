package com.example.hotelbooking.model;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SavedViewModel extends ViewModel {
    private List<Hotel> savedHotels = new ArrayList<>();

    public List<Hotel> getSavedHotels() { return savedHotels; }

    public void toggleSaved(Hotel hotel) {
        if (hotel.isSaved()) {
            hotel.setSaved(false);
            savedHotels.remove(hotel);
        } else {
            hotel.setSaved(true);
            savedHotels.add(hotel);
        }
    }

    public int getCount() { return savedHotels.size(); }
}