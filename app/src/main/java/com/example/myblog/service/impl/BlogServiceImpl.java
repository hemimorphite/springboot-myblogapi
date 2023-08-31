package com.example.myblog.service.impl;

import lombok.AllArgsConstructor;
import com.example.myblog.entity.Blog;
import com.example.myblog.repository.BlogRepository;
import com.example.myblog.service.BlogService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;

    @Override
    public Blog create(Blog blog) {
        blog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        blog.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return blogRepository.save(blog);
    }

    @Override
    public Blog show(Long id) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        
        if(optionalBlog.isEmpty()) {
            return null;
        }
        return optionalBlog.get();
    }
    
    @Override
    public List<Blog> showAll() {
        return blogRepository.findAll();
    }
    
    @Override
    public Page<Blog> showAllWithPaginating(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Blog update(Blog blog) {
        Blog existingBlog = blogRepository.findById(blog.getId()).get();
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        existingBlog.setAuthor(blog.getAuthor());
        existingBlog.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Blog savedBlog = blogRepository.save(existingBlog);
        return savedBlog;
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}