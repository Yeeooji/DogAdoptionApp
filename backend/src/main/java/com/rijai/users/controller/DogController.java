package com.rijai.users.controller;

import com.rijai.users.model.Dog;
import com.rijai.users.services.DogService;
import com.rijai.users.services.AdminService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class DogController {
    public final DogService dogService;

    public DogController(DogService dogService, AdminService adminService) {
        this.dogService = dogService;
    }

    @GetMapping("dogs")
    public List<Dog> getAllDogs() {
        return dogService.getAllDogs();
    }

    @GetMapping("dog/{id}")
    public Dog getDogById(@PathVariable Long id) {
        return dogService.getDogById(id).orElse(null);
    }

    @PostMapping("/dog")
    public String addDog(@RequestParam("dogImage") MultipartFile dogImage, @ModelAttribute Dog dog) {
        try {
            String pictureName = dogImage.getOriginalFilename();
            String pictureType = dogImage.getContentType();
            byte[] fileContent = dogImage.getBytes();
            // Set image information
            dog.setPictureName(pictureName);
            dog.setPictureType(pictureType);
            dog.setPicture(fileContent);
            dogService.addDog(dog);
        } catch (Exception e) {
            return "Dog not saved\n" + e;
        }
        return "";
    }

    @PostMapping("/dog/update/{id}")
    public String updateDog(
            @PathVariable("id") long id, // Path variable to identify the dog by its ID
            @RequestParam("dogImage") MultipartFile dogImage,
            @ModelAttribute Dog dog) {
        try {
            // Check if the dog with the specified ID exists in the database


            Optional<Dog> dogOptional = dogService.getDogById(id);

            if (dogOptional.isPresent()) {
                Dog existingDog = dogOptional.get();
                // Update the dog's information, including the image if provided
                String pictureName = dogImage.getOriginalFilename();
                String pictureType = dogImage.getContentType();
                byte[] fileContent = dogImage.getBytes();

                // Update the dog's information
                existingDog.setPictureName(pictureName);
                existingDog.setPictureType(pictureType);
                existingDog.setPicture(fileContent);
                existingDog.setName(dog.getName()); // Update other dog fields as needed
                existingDog.setAge(dog.getAge());
                existingDog.setDateOfBirth(dog.getDateOfBirth());
                existingDog.setGender(dog.getGender());
                existingDog.setBreed(dog.getBreed());
                existingDog.setHeight(dog.getHeight());
                existingDog.setWeight(dog.getWeight());
                existingDog.setMedicalConditions(dog.getMedicalConditions());

                dogService.updateDog(id, existingDog);
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return "Dog updated successfully";

    }


    @DeleteMapping("dog/{id}")
    public void deleteDogById(@PathVariable Long id) {
        dogService.deleteDogById(id);
    }
}
