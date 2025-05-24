package com.krish.service;

import java.util.List;

import com.krish.dto.Postdto;
import com.krish.model.PostModal;

public interface PostService {

	public PostModal savePost(Postdto dto, String userEmail);  // add userEmail param
    public List<PostModal> getAllPosts(String userEmail);      // fetch posts for specific user
    public List<PostModal> searchPosts(String query);
}
