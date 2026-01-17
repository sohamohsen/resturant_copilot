package com.research.service;

import com.research.exception.BusinessRuleViolationException;
import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Reservation;
import com.research.model.RestaurantTable;
import com.research.model.TableStatus;
import com.research.repository.ReservationRepository;
import com.research.repository.TableRepository;

import java.util.List;

public class ReservationService {
    private final ReservationRepository repository;
    private final TableRepository tableRepository;

    public ReservationService(ReservationRepository repository, TableRepository tableRepository) {
        this.repository = repository;
        this.tableRepository = tableRepository;
    }

    public void createReservation(Reservation reservation) throws DuplicateIdException, BusinessRuleViolationException, EntityNotFoundException {
        RestaurantTable table = tableRepository.getTableById(reservation.getTable().getId());

        if (table.getStatus() != TableStatus.AVAILABLE)
            throw new BusinessRuleViolationException("Table is not available for reservation!");

        if (reservation.getNumberOfGuests() > table.getCapacity())
            throw new BusinessRuleViolationException("Number of guests exceeds table capacity!");

        for (Reservation existing : repository.getAllReservations()) {
            // Assumes overlap if date, time, and table are the same
            if (existing.getTable().getId() == table.getId() &&
                    existing.getReservationDate().equals(reservation.getReservationDate()) &&
                    existing.getReservationTime().equals(reservation.getReservationTime())) {
                throw new BusinessRuleViolationException("There is already a reservation for this table at this time.");
            }
        }

        repository.addReservation(reservation);
        table.setStatus(TableStatus.RESERVED);
        tableRepository.updateTable(table);
    }

    public Reservation getReservationById(int id) throws EntityNotFoundException {
        return repository.getReservationById(id);
    }

    public List<Reservation> getAllReservations() {
        return repository.getAllReservations();
    }

    public void cancelReservation(int id) throws EntityNotFoundException {
        Reservation reservation = repository.getReservationById(id);
        RestaurantTable table = tableRepository.getTableById(reservation.getTable().getId());
        table.setStatus(TableStatus.AVAILABLE);
        tableRepository.updateTable(table);
        repository.deleteReservation(id);
    }
}