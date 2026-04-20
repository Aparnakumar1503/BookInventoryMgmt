package com.sprint.BookInventoryMgmt.bookmgmt.repository;


import com.sprint.BookInventoryMgmt.bookmgmt.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}