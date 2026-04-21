//package com.sprint.bookinventorymgmt.bookmgmt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
//import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
//import com.sprint.bookinventorymgmt.bookmgmt.entity.State;
//import com.sprint.bookinventorymgmt.bookmgmt.repository.CategoryRepository;
//import com.sprint.bookinventorymgmt.bookmgmt.repository.PublisherRepository;
//import com.sprint.bookinventorymgmt.bookmgmt.repository.StateRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//@Rollback
//class BookControllerCrudTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PublisherRepository publisherRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private StateRepository stateRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private static final String ISBN = "ISBN123";
//
//    private Integer categoryId;
//    private Integer publisherId;
//
//    @BeforeEach
//    void setup() throws Exception {
//
//        // =========================
//        // CLEANUP (IMPORTANT FIX)
//        // =========================
//        mockMvc.perform(delete("/api/v1/books/" + ISBN));
//
//        publisherRepository.deleteAll();
//        categoryRepository.deleteAll();
//        stateRepository.deleteAll();
//
//        // =========================
//        // STATE (FIXED - REQUIRED FIELD SAFE)
//        // =========================
//        State state = new State();
//        state.setStateCode("TN");   // IMPORTANT: ID is manually assigned
//        state.setStateName("Tamil Nadu");
//        stateRepository.saveAndFlush(state);
//
//        // =========================
//        // CATEGORY
//        // =========================
//        Category category = new Category();
//        category.setCatId(1); // manually assigned ID
//        category.setCatDescription("Fiction");
//        categoryRepository.saveAndFlush(category);
//
//        categoryId = category.getCatId();
//
//        // =========================
//        // PUBLISHER
//        // =========================
//        Publisher publisher = new Publisher();
//        publisher.setPublisherId(1); // manually assigned ID
//        publisher.setName("Test Publisher");
//        publisher.setCity("Chennai");
//        publisher.setState(state);
//
//        publisherRepository.saveAndFlush(publisher);
//
//        publisherId = publisher.getPublisherId();
//
//        // =========================
//        // CREATE INITIAL BOOK
//        // =========================
//        String bookJson = """
//        {
//          "isbn": "ISBN123",
//          "title": "Test Book",
//          "description": "Test Desc",
//          "edition": "1st",
//          "categoryId": 1,
//          "publisherId": 1
//        }
//        """;
//
//        mockMvc.perform(post("/api/v1/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(bookJson))
//                .andExpect(status().isCreated());
//    }
//
//    // =========================
//    // CREATE
//    // =========================
//    @Test
//    void testCreateBook() throws Exception {
//
//        String body = """
//        {
//          "isbn": "ISBN456",
//          "title": "Spring Boot Basics",
//          "description": "Learn Spring Boot",
//          "edition": "1st",
//          "categoryId": %d,
//          "publisherId": %d
//        }
//        """.formatted(categoryId, publisherId);
//
//        mockMvc.perform(post("/api/v1/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.isbn").value("ISBN456"));
//    }
//
//    // =========================
//    // READ
//    // =========================
//    @Test
//    void testGetBook() throws Exception {
//
//        mockMvc.perform(get("/api/v1/books/" + ISBN))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.isbn").value(ISBN));
//    }
//
//    // =========================
//    // UPDATE
//    // =========================
//    @Test
//    void testUpdateBook() throws Exception {
//
//        String body = """
//        {
//          "isbn": "%s",
//          "title": "Updated Title",
//          "description": "Updated Desc",
//          "edition": "2nd",
//          "categoryId": %d,
//          "publisherId": %d
//        }
//        """.formatted(ISBN, categoryId, publisherId);
//
//        mockMvc.perform(put("/api/v1/books/" + ISBN)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(body))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.title").value("Updated Title"));
//    }
//
//    // =========================
//    // DELETE
//    // =========================
//    @Test
//    void testDeleteBook() throws Exception {
//
//        mockMvc.perform(delete("/api/v1/books/" + ISBN))
//                .andExpect(status().isOk());
//    }
//}