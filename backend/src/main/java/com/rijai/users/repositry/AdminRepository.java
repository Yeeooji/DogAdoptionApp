package com.rijai.users.repositry;

import com.rijai.users.model.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//pass password
@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {
}
