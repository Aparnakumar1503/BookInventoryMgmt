package com.sprint.BookInventoryMgmt.UserMgmt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;
@Repository
public interface PermRoleRepository extends JpaRepository<PermRole, Integer> {

}
