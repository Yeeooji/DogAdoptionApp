package com.rijai.users.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin")
public class Admin {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Primary key
    @Id
    private String username;
    private String password;
    private String address;

    public Admin() {
    }

    public Admin(long id, String username, String password, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return getId() == admin.getId() && Objects.equals(getUsername(), admin.getUsername()) && Objects.equals(getPassword(), admin.getPassword()) && Objects.equals(getAddress(), admin.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getAddress());
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
