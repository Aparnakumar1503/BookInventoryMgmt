package com.sprint.BookInventoryMgmt.OrderMgmt.Entity;


import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

   // @NotNull
    private int userId;

    private LocalDateTime orderDate = LocalDateTime.now();

   // @NotNull
    private Double totalAmount;

    //@NotBlank
    private String status;

    // getters and setters
}