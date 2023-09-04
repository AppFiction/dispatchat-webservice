package com.dispatchat.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dispatchat.app.entity.Truck;
import com.dispatchat.app.repository.TruckRepository;

@Controller
@RequestMapping(path = "/trucks")
public class TruckController {

	@Autowired
	private TruckRepository truckRepository;

	@PostMapping(path = "/add")
	public @ResponseBody Truck addNewTruck(@RequestBody Truck truck) {
		return truckRepository.save(truck);
	}

	@GetMapping("/{id}")
	public @ResponseBody Truck getTruckById(@PathVariable Long id) {
		return truckRepository.findById(id).orElse(null);
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Truck> getAllTrucks(@RequestBody Truck truck) {
		Long userID = truck.getUserID();

		if (userID != null) {
			return truckRepository.findByUserID(userID);
		} else {
			return truckRepository.findAll();
		}
	}
	@DeleteMapping("/{id}")
	public @ResponseBody String deleteTruck(@PathVariable Long id) {
		truckRepository.deleteById(id);
		return "deleted";
	}

}
