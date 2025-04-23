package com.krish.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krish.Dao.PostDao;
import com.krish.dto.Postdto;
import com.krish.model.PostModal;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostDao postDao;
	
	
	@Override
	public PostModal savePost(Postdto dto) {
		PostModal postModel = new PostModal();
		
		postModel.setProfile(dto.getProfile());
		postModel.setDescription(dto.getDescription());
		postModel.setSalary(dto.getSalary());
		postModel.setTechnology(dto.getTechnology());
		postModel.setExperience(dto.getExperience());
		postModel.setType(dto.getType());
		
		return postDao.save(postModel);
		
		
	}


	@Override
	public List<PostModal> getAllPosts() {
		
		return postDao.findAll();
	}

	public List<PostModal> searchPosts(String query) {
	    // Assuming you're searching by profile and description
	    return postDao.findByProfileContainingOrDescriptionContaining(query, query);
	}

}
