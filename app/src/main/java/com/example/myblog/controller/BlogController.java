package com.example.myblog.controller;

import lombok.AllArgsConstructor;
import com.example.myblog.entity.Blog;
import com.example.myblog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/blogs")
public class BlogController {

    private BlogService blogService;

    @PostMapping
    public ResponseEntity<Blog> create(@RequestBody Blog blog) {
        Blog createdblog = blogService.create(blog);
        return new ResponseEntity<>(createdblog, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Blog> show(@PathVariable("id") Long id){
        Blog blog = blogService.show(id);
        
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }
    
    @GetMapping("all")
    public ResponseEntity<List<Blog>> showAll(){
        List<Blog> blogs = blogService.showAll();
        
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> showAllWithPaginating(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort){
        
        List<Order> orders = new ArrayList<Order>();
        
        if (sort[0].contains(",")) {
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                
                if(_sort[1].equalsIgnoreCase("asc")) {
                    orders.add(new Order(Sort.Direction.ASC, _sort[0]));    
                }
                
                if(_sort[1].equalsIgnoreCase("desc")) {
                    orders.add(new Order(Sort.Direction.DESC, _sort[0]));    
                }
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(Sort.Direction.ASC, sort[0]));
        }
        
        List<Blog> blogs = new ArrayList<Blog>();
        
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        
        Page<Blog> pageBlogs = blogService.showAllWithPaginating(pagingSort);
        
        blogs = pageBlogs.getContent();
        
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("blogs", blogs);
        response.put("currentPage", pageBlogs.getNumber());
        response.put("totalElements", pageBlogs.getTotalElements());
        response.put("totalPages", pageBlogs.getTotalPages());
  
        return new ResponseEntity<>(response, HttpStatus.OK);
    
        //List<Blog> blogs = blogService.showAll();
        //return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") Long id, @RequestBody Blog blog) {
        blog.setId(id);
        Blog updatedBlog = blogService.update(blog);
        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        blogService.delete(id);
        return new ResponseEntity<>("Blog successfully deleted!", HttpStatus.OK);
    }
}