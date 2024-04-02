package com.blogApp.services;

import com.blogApp.payloads.PostDto;
import com.blogApp.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDirection);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    //search posts
    List<PostDto> searchPosts(String keyword);
}
