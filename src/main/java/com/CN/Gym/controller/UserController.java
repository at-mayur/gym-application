package com.CN.Gym.controller;


import com.CN.Gym.dto.UserRequest;
import com.CN.Gym.dto.WorkoutDto;
import com.CN.Gym.model.User;
import com.CN.Gym.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
		return userService.getAllUsers();
    }

	@PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRequest userRequest) {
		userService.createUser(userRequest);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public User getUserById(@PathVariable Long id) {
    	return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
    	userService.updateUser(userRequest, id);
    }

    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id){
    	userService.deleteUser(id);
    }

    @PostMapping("/workout/{userId}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<Object> addWorkout(@RequestBody WorkoutDto workoutDto, @PathVariable Long userId) {
    	userService.addWorkout(workoutDto, userId);
    	ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(HttpStatus.CREATED);
    	return responseEntity;
    }
}
