package com.krish.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krish.constants.APIConstants;
import com.krish.dto.ApiResponse;
import com.krish.dto.Postdto;
import com.krish.model.PostModal;
import com.krish.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PostController {

	final PostService postService;
	
	public PostController(PostService postService) {
        this.postService = postService;
    }


	@GetMapping(APIConstants.GET_ALL_POSTS)
	public ResponseEntity<ApiResponse<List<PostModal>>> getAllPosts(Authentication authentication) {
	    String userEmail = authentication.getName();
	    List<PostModal> posts = postService.getAllPosts(userEmail);

	    ApiResponse<List<PostModal>> response = new ApiResponse<>(
	        200, 
	        "Posts fetched successfully for user " + userEmail, 
	        posts
	    );

	    return ResponseEntity.ok(response);
	}

	
	@PostMapping("/post")
	public ResponseEntity<ApiResponse<PostModal>> savePost(@RequestBody Postdto postdtoRequest, Authentication authentication) {
	    String userEmail = authentication.getName();
	    PostModal savedPost = postService.savePost(postdtoRequest, userEmail);

	    ApiResponse<PostModal> response = new ApiResponse<>(
	        201, 
	        "Post created successfully", 
	        savedPost
	    );

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
	@GetMapping("/posts/search")
	public ResponseEntity<ApiResponse<List<PostModal>>> searchPosts(@RequestParam String query) {
	    List<PostModal> posts = postService.searchPosts(query);

	    ApiResponse<List<PostModal>> response = new ApiResponse<>(
	        200,
	        "Search results for query: " + query,
	        posts
	    );

	    return ResponseEntity.ok(response);
	}





}
