package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.PublisherRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.PublisherResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IPublisherService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages publisher CRUD endpoints separately so book catalog logic stays focused on books.
 */
@RestController
public class PublisherController {

    private final IPublisherService publisherService;

    public PublisherController(IPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    /**
     * Returns all publishers.
     */
    @GetMapping("/api/v1/publishers")
    public ResponseStructure<PaginatedResponse<PublisherResponseDTO>> getAllPublishers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Publishers fetched successfully",
                PaginationUtils.paginate(publisherService.getAllPublishers(), page, size)
        );
    }

    /**
     * Returns one publisher by identifier.
     */
    @GetMapping("/api/v1/publishers/{publisherId}")
    public ResponseStructure<PublisherResponseDTO> getPublisherById(@PathVariable Integer publisherId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Publisher fetched successfully",
                publisherService.getPublisherById(publisherId)
        );
    }

    /**
     * Creates a publisher after request validation completes.
     */
    @PostMapping("/api/v1/publishers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<PublisherResponseDTO> createPublisher(@Valid @RequestBody PublisherRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Publisher created successfully",
                publisherService.createPublisher(dto)
        );
    }

    /**
     * Updates a publisher by identifier.
     */
    @PutMapping("/api/v1/publishers/{publisherId}")
    public ResponseStructure<PublisherResponseDTO> updatePublisher(
            @PathVariable Integer publisherId,
            @Valid @RequestBody PublisherRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Publisher updated successfully",
                publisherService.updatePublisher(publisherId, dto)
        );
    }

    /**
     * Deletes a publisher by identifier.
     */
    @DeleteMapping("/api/v1/publishers/{publisherId}")
    public ResponseStructure<String> deletePublisher(@PathVariable Integer publisherId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Publisher deleted successfully",
                publisherService.deletePublisher(publisherId)
        );
    }
}
