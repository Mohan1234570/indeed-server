package com.krish.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krish.Dao.PostDao;
import com.krish.dto.Postdto;
import com.krish.model.PostModal;
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Override
    public PostModal savePost(Postdto dto, String userEmail) {
        PostModal postModel = new PostModal();

        postModel.setProfile(dto.getProfile());
        postModel.setDescription(dto.getDescription());
        postModel.setSalary(dto.getSalary());
        postModel.setTechnology(dto.getTechnology());
        postModel.setExperience(dto.getExperience());
        postModel.setType(dto.getType());

        postModel.setUserEmail(userEmail);  // assign user email

        return postDao.save(postModel);
    }

    @Override
    public List<PostModal> getAllPosts(String userEmail) {
        return postDao.findByUserEmail(userEmail);
    }

    @Override
    public List<PostModal> searchPosts(String query) {
        return postDao.findByProfileContainingOrDescriptionContaining(query, query);
    }
}
