package com.example.myblog;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.myblog.controller.BlogController;
import com.example.myblog.entity.Blog;
import com.example.myblog.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.Timestamp;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTests {
    @MockBean
    private BlogRepository blogRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBlog() throws Exception {
        Blog blog = new Blog(1L, "Spring Boot Test", "Spring Boot Test Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        
        mockMvc.perform(post("/api/v1/blogs").contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(blog)))
              .andExpect(status().isCreated())
              .andDo(print());
    }
    
    @Test
    void shouldReturnBlog() throws Exception {
        long id = 1L;
        Blog blog = new Blog(id, "Spring Boot Test", "Spring Boot Test Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    
        when(blogRepository.findById(id)).thenReturn(Optional.of(blog));
        mockMvc.perform(get("/api/v1/blogs/{id}", id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.title").value(blog.getTitle()))
            .andExpect(jsonPath("$.content").value(blog.getContent()))
            .andExpect(jsonPath("$.author").value(blog.getAuthor()))
            .andDo(print());
    }

    @Test
    void shouldReturnNotFoundBlog() throws Exception {
        long id = 1L;
      
        when(blogRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/blogs/{id}", id))
             .andExpect(status().isNotFound())
             .andDo(print());
    }
    
    @Test
    void shouldReturnListOfBlogs() throws Exception {
        List<Blog> blogs = new ArrayList<>(
            Arrays.asList(new Blog(1L, "Spring Boot Test 1", "Spring Boot Test 1 Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())),
                new Blog(2L, "Spring Boot Test 2", "Spring Boot Test 2 Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())),
                new Blog(3L, "Spring Boot Test 3", "Spring Boot Test 3 Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()))));
        
        when(blogRepository.findAll()).thenReturn(blogs);
        mockMvc.perform(get("/api/v1/blogs/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(blogs.size()))
            .andDo(print());
    }
    
    @Test
    void shouldUpdateBlog() throws Exception {
        long id = 1L;
        
        Blog blog = new Blog(id, "Spring Boot Test", "Spring Boot Test Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Blog updatedBlog = new Blog(id, "Updated Spring Boot Test", "Updated Spring Boot Test Blog Content", "John Doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        
        when(blogRepository.findById(id)).thenReturn(Optional.of(blog));
        when(blogRepository.save(any(Blog.class))).thenReturn(updatedBlog);
        
        mockMvc.perform(put("/api/v1/blogs/{id}", id).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedBlog)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(updatedBlog.getTitle()))
            .andExpect(jsonPath("$.content").value(updatedBlog.getContent()))
            .andExpect(jsonPath("$.author").value(updatedBlog.getAuthor()))
            .andDo(print());
    }
}