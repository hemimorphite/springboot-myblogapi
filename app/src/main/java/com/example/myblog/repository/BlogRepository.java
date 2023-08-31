package com.example.myblog.repository;

import com.example.myblog.entity.Blog;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAll();
    Page<Blog> findAll(Pageable pageable);
    List<Blog> findByAuthor(String author);
}