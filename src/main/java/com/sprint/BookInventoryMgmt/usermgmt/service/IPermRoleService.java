package com.sprint.BookInventoryMgmt.usermgmt.service;

import com.sprint.BookInventoryMgmt.usermgmt.dto.requestdto.PermRoleRequestDTO;
import com.sprint.BookInventoryMgmt.usermgmt.dto.responsedto.PermRoleResponseDTO;
import com.sprint.BookInventoryMgmt.usermgmt.entity.PermRole;

import java.util.List;

public interface IPermRoleService {
    PermRoleResponseDTO addRole(PermRoleRequestDTO requestDTO);
    List<PermRoleResponseDTO> getAllRoles();
    PermRoleResponseDTO getRoleById(Integer roleNumber);
    PermRoleResponseDTO updateRole(Integer roleNumber, PermRoleRequestDTO requestDTO);
    String deleteRole(Integer roleNumber);
    PermRoleResponseDTO getRoleByName(String permRole);
    List<PermRoleResponseDTO> searchByPermRole(String keyword);
    Long countAllRoles();
}
