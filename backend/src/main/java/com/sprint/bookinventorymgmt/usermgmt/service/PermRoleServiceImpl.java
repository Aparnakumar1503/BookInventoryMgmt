package com.sprint.bookinventorymgmt.usermgmt.service;

import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.PermRoleRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.PermRoleResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.repository.IPermRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PermRoleServiceImpl implements IPermRoleService {

    @Autowired
    private  IPermRoleRepository repo;

    @Override
    public PermRoleResponseDTO addRole(PermRoleRequestDTO dto) {
        PermRole entity = new PermRole();
        entity.setPermRole(dto.getPermRole());

        PermRole saved = repo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<PermRoleResponseDTO> getAllRoles() {
        List<PermRole> roles = repo.findAll();
        List<PermRoleResponseDTO> response = new ArrayList<>();

        for (PermRole role : roles) {
            response.add(mapToDTO(role));
        }

        return response;
    }

    @Override
    public PermRoleResponseDTO getRoleById(Integer roleNumber) {
        PermRole role = repo.findById(roleNumber)
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + roleNumber));
        return mapToDTO(role);
    }

    @Override
    public PermRoleResponseDTO updateRole(Integer roleNumber, PermRoleRequestDTO dto) {
        PermRole role = repo.findById(roleNumber)
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + roleNumber));

        role.setPermRole(dto.getPermRole());
        PermRole updated = repo.save(role);
        return mapToDTO(updated);
    }

    @Override
    public String deleteRole(Integer roleNumber) {
        PermRole role = repo.findById(roleNumber)
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + roleNumber));
        repo.delete(role);
        return "Role deleted successfully with id: " + roleNumber;
    }

    @Override
    public PermRoleResponseDTO getRoleByName(String permRole) {
        PermRole role = repo.findByPermRole(permRole)
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with name: " + permRole));
        return mapToDTO(role);
    }

    @Override
    public List<PermRoleResponseDTO> searchByPermRole(String keyword) {
        List<PermRole> roles = repo.searchByPermRole(keyword);
        if (roles.isEmpty()) {
            throw new PermRoleNotFoundException("No roles found with keyword: " + keyword);
        }

        List<PermRoleResponseDTO> response = new ArrayList<>();
        for (PermRole role : roles) {
            response.add(mapToDTO(role));
        }

        return response;
    }

    @Override
    public Long countAllRoles() {
        return repo.countAllRoles();
    }

    private PermRoleResponseDTO mapToDTO(PermRole entity) {
        PermRoleResponseDTO dto = new PermRoleResponseDTO();
        dto.setRoleNumber(entity.getRoleNumber());
        dto.setPermRole(entity.getPermRole());
        return dto;
    }
}
