package com.krish.service;

import java.util.List;

import com.krish.dto.Postdto;
import com.krish.model.PostModal;

public interface PostService {

	public PostModal savePost(Postdto dto);
	
	public List<PostModal> getAllPosts();
	
	public List<PostModal> searchPosts(String query);
}
