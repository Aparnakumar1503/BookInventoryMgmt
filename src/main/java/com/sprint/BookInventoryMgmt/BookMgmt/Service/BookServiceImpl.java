// package com.sprint.BookInventoryMgmt.BookMgmt.Service;


// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;

// import com.sprint.BookInventoryMgmt.BookMgmt.DTO.*;
// import com.sprint.BookInventoryMgmt.BookMgmt.Entity.*;
// import com.sprint.BookInventoryMgmt.BookMgmt.Repository.*;

// @Service
// public class BookServiceImpl implements BookService {

//     private final BookRepository bookRepository;
//     private final CategoryRepository categoryRepo;
//     private final PublisherRepository publisherRepo;

//     // ✅ Manual constructor (instead of Lombok)
//     public BookServiceImpl(BookRepository bookRepository,
//                            CategoryRepository categoryRepo,
//                            PublisherRepository publisherRepo) {
//         this.bookRepository = bookRepository;
//         this.categoryRepo = categoryRepo;
//         this.publisherRepo = publisherRepo;
//     }

//     // 🔁 Entity → DTO (NO builder)
//     private BookResponseDTO mapToDTO(Book book) {
//         return new BookResponseDTO(
//                 book.getIsbn(),
//                 book.getTitle(),
//                 book.getDescription(),
//                 book.getEdition(),
//                 book.getCategory().getCatId(),
//                 book.getPublisher().getPublisherId()
//         );
//     }

//     @Override
//     public List<BookResponseDTO> getAllBooks() {
//         return bookRepository.findAll()
//                 .stream()
//                 .map(this::mapToDTO)
//                 .collect(Collectors.toList());
//     }

//     @Override
//     public BookResponseDTO getBookById(String isbn) {
//         Book book = bookRepository.findById(isbn)
//                 .orElseThrow(() -> new RuntimeException("Book not found"));
//         return mapToDTO(book);
//     }

//     @Override
//     public BookResponseDTO createBook(BookRequestDTO dto) {

//         Category category = categoryRepo.findById(dto.getCategoryId())
//                 .orElseThrow(() -> new RuntimeException("Category not found"));

//         Publisher publisher = publisherRepo.findById(dto.getPublisherId())
//                 .orElseThrow(() -> new RuntimeException("Publisher not found"));

//         Book book = new Book();
//         book.setIsbn(dto.getIsbn());
//         book.setTitle(dto.getTitle());
//         book.setDescription(dto.getDescription());
//         book.setEdition(dto.getEdition());
//         book.setCategory(category);
//         book.setPublisher(publisher);

//         return mapToDTO(bookRepository.save(book));
//     }

//     @Override
//     public BookResponseDTO updateBook(String isbn, BookRequestDTO dto) {

//         Book book = bookRepository.findById(isbn)
//                 .orElseThrow(() -> new RuntimeException("Book not found"));

//         Category category = categoryRepo.findById(dto.getCategoryId())
//                 .orElseThrow(() -> new RuntimeException("Category not found"));

//         Publisher publisher = publisherRepo.findById(dto.getPublisherId())
//                 .orElseThrow(() -> new RuntimeException("Publisher not found"));

//         book.setTitle(dto.getTitle());
//         book.setDescription(dto.getDescription());
//         book.setEdition(dto.getEdition());
//         book.setCategory(category);
//         book.setPublisher(publisher);

//         return mapToDTO(bookRepository.save(book));
//     }

//     @Override
//     public void deleteBook(String isbn) {
//         bookRepository.deleteById(isbn);
//     }

//     @Override
//     public List<BookResponseDTO> searchBooks(Integer categoryId, Integer publisherId) {

//         return bookRepository.findAll()
//                 .stream()
//                 .filter(b -> categoryId == null || b.getCategory().getCatId().equals(categoryId))
//                 .filter(b -> publisherId == null || b.getPublisher().getPublisherId().equals(publisherId))
//                 .map(this::mapToDTO)
//                 .collect(Collectors.toList());
//     }
// }