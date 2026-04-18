package com.sprint.BookInventoryMgmt.UserMgmt.Service;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;
import com.sprint.BookInventoryMgmt.UserMgmt.Exception.UserNotFoundException;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.PermRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PermRoleServiceImpl implements PermRoleService{
    @Autowired
    private PermRoleRepository roleRepository;

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
