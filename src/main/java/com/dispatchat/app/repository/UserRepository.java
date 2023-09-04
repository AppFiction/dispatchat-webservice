package com.dispatchat.app.repository;

import com.dispatchat.app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
}
