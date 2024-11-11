package com.rijai.users.services;

import com.rijai.users.model.AdoptionInterest;
import com.rijai.users.repositry.AdoptionInterestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionInterestService {
    private final AdoptionInterestRepository adoptionInterestRepository;

    public AdoptionInterestService(AdoptionInterestRepository adoptionInterestRepository) {
        this.adoptionInterestRepository = adoptionInterestRepository;
    }

    public AdoptionInterest createAdoption(AdoptionInterest adoptionInterest) {
        return adoptionInterestRepository.save(adoptionInterest);
    }

    public List<AdoptionInterest> findUserAdoption(List<String> id) {
        return (List<AdoptionInterest>) adoptionInterestRepository.findAllById(id);
    }

    public List<AdoptionInterest> adoptionInterestList() {
        return (List<AdoptionInterest>) adoptionInterestRepository.findAll();
    }

    public Optional<AdoptionInterest> findAdoptionInterest(String id) {
        if (adoptionInterestRepository.existsById(id)) {
            return adoptionInterestRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    public AdoptionInterest updateAdoptionInterestStatus(String id, AdoptionInterest adoptionInterest) {
        Optional<AdoptionInterest> placeholder = adoptionInterestRepository.findById(id);
        // if there is a dog with that id, update the dog and save it
        if (placeholder.isPresent()) {
            AdoptionInterest aiUpdated = placeholder.get();
            if (adoptionInterest.getStatus().equals("Accept")) {
                aiUpdated.setStatus("Accepted");
            } else if (adoptionInterest.getStatus().equals("Denied")) {
                aiUpdated.setStatus("Denied");
            } else {
                return new AdoptionInterest();
            }
            return adoptionInterestRepository.save(aiUpdated);
        } else {
            return new AdoptionInterest();
        }
    }

    public void deleteAdoptionInterest(String id) {
        if (adoptionInterestRepository.existsById(id)) {
            adoptionInterestRepository.deleteById(id);
        } else {
            System.out.println("Adoption Interest Deleted!");
        }
    }
}
