package com.blogApp.controllers;

import com.blogApp.payloads.ApiResponse;
import com.blogApp.payloads.CommentDto;
import com.blogApp.repositories.PostRepository;
import com.blogApp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId){
        return new ResponseEntity<>(this.commentService
                            .createComment(commentDto,postId),HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted",true),HttpStatus.OK);
    }
}
