package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.StateResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IStateService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes state reference endpoints independently because they support publisher validation and display.
 */
@RestController
public class StateController {

    private final IStateService stateService;

    public StateController(IStateService stateService) {
        this.stateService = stateService;
    }

    /**
     * Returns all states.
     */
    @GetMapping("/api/v1/states")
    public ResponseStructure<PaginatedResponse<StateResponseDTO>> getAllStates(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "States fetched successfully",
                PaginationUtils.paginate(stateService.getAllStates(), page, size)
        );
    }

    /**
     * Returns one state by code.
     */
    @GetMapping("/api/v1/states/{code}")
    public ResponseStructure<StateResponseDTO> getStateByCode(@PathVariable String code) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "State fetched successfully",
                stateService.getStateByCode(code)
        );
    }
}
