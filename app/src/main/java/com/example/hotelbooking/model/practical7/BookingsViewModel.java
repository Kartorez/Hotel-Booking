package com.example.hotelbooking.model.practical7;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.HotelsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BookingsViewModel extends ViewModel {
    private static final String TAG = "BookingsViewModel";

    private MutableLiveData<List<Booking>> bookings = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Hotel>> bookedHotels = new MutableLiveData<>(new ArrayList<>());

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collection;
    private List<Hotel> allHotels;

    public BookingsViewModel() {
        allHotels = new HotelsViewModel().getHotels();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        collection = db.collection("users/" + userId + "/bookings");
        load();
    }

    public LiveData<List<Booking>> getBookingsLive() {
        return bookings;
    }

    public LiveData<List<Hotel>> getBookedHotelsLive() {
        return bookedHotels;
    }

    public List<Booking> getBookings() {
        return bookings.getValue() != null ? bookings.getValue() : new ArrayList<>();
    }

    public List<Hotel> getBookedHotels() {
        return bookedHotels.getValue() != null ? bookedHotels.getValue() : new ArrayList<>();
    }

    private void load() {
        Log.d(TAG, "load() called");
        collection.get().addOnSuccessListener(snapshot -> {
            Log.d(TAG, "Documents count: " + snapshot.getDocuments().size());
            List<Booking> remoteBookings = new ArrayList<>();
            List<Hotel> remoteHotels = new ArrayList<>();

            for (var doc : snapshot.getDocuments()) {
                Booking b = doc.toObject(Booking.class);
                if (b != null) {
                    b.setFirestoreId(doc.getId());
                    remoteBookings.add(b);
                    Hotel hotel = allHotels.stream()
                            .filter(h -> h.getId() == b.getHotelId())
                            .findFirst()
                            .orElse(null);
                    remoteHotels.add(hotel);
                }
            }

            bookings.setValue(remoteBookings);
            bookedHotels.setValue(remoteHotels);
        }).addOnFailureListener(e -> Log.w(TAG, "Failed to load bookings", e));
    }

    public void addBooking(Booking booking, Hotel hotel) {
        Log.d(TAG, "addBooking called: " + booking.getHotelId());
        collection.add(booking).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Booking saved to Firestore: " + task.getResult().getId());
                load();
            } else {
                Log.w(TAG, "Failed to create booking", task.getException());
            }
        });
    }

    public void updateBooking(int position, Booking booking) {
        String firestoreId = booking.getFirestoreId();
        if (firestoreId == null || firestoreId.isEmpty()) {
            Log.w(TAG, "No Firestore ID to update");
            return;
        }

        collection.document(firestoreId).set(booking)
                .addOnSuccessListener(unused -> {
                    List<Booking> current = new ArrayList<>(getBookings());
                    current.set(position, booking);
                    bookings.setValue(current);
                })
                .addOnFailureListener(e -> Log.w(TAG, "Failed to update booking", e));
    }

    public void cancelBooking(int position) {
        List<Booking> current = new ArrayList<>(getBookings());
        if (position >= current.size()) return;

        Booking booking = current.get(position);
        booking.setStatus("Скасовано");

        String firestoreId = booking.getFirestoreId();
        if (firestoreId != null && !firestoreId.isEmpty()) {
            collection.document(firestoreId).set(booking)
                    .addOnFailureListener(e -> Log.w(TAG, "Failed to cancel booking", e));
        }

        current.set(position, booking);
        bookings.setValue(current);
        reload();
    }
    public void reload() {
        load();
    }
}