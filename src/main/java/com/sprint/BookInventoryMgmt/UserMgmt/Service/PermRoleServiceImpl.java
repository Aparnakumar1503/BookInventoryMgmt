package com.sprint.BookInventoryMgmt.usermgmt.service;

import com.sprint.BookInventoryMgmt.usermgmt.entity.PermRole;
import com.sprint.BookInventoryMgmt.usermgmt.exceptions.UserNotFoundException;
import com.sprint.BookInventoryMgmt.usermgmt.repository.IPermRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
