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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@Tag(name = "Publisher APIs", description = "CRUD operations for Publishers")
public class PublisherController {

    @Autowired
    private IPublisherService IPublisherService;

    @Operation(summary = "Get all publishers")
    @GetMapping
    public ResponseStructure<PaginatedResponse<PublisherResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return ResponseBuilder.success(
                200,
                "Publishers fetched successfully",
                PaginationUtils.paginate(IPublisherService.getAllPublishers(), page, size)
        );
    }

    @Operation(summary = "Get publisher by ID")
    @GetMapping("/{id}")
    public ResponseStructure<PublisherResponseDTO> getById(@PathVariable Integer id) {

        return ResponseBuilder.success(
                200,
                "Publisher fetched successfully",
                IPublisherService.getPublisherById(id)
        );
    }

    @Operation(summary = "Create publisher")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<PublisherResponseDTO> create(@Valid @RequestBody PublisherRequestDTO dto) {

        return ResponseBuilder.success(
                201,
                "Publisher created successfully",
                IPublisherService.createPublisher(dto)
        );
    }

    @Operation(summary = "Update publisher")
    @PutMapping("/{id}")
    public ResponseStructure<PublisherResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PublisherRequestDTO dto) {

        return ResponseBuilder.success(
                200,
                "Publisher updated successfully",
                IPublisherService.updatePublisher(id, dto)
        );
    }

    @Operation(summary = "Delete publisher")
    @DeleteMapping("/{id}")
    public ResponseStructure<String> delete(@PathVariable Integer id) {

        String response = IPublisherService.deletePublisher(id);

        return ResponseBuilder.success(
                200,
                "Publisher deleted successfully",
                response
        );
    }
}
