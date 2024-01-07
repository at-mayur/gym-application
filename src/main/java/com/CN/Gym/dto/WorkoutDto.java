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
public class WorkoutDto {

    /* This is the Workout Dto class, and you need to complete the class by doing the following:
     a. Add the following attributes:
       1. workoutName
       2. description
       3. difficultyLevel
       4. duration
     b. Use lombok annotations for getter, setters and constructors
     */
	
    private String workoutName;
    private String description;
    private String difficultyLevel;
    private int duration;

}
