package com.sprint.BookInventoryMgmt.usermgmt.service;

import com.sprint.BookInventoryMgmt.usermgmt.entity.PermRole;

import java.util.List;

public interface PermRoleService {
    PermRole createRole(PermRole role);

    List<PermRole> getAllRoles();

    PermRole getRoleById(Integer id);

    void deleteRole(Integer id);
}
