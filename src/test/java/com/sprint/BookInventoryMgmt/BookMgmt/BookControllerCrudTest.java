package com.sprint.BookInventoryMgmt.BookMgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.CategoryRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerCrudTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String ISBN = "ISBN123";

    @BeforeEach
    void setup() throws Exception {

        mockMvc.perform(delete("/api/v1/books/" + ISBN));

        publisherRepository.deleteAll();
        categoryRepository.deleteAll();

        Category category = new Category();
        category.setCatId(1);
        category.setCatDescription("Fiction");
        categoryRepository.save(category);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Publisher");
        publisherRepository.save(publisher);

        String bookJson = """
        {
          "isbn": "ISBN123",
          "title": "Test Book",
          "description": "Test Desc",
          "edition": "1st",
          "categoryId": 1,
          "publisherId": 1
        }
        """;

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated());
    }

    // ✅ CREATE (use different ISBN)
    @Test
    void testCreateBook() throws Exception {

        String body = """
        {
          "isbn": "ISBN999",
          "title": "Spring Boot Basics",
          "description": "Learn Spring Boot",
          "edition": "1st",
          "categoryId": 1,
          "publisherId": 1
        }
        """;

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.isbn").value("ISBN999"));
    }

    // ✅ READ
    @Test
    void testGetBook() throws Exception {

        mockMvc.perform(get("/api/v1/books/" + ISBN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isbn").value(ISBN));
    }

    // ✅ UPDATE
    @Test
    void testUpdateBook() throws Exception {

        String body = """
        {
          "isbn": "ISBN123",
          "title": "Updated Title",
          "description": "Updated Desc",
          "edition": "2nd",
          "categoryId": 1,
          "publisherId": 1
        }
        """;

        mockMvc.perform(put("/api/v1/books/" + ISBN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Title"));
    }

    // ✅ DELETE
    @Test
    void testDeleteBook() throws Exception {

        mockMvc.perform(delete("/api/v1/books/" + ISBN))
                .andExpect(status().isOk());

        // verify deletion
        mockMvc.perform(get("/api/v1/books/" + ISBN))
                .andExpect(status().isNotFound());
    }
}