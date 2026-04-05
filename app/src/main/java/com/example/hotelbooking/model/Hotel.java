package com.example.hotelbooking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.List;

public class Hotel extends BaseObservable {
    public int id;
    private String name;
    private String address;
    private String city;
    @Bindable
    private float rating;
    private int pricePerNight;
    private int availableRooms;
    private int imageRes;
    private boolean saved = false;
    private double latitude;

    private double longitude;
    private List<Room> rooms;

    public Hotel() {}

    public Hotel(int id, String name, String address, String city,
                 float rating, int pricePerNight, int availableRooms,
                 int imageRes, List<Room> rooms, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
        this.imageRes = imageRes;
        this.rooms = rooms;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getTotalAvailableRooms() {
        int total = 0;
        for (Room room : rooms) {
            total += room.getAvailableRooms();
        }
        return total;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}