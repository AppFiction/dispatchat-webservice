package com.dispatchat.app.repository;
import org.springframework.data.repository.CrudRepository;

import com.dispatchat.app.entity.Load;

import java.util.List;

public interface LoadRepository extends CrudRepository<Load, Long> {

    List<Load> findByUserID(Long userID);

}
