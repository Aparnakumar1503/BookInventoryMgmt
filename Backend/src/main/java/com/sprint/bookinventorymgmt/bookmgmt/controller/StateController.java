package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.StateResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IStateService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
@Tag(name = "State APIs", description = "Read-only State APIs")
@Validated
public class StateController {

   @Autowired
    private  IStateService IStateService;

    public StateController(IStateService IStateService) {
        this.IStateService = IStateService;
    }

    @Operation(summary = "Get all states")
    @GetMapping
    public ResponseStructure<PaginatedResponse<StateResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {

        return ResponseBuilder.success(
                200,
                "States fetched successfully",
                PaginationUtils.paginate(IStateService.getAllStates(), page, size)
        );
    }

    @Operation(summary = "Get state by code")
    @GetMapping("/{code}")
    public ResponseStructure<StateResponseDTO> getByCode(
            @PathVariable @NotBlank(message = "State code cannot be blank") @Size(min = 2, max = 10, message = "State code must be between 2 and 10 characters") String code) {

        return ResponseBuilder.success(
                200,
                "State fetched successfully",
                IStateService.getStateByCode(code)
        );
    }
}
