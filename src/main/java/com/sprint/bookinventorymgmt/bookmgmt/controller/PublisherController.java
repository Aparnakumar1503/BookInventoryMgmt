package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.PublisherRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.PublisherResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IPublisherService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@Tag(name = "Publisher APIs", description = "CRUD operations for Publishers")
public class PublisherController {

    private final IPublisherService publisherService;

    public PublisherController(IPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Operation(summary = "Get all publishers")
    @GetMapping
    public ResponseStructure<PaginatedResponse<PublisherResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return ResponseBuilder.success(
                200,
                "Publishers fetched successfully",
                PaginationUtils.paginate(publisherService.getAllPublishers(), page, size)
        );
    }

    @Operation(summary = "Get publisher by ID")
    @GetMapping("/{publisherId}")
    public ResponseStructure<PublisherResponseDTO> getById(@PathVariable Integer publisherId) {

        return ResponseBuilder.success(
                200,
                "Publisher fetched successfully",
                publisherService.getPublisherById(publisherId)
        );
    }

    @Operation(summary = "Create publisher")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<PublisherResponseDTO> create(@Valid @RequestBody PublisherRequestDTO dto) {

        return ResponseBuilder.success(
                201,
                "Publisher created successfully",
                publisherService.createPublisher(dto)
        );
    }

    @Operation(summary = "Update publisher")
    @PutMapping("/{publisherId}")
    public ResponseStructure<PublisherResponseDTO> update(
            @PathVariable Integer publisherId,
            @Valid @RequestBody PublisherRequestDTO dto) {

        return ResponseBuilder.success(
                200,
                "Publisher updated successfully",
                publisherService.updatePublisher(publisherId, dto)
        );
    }

    @Operation(summary = "Delete publisher")
    @DeleteMapping("/{publisherId}")
    public ResponseStructure<String> delete(@PathVariable Integer publisherId) {

        String response = publisherService.deletePublisher(publisherId);

        return ResponseBuilder.success(
                200,
                "Publisher deleted successfully",
                response
        );
    }
}
