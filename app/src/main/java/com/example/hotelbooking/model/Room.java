package com.example.hotelbooking.model;

import androidx.databinding.Bindable;

public class Room {
    private int id;
    private String type;
    private double pricePerNight;
    private int squareMeters;
    private int maxGuests;

    private int imageRes;

    private int availableRooms;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public Room(int id, String type, double pricePerNight,
                int squareMeters, int maxGuests, int availableRooms) {
        this.id = id;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.squareMeters = squareMeters;
        this.maxGuests = maxGuests;
        this.availableRooms = availableRooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }
}