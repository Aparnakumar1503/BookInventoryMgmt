package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;
import com.sprint.BookInventoryMgmt.BookMgmt.Service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Publisher get(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Publisher create(@RequestBody Publisher publisher) {
        return service.create(publisher);
    }

    @PutMapping("/{id}")
    public Publisher update(@PathVariable Integer id, @RequestBody Publisher publisher) {
        return service.update(id, publisher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}