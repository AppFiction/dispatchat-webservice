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

import com.dispatchat.app.entity.Load;
import com.dispatchat.app.repository.LoadRepository;


@Controller
@RequestMapping(path = "/loads")
public class LoadController {

	@Autowired
	private LoadRepository loadRepository;

	@PostMapping(path = "/")
	public @ResponseBody String test() {
		return "Load";
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody Load addNewLoad(@RequestBody Load load) {
		return loadRepository.save(load);
	}

	@GetMapping("/{id}")
	public @ResponseBody Load getLoadById(@PathVariable Long id) {
		return loadRepository.findById(id).orElse(null);
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Load> getLoads(@RequestBody Load load) {
		Long userID = load.getUserID();

		if (userID != null) {
			return loadRepository.findByUserID(userID);
		} else {
			return loadRepository.findAll();
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody String deleteLoad(@PathVariable Long id) {
		loadRepository.deleteById(id);
		return "deleted";
	}

}
