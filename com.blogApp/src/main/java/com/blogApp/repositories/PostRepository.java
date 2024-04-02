package com.blogApp.repositories;

import com.blogApp.entities.Category;
import com.blogApp.entities.Post;
import com.blogApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    //@Query("select p from Post p where p.title like :key")
    List<Post> findByTitleContaining(String title);
}
