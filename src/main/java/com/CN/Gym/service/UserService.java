package com.CN.Gym.service;

import com.CN.Gym.dto.UserRequest;
import com.CN.Gym.dto.WorkoutDto;
import com.CN.Gym.exception.UserNotFoundException;
import com.CN.Gym.model.Role;
import com.CN.Gym.model.User;
import com.CN.Gym.model.Workout;
import com.CN.Gym.repository.UserRepository;
import com.CN.Gym.repository.WorkoutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkoutRepository workoutRepository;

    public List<User> getAllUsers() {
    	return userRepository.findAll();
    }

    public void createUser(UserRequest userRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userRequest.getPassword());
        User user = User.builder().email(userRequest.getEmail()).age(userRequest.getAge())
                .gender(userRequest.getGender()).password(encodedPassword)
                .build();
        Role role = new Role();
        Set<Role> roles = new HashSet<>();
        if(userRequest.getUserType() != null) {
            if (userRequest.getUserType().equalsIgnoreCase("TRAINER")) {
                role.setRoleName("ROLE_TRAINER");
                roles.add(role);
                user.setRoles(roles);
            } else if (userRequest.getUserType().equalsIgnoreCase("ADMIN")) {
                role.setRoleName("ROLE_ADMIN");
                roles.add(role);
                user.setRoles(roles);
            } else {
                role.setRoleName("ROLE_CUSTOMER");
                roles.add(role);
                user.setRoles(roles);
            }
        }
        else {
            role.setRoleName("ROLE_CUSTOMER");
            roles.add(role);
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public User getUserById(Long id) {
    	return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist with id "+id));
    }

    public void updateUser(UserRequest userRequest, Long id) {
    	User user = this.getUserById(id);
    	
    	if(userRequest.getPassword()!=null) {
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(userRequest.getPassword());
            user.setPassword(encodedPassword);
    	}
    	if(userRequest.getEmail()!=null) {
    		user.setEmail(userRequest.getEmail());
    	}
    	if(userRequest.getGender()!=null) {
    		user.setGender(userRequest.getGender());
    	}
    	if(userRequest.getAge()>=0) {
    		user.setAge(userRequest.getAge());
    	}
    	
    	userRepository.save(user);
    }

    public void deleteUser(Long id) {
    	User user = this.getUserById(id);
    	userRepository.delete(user);
    }

    public User getUserByEmail(String email) {
    	return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with given email"));
    }

    public void addWorkout(WorkoutDto workoutDto, Long userId) {
    	User user = this.getUserById(userId);
    	
    	Workout workout = new Workout();
    	workout.setWorkoutName(workoutDto.getWorkoutName());
    	workout.setDescription(workoutDto.getDescription());
    	workout.setDifficultyLevel(workoutDto.getDifficultyLevel());
    	workout.setDuration(workoutDto.getDuration());
    	workout.setUser(user);
    	
    	workoutRepository.save(workout);
    }
}
