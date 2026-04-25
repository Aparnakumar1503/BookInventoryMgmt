package com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto;
import lombok.Data;

// DTO class used to send shopping cart response data from backend to client
@Data
// Lombok annotation that auto-generates getters, setters, toString, equals, and hashCode
public class ShoppingCartResponseDTO {

    // Stores the user ID associated with the cart response
    private Integer userId;

    // Stores the ISBN of the book added to the cart
    private String isbn;

    // Default constructor required for framework object creation and serialization
    public ShoppingCartResponseDTO() {
    }

    // Getter method to retrieve ISBN value from response object
    public String getIsbn() {
        return isbn;
    }

    // Setter method to assign ISBN value while building response object
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Getter method to retrieve userId value from response object
    public Integer getUserId() {
        return userId;
    }

    // Setter method to assign userId value while building response object
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Parameterized constructor used to create response object with values
    public ShoppingCartResponseDTO(String isbn, Integer userId) {
        this.isbn = isbn;
        this.userId = userId;
    }
}