package com.sprint.BookInventoryMgmt.UserMgmt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;
@Repository
public interface UserMgmtRepository extends JpaRepository <User,Integer>{
	 Optional<User> findByUserName(String userName);
}
