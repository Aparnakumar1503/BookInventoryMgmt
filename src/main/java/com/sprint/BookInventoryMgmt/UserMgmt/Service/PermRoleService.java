package com.sprint.BookInventoryMgmt.UserMgmt.Service;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;

import java.util.List;

public interface PermRoleService {
    PermRole createRole(PermRole role);

    List<PermRole> getAllRoles();

    PermRole getRoleById(Integer id);

    void deleteRole(Integer id);
}
