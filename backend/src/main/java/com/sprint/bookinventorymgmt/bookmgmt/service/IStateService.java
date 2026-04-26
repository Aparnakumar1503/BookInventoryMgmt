package com.sprint.bookinventorymgmt.bookmgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.StateResponseDTO;

import java.util.List;

public interface IStateService {

    List<StateResponseDTO> getAllStates();

    StateResponseDTO getStateByCode(String code);
}