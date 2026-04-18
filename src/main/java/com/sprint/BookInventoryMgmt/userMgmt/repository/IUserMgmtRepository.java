package com.sprint.BookInventoryMgmt.userMgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.BookInventoryMgmt.userMgmt.entity.User;
@Repository
public interface IUserMgmtRepository extends JpaRepository <User,Integer>{
	 Optional<User> findByUserName(String userName);
}
