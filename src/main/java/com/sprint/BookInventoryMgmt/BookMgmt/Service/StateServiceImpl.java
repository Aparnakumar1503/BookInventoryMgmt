package com.sprint.bookinventorymgmt.bookmgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.StateResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.entity.State;
import com.sprint.bookinventorymgmt.bookmgmt.exception.DataNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exception.InvalidInputException;
import com.sprint.bookinventorymgmt.bookmgmt.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IStateServiceImpl implements IStateService {

    private final StateRepository stateRepository;

    public IStateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<StateResponseDTO> getAllStates() {

        List<State> states = stateRepository.findAll();

        if (states.isEmpty()) {
            throw new DataNotFoundException("No states available in system");
        }

        return states.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public StateResponseDTO getStateByCode(String code) {

        if (code == null || code.trim().isEmpty()) {
            throw new InvalidInputException("State code cannot be null or empty");
        }

        State state = stateRepository.findById(code)
                .orElseThrow(() ->
                        new DataNotFoundException("State not found with code: " + code));

        return mapToDTO(state);
    }

    private StateResponseDTO mapToDTO(State state) {
        return new StateResponseDTO(
                state.getStateCode(),
                state.getStateName()
        );
    }
}