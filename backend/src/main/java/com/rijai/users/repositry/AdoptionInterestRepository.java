package com.rijai.users.repositry;

import com.rijai.users.model.AdoptionInterest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionInterestRepository extends CrudRepository<AdoptionInterest, String> {
}
