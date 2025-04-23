package com.krish.Dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.krish.model.PostModal;

public interface PostDao extends MongoRepository<PostModal, String>{

	List<PostModal> findByProfileContainingOrDescriptionContaining(String profile, String description);

	
}
