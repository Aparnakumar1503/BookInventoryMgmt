package com.sprint.BookInventoryMgmt.userMgmt.service;

import com.sprint.BookInventoryMgmt.userMgmt.entity.PermRole;

import java.util.List;

public interface IPermRoleService {
    PermRole createRole(PermRole role);

    List<PermRole> getAllRoles();

    PermRole getRoleById(Integer id);

    void deleteRole(Integer id);
}
