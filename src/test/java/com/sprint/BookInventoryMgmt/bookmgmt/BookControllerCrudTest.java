package com.sprint.BookInventoryMgmt.bookmgmt;


//
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
class BookControllerCrudTest {

//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private PublisherRepository publisherRepository;
//
//    @BeforeEach
//    void setup() {
//        bookRepository.deleteAll();
//        publisherRepository.deleteAll();
//        categoryRepository.deleteAll();
//
//        Category category = new Category();
//        category.setCatId(1);
//        category.setCatDescription("Fiction");
//        categoryRepository.save(category);
//
//        Publisher publisher = new Publisher();
//        publisher.setPublisherId(1);
//        publisher.setName("Test Publisher");
//        publisherRepository.save(publisher);
//
//        Book book = new Book();
//        book.setIsbn("ISBN123");
//        book.setTitle("Test Book");
//        book.setDescription("Test Desc");
//        book.setEdition("1st");
//        book.setCategory(category);
//        book.setPublisher(publisher);
//        bookRepository.save(book);
//    }
//
//    @Test
//    void testFindById() {
//        Optional<Book> result = bookRepository.findById("ISBN123");
//        assertThat(result).isPresent();
//        assertThat(result.get().getTitle()).isEqualTo("Test Book");
//    }
//
//    @Test
//    void testFindAll() {
//        List<Book> books = bookRepository.findAll();
//        assertThat(books).hasSize(1);
//    }
//
//    @Test
//    void testSearchByTitle() {
//        List<Book> result = bookRepository.searchByTitle("Test");
//        assertThat(result).isNotEmpty();
//    }
//
//    @Test
//    void testFindByCategoryId() {
//        List<Book> result = bookRepository.findByCategoryId(1);
//        assertThat(result).isNotEmpty();
//    }
//
//    @Test
//    void testFindByPublisherId() {
//        List<Book> result = bookRepository.findByPublisherId(1);
//        assertThat(result).isNotEmpty();
//    }
//
//    @Test
//    void testFindByCategoryIdAndPublisherId() {
//        List<Book> result = bookRepository.findByCategoryIdAndPublisherId(1, 1);
//        assertThat(result).isNotEmpty();
//    }
//
//    @Test
//    void testExistsById() {
//        assertThat(bookRepository.existsById("ISBN123")).isTrue();
//    }
//
//    @Test
//    void testUpdateBook() {
//        Book book = bookRepository.findById("ISBN123").get();
//        book.setTitle("Updated Title");
//        bookRepository.save(book);
//        assertThat(bookRepository.findById("ISBN123").get().getTitle()).isEqualTo("Updated Title");
//    }
//
//    @Test
//    void testDeleteBook() {
//        bookRepository.deleteById("ISBN123");
//        assertThat(bookRepository.findById("ISBN123")).isEmpty();
//    }
//
//    @Test
//    void testSaveNewBook() {
//        Book book = new Book();
//        book.setIsbn("ISBN999");
//        book.setTitle("New Book");
//        book.setCategory(categoryRepository.findById(1).get());
//        book.setPublisher(publisherRepository.findById(1).get());
//        bookRepository.save(book);
//        assertThat(bookRepository.findById("ISBN999")).isPresent();
//    }
}