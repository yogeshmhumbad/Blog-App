package com.blogApp.services.implimentaion;

import com.blogApp.entities.Comment;
import com.blogApp.entities.Post;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payloads.CommentDto;
import com.blogApp.payloads.PostDto;
import com.blogApp.repositories.CommentRepository;
import com.blogApp.repositories.PostRepository;
import com.blogApp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(
                                ()->new ResourceNotFoundException("post","post id",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        return this.modelMapper.map(this.commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","comment id",commentId));
        this.commentRepository.delete(comment);
    }
}
