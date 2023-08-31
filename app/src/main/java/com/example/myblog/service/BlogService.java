package com.example.myblog.service;

import com.example.myblog.entity.Blog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog create(Blog blog);

    Blog show(Long id);
    
    List<Blog> showAll();
    
    Page<Blog> showAllWithPaginating(Pageable pageable);

    Blog update(Blog blog);

    void delete(Long id);
}