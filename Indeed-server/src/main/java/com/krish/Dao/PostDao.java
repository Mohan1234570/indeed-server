//package com.krish.Dao;
//
//import java.util.List;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import com.krish.model.PostModal;
//
//public interface PostDao extends MongoRepository<PostModal, String>{
//
//	List<PostModal> findByProfileContainingOrDescriptionContaining(String profile, String description);
//
//	// New method to find posts by user email
//    List<PostModal> findByUserEmail(String userEmail);
//}



package com.krish.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krish.model.PostModal;



public interface PostDao extends JpaRepository<PostModal, Long> {
    List<PostModal> findByProfileContainingOrDescriptionContaining(String profile, String description);
    List<PostModal> findByUserEmail(String userEmail);
}
