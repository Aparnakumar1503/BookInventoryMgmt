// package com.sprint.bookinventorymgmt;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.sprint.bookinventorymgmt.BookMgmt.Entity.*;
// import com.sprint.bookinventorymgmt.BookMgmt.Repository.*;

// @Configuration
// public class TestRunner {

//     @Bean
//     CommandLineRunner run(
//             BookRepository bookRepo,
//             CategoryRepository categoryRepo,
//             PublisherRepository publisherRepo,
//             StateRepository stateRepo) {

//         return args -> {

//             State state = stateRepo.findById("TN")
//                     .orElseGet(() -> stateRepo.save(
//                             State.builder()
//                                     .stateCode("TN")
//                                     .stateName("Tamil Nadu")
//                                     .build()
//                     ));

//             Category category = categoryRepo.findById(1)
//                     .orElseGet(() -> categoryRepo.save(
//                             Category.builder()
//                                     .catId(1)
//                                     .catDescription("Programming")
//                                     .build()
//                     ));

//             Publisher publisher = publisherRepo.findById(1)
//                     .orElseGet(() -> publisherRepo.save(
//                             Publisher.builder()
//                                     .publisherId(1)
//                                     .name("O'Reilly")
//                                     .city("Chennai")
//                                     .state(state)
//                                     .build()
//                     ));

//             Book book = Book.builder()
//                     .isbn("9999999999999")
//                     .title("Spring Boot Guide")
//                     .description("Testing CRUD")
//                     .edition("1st")
//                     .category(category)
//                     .publisher(publisher)
//                     .build();

//             bookRepo.save(book);

//             System.out.println("✅ Book Saved Successfully");
//         };
//     }
// }