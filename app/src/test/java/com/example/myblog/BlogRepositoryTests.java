package com.example.myblog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.myblog.entity.Blog;
import com.example.myblog.repository.BlogRepository;

import java.sql.Timestamp;

@DataJpaTest
public class BlogRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    BlogRepository repository;
    
    @Test
    public void should_find_no_blogs_if_repository_is_empty() {
        Iterable blogs = repository.findAll();
        
        assertThat(blogs).isEmpty();
    }

    @Test
    public void should_store_a_blog() {
        Blog blog = repository.save(new Blog(null, "Blog title", "Blog content", "John doe", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        
        assertThat(blog).hasFieldOrPropertyWithValue("title", "Blog title");
        assertThat(blog).hasFieldOrPropertyWithValue("content", "Blog content");
        assertThat(blog).hasFieldOrPropertyWithValue("author", "John doe");
    }

    @Test
    public void should_find_all_blogs() {
        Blog blog1 = new Blog(null, "Blog title 1", "Blog content 1", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog1);
        
        Blog blog2 = new Blog(null, "Blog title 2", "Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog2);
        
        Blog blog3 = new Blog(null, "Blog title 3", "Blog content 3", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog3);
    
        Iterable blogs = repository.findAll();
    
        assertThat(blogs).hasSize(3).contains(blog1, blog2, blog3);
    }

    @Test
    public void should_find_blog_by_id() {
        Blog blog1 = new Blog(null, "Blog title 1", "Blog content 1", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog1);
        
        Blog blog2 = new Blog(null, "Blog title 2", "Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog2);
        
        Blog blog3 = new Blog(null, "Blog title 3", "Blog content 3", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog3);
        
        Blog blog = repository.findById(blog2.getId()).get();
        
        assertThat(blog).isEqualTo(blog2);
    }

    @Test
    public void should_find_author_blogs() {
        Blog blog1 = new Blog(null, "Blog title 1", "Blog content 1", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog1);
        
        Blog blog2 = new Blog(null, "Blog title 2", "Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog2);
        
        Blog blog3 = new Blog(null, "Blog title 3", "Blog content 3", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog3);
        
        Blog blog4 = new Blog(null, "Blog title 4", "Blog content 4", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog4);
        
        Blog blog5 = new Blog(null, "Blog title 5", "Blog content 5", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog5);
        
        Iterable blogs = repository.findByAuthor("John doe 3");
    
        assertThat(blogs).hasSize(2).contains(blog3, blog5);
    }

    @Test
    public void should_update_blog_by_id() {
        Blog blog1 = new Blog(null, "Blog title 1", "Blog content 1", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog1);
        
        Blog blog2 = new Blog(null, "Blog title 2", "Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog2);
        
        Blog blog3 = new Blog(null, "Blog title 3", "Blog content 3", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog3);
    
        Blog updatedBlog = new Blog(null, "Updated Blog title 2", "Updated Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        
        Blog blog = repository.findById(blog2.getId()).get();
        blog.setTitle(updatedBlog.getTitle());
        blog.setContent(updatedBlog.getContent());
        blog.setAuthor(updatedBlog.getAuthor());
        repository.save(blog);
        
        Blog checkBlog = repository.findById(blog2.getId()).get();
        
        assertThat(checkBlog.getId()).isEqualTo(blog2.getId());
        assertThat(checkBlog.getTitle()).isEqualTo(updatedBlog.getTitle());
        assertThat(checkBlog.getContent()).isEqualTo(updatedBlog.getContent());
        assertThat(checkBlog.getAuthor()).isEqualTo(updatedBlog.getAuthor());
    }

    @Test
    public void should_delete_blog_by_id() {
        Blog blog1 = new Blog(null, "Blog title 1", "Blog content 1", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog1);
        
        Blog blog2 = new Blog(null, "Blog title 2", "Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog2);
        
        Blog blog3 = new Blog(null, "Blog title 3", "Blog content 3", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog3);
    
        repository.deleteById(blog2.getId());
    
        Iterable blogs = repository.findAll();
    
        assertThat(blogs).hasSize(2).contains(blog1, blog3);
    }

    @Test
    public void should_delete_all_blogs() {
        Blog blog1 = new Blog(null, "Blog title 1", "Blog content 1", "John doe 1", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog1);
        
        Blog blog2 = new Blog(null, "Blog title 2", "Blog content 2", "John doe 2", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog2);
        
        Blog blog3 = new Blog(null, "Blog title 3", "Blog content 3", "John doe 3", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        entityManager.persist(blog3);
  
        repository.deleteAll();
  
        assertThat(repository.findAll()).isEmpty();
    }
}