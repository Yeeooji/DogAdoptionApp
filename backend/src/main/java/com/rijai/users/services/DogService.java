package com.rijai.users.services;

import com.rijai.users.model.Dog;
import com.rijai.users.repositry.DogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }


    public List<Dog> getAllDogs() {
        return (List<Dog>) dogRepository.findAll();
    }


    public Optional<Dog> getDogById(Long id) {
        // automatically returns null if there are no results
        return dogRepository.findById(id);
    }

    public void addDog(Dog dog) {
        dogRepository.save(dog);
    }

    public void updateDog(Long id, Dog dog) {
        // Store the dog in placeholder variable based on id
        Optional<Dog> placeholder = dogRepository.findById(id);
        // if there is a dog with that id, update the dog and save it
        if (placeholder.isPresent()) {
            Dog updatedDog = getUpdatedDog(dog, placeholder);
            dogRepository.save(updatedDog);
        }
        // if there is no dog with that id, return null
        else {
            System.out.println("Dog not found");
        }
    }

    private static Dog getUpdatedDog(Dog dog, Optional<Dog> placeholder) {
        Dog updatedDog = placeholder.get();
        updatedDog.setPicture(dog.getPicture());
        updatedDog.setName(dog.getName());
        updatedDog.setAge(dog.getAge());
        updatedDog.setDateOfBirth(dog.getDateOfBirth());
        updatedDog.setGender(dog.getGender());
        updatedDog.setBreed(dog.getBreed());
        updatedDog.setHeight(dog.getHeight());
        updatedDog.setWeight(dog.getWeight());
        updatedDog.setMedicalConditions(dog.getMedicalConditions());
        return updatedDog;
    }


    public void deleteDogById(Long id) {
        // store the dog in placeholder variable based on id
        Optional<Dog> placeholder = dogRepository.findById(id);
        // if dog with the id is present, delete it
        if (placeholder.isPresent()) {
            dogRepository.deleteById(id);
        }
        // if dog with the id is not present, print "Dog not found"
        else {
            System.out.println("Dog not found");
        }
    }
}
