package com.sprint.BookInventoryMgmt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BookInventoryMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookInventoryMgmtApplication.class, args);

		System.out.println("Order Module");
		System.out.println("Inventory Module");
		System.out.println("Reviewer Module Added");
		System.out.println("hello");
		System.out.println("Added Book Module");
	}

}