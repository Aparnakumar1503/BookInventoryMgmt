package com.sprint.bookinventorymgmt.usermgmt.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPermRoleRepository extends JpaRepository<PermRole, Integer> {
    Optional<PermRole> findByPermRole(String permRole);
    boolean existsByPermRole(String permRole);

    // Custom Query 1 — find all roles containing keyword
    @Query("SELECT p FROM PermRole p WHERE LOWER(p.permRole) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<PermRole> searchByPermRole(@Param("keyword") String keyword);

    // Custom Query 2 — count total roles
    @Query("SELECT COUNT(p) FROM PermRole p")
    Long countAllRoles();

    // Custom Query 3 — update role name by roleNumber
    @Modifying
    @Transactional
    @Query("UPDATE PermRole p SET p.permRole = :permRole WHERE p.roleNumber = :roleNumber")
    int updatePermRole(@Param("roleNumber") Integer roleNumber,
                       @Param("permRole") String permRole);
}
