package com.research.service;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Staff;
import com.research.repository.StaffRepository;

import java.util.List;

public class StaffService {
    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void addStaff(Staff staff) throws DuplicateIdException {
        staffRepository.addStaff(staff);
    }

    public Staff getStaffById(int id) throws EntityNotFoundException {
        return staffRepository.getStaffById(id);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.getAllStaff();
    }

    public void updateStaff(Staff staff) throws EntityNotFoundException {
        staffRepository.updateStaff(staff);
    }

    public void deleteStaff(int id) throws EntityNotFoundException {
        staffRepository.deleteStaff(id);
    }

    public List<Staff> searchStaffByName(String name) {
        return staffRepository.searchByName(name);
    }

    public List<Staff> searchStaffByRole(String role) {
        return staffRepository.searchByRole(role);
    }
}