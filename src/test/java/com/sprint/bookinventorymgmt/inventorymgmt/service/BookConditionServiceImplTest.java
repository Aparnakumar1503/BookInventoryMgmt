package com.sprint.bookinventorymgmt.inventorymgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.BookCondition;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.*;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import com.sprint.bookinventorymgmt.inventorymgmt.repository.IBookConditionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookConditionServiceImplTest {

    @Mock
    IBookConditionRepository repository;

    @InjectMocks
    BookConditionServiceImpl service;


    // POSITIVE 5
    @Test
    void saveBookConditionSuccess() {

        BookCondition bc=
                new BookCondition(1,"Good","Good Book",100.0);

        when(repository.save(any()))
                .thenReturn(bc);

        assertEquals(
                "Good",
                service.saveBookCondition(bc)
                        .getDescription()
        );
    }

    // POSITIVE 6
    @Test
    void getByIdSuccess() {

        BookCondition bc=
                new BookCondition(1,"Good","Good",100.0);

        when(repository.findById(1))
                .thenReturn(Optional.of(bc));

        assertEquals(
                1,
                service.getById(1).getRanks()
        );
    }

    // POSITIVE 7
    @Test
    void updateSuccess() {

        BookCondition bc=
                new BookCondition(1,"Good","Good",100.0);

        when(repository.findById(1))
                .thenReturn(Optional.of(bc));

        when(repository.save(any()))
                .thenReturn(bc);

        ResponseStructure<BookCondition> result=
                service.updateBookCondition(1,bc);

        assertEquals(
                200,
                result.getStatusCode()
        );
    }

    // POSITIVE 8
    @Test
    void getAllSuccess() {

        when(repository.findAll())
                .thenReturn(java.util.List.of(
                        new BookCondition(1,"Good","Good",100.0)
                ));

        assertEquals(
                1,
                service.getAllBookConditions().size()
        );
    }


    // NEGATIVE 5
    @Test
    void invalidBookCondition() {

        BookCondition bc=
                new BookCondition();

        assertThrows(
                InvalidInventoryDataException.class,
                ()->service.saveBookCondition(bc)
        );
    }

    // NEGATIVE 6
    @Test
    void bookConditionNotFound() {

        when(repository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                BookConditionNotFoundException.class,
                ()->service.getById(1)
        );
    }

    // NEGATIVE 7
    @Test
    void databaseFailure() {

        BookCondition bc=
                new BookCondition(1,"Good","Good",100.0);

        when(repository.save(any()))
                .thenThrow(RuntimeException.class);

        assertThrows(
                DatabaseOperationException.class,
                ()->service.saveBookCondition(bc)
        );
    }

}