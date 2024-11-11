package com.example.dogapp.model;


import java.util.Objects;

public class AdoptionInterest {
    private String id;

    // user's email
    private String userEmail;

    // user's name
    private String name;

    // selected dog's name for adoption
    private String dogName;
    private String dateAndTimeCreated;

    // adoption status: Pending, Approved, Rejected
    private String status;

    public AdoptionInterest() {
    }

    public AdoptionInterest(String id, String userEmail, String userName, String dogName, String dateAndTimeCreated, String adoptionStatus) {
        this.id = id;
        this.userEmail = userEmail;
        this.name = userName;
        this.dogName = dogName;
        this.dateAndTimeCreated = dateAndTimeCreated;
        this.status = adoptionStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDateAndTimeCreated() {
        return dateAndTimeCreated;
    }

    public void setDateAndTimeCreated(String dateAndTimeCreated) {
        this.dateAndTimeCreated = dateAndTimeCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String adoptionStatus) {
        this.status = adoptionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptionInterest that = (AdoptionInterest) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getDogName(), that.getDogName()) && Objects.equals(getDateAndTimeCreated(), that.getDateAndTimeCreated()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDogName(), getDateAndTimeCreated(), getStatus());
    }

    @Override
    public String toString() {
        return "AdoptionInterest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dogName='" + dogName + '\'' +
                ", dateAndTimeCreated='" + dateAndTimeCreated + '\'' +
                ", adoptionStatus='" + status + '\'' +
                '}';
    }
}
