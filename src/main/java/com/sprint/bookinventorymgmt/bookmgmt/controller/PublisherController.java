package com.sprint.BookInventoryMgmt.bookmgmt.controller;

import com.sprint.BookInventoryMgmt.bookmgmt.dto.request.PublisherRequestDTO;
import com.sprint.BookInventoryMgmt.bookmgmt.dto.response.PublisherResponseDTO;
import com.sprint.BookInventoryMgmt.bookmgmt.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherResponseDTO> getAll() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public PublisherResponseDTO getById(@PathVariable Integer id) {
        return publisherService.getPublisherById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherResponseDTO create(@Valid @RequestBody PublisherRequestDTO dto) {
        return publisherService.createPublisher(dto);
    }

    @PutMapping("/{id}")
    public PublisherResponseDTO update(@PathVariable Integer id,
                                       @Valid @RequestBody PublisherRequestDTO dto) {
        return publisherService.updatePublisher(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        publisherService.deletePublisher(id);
    }
}