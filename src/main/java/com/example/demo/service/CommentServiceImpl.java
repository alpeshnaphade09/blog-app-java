package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CommentDto;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository cRepo;

	@Autowired
	private PostRepository pRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = pRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		
		Comment comment = mapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		Comment savedComment = cRepo.save(comment);
		
		return mapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = cRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));
		cRepo.delete(comment);
	}

}
