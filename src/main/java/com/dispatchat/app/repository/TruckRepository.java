package com.dispatchat.app.repository;
import org.springframework.data.repository.CrudRepository;

import com.dispatchat.app.entity.Truck;

import java.util.List;

public interface TruckRepository extends CrudRepository<Truck, Long> {

    List<Truck> findByUserID(Long userID);
}
