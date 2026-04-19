package com.sprint.bookinventorymgmt.usermgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
@Repository
public interface IPermRoleRepository extends JpaRepository<PermRole, Integer> {

}
