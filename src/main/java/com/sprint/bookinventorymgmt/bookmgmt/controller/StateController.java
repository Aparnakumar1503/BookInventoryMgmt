package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.StateResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IStateService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
@Tag(name = "State APIs", description = "Read-only State APIs")
public class StateController {

    private final IStateService IStateService;

    public StateController(IStateService IStateService) {
        this.IStateService = IStateService;
    }

    @Operation(summary = "Get all states")
    @GetMapping
    public ResponseStructure<List<StateResponseDTO>> getAll() {

        return ResponseBuilder.success(
                200,
                "States fetched successfully",
                IStateService.getAllStates()
        );
    }

    @Operation(summary = "Get state by code")
    @GetMapping("/{code}")
    public ResponseStructure<StateResponseDTO> getByCode(@PathVariable String code) {

        return ResponseBuilder.success(
                200,
                "State fetched successfully",
                IStateService.getStateByCode(code)
        );
    }
}