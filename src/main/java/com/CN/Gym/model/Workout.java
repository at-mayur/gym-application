package com.CN.Gym.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
    This is the entity class, complete this class by doing the following:
    a. Add the required annotations for making this class an entity.
    b. Add the required lombok annotations for getter, setter and constructors
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workout {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String workoutName;
    private String description;
    private String difficultyLevel;
    private int duration;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
