package com.blogApp.payloads;

import com.blogApp.entities.Category;
import com.blogApp.entities.Comment;
import com.blogApp.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer id;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
   // private List<CommentDto> comments = new ArrayList<>();

}
