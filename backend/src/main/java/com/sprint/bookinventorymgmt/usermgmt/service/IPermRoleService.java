package com.sprint.bookinventorymgmt.usermgmt.service;

import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.PermRoleRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.PermRoleResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;

import java.util.List;

public interface IPermRoleService {
    PermRoleResponseDTO addRole(PermRoleRequestDTO requestDTO);
    List<PermRoleResponseDTO> getAllRoles();
    PermRoleResponseDTO getRoleById(Integer roleNumber);
    String deleteRole(Integer roleNumber);
    List<PermRoleResponseDTO> searchByPermRole(String keyword);
}
