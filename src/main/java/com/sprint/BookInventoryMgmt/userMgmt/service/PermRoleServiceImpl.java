package com.sprint.BookInventoryMgmt.userMgmt.service;

import com.sprint.BookInventoryMgmt.userMgmt.entity.PermRole;
import com.sprint.BookInventoryMgmt.userMgmt.exceptions.UserNotFoundException;
import com.sprint.BookInventoryMgmt.userMgmt.repository.IPermRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermRoleServiceImpl implements IPermRoleService {
    @Autowired
    private IPermRoleRepository roleRepository;

    @Override
    public PermRole createRole(PermRole role) {
        return roleRepository.save(role);
    }

    @Override
    public List<PermRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public PermRole getRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Role not found with id: " + id));
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
