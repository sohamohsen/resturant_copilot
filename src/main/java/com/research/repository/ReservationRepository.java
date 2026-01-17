package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Reservation;

import java.util.*;

public class ReservationRepository {
    private final Map<Integer, Reservation> reservationMap = new HashMap<>();

    public void addReservation(Reservation reservation) throws DuplicateIdException {
        if (reservationMap.containsKey(reservation.getId()))
            throw new DuplicateIdException("Reservation ID already exists!");
        reservationMap.put(reservation.getId(), reservation);
    }

    public Reservation getReservationById(int id) throws EntityNotFoundException {
        Reservation reservation = reservationMap.get(id);
        if (reservation == null)
            throw new EntityNotFoundException("Reservation not found!");
        return reservation;
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservationMap.values());
    }

    public void updateReservation(Reservation reservation) throws EntityNotFoundException {
        if (!reservationMap.containsKey(reservation.getId()))
            throw new EntityNotFoundException("Reservation not found!");
        reservationMap.put(reservation.getId(), reservation);
    }

    public void deleteReservation(int id) throws EntityNotFoundException {
        if (!reservationMap.containsKey(id))
            throw new EntityNotFoundException("Reservation not found!");
        reservationMap.remove(id);
    }
}