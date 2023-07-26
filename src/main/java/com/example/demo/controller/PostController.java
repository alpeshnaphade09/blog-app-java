package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.AppConstants;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostResponse;
import com.example.demo.service.FileService;
import com.example.demo.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService service;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	
	@PostMapping("/user/{uid}/category/{cid}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable("uid") Integer uid,
			@PathVariable("cid") Integer cid) {

		PostDto dto = service.createPost(postDto, uid, cid);
		return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{uid}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("uid") Integer uid){
		List<PostDto> postsByUser = service.getPostsByUser(uid);
		return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
	}
	
	@GetMapping("/category/{cid}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("cid") Integer cid){
		List<PostDto> postsByCategory = service.getPostsByCategory(cid);
		return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, value = "pageSize", required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORT_BY, value = "sortBy", required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIR, value = "sortDir", required = false) String sortDir
			){
		PostResponse res = service.gstAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/{pid}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer pid){
		PostDto post = service.getPostById(pid);
		return ResponseEntity.ok(post);
	}
	
	@DeleteMapping("/{pid}")
	public ApiResponse deletePost(@PathVariable Integer pid) {
		service.deletePost(pid);
		return new ApiResponse("Post is deleted successfully", true);
	}
	
	
	@PutMapping("/{pid}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto dto, @PathVariable Integer pid){
		PostDto updatePost = service.updatePost(dto, pid);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keyword){
		List<PostDto> list = service.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/image/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = service.getPostById(postId);
		String fileName = fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = service.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
