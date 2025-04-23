package com.krish.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krish.constants.APIConstants;
import com.krish.dto.Postdto;
import com.krish.model.PostModal;
import com.krish.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class PostController {

	final PostService postService;

//	@PostMapping(APIConstants.SAVE_POST)
//	public PostModal savePost(@Valid @RequestBody Postdto postdtoRequest) {
//
//		log.info("Saving post #####");
//
//		return this.postService.savePost(postdtoRequest);
//	}
//
	@GetMapping(APIConstants.GET_ALL_POSTS)
	public java.util.List<PostModal> getAllPosts() {
		log.info("Getting all posts ########");
		return this.postService.getAllPosts();
	}
	
	@PostMapping("/post")
	public ResponseEntity<PostModal> savePost(@RequestBody Postdto postdtoRequest) {
	    log.info("Received request body: " + postdtoRequest);
	    System.out.println(postdtoRequest);
	    PostModal savedPost = postService.savePost(postdtoRequest);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
	}
	
	@GetMapping("/posts/search")
	public ResponseEntity<List<PostModal>> searchPosts(@RequestParam String query) {
		 System.out.println("🔍 API hit with: " + query);
	    List<PostModal> posts = postService.searchPosts(query);
	    return ResponseEntity.ok(posts);
	}



//	// Get All Posts
//	@GetMapping("/posts")
//	public ResponseEntity<List<PostModal>> getAllPosts() {
//	    List<PostModal> posts = postService.getAllPosts();
//	    return ResponseEntity.ok(posts); // Return the list of posts
//	}


}
