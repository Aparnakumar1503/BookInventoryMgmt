package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;
import java.util.List;

public interface PublisherService {
    List<Publisher> getAll();
    Publisher getById(Integer id);
    Publisher create(Publisher publisher);
    Publisher update(Integer id, Publisher publisher);
    void delete(Integer id);
}