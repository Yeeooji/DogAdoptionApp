package com.rijai.users.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // you can exclude id from post method because it is auto-generated
    private long id;

    // TODO: Determine how to store images in database
    @Lob
    private byte[] picture;
    private String pictureName;
    private String pictureType;

    private String name;
    private String age;
    private String dateOfBirth;
    private String gender;
    private String breed;
    private String height;
    private String weight;
    private String medicalConditions;

    public Dog() {
    }




    public Dog(long id, byte[] picture, String pictureName, String pictureType, String name, String age, String dateOfBirth, String gender, String breed, String height, String weight, String medicalConditions) {
        this.id = id;
        this.picture = picture;
        this.pictureName = pictureName;
        this.pictureType = pictureType;
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.breed = breed;
        this.height = height;
        this.weight = weight;
        this.medicalConditions = medicalConditions;
    }



    public long getId() {
        return id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", picture=" + Arrays.toString(picture) +
                ", pictureName='" + pictureName + '\'' +
                ", pictureType='" + pictureType + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", breed='" + breed + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", medicalConditions='" + medicalConditions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return getId() == dog.getId() && getAge() == dog.getAge() && getHeight() == dog.getHeight() && getWeight() == dog.getWeight() && Arrays.equals(getPicture(), dog.getPicture()) && Objects.equals(getPictureName(), dog.getPictureName()) && Objects.equals(getPictureType(), dog.getPictureType()) && Objects.equals(getName(), dog.getName()) && Objects.equals(getDateOfBirth(), dog.getDateOfBirth()) && Objects.equals(getGender(), dog.getGender()) && Objects.equals(getBreed(), dog.getBreed()) && Objects.equals(getMedicalConditions(), dog.getMedicalConditions());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getPictureName(), getPictureType(), getName(), getAge(), getDateOfBirth(), getGender(), getBreed(), getHeight(), getWeight(), getMedicalConditions());
        result = 31 * result + Arrays.hashCode(getPicture());
        return result;
    }

}
