package com.blogApp.controllers;

import com.blogApp.config.AppConstants;
import com.blogApp.payloads.ApiResponse;
import com.blogApp.payloads.PostDto;
import com.blogApp.payloads.PostResponse;
import com.blogApp.services.FileService;
import com.blogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        return new ResponseEntity<PostDto>
                    (this.postService.createPost
                            (postDto,userId,categoryId),HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

        return new ResponseEntity<>(this.postService.getPostsByUser(userId),HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){

        return new ResponseEntity<>(this.postService.getPostsByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                   @RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION,required = false)String sortDirection){

        return new ResponseEntity<PostResponse>
                        (this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){

        return new ResponseEntity<>(this.postService.getPostById(postId),HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){

        this.postService.deletePost(postId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully",true),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Integer postId){
        return new ResponseEntity<>(this.postService.updatePost(postDto,postId),HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle (@PathVariable String keyword){
        return new ResponseEntity<List<PostDto>>(this.postService.searchPosts(keyword),HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                   @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);

        return new ResponseEntity<PostDto>(this.postService.updatePost(postDto,postId),HttpStatus.OK);
    }
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException{

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
