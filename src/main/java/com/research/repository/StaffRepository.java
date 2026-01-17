package com.research.repository;

import com.research.exception.DuplicateIdException;
import com.research.exception.EntityNotFoundException;
import com.research.model.Staff;

import java.util.*;

public class StaffRepository {
    private final Map<Integer, Staff> staffMap = new HashMap<>();

    public void addStaff(Staff staff) throws DuplicateIdException {
        if (staffMap.containsKey(staff.getId())) {
            throw new DuplicateIdException("Staff ID already exists.");
        }
        staffMap.put(staff.getId(), staff);
    }

    public Staff getStaffById(int id) throws EntityNotFoundException {
        Staff staff = staffMap.get(id);
        if (staff == null) throw new EntityNotFoundException("Staff not found!");
        return staff;
    }

    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffMap.values());
    }

    public void updateStaff(Staff staff) throws EntityNotFoundException {
        if (!staffMap.containsKey(staff.getId()))
            throw new EntityNotFoundException("Staff not found!");
        staffMap.put(staff.getId(), staff);
    }

    public void deleteStaff(int id) throws EntityNotFoundException {
        if (!staffMap.containsKey(id)) throw new EntityNotFoundException("Staff not found!");
        staffMap.remove(id);
    }

    public List<Staff> searchByName(String name) {
        List<Staff> result = new ArrayList<>();
        for (Staff staff : staffMap.values()) {
            if (staff.getFullName().toLowerCase().contains(name.toLowerCase()))
                result.add(staff);
        }
        return result;
    }

    public List<Staff> searchByRole(String role) {
        List<Staff> result = new ArrayList<>();
        for (Staff staff : staffMap.values()) {
            if (staff.getRole().equalsIgnoreCase(role))
                result.add(staff);
        }
        return result;
    }
}