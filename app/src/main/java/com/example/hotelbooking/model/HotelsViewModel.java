package com.example.hotelbooking.model;

import androidx.lifecycle.ViewModel;

import com.example.hotelbooking.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelsViewModel extends ViewModel {
    private List<Hotel> hotels = new ArrayList<>();

    public HotelsViewModel() {
        hotels.add(new Hotel(0, "Panorama Grand", "вул. Хрещатик, 1", "Київ", 4.7f, 3200, 8, R.drawable.hotel_1,
                Arrays.asList(
                        new Room(0, "Стандарт", 2200, 20, 2, 8),
                        new Room(1, "Делюкс", 3200, 32, 2, 4),
                        new Room(2, "Люкс", 5600, 55, 2, 2),
                        new Room(3, "Сімейний", 4800, 45, 4, 3)
                ), 50.4501, 30.5234));

        hotels.add(new Hotel(1, "The Fortress", "пл. Ринок, 4", "Львів", 4.9f, 5600, 3, R.drawable.hotel_2,
                Arrays.asList(
                        new Room(0, "Стандарт", 3000, 22, 2, 3),
                        new Room(1, "Делюкс", 4200, 35, 2, 2),
                        new Room(2, "Люкс", 6500, 60, 2, 1)
                ), 49.8397, 24.0297));

        hotels.add(new Hotel(2, "Eco Retreat", "вул. Лісова, 12", "Яремче", 4.5f, 2240, 5, R.drawable.hotel_3,
                Arrays.asList(
                        new Room(0, "Стандарт", 1800, 18, 2, 5),
                        new Room(1, "Сімейний", 3200, 40, 4, 2)
                ), 48.4554, 24.5487));

        hotels.add(new Hotel(3, "Marina Palace", "Морська набережна, 1", "Одеса", 4.5f, 3485, 6, R.drawable.hotel_4,
                Arrays.asList(
                        new Room(0, "Стандарт", 2500, 20, 2, 6),
                        new Room(1, "Делюкс", 3800, 34, 2, 3),
                        new Room(2, "Люкс", 5800, 58, 2, 1)
                ), 46.4825, 30.7233));

        hotels.add(new Hotel(4, "Skyline Tower", "просп. ВДНГ, 1", "Київ", 4.8f, 7800, 2, R.drawable.hotel_5,
                Arrays.asList(
                        new Room(0, "Делюкс", 6000, 38, 2, 2),
                        new Room(1, "Люкс", 9000, 65, 2, 1)
                ), 50.4720, 30.6350));
    }

    public List<Hotel> getHotels() {
        return hotels;
    }
}
