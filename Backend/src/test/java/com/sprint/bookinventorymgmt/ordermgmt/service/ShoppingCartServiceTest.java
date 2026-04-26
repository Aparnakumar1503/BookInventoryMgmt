package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
import com.sprint.bookinventorymgmt.bookmgmt.entity.State;
import com.sprint.bookinventorymgmt.bookmgmt.repository.BookRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.CategoryRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.PublisherRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.StateRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookNotAvailableException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.DuplicateCartItemException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.EmptyCartException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShoppingCartServiceTest {

    private static final String VALID_ISBN = "1-111-11111-4";

    @Autowired
    private IShoppingCartService service;
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private StateRepository stateRepository;

    private ShoppingCartRequestDTO request(int userId, String isbn) {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(userId);
        dto.setIsbn(isbn);
        return dto;
    }

    @BeforeEach
    void setUp() {
        shoppingCartRepository.deleteAll();
        bookRepository.deleteAll();
        publisherRepository.deleteAll();
        categoryRepository.deleteAll();
        stateRepository.deleteAll();

        State state = new State();
        state.setStateCode("TS");
        state.setStateName("Test State");
        stateRepository.save(state);

        Category category = new Category(1, "Test Category");
        categoryRepository.save(category);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Publisher");
        publisher.setCity("Test City");
        publisher.setState(state);
        publisherRepository.save(publisher);

        bookRepository.save(new Book(
                VALID_ISBN,
                "Test Book",
                "Test Description",
                "1",
                category,
                publisher
        ));
    }

    @Test
    void addCart_persistsCartItem() {
        ShoppingCartResponseDTO result = service.addCart(request(31, VALID_ISBN));

        assertEquals(31, result.getUserId());
        assertEquals(VALID_ISBN, result.getIsbn());
    }

    @Test
    void getAll_returnsInsertedCartItems() {
        service.addCart(request(32, VALID_ISBN));

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void getByUserId_returnsItemsForSpecificUser() {
        service.addCart(request(33, VALID_ISBN));

        List<ShoppingCartResponseDTO> carts = service.getByUserId(33);

        assertEquals(1, carts.size());
        assertEquals(VALID_ISBN, carts.get(0).getIsbn());
    }

    @Test
    void delete_removesExistingCartItem() {
        service.addCart(request(34, VALID_ISBN));

        String result = service.delete(34, VALID_ISBN);

        assertTrue(result.contains("Cart Deleted Successfully"));
    }

    @Test
    void getAll_edgeCase_returnsNonNullList() {
        assertNotNull(service.getAll());
    }

    @Test
    void addCart_rejectsNullOrIncompleteRequest() {
        assertThrows(EmptyCartException.class, () -> service.addCart(null));
        assertThrows(EmptyCartException.class, () -> service.addCart(new ShoppingCartRequestDTO()));
    }

    @Test
    void addCart_rejectsDuplicateItem() {
        service.addCart(request(35, VALID_ISBN));

        assertThrows(DuplicateCartItemException.class, () -> service.addCart(request(35, VALID_ISBN)));
    }

    @Test
    void addCart_rejectsUnknownIsbn() {
        assertThrows(BookNotAvailableException.class, () -> service.addCart(request(36, "1-111-11111-8")));
    }

    @Test
    void delete_throwsWhenCartItemDoesNotExist() {
        assertThrows(ShoppingCartNotFoundException.class, () -> service.delete(999, "NOTEXIST"));
    }
}
