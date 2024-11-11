package com.rijai.users.controller;

import com.rijai.users.model.Admin;
import com.rijai.users.services.AdminService;
import com.rijai.users.services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AdminController {

    public final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @RequestMapping(value = "/admin/{username}/{password}")
    public Optional<Admin> findAdminById(@PathVariable String username, @PathVariable String password){
        Optional<Admin> placeholder = adminService.findAdminUsername(username);
        if(placeholder.isPresent()){
            Admin admin = placeholder.get();
            System.out.println(admin.getPassword().equals(password));
            if(admin.getPassword().equals(password)){
                return placeholder;
            }else{
                return Optional.of(new Admin());
            }
        }else {
            return Optional.of(new Admin());
        }
    }
    @RequestMapping(value = "admin/create")
    public Admin createAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }
    @RequestMapping(value = "/admin/{username}/delete")
    public void deleteAdmin(@PathVariable String username){
        adminService.deleteAdmin(username);
    }
}
