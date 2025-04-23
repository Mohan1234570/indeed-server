package com.krish.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Document(collection = "posts")
@Data
public class PostModal {
	
	@Id
    private String id;


	@NotNull
	@NotEmpty
	private String profile;
	
	@NotNull
	private String type;
	
	@NotNull
	private String description;
	
	@NotNull
	private String experience;
	
	@NotNull
	private List<String> technology; 
	
	@NotNull
	private String salary;
	
	@CreatedDate
	private Date createdDate;
	
	public List<String> getTechnology() {
        return technology;
    }

    public void setTechnology(List<String> technology) {
        this.technology = technology;
    }
	
}
