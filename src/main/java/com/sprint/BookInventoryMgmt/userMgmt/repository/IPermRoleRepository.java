package com.sprint.BookInventoryMgmt.userMgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.BookInventoryMgmt.userMgmt.entity.PermRole;
@Repository
public interface IPermRoleRepository extends JpaRepository<PermRole, Integer> {

}
