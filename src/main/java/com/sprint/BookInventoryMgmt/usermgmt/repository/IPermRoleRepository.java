package com.sprint.BookInventoryMgmt.usermgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.BookInventoryMgmt.usermgmt.entity.PermRole;
@Repository
public interface IPermRoleRepository extends JpaRepository<PermRole, Integer> {

}
