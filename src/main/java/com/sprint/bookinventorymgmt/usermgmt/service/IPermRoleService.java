package com.sprint.bookinventorymgmt.usermgmt.service;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;

import java.util.List;

public interface IPermRoleService {
    PermRole createRole(PermRole role);

    List<PermRole> getAllRoles();

    PermRole getRoleById(Integer id);

    void deleteRole(Integer id);
}
