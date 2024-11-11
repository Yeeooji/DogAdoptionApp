package com.rijai.users.services;

import com.rijai.users.model.Admin;
import com.rijai.users.repositry.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> findAdminUsername(String username) {
        if (adminRepository.existsById(username)) {
            return adminRepository.findById(username);
        } else {
            return Optional.empty();
        }
    }

    public Admin addAdmin(Admin admin) {
        admin.setUsername(admin.getUsername());
        admin.setAddress(admin.getAddress());
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public void deleteAdmin(String username) {
        if (adminRepository.existsById(username)) {
            adminRepository.deleteById(username);
        } else {
            System.out.println("Admin Deleted!");
        }
    }

    public boolean isPasswordCorrect(Admin admin, String enteredPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(enteredPassword, admin.getPassword());
    }

}
