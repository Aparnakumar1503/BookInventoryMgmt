package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repository;

    public PublisherServiceImpl(PublisherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Publisher> getAll() {
        return repository.findAll();
    }

    @Override
    public Publisher getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found: " + id));
    }

    @Override
    public Publisher create(Publisher publisher) {
        return repository.save(publisher);
    }

    @Override
    public Publisher update(Integer id, Publisher publisher) {
        Publisher existing = getById(id);
        existing.setName(publisher.getName());
        existing.setCity(publisher.getCity());
        existing.setState(publisher.getState());
        return repository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(getById(id));
    }
}