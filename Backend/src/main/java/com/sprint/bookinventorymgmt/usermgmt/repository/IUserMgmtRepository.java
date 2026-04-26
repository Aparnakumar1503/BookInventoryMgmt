package com.sprint.bookinventorymgmt.usermgmt.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sprint.bookinventorymgmt.usermgmt.entity.User;
@Repository
public interface IUserMgmtRepository extends JpaRepository <User,Integer>{
	Optional<User> findByUserName(String userName);
	Optional<User> findByUserNameIgnoreCase(String userName);
	List<User> findByLastName(String lastName);
	boolean existsByUserName(String userName);
	boolean existsByUserNameIgnoreCase(String userName);

	// Custom Query 1 — find user by username and password (login)
	@Query("SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password")
	Optional<User> findByUserNameAndPassword(@Param("userName") String userName,
											 @Param("password") String password);

	// Custom Query 2 — find all users by role number
	@Query("SELECT u FROM User u WHERE u.role.roleNumber = :roleNumber")
	List<User> findByRoleNumber(@Param("roleNumber") Integer roleNumber);

	// Custom Query 3 — update password by userId
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = :password WHERE u.userId = :userId")
	int updatePassword(@Param("userId") Integer userId,
					   @Param("password") String password);
}
