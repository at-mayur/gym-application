package com.CN.Gym.dto;

import java.util.List;

import com.CN.Gym.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymDto {

    /* This is the Gym Dto class, and you need to complete the class by doing the following:
     a. Add the following attributes:
       1. name
       2. address
       3. contactNo
       4. membershipPlans
       5. facilities
       6. List of members i.e (User)
     b. Use lombok annotations for getter, setters and constructors
     */
	
	private String name;
	private String address;
	private Long contactNo;
	private String membershipPlans;
	private String facilities;
	private List<User> members;

}
