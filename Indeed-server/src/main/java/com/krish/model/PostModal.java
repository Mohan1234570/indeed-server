//package com.krish.model;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//
//@Document(collection = "posts")
//@Data
//public class PostModal {
//	
//	@Id
//    private String id;
//
//
//	@NotNull
//	@NotEmpty
//	private String profile;
//	
//	@NotNull
//	private String type;
//	
//	@NotNull
//	private String description;
//	
//	@NotNull
//	private String experience;
//	
//	@NotNull
//	private List<String> technology; 
//	
//	@NotNull
//	private String salary;
//	
//	@CreatedDate
//	private Date createdDate;
//	
//	public List<String> getTechnology() {
//        return technology;
//    }
//
//    public void setTechnology(List<String> technology) {
//        this.technology = technology;
//    }
//	
//    private String userEmail;
//}




package com.krish.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class PostModal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String profile;

    @NotNull
    private String type;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;


    @NotNull
    private String experience;

    @ElementCollection
    @CollectionTable(name = "post_technologies", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "technology")
    private List<String> technology;

    @NotNull
    private String salary;

    @CreationTimestamp
    private Date createdDate;

    private String userEmail;
}
