package com.sprint.BookInventoryMgmt.UserMgmt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;

public interface PermRoleRepository extends JpaRepository<PermRole, Integer> {

}
