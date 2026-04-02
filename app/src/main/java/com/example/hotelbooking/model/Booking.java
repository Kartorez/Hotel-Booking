package com.example.hotelbooking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.hotelbooking.BR;

public class Booking extends BaseObservable {
    @Bindable
    private String checkInDate;
    @Bindable
    private String checkOutDate;
    @Bindable
    private String firstGuestName;
    @Bindable
    private String email;
    @Bindable
    private String phone;
    @Bindable
    private String roomType;

    private int totalPrice;

    private String status;
    private int hotelId;
    private int roomPrice;


    public Booking() {
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String v) {
        checkInDate = v;
        notifyPropertyChanged(BR.checkInDate);
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String v) {
        checkOutDate = v;
        notifyPropertyChanged(BR.checkOutDate);
    }

    public String getFirstGuestName() {
        return firstGuestName;
    }

    public void setFirstGuestName(String v) {
        firstGuestName = v;
        notifyPropertyChanged(BR.firstGuestName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        email = v;
        notifyPropertyChanged(BR.email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String v) {
        phone = v;
        notifyPropertyChanged(BR.phone);
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String v) {
        roomType = v;
        notifyPropertyChanged(BR.roomType);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int v) {
        hotelId = v;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int v) {
        roomPrice = v;
    }

}