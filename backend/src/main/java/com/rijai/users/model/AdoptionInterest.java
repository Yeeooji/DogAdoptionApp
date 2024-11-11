package com.rijai.users.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AdoptionInterest {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // TODO: Determine relationship between AdoptionInterest, User, and Dog models
    @Id
    private String userEmail;
    private String userName;
    private String dogName;
    private String dateAndTimeCreated;
    private String adoptionStatus;

    public AdoptionInterest() {
    }

    public AdoptionInterest(long id, String userEmail, String userName, String dogName, String dateAndTimeCreated, String adoptionStatus) {
        this.id = id;
        this.userEmail = userEmail;
        this.userName = userName;
        this.dogName = dogName;
        this.dateAndTimeCreated = dateAndTimeCreated;
        this.adoptionStatus = adoptionStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
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
        return adoptionStatus;
    }

    public void setStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
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
                ", name='" + userName + '\'' +
                ", dogName='" + dogName + '\'' +
                ", dateAndTimeCreated='" + dateAndTimeCreated + '\'' +
                ", adoptionStatus='" + adoptionStatus + '\'' +
                '}';
    }
}
