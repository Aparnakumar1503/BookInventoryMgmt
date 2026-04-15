// package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.sprint.BookInventoryMgmt.BookMgmt.DTO.BookRequestDTO;
// import com.sprint.BookInventoryMgmt.BookMgmt.DTO.BookResponseDTO;
// import com.sprint.BookInventoryMgmt.BookMgmt.Service.BookService;

// import jakarta.validation.Valid;
// import lombok.*;
// import java.util.List;

// @RestController
// @RequestMapping("/api/v1/books")
// @RequiredArgsConstructor

// public class BookController {

//     private final BookService bookService;

//     @GetMapping
//     public List<BookResponseDTO> getAllBooks(
//             @RequestParam(required = false) Integer categoryId,
//             @RequestParam(required = false) Integer publisherId
//     ) {
//         return bookService.searchBooks(categoryId, publisherId);
//     }

//     @GetMapping("/{isbn}")
//     public BookResponseDTO getBook(@PathVariable String isbn) {
//         return bookService.getBookById(isbn);
//     }

//     @PostMapping
//     public BookResponseDTO createBook(@RequestBody @Valid BookRequestDTO dto) {
//         return bookService.createBook(dto);
//     }

//     @PutMapping("/{isbn}")
//     public BookResponseDTO updateBook(@PathVariable String isbn,
//                                       @RequestBody @Valid BookRequestDTO dto) {
//         return bookService.updateBook(isbn, dto);
//     }

//     @DeleteMapping("/{isbn}")
//     public void deleteBook(@PathVariable String isbn) {
//         bookService.deleteBook(isbn);
//     }
// }