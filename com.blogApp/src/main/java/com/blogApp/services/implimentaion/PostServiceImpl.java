package com.blogApp.services.implimentaion;

import com.blogApp.entities.Category;
import com.blogApp.entities.Post;
import com.blogApp.entities.User;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payloads.PostDto;
import com.blogApp.payloads.PostResponse;
import com.blogApp.repositories.CategoryRepository;
import com.blogApp.repositories.PostRepository;
import com.blogApp.repositories.UserRepository;
import com.blogApp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user Id",userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));


        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        return this.modelMapper.map(this.postRepository.save(post),PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","post id",postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setAddedDate(new Date());

        //post.setUser(user);
        //post.setCategory(category);

        return this.modelMapper.map(this.postRepository.save(post),PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        this.postRepository.delete(this.postRepository
                                        .findById(postId)
                                        .orElseThrow(()->new ResourceNotFoundException("post","post id",postId)));
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("dsc")
                                ?Sort.by(sortBy).descending()
                                :Sort.by(sortBy).ascending();

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);

       Page<Post> pagePost = this.postRepository.findAll(p);

       List<Post> allPosts = pagePost.getContent();
       List<PostDto> postDtos =allPosts.stream().map((post)-> this.modelMapper
                       .map(post,PostDto.class))
               .collect(Collectors.toList());
       PostResponse postResponse = new PostResponse();

       postResponse.setContent(postDtos);
       postResponse.setPageNumber(pagePost.getNumber());
       postResponse.setPageSize(pagePost.getSize());
       postResponse.setTotalElement(pagePost.getTotalElements());
       postResponse.setTotalPages(pagePost.getTotalPages());
       postResponse.setLastPage(pagePost.isLast());

       return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("post","post id",postId));

        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                            .orElseThrow(()-> new ResourceNotFoundException("category","category id",categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);

        return posts.stream().map((post)->this.modelMapper
                                                .map(post,PostDto.class))
                                .collect(Collectors.toList());

    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","user id",userId));
        List<Post> posts = this.postRepository.findByUser(user);

        return posts.stream().map((post)->this.modelMapper
                                        .map(post,PostDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {

        List<Post> posts = this.postRepository.findByTitleContaining(keyword);

        return posts.stream().map((post)-> this.modelMapper
                                               .map(post,PostDto.class)).collect(Collectors.toList());
    }
}
