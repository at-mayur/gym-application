package com.CN.Gym.service;


import com.CN.Gym.dto.GymDto;
import com.CN.Gym.exception.GymNotFoundException;
import com.CN.Gym.model.Gym;
import com.CN.Gym.model.User;
import com.CN.Gym.repository.GymRepository;
import com.CN.Gym.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;


@Service
public class GymService {

    /*
        This is the service class for Gym, you need to complete the class by doing the following:
        a. Use appropriate annotations.
        b. Complete the methods given below.
        c. Autowire the necessary dependencies.
     */
	@Autowired
	private GymRepository gymRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	UserService userService;


    public List<Gym> getAllGyms() {
    	return gymRepository.findAll();
    }

    public Gym getGymById(Long id) {
    	return gymRepository.findById(id).orElseThrow(() -> new GymNotFoundException("Gym not found with given id "+id));
    }

    public void deleteGymById(Long id) {
    	gymRepository.deleteById(id);
    }

    public void updateGym(GymDto gymDto, Long id) {
    	Gym gym = this.getGymById(id);
    	
    	if(gymDto.getName()!=null) {
    		gym.setName(gymDto.getName());
    	}
    	if(gymDto.getAddress()!=null) {
    		gym.setAddress(gymDto.getAddress());
    	}
    	if(gymDto.getContactNo()!=null) {
    		gym.setContactNo(gymDto.getContactNo());
    	}
    	if(gymDto.getMembershipPlans()!=null) {
    		gym.setMembershipPlans(gymDto.getMembershipPlans());
    	}
    	if(gymDto.getFacilities()!=null) {
    		gym.setFacilities(gymDto.getFacilities());
    	}
    	if(gymDto.getMembers()!=null) {
    		for(User member : gymDto.getMembers()) {
    			User user = userService.getUserByEmail(member.getEmail());
    			gym.getMembers().add(user);
    		}
    	}
    	
    	gymRepository.save(gym);
    }

    public void createGym(GymDto gymDto) {
    	Gym gym = Gym.builder().name(gymDto.getName()).address(gymDto.getAddress()).contactNo(gymDto.getContactNo())
    			.membershipPlans(gymDto.getMembershipPlans()).facilities(gymDto.getFacilities()).build();
    	
    	if(gymDto.getMembers()!=null) {
    		for(User member : gymDto.getMembers()) {
    			User user = userService.getUserByEmail(member.getEmail());
    			gym.getMembers().add(user);
    		}
    	}
    	
    	gymRepository.save(gym);
    }

    public void addMember(Long userId, Long gymId) {
    	User member = userService.getUserById(userId);
    	Gym gym = this.getGymById(gymId);
    	
    	member.setGym(gym);
    	userRepository.save(member);
    }

    public void deleteMember(Long userId, Long gymId) {
    	User member = userService.getUserById(userId);
    	Gym gym = this.getGymById(gymId);
    	
    	for(int i=0 ; i<gym.getMembers().size() ; i++) {
			if(gym.getMembers().get(i).getEmail().equals(member.getEmail())) {
				member.setGym(null);
				gym.getMembers().remove(i);
				gymRepository.save(gym);
				userRepository.save(member);
				break;
			}
		}
    }
}
